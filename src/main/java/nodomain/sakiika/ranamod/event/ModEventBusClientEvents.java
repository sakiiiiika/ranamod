package nodomain.sakiika.ranamod.event;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import nodomain.sakiika.ranamod.RanaMod;
import nodomain.sakiika.ranamod.entity.client.ModModelLayers;
import nodomain.sakiika.ranamod.entity.client.RanaModel;

@EventBusSubscriber(modid = RanaMod.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModEventBusClientEvents {
    @SubscribeEvent
    public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ModModelLayers.RANA_LAYER, RanaModel::createBodyLayer);
    }
}
