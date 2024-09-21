package nodomain.sakiika.ranamod.entity.custom;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.target.*;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import nodomain.sakiika.ranamod.entity.ModEntities;
import nodomain.sakiika.ranamod.entity.ai.RanaAttackGoal;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import nodomain.sakiika.ranamod.entity.ai.RanaFollowParentGoal;
import nodomain.sakiika.ranamod.entity.ai.RanaPanicGoal;
import nodomain.sakiika.ranamod.entity.ai.RanaTemptGoal;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.UUID;

public class RanaEntity extends TamableAnimal {
    private static final EntityDataAccessor<Boolean> ATTACKING =
            SynchedEntityData.defineId(RanaEntity.class, EntityDataSerializers.BOOLEAN);

    public RanaEntity(EntityType<? extends TamableAnimal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.setTame(false, false);
    }

    public final AnimationState idleAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;

    public final AnimationState attackAnimationState = new AnimationState();
    public int attackAnimationTimeout = 0;

    public final AnimationState sitAnimationState = new AnimationState();

    @Override
    public void tick() {
        super.tick();

        if(this.level().isClientSide()) {
            setupAnimationStates();
        }
    }

    private void setupAnimationStates() {
        //From Camel.
        if(this.idleAnimationTimeout <= 0) {
            this.idleAnimationTimeout = (this.random.nextInt(2) + 1)*120;
            this.idleAnimationState.start(this.tickCount);
        } else {
            --this.idleAnimationTimeout;
        }

        if(this.isInSittingPose()) {
            sitAnimationState.startIfStopped(this.tickCount);
        } else {
            sitAnimationState.stop();
        }

        if(this.isAttacking() && attackAnimationTimeout <= 0) {
            attackAnimationTimeout = 45; // Length in ticks of animation (1sec = 20ticks)
            attackAnimationState.start(this.tickCount);
        } else {
            --this.attackAnimationTimeout;
        }

        if(!this.isAttacking()) {
            attackAnimationState.stop();
        }
    }

    @Override
    protected void updateWalkAnimation(float pPartialTick) {
        float f;
        if(this.getPose() == Pose.STANDING) {
            f = Math.min(pPartialTick * 6F, 1f);
        } else {
            f = 0f;
        }

        this.walkAnimation.update(f, 0.2f);
    }

    public void setAttacking(boolean attacking) {
        this.entityData.set(ATTACKING, attacking);
    }

