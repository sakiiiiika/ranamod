package nodomain.sakiika.ranamod.entity.ai;

import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.goal.FollowParentGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.item.crafting.Ingredient;

public class RanaTemptGoal extends TemptGoal {
    private PathfinderMob animal;

    public RanaTemptGoal(PathfinderMob pMob, double pSpeedModifier, Ingredient pItems, boolean pCanScare) {
        super(pMob, pSpeedModifier, pItems, pCanScare);
        this.animal = pMob;
    }

    @Override
    public boolean canUse() {
        if (animal instanceof TamableAnimal) {
            if (((TamableAnimal) animal).isTame()) return false; // If animal is tamed, this goal cannot be used. (Don't be tempted with food)
        }

        return super.canUse();
    }
}
