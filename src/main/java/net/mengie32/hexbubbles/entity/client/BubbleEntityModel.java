package net.mengie32.hexbubbles.entity.client;

// Made with Blockbench 4.12.6
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports

import net.mengie32.hexbubbles.entity.custom.BubbleEntity;
import net.minecraft.client.model.Dilation;
import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;

public class BubbleEntityModel<T extends BubbleEntity> extends SinglePartEntityModel<BubbleEntity> {
	private final ModelPart Bubble;
	public BubbleEntityModel(ModelPart root) {
		this.Bubble = root.getChild("Bubble");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData Bubble = modelPartData.addChild("Bubble", ModelPartBuilder.create().uv(12, 0).cuboid(6.0F, -6.0F, -6.0F, 0.0F, 12.0F, 12.0F, new Dilation(0.0F))
		.uv(12, 0).cuboid(-6.0F, -6.0F, -6.0F, 0.0F, 12.0F, 12.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 16.0F, 0.0F));

		ModelPartData BubbleNorth_r1 = Bubble.addChild("BubbleNorth_r1", ModelPartBuilder.create().uv(0, 0).cuboid(-6.0F, -6.0F, -6.0F, 0.0F, 12.0F, 12.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

		ModelPartData BubbleSouth_r1 = Bubble.addChild("BubbleSouth_r1", ModelPartBuilder.create().uv(24, 0).cuboid(0.0F, -6.0F, -6.0F, 0.0F, 12.0F, 12.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 6.0F, 0.0F, -1.5708F, 0.0F));

		ModelPartData BubbleTop_r1 = Bubble.addChild("BubbleTop_r1", ModelPartBuilder.create().uv(12, -12).cuboid(0.0F, -6.0F, -6.0F, 0.0F, 12.0F, 12.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 6.0F, 0.0F, 0.0F, 0.0F, 1.5708F));

		ModelPartData BubbleBottom_r1 = Bubble.addChild("BubbleBottom_r1", ModelPartBuilder.create().uv(12, -12).cuboid(-6.0F, -6.0F, -6.0F, 0.0F, 12.0F, 12.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, -3.1416F, 0.0F, 1.5708F));
		return TexturedModelData.of(modelData, 64, 64);
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		Bubble.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}
	@Override
	public ModelPart getPart() {
		return Bubble;
	}
	@Override
	public void setAngles(BubbleEntity entity, float limbAngle, float limbDistance, float animationProgress,
			float headYaw, float headPitch) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'setAngles'");
	}
}