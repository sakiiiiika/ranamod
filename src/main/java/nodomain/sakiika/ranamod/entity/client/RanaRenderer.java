package nodomain.sakiika.ranamod.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.block.model.ItemTransform;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import nodomain.sakiika.ranamod.RanaMod;
import nodomain.sakiika.ranamod.entity.custom.RanaEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class RanaRenderer extends MobRenderer<RanaEntity, RanaModel<RanaEntity>> {
    //private final ItemInHandRenderer itemInHandRenderer;

    public RanaRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new RanaModel<>(pContext.bakeLayer(ModModelLayers.RANA_LAYER)), 0.4F); //ShadowRadius 0.4
        this.addLayer(new ItemInHandLayer<>(this, pContext.getItemInHandRenderer()));   //Render item in hand

        //this.itemInHandRenderer = pContext.getItemInHandRenderer();
    }

    @Override
    public ResourceLocation getTextureLocation(RanaEntity pEntity) {
        return new ResourceLocation(RanaMod.MOD_ID, "textures/entity/rana.png");
    }

    @Override
    public void render(RanaEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight) {
        if(pEntity.isBaby()) {
            pMatrixStack.scale(0.5f, 0.5f, 0.5f);
        }

        //Render holding item
        /*
        model.setupAnim(pEntity, 0, 0, 0, pEntityYaw, 0); //

        //Get location and rotation of arm bone
        ModelPart rightArm = model.rightArm(); //Get right arm

        //Get location and rotation of item according to arm bone
        pMatrixStack.pushPose();
        pMatrixStack.translate(rightArm.x, rightArm.y, rightArm.z); //Move to bone location
        pMatrixStack.mulPose(Axis.XP.rotationDegrees(rightArm.xRot)); //Rotate X
        pMatrixStack.mulPose(Axis.YP.rotationDegrees(rightArm.yRot)); //Rotate Y
        pMatrixStack.mulPose(Axis.ZP.rotationDegrees(rightArm.zRot)); //Rotate Z

        //Draw item
        //ItemStack itemStack = pEntity.getItemInHand(InteractionHand.MAIN_HAND);
        ItemStack itemStack = pEntity.getItemBySlot(EquipmentSlot.MAINHAND);
        if (!itemStack.isEmpty()) {
            //Offset
            pMatrixStack.translate(0.0, 0.0, 0.1);

            // Render the item
            itemInHandRenderer.renderItem(pEntity, itemStack, ItemDisplayContext.THIRD_PERSON_RIGHT_HAND, false, pMatrixStack, pBuffer, pEntity.getId());
        }

        pMatrixStack.popPose();
        */



        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }
}
