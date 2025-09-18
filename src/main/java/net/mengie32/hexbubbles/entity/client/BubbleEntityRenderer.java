package net.mengie32.hexbubbles.entity.client;

import net.mengie32.hexbubbles.Hexbubbles;
import net.mengie32.hexbubbles.entity.custom.BubbleEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory.Context;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class BubbleEntityRenderer<M extends BubbleEntityModel<BubbleEntity>> extends EntityRenderer<BubbleEntity> {
    private static final Identifier TEXTURE = new Identifier(Hexbubbles.MOD_ID, "textures/entity/bubble.png");
    protected M model;

    public BubbleEntityRenderer(Context context) {
        super(context);
        this.model = getModel(context);
    }

    @Override
    public Identifier getTexture(BubbleEntity entity) {
        return TEXTURE;
    }

    @Override
    public void render(BubbleEntity entity, float yaw, float tickDelta, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int light) {

        matrixStack.push();
        matrixStack.translate(0.0f, -0.5f, 0.0f);   // I suspect this is needed because the blockbench model was constructed entirely above y=0

        // With the custom renderer, I am no longer restriceted to animations baked into the model
        // TODO: Improve animations
        float animationProgress = getAnimationProgress(entity, tickDelta);
        model.setAngles(entity, animationProgress, yaw, 0f);

        MinecraftClient minecraftClient = MinecraftClient.getInstance();
        boolean bl = !entity.isInvisible();
        boolean bl2 = !bl && !entity.isInvisibleTo(minecraftClient.player);
        boolean bl3 = minecraftClient.hasOutline(entity);
        RenderLayer renderLayer = this.getRenderLayer(entity, bl, bl2, bl3);
        if (renderLayer != null) {
            VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(RenderLayer.getEntityTranslucent(this.getTexture(entity)));
            this.model.render(matrixStack, vertexConsumer, light, OverlayTexture.DEFAULT_UV, 1f, 1f, 1f, 1f);
        }
        matrixStack.pop();
    }

    protected RenderLayer getRenderLayer(BubbleEntity entity, boolean showBody, boolean translucent,boolean showOutline) {
        Identifier identifier = this.getTexture(entity);
        if (translucent) {
            return RenderLayer.getItemEntityTranslucentCull(identifier);
        } else if (showBody) {
            return this.model.getLayer(identifier);
        } else {
            return showOutline ? RenderLayer.getOutline(identifier) : null;
        }
    }

    protected M getModel(EntityRendererFactory.Context ctx) {
        EntityModelLayer entityModelLayer = ModModelLayers.BUBBLE;
        ModelPart modelPart = ctx.getPart(entityModelLayer);
        return (M)(new BubbleEntityModel<>(modelPart));
    }

    protected float getAnimationProgress(BubbleEntity entity, float tickDelta) {
      return (float)entity.age + tickDelta;
   }
}
