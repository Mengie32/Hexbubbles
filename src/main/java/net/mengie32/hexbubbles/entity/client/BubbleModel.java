package net.mengie32.hexbubbles.entity.client;

import net.mengie32.hexbubbles.entity.animation.BubbleAnimations;
import net.mengie32.hexbubbles.entity.custom.BubbleEntity;
import net.minecraft.client.model.Dilation;
import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;

// Made with Blockbench 4.12.6
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports
public class BubbleModel<T extends BubbleEntity> extends SinglePartEntityModel<T> {
	private final ModelPart bubble;
	//private final ModelPart head;


	public BubbleModel(ModelPart root) {
		this.bubble = root.getChild("Bubble");
		//this.head = bubble;	// may not be used since this is not an animal
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		
		@SuppressWarnings("unused")
		ModelPartData Bubble = modelPartData.addChild(
			"Bubble", 
			ModelPartBuilder.create()
				.uv(0, 0)
				.cuboid(-6.0F, -3.0F, -6.0F,
					12.0F, 12.0F, 12.0F, 
					new Dilation(0.0F)), 
			ModelTransform.pivot(0.0F, 14.5F, 0.0F)
		);
		
		return TexturedModelData.of(modelData, 64, 64);
	}
	@Override
	public void setAngles(BubbleEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.getPart().traverse().forEach(ModelPart::resetTransform);
		this.updateAnimation(entity.idleAnimationState, BubbleAnimations.IDLE, ageInTicks,1.0f);
		this.updateAnimation(entity.rippleAnimationState, BubbleAnimations.RIPPLE, ageInTicks,1.0f);
	}

	// @Override
	// public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
	// 	bubble.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	// }

	@Override
	public ModelPart getPart() {
		return this.bubble;
	}
}