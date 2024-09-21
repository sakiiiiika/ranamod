package nodomain.sakiika.ranamod.item;

import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import nodomain.sakiika.ranamod.RanaMod;
import nodomain.sakiika.ranamod.entity.ModEntities;
import net.minecraft.world.item.*;


public class ModItems {
    public static final DeferredRegister.Items ITEMS =
            DeferredRegister.createItems(RanaMod.MOD_ID);

    public static final DeferredItem<Item> RANA_SPAWN_EGG = ITEMS.register("rana_spawn_egg",
            () -> new DeferredSpawnEggItem(ModEntities.RANA, 0xcfdfcf, 0x00ac48, new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
