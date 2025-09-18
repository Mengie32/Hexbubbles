package net.mengie32.hexbubbles.entity.client;

import com.mojang.blaze3d.systems.RenderCallStorage;

import net.mengie32.hexbubbles.Hexbubbles;
import net.mengie32.hexbubbles.entity.custom.BubbleEntity;
import net.minecraft.block.Block;
import net.minecraft.block.StainedGlassPaneBlock;
import net.minecraft.block.TransparentBlock;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory.Context;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.MatrixUtil;

public class BubbleEntityRenderer<M extends BubbleEntityModel<BubbleEntity>> extends EntityRenderer<BubbleEntity> {
    private static final Identifier TEXTURE = new Identifier(Hexbubbles.MOD_ID, "textures/entity/bubble.png");
    protected M model;
    private final ItemRenderer itemRenderer;

    public BubbleEntityRenderer(Context context) {
        super(context);
        this.model = getModel(context);
        this.itemRenderer = context.getItemRenderer();
    }

    @Override
    public Identifier getTexture(BubbleEntity entity) {
        return TEXTURE;
    }

    @Override
    public void render(BubbleEntity entity, float yaw, float tickDelta, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int light) {
        super.render(entity, yaw, tickDelta, matrixStack, vertexConsumerProvider, light);
        matrixStack.push();
        matrixStack.translate(0.0f, -0.5f, 0.0f);   // I suspect this is needed because the blockbench model was constructed entirely above y=0

        // With the custom renderer, I am no longer restriceted to animations baked into the model
        // TODO: Improve animations
        float animationProgress = getAnimationProgress(entity, tickDelta);
        model.setAngles(entity, animationProgress, yaw, 0f);

        // RenderLayer renderLayer = RenderLayer.getTranslucentMovingBlock();
        RenderLayer renderLayer = BubbleEntityRenderLayer.getBubbleRenderLayer(this.getTexture(entity));
        if (renderLayer != null) {
            VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(renderLayer);
            this.model.render(matrixStack, vertexConsumer, light, OverlayTexture.DEFAULT_UV, 1f, 1f, 1f, 1f);
        }
        matrixStack.pop();
        /* Known Bugs:
         * Transluscent block and item models don't render through the bubble
         * Backface of bubble renders in front of item model (can be fixed by enabling culling in the render layer, but I don't like how this looks) 
         */
        renderInventory(entity, matrixStack, vertexConsumerProvider, light);
    }

    private void renderInventory(BubbleEntity entity, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int light) {
        DefaultedList<ItemStack> inventory = entity.getInventoryLive();
        matrixStack.push();
        matrixStack.scale(0.3f, 0.3f, 0.3f);
        matrixStack.translate(0.0f, 2f, 0.0f);
        for (ItemStack itemStack : inventory) {
            itemRenderer.renderItem(itemStack,ModelTransformationMode.FIXED,light,OverlayTexture.DEFAULT_UV,matrixStack,vertexConsumerProvider,entity.getWorld(),entity.getId());
        }
        matrixStack.pop();
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
