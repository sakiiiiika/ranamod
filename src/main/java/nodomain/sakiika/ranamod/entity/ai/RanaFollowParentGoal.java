package nodomain.sakiika.ranamod.entity.ai;

import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.goal.FollowParentGoal;
import net.minecraft.world.entity.animal.Animal;

public class RanaFollowParentGoal extends FollowParentGoal {
    private Animal animal;

    public RanaFollowParentGoal(Animal pAnimal, double pSpeedModifier) {
        super(pAnimal, pSpeedModifier);
        this.animal = pAnimal;
    }

    @Override
    public boolean canUse() {
        if (animal instanceof TamableAnimal) {
            if (((TamableAnimal) animal).isTame()) return false; // If animal is tamed, this goal cannot be used. (Don't follow parent)
        }

        return super.canUse();
    }
}
