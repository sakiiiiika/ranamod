package nodomain.sakiika.ranamod.event;

import nodomain.sakiika.ranamod.RanaMod;
import nodomain.sakiika.ranamod.entity.client.ModModelLayers;
import nodomain.sakiika.ranamod.entity.client.RanaModel;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = RanaMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModEventBusClientEvents {
    @SubscribeEvent
    public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ModModelLayers.RANA_LAYER, RanaModel::createBodyLayer);
    }
}
