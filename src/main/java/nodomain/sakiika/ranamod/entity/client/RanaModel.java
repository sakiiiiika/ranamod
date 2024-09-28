package nodomain.sakiika.ranamod.entity.client;// Made with Blockbench 4.8.3
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.HumanoidArm;
import nodomain.sakiika.ranamod.entity.animation.RanaAnimationDefinitions;
import nodomain.sakiika.ranamod.entity.custom.RanaEntity;

public class RanaModel<T extends Entity> extends HierarchicalModel<T> implements ArmedModel {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	//public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("modid", "rana"), "main");
	private final ModelPart Root;
	private final ModelPart Body;
	private final ModelPart Head;
	private final ModelPart RightArm;
	private final ModelPart LeftArm;

	public RanaModel(ModelPart root) {
		this.Root = root.getChild("Root");
		this.Body = Root.getChild("Body");	//See hierarchy in BlockBench
		this.Head = Root.getChild("Body").getChild("Head");	//See hierarchy in BlockBench
		this.RightArm = Root.getChild("Body").getChild("Hand").getChild("R")/*.getChild("FingerR")*/;	//See hierarchy in BlockBench
		this.LeftArm = Root.getChild("Body").getChild("Hand").getChild("L")/*.getChild("FingerL")*/;	//See hierarchy in BlockBench
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition Root = partdefinition.addOrReplaceChild("Root", CubeListBuilder.create(), PartPose.offset(0.0F, 17.0F, 0.0F));

		PartDefinition Body = Root.addOrReplaceChild("Body", CubeListBuilder.create().texOffs(0, 25).addBox(-2.5F, -6.5F, -1.5F, 5.0F, 6.5F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(1, 24).addBox(-2.5F, -7.5F, -0.5F, 5.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition cube_r1 = Body.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(16, 25).addBox(0.0F, 0.0F, -1.5F, 2.0F, 3.05F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.5F, -3.0F, 0.0F, 0.0F, 0.0F, 0.1658F));

		PartDefinition cube_r2 = Body.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(16, 25).mirror().addBox(-2.0F, 0.0F, -1.5F, 2.0F, 3.05F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(2.5F, -3.0F, 0.0F, 0.0F, 0.0F, -0.1658F));

		PartDefinition cube_r3 = Body.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(2, 25).addBox(-2.5F, -1.0F, 0.0F, 5.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -6.5F, -1.5F, -0.4363F, 0.0F, 0.0F));

		PartDefinition Backpack = Body.addOrReplaceChild("Backpack", CubeListBuilder.create().texOffs(17, 41).addBox(-2.6554F, -3.1654F, 1.5F, 3.0F, 2.0F, 2.25F, new CubeDeformation(0.0F))
				.texOffs(5, 39).addBox(-3.1554F, -2.4154F, 1.5F, 4.0F, 4.5F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(17, 41).addBox(-2.6554F, -0.1654F, 1.75F, 3.0F, 2.0F, 2.25F, new CubeDeformation(0.0F)), PartPose.offset(1.1554F, -3.0846F, 0.0F));

		PartDefinition Hand = Body.addOrReplaceChild("Hand", CubeListBuilder.create(), PartPose.offset(0.0F, 7.0F, 0.0F));

		PartDefinition L = Hand.addOrReplaceChild("L", CubeListBuilder.create().texOffs(22, 12).mirror().addBox(-1.3927F, 0.7456F, -0.5F, 8.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(3.5F, -13.5F, 0.0F, 0.0F, 0.0F, 0.9599F));

		PartDefinition FingerL = L.addOrReplaceChild("FingerL", CubeListBuilder.create().texOffs(28, 0).addBox(-1.1808F, -1.9264F, -1.5F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(4.7881F, 1.672F, 0.0F));

		PartDefinition R = Hand.addOrReplaceChild("R", CubeListBuilder.create().texOffs(22, 12).addBox(-6.6073F, 0.7456F, -0.5F, 8.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.5F, -13.5F, 0.0F, 0.0F, 0.0F, -0.9599F));

		PartDefinition FingerR = R.addOrReplaceChild("FingerR", CubeListBuilder.create().texOffs(28, 0).addBox(-0.8192F, -1.9264F, -1.5F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.7881F, 1.672F, 0.0F));

		PartDefinition Head = Body.addOrReplaceChild("Head", CubeListBuilder.create().texOffs(0, 12).addBox(-4.0F, -5.0F, -3.0F, 8.0F, 5.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(22, 17).addBox(-4.0F, -10.0F, -3.0F, 8.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(-5.0F, -8.0F, -4.0F, 10.0F, 4.0F, 8.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(-5.0F, -12.0F, 2.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(3.0F, -12.0F, 2.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(26, 14).addBox(4.0F, -3.0F, -1.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(26, 14).addBox(-5.0F, -3.0F, -1.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -7.0F, 0.0F));

		PartDefinition HairR = Head.addOrReplaceChild("HairR", CubeListBuilder.create().texOffs(0, 35).addBox(0.0F, -1.0F, -1.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(0, 35).addBox(0.2F, -1.0F, 3.0F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.0F, -3.0F, -1.0F));

		PartDefinition cube_r4 = HairR.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(34, 33).addBox(-1.0F, -3.0F, -0.5F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 6.0F, 4.0F, 0.4483F, -0.1776F, 0.3521F));

		PartDefinition cube_r5 = HairR.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(0, 35).addBox(-0.8F, 1.3604F, -0.1725F, 1.0F, 1.7F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 0.6995F, 1.2342F, 0.7418F, 0.0F, 0.0F));

		PartDefinition cube_r6 = HairR.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(0, 35).addBox(-0.8F, 0.0F, 0.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, -1.0F, 2.0F, 0.3491F, 0.0F, 0.0F));

		PartDefinition cube_r7 = HairR.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(0, 35).addBox(-0.8F, 0.0F, 0.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, -1.0F, 1.0F, 0.3491F, 0.0F, 0.0F));

		PartDefinition cube_r8 = HairR.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(0, 35).addBox(-1.0F, 0.0F, -1.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 1.0F, 0.0F, -0.4363F, 0.0F, 0.0F));

		PartDefinition HairL = Head.addOrReplaceChild("HairL", CubeListBuilder.create().texOffs(0, 35).addBox(-1.0F, -1.0F, -1.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(0, 35).addBox(-1.2F, -1.0F, 3.0F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(5.0F, -3.0F, -1.0F));

		PartDefinition cube_r9 = HairL.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(34, 33).addBox(-2.4F, -2.0F, -1.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 5.0F, 4.0F, 0.4483F, 0.1776F, -0.3521F));

		PartDefinition cube_r10 = HairL.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(0, 35).addBox(-1.2F, 1.3604F, -0.1725F, 1.0F, 1.7F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.6995F, 1.2342F, 0.7418F, 0.0F, 0.0F));

		PartDefinition cube_r11 = HairL.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(0, 35).addBox(-1.2F, 0.0F, 0.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.0F, 2.0F, 0.3491F, 0.0F, 0.0F));

		PartDefinition cube_r12 = HairL.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(0, 35).addBox(-1.2F, 0.0F, 0.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.0F, 1.0F, 0.3491F, 0.0F, 0.0F));

		PartDefinition cube_r13 = HairL.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(0, 35).addBox(-1.0F, 0.0F, -1.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.0F, 0.0F, -0.4363F, 0.0F, 0.0F));

		PartDefinition HairF = Head.addOrReplaceChild("HairF", CubeListBuilder.create(), PartPose.offset(0.0F, -5.0F, -3.0F));

		PartDefinition cube_r14 = HairF.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(0, 35).addBox(-1.0F, 0.0F, -0.8F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.3927F));

		PartDefinition cube_r15 = HairF.addOrReplaceChild("cube_r15", CubeListBuilder.create().texOffs(0, 35).addBox(0.5F, -0.2F, -0.8F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.0F, 0.0F, 0.0F, 0.0F, -0.3054F));

		PartDefinition cube_r16 = HairF.addOrReplaceChild("cube_r16", CubeListBuilder.create().texOffs(0, 35).addBox(-0.5F, -0.2F, -0.8F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0873F));

		PartDefinition Leg = Root.addOrReplaceChild("Leg", CubeListBuilder.create().texOffs(16, 31).addBox(-1.0F, -7.0F, -1.5F, 2.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(16, 31).addBox(0.5F, -7.0F, -1.5F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(16, 31).addBox(-2.5F, -7.0F, -1.5F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 7.0F, 0.0F));

		PartDefinition L2 = Leg.addOrReplaceChild("L2", CubeListBuilder.create().texOffs(0, 12).addBox(-0.5F, -1.0F, -0.5F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(0, 4).addBox(-1.0F, 3.0F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(26, 31).addBox(-1.0F, 4.0F, -2.0F, 2.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(1.5F, -5.0F, 0.0F));

		PartDefinition R2 = Leg.addOrReplaceChild("R2", CubeListBuilder.create().texOffs(26, 31).mirror().addBox(-1.0F, 4.0F, -2.0F, 2.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(0, 12).mirror().addBox(-0.5F, -1.0F, -0.5F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(0, 4).mirror().addBox(-1.0F, 3.0F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-1.5F, -5.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.applyHeadRotation(netHeadYaw, headPitch, ageInTicks);

		this.animateWalk(RanaAnimationDefinitions.RANA_WALK, limbSwing, limbSwingAmount, 2f, 2.5f);
		this.animate(((RanaEntity) entity).idleAnimationState, RanaAnimationDefinitions.RANA_IDLE, ageInTicks, 1f);
		this.animate(((RanaEntity) entity).sitAnimationState, RanaAnimationDefinitions.RANA_SIT, ageInTicks, 1f);
		this.animate(((RanaEntity) entity).attackAnimationState, RanaAnimationDefinitions.RANA_ATTACK, ageInTicks, 1f);
	}

	private void applyHeadRotation(float pNetHeadYaw, float pHeadPitch, float pAgeInTicks) {
		pNetHeadYaw = Mth.clamp(pNetHeadYaw, -30.0F, 30.0F);
		pHeadPitch = Mth.clamp(pHeadPitch, -25.0F, 45.0F);

		this.Head.yRot = pNetHeadYaw * ((float)Math.PI / 180F);
		this.Head.xRot = pHeadPitch * ((float)Math.PI / 180F);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		Root.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	public ModelPart root() {
		return Root;
	}
	public ModelPart body() {
		return Body;
	}
	public ModelPart head() {
		return Head;
	}
	public ModelPart rightArm() {
		return RightArm;
	}
	public ModelPart leftArm() {
		return LeftArm;
	}
	public ModelPart getArm(HumanoidArm pSide) {
		//return pSide == HumanoidArm.RIGHT ? rightArm() : leftArm();
		return rightArm();	//At this time return right arm regardless of pSide
	}

	@Override
	//Adjust PoseStack to fit to hand
	public void translateToHand(HumanoidArm pSide, PoseStack pPoseStack) {
		this.root().translateAndRotate(pPoseStack);
		this.body().translateAndRotate(pPoseStack);
		this.getArm(pSide).translateAndRotate(pPoseStack);

		pPoseStack.mulPose(Axis.XP.rotationDegrees(this.getArm(pSide).xRot)); //Rotate X
		pPoseStack.mulPose(Axis.YP.rotationDegrees(this.getArm(pSide).yRot)); //Rotate Y
		pPoseStack.mulPose(Axis.ZP.rotationDegrees(this.getArm(pSide).zRot)); //Rotate Z

		//X: Parallel to arm (Positive: Underarm, Negative: Finger)	(Invert if pSide is left)
		//Y: Vertical to arm (Positive: Inside body, Negative: Outside body)
		//Z: Vertical to arm (Positive: Back of body, Negative: Front of body)
		if (!this.young) pPoseStack.translate(pSide == HumanoidArm.RIGHT ? -0.2D : 0.4D, pSide == HumanoidArm.RIGHT ? 0.35D : 0D, 0.1D);
		else pPoseStack.translate(pSide == HumanoidArm.RIGHT ? -0.125D : 0.25D, 0D, 0.1D);

		if (pSide == HumanoidArm.RIGHT) pPoseStack.mulPose(Axis.ZP.rotationDegrees(90.0F)); //Rotate Z
		else pPoseStack.mulPose(Axis.ZP.rotationDegrees(-45.0F)); //Rotate Z
	}
}