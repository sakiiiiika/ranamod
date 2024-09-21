package nodomain.sakiika.ranamod.item;

import nodomain.sakiika.ranamod.RanaMod;
import nodomain.sakiika.ranamod.entity.ModEntities;
import net.minecraft.world.item.*;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, RanaMod.MOD_ID);

    public static final RegistryObject<Item> RANA_SPAWN_EGG = ITEMS.register("rana_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.RANA, 0xcfdfcf, 0x00ac48, new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
