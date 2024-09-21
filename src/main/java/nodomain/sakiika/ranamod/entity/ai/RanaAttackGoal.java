package nodomain.sakiika.ranamod.entity.ai;

import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.monster.Creeper;
import nodomain.sakiika.ranamod.entity.custom.RanaEntity;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;

import java.util.Objects;

// TODO: Won't work in 1.10.2+?
public class RanaAttackGoal extends MeleeAttackGoal {
    private final RanaEntity entity;
    private final int INITIAL_ATTACK_DELAY = 21; //Attack begins (1 + 1/20)sec after animation started
    private final int INITIAL_TICKS_UNTIL_NEXT_ATTACK = 24; //After gave damage, it takes (1 + 4/20)sec to back to initial pose
    private int attackDelay = INITIAL_ATTACK_DELAY;
    private int ticksUntilNextAttack = INITIAL_TICKS_UNTIL_NEXT_ATTACK;
    private boolean shouldCountTillNextAttack = false;

    public RanaAttackGoal(PathfinderMob pMob, double pSpeedModifier, boolean pFollowingTargetEvenIfNotSeen) {
        super(pMob, pSpeedModifier, pFollowingTargetEvenIfNotSeen);
        entity = ((RanaEntity) pMob);
    }

    @Override
    public void start() {
        super.start();
        attackDelay = INITIAL_ATTACK_DELAY;
        ticksUntilNextAttack = INITIAL_TICKS_UNTIL_NEXT_ATTACK;
    }

    @Override
    protected void checkAndPerformAttack(LivingEntity pEnemy, double pDistToEnemySqr) {
        if (isEnemyWithinAttackDistance(pEnemy, pDistToEnemySqr)) {
            shouldCountTillNextAttack = true;

            if(isTimeToStartAttackAnimation()) {
                entity.setAttacking(true);
            }

            if(isTimeToAttack()) {
                this.mob.getLookControl().setLookAt(pEnemy.getX(), pEnemy.getEyeY(), pEnemy.getZ());
                performAttack(pEnemy);
            }
        } else {
            resetAttackCooldown();
            shouldCountTillNextAttack = false;
            entity.setAttacking(false);
            entity.attackAnimationTimeout = 0;
        }
    }

    private boolean isEnemyWithinAttackDistance(LivingEntity pEnemy, double pDistToEnemySqr) {
        return pDistToEnemySqr <= this.getAttackReachSqr(pEnemy);
    }

    //Overrode and slightly improved super method to allow rana easyly attack enemy
    @Override
    protected double getAttackReachSqr(LivingEntity pAttackTarget) {
        return (double)(this.mob.getBbWidth() * 4.0F * this.mob.getBbWidth() * 4.0F + pAttackTarget.getBbWidth());
    }

    protected void resetAttackCooldown() {
        this.ticksUntilNextAttack = this.adjustedTickDelay(INITIAL_ATTACK_DELAY + INITIAL_TICKS_UNTIL_NEXT_ATTACK + 1);
    }

    protected boolean isTimeToAttack() {
        return this.ticksUntilNextAttack <= 0;
    }

    protected boolean isTimeToStartAttackAnimation() {
        return this.ticksUntilNextAttack <= attackDelay;
    }

    protected int getTicksUntilNextAttack() {
        return this.ticksUntilNextAttack;
    }


    protected void performAttack(LivingEntity pEnemy) {
        this.resetAttackCooldown();
        this.mob.swing(InteractionHand.MAIN_HAND);
        this.mob.doHurtTarget(pEnemy);
    }

    @Override
    public void tick() {
        //Countdown ticksUntilNextAttack
        super.tick();
        if(shouldCountTillNextAttack) {
            this.ticksUntilNextAttack = Math.max(this.ticksUntilNextAttack - 1, 0);
        }
    }

    @Override
    public void stop() {
        entity.setAttacking(false);
        super.stop();
    }

    public boolean canUse() {
        if (this.mob.getTarget() instanceof RanaEntity) return false; // Won't attack rana
        else if (this.mob instanceof TamableAnimal tamableThis  &&  this.mob.getTarget() instanceof TamableAnimal tamableTarget
                &&  Objects.equals(tamableTarget.getOwnerUUID(), tamableThis.getOwnerUUID())) return false; // Won't attack animal owned by rana's owner
        else if (this.mob.getTarget() instanceof Creeper) return false; // Won't attack Creeper

        return super.canUse();
    }
}
