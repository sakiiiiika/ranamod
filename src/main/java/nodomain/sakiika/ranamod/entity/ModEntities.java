package nodomain.sakiika.ranamod.entity;

import nodomain.sakiika.ranamod.RanaMod;
import nodomain.sakiika.ranamod.entity.custom.RanaEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, RanaMod.MOD_ID);

    public static final RegistryObject<EntityType<RanaEntity>> RANA =
            ENTITY_TYPES.register("rana", () -> EntityType.Builder.of(RanaEntity::new, MobCategory.CREATURE)
                    .sized(0.5f, 1.5f).build("rana")); // sized means boundary box

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
