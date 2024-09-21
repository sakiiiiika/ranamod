package nodomain.sakiika.ranamod.entity.client;

import nodomain.sakiika.ranamod.RanaMod;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;

public class ModModelLayers {
    public static final ModelLayerLocation RANA_LAYER = new ModelLayerLocation(
            ResourceLocation.fromNamespaceAndPath(RanaMod.MOD_ID, "rana_layer"), "main");
}