    public boolean isAttacking() {
        return this.entityData.get(ATTACKING);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder pBuilder) {
        super.defineSynchedData(pBuilder);
        pBuilder.define(ATTACKING, false);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(1, new RanaPanicGoal(this, 2.0D));
        this.goalSelector.addGoal(2, new SitWhenOrderedToGoal(this));
        this.goalSelector.addGoal(3, new RanaAttackGoal(this, 2.0D, true));
        this.goalSelector.addGoal(4, new FollowOwnerGoal(this, 1.0D, 3.0F, 1.0F));
        this.goalSelector.addGoal(5, new BreedGoal(this, 1.15D));
        this.goalSelector.addGoal(6, new RanaTemptGoal(this, 1.2D, Ingredient.of(Items.MELON_SLICE), false));
        this.goalSelector.addGoal(7, new RanaFollowParentGoal(this, 1.1D));
        this.goalSelector.addGoal(8, new WaterAvoidingRandomStrollGoal(this, 1.1D));
        this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 3f));
        this.goalSelector.addGoal(9, new RandomLookAroundGoal(this));

        // From Wolf.
        this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 10.0D)
                .add(Attributes.FOLLOW_RANGE, 24D)
                .add(Attributes.MOVEMENT_SPEED, 0.25D)
                .add(Attributes.ATTACK_KNOCKBACK, 0.5f)
                .add(Attributes.ATTACK_DAMAGE, 1f);
    }

    // Set Attributes. from wolf
    @Override
    protected void applyTamingSideEffects() {
        if (this.isTame()) {
            Objects.requireNonNull(this.getAttribute(Attributes.MAX_HEALTH)).setBaseValue(20.0D);
            this.setHealth(20.0F);
        } else {
            Objects.requireNonNull(this.getAttribute(Attributes.MAX_HEALTH)).setBaseValue(10.0D);
        }
    }

    /**
     * Called when the entity is attacked.
     * From Wolf.
     */
    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (this.isInvulnerableTo(source)) {
            return false;
        } else {
            if (!this.level().isClientSide) {
                this.setOrderedToSit(false);
            }

            return super.hurt(source, amount);
        }
    }

    @Override
    public InteractionResult mobInteract(Player pPlayer, InteractionHand pHand) {
        //Given honey bottle, return empty bottle and recover its health (If they've been hurt)
        //See cow code handling milk
        //and wolf code handling tame etc
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        if (this.isTame()) {
            //Heal its health when used honey bottle or melon.
            //Only if health is not max value
            if ( (itemstack.is(Items.MELON_SLICE) || itemstack.is(Items.HONEY_BOTTLE)) && this.getHealth() < this.getMaxHealth() ) {
                if (itemstack.is(Items.HONEY_BOTTLE)) {
                    //Play sound. From mobInteract() in cow.
                    pPlayer.playSound(SoundEvents.HONEY_DRINK);
                    //Add empty bottle
                    pPlayer.getInventory().add(Items.GLASS_BOTTLE.getDefaultInstance());
                }
                //Play sound. From mobInteract() in cow.
                else pPlayer.playSound(SoundEvents.GENERIC_EAT);

                //Recover health. From mobInteract() in wolf.
                FoodProperties foodproperties = itemstack.getFoodProperties(this);
                float f = foodproperties != null ? (float)foodproperties.nutrition() : 1.0F;
                this.heal(f);

                //If player is not in creative mode. From mobInteract() in wolf.
                if (!pPlayer.getAbilities().instabuild) {
                    //Decrement item count in hand
                    pPlayer.getItemInHand(pHand).shrink(1);
                }

                //Determine particle speed. from handleEntityEvent() in Fox.
                Vec3 vec3 = (new Vec3(((double) this.random.nextFloat() - 0.5D) * 0.1D, Math.random() * 0.1D + 0.1D, 0.0D)).xRot(-this.getXRot() * ((float) Math.PI / 180F)).yRot(-this.getYRot() * ((float) Math.PI / 180F));
                if (this.getHealth() >= this.getMaxHealth()) {
                    for (int i = 0; i < 5; i++) {
                        //Spawn particle. From spawnSoulSpeedParticle().
                        //addParticleAroundSelf() in AbstractVillager is also helpful.
                        this.level().addParticle(ParticleTypes.SCRAPE, this.getX() + (this.random.nextDouble() - 0.5D) * (double) this.getBbWidth(), this.getY() + 1.7D + (this.random.nextDouble() - 0.5D), this.getZ() + (this.random.nextDouble() - 0.5D) * (double) this.getBbWidth(), vec3.x * -0.2D, 0.1D, vec3.z * -0.2D);
                    }
                } else {
                    for (int i = 0; i < 5; i++) {
                        //Spawn particle. From spawnSoulSpeedParticle().
                        //addParticleAroundSelf() in AbstractVillager is also helpful.
                        this.level().addParticle(ParticleTypes.WAX_ON, this.getX() + (this.random.nextDouble() - 0.5D) * (double) this.getBbWidth(), this.getY() + 1.7D + (this.random.nextDouble() - 0.5D), this.getZ() + (this.random.nextDouble() - 0.5D) * (double) this.getBbWidth(), vec3.x * -0.2D, 0.1D, vec3.z * -0.2D);
                    }
                }

                this.gameEvent(GameEvent.EAT, this);
                return InteractionResult.SUCCESS;
            } /* Handle holding sword */ else if ((itemstack.getItem() instanceof SwordItem || itemstack.getItem() instanceof AxeItem)  &&  !pPlayer.isShiftKeyDown()) {
                //Return sword
                pPlayer.getInventory().add(this.getItemBySlot(EquipmentSlot.MAINHAND));

                //The entity holds item
                this.setItemSlot(EquipmentSlot.MAINHAND, itemstack.copy()); //Give copy of itemstack

                //If player is not in creative mode. From mobInteract() in wolf.
                if (!pPlayer.getAbilities().instabuild) {
                    //Decrement sword count in hand
                    pPlayer.getItemInHand(pHand).shrink(1);
                }

                return InteractionResult.SUCCESS;

            } /* Handle ordering sit, From Wolf. And handle returning sword */ else {

                //If player is sneaking
                if (pPlayer.isShiftKeyDown()) {
                    //Return sword
                    pPlayer.getInventory().add(this.getItemBySlot(EquipmentSlot.MAINHAND));

                    //The entity holds nothing
                    this.setItemInHand(InteractionHand.MAIN_HAND, ItemStack.EMPTY);

                    return InteractionResult.SUCCESS;
                }

                InteractionResult interactionresult = super.mobInteract(pPlayer, pHand);
                if ((!interactionresult.consumesAction() || this.isBaby()) && this.isOwnedBy(pPlayer)) {
                    this.setOrderedToSit(!this.isOrderedToSit());
                    //this.setInSittingPose(!this.isOrderedToSit());
                    this.jumping = false;
                    this.navigation.stop();
                    this.setTarget((LivingEntity)null);
                    return InteractionResult.SUCCESS;
                } else {
                    return interactionresult;
                }
            }

        } /* Handling Tame. From Wolf */ else if (itemstack.is(Items.HONEY_BOTTLE)) {
            //If player is not in creative mode. From mobInteract() in wolf.
            if (!pPlayer.getAbilities().instabuild) {
                //Decrement honey bottles count in hand
                pPlayer.getItemInHand(pHand).shrink(1);
            }
            //Add empty bottle
            pPlayer.getInventory().add(Items.GLASS_BOTTLE.getDefaultInstance());

            if (this.random.nextInt(3) == 0 && !net.neoforged.neoforge.event.EventHooks.onAnimalTame(this, pPlayer)) {
                this.tame(pPlayer);
                this.navigation.stop();
                this.setTarget(null);
                this.setOrderedToSit(true);
                //this.setInSittingPose(true);
                this.level().broadcastEntityEvent(this, (byte)7);
            } else {
                this.level().broadcastEntityEvent(this, (byte)6);
            }

            return InteractionResult.SUCCESS;

        } else {
            //Breed and growing handling in super
            return super.mobInteract(pPlayer, pHand);
        }
    }

    // Give birth to baby. From Wolf.
    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) {
        RanaEntity rana = ModEntities.RANA.get().create(pLevel);
        if (rana != null) {
            if (this.isTame()) {
                rana.setOwnerUUID(this.getOwnerUUID());
                rana.setTame(true, true);
            }
        }

        return rana;
    }

    @Override
    public boolean isFood(ItemStack pStack) {
        return pStack.is(Items.MELON_SLICE);
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.DOLPHIN_PLAY;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return SoundEvents.DOLPHIN_HURT;
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.DOLPHIN_DEATH;
    }


     //Returns {@code true} if the mob is currently able to mate with the specified mob.
     //From Wolf
    public boolean canMate(@NotNull Animal pOtherAnimal) {
        if (pOtherAnimal == this) {
            return false;
        /*} else if (!this.isTame()) {
            return false;*/
        } else if (!(pOtherAnimal instanceof RanaEntity rana)) {
            return false;
        } else {
            /*if (!rana.isTame()) {
                return false;*/
            //If not both of ranas are tamed or not both of them not tamed, can't falling love and return false
            if ( !( (this.isTame() && rana.isTame() ) || (!this.isTame() && !rana.isTame() ) ) ) {
                return false;
            } else if (rana.isInSittingPose()) {
                return false;
            } else {
                return this.isInLove() && rana.isInLove();
            }
        }
    }
}
