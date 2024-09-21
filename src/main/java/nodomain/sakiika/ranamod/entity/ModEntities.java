package nodomain.sakiika.ranamod.entity;

import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import nodomain.sakiika.ranamod.RanaMod;
import nodomain.sakiika.ranamod.entity.custom.RanaEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

import java.util.function.Supplier;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, RanaMod.MOD_ID);

    public static final Supplier<EntityType<RanaEntity>> RANA =
            ENTITY_TYPES.register("rana", () -> EntityType.Builder.of(RanaEntity::new, MobCategory.CREATURE)
                    .sized(0.5f, 1.5f).build("rana")); // sized means boundary box

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
