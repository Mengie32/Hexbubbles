package net.mengie32.hexbubbles.entity.client;

import net.mengie32.hexbubbles.Hexbubbles;
import net.mengie32.hexbubbles.entity.custom.BubbleEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory.Context;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;

public class BubbleRenderer extends MobEntityRenderer<BubbleEntity, BubbleModel<BubbleEntity>>{
    private static final Identifier TEXTURE = new Identifier(Hexbubbles.MOD_ID,"textures/entity/bubble.png");


    public BubbleRenderer(Context context) {
        super(context, new BubbleModel<>(context.getPart(ModModelLayers.BUBBLE)),0.3f);
    }

    @Override
    public Identifier getTexture(BubbleEntity entity) {
        return TEXTURE;
    }

    @Override
    public void render(BubbleEntity livingEntity, float f, float g, MatrixStack matrixStack,
            VertexConsumerProvider vertexConsumerProvider, int i) {

        matrixStack.push();
		this.model.handSwingProgress = this.getHandSwingProgress(livingEntity, g);
		this.model.riding = livingEntity.hasVehicle();
		this.model.child = livingEntity.isBaby();
		float h = MathHelper.lerpAngleDegrees(g, livingEntity.prevBodyYaw, livingEntity.bodyYaw);
		float j = MathHelper.lerpAngleDegrees(g, livingEntity.prevHeadYaw, livingEntity.headYaw);
		float k = j - h;
		if (livingEntity.hasVehicle() && livingEntity.getVehicle() instanceof LivingEntity) {
			LivingEntity livingEntity2 = (LivingEntity)livingEntity.getVehicle();
			h = MathHelper.lerpAngleDegrees(g, livingEntity2.prevBodyYaw, livingEntity2.bodyYaw);
			k = j - h;
			float l = MathHelper.wrapDegrees(k);
			if (l < -85.0F) {
				l = -85.0F;
			}

			if (l >= 85.0F) {
				l = 85.0F;
			}

			h = j - l;
			if (l * l > 2500.0F) {
				h += l * 0.2F;
			}

			k = j - h;
		}

		float m = MathHelper.lerp(g, livingEntity.prevPitch, livingEntity.getPitch());
		if (shouldFlipUpsideDown(livingEntity)) {
			m *= -1.0F;
			k *= -1.0F;
		}

		if (livingEntity.isInPose(EntityPose.SLEEPING)) {
			Direction direction = livingEntity.getSleepingDirection();
			if (direction != null) {
				float n = livingEntity.getEyeHeight(EntityPose.STANDING) - 0.1F;
				matrixStack.translate(-direction.getOffsetX() * n, 0.0F, -direction.getOffsetZ() * n);
			}
		}

		float lx = this.getAnimationProgress(livingEntity, g);
		this.setupTransforms(livingEntity, matrixStack, lx, h, g);
		matrixStack.scale(-1.0F, -1.0F, 1.0F);
		this.scale(livingEntity, matrixStack, g);
		matrixStack.translate(0.0F, -1.501F, 0.0F);
		float n = 0.0F;
		float o = 0.0F;
		if (!livingEntity.hasVehicle() && livingEntity.isAlive()) {
			n = livingEntity.limbAnimator.getSpeed(g);
			o = livingEntity.limbAnimator.getPos(g);
			if (livingEntity.isBaby()) {
				o *= 3.0F;
			}

			if (n > 1.0F) {
				n = 1.0F;
			}
		}

		this.model.animateModel(livingEntity, o, n, g);
		this.model.setAngles(livingEntity, o, n, lx, k, m);
		MinecraftClient minecraftClient = MinecraftClient.getInstance();
		boolean bl = this.isVisible(livingEntity);
		boolean bl2 = !bl && !livingEntity.isInvisibleTo(minecraftClient.player);
		boolean bl3 = minecraftClient.hasOutline(livingEntity);
		RenderLayer renderLayer = this.getRenderLayer(livingEntity, bl, bl2, bl3);
		if (renderLayer != null) {
			VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(RenderLayer.getEntityTranslucent(this.getTexture(livingEntity)));
			int p = getOverlay(livingEntity, this.getAnimationCounter(livingEntity, g));
			this.model.render(matrixStack, vertexConsumer, i, p, 1.0F, 1.0F, 1.0F, bl2 ? 0.15F : 1.0F);
		}
        /*

		if (!livingEntity.isSpectator()) {
			for (FeatureRenderer<T, M> featureRenderer : this.features) {
				featureRenderer.render(matrixStack, vertexConsumerProvider, i, livingEntity, o, n, g, lx, k, m);
			}
		}
            */

		matrixStack.pop();
    }
}
