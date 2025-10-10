package net.mengie32.hexbubbles.entity.client;

import java.util.ArrayList;
import java.util.List;

import net.mengie32.hexbubbles.Hexbubbles;
import net.mengie32.hexbubbles.entity.custom.BubbleEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.EntityRendererFactory.Context;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;

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
        matrixStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(180)); // Texture is upsidedown without this for some reason
        matrixStack.translate(0.0f, -1.4f, 0.0f);   // I suspect this is needed because the blockbench model was constructed entirely above y=0

        boolean fabulousGraphics = MinecraftClient.isFabulousGraphicsOrBetter();

        // With the custom renderer, I am no longer restriceted to animations baked into the model
        // TODO: Improve animations
        float animationProgress = getAnimationProgress(entity, tickDelta);
        model.setAngles(entity, animationProgress, yaw, 0f);

        // RenderLayer renderLayer = RenderLayer.getTranslucentMovingBlock();
        // RenderLayer renderLayer = RenderLayer.getEntityTransluscentCull(this.getTexture(entity));
        RenderLayer renderLayer = BubbleEntityRenderLayers.getBubbleRenderLayer(this.getTexture(entity));
        if (renderLayer != null) {
            VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(renderLayer);
            this.model.render(matrixStack, vertexConsumer, light, OverlayTexture.DEFAULT_UV, 1f, 1f, 1f, fabulousGraphics ? 0.8f : 1.0f);
        }
        matrixStack.pop();

        /* Known Bugs:
         * Transluscent block and item models don't render through the bubble (Might need a custom item renderer that always renders them as solid)
         * Models do not render at all below Fabulous graphics (Might need a custom item renderer. EntityTranslucentCull seems to be the best graphics render layer)
         * ^ Custom item renderer seems to be a problem since most relevant methods are private. Might need mixins?
         * Backface of bubble renders in front of item model (can be fixed by enabling culling in the render layer, but I don't like how this looks) 
         */
        // Items won't render properly below fabulous graphics anyway, may as well not bother and save some work
        if(fabulousGraphics){
            renderInventory(entity, matrixStack, vertexConsumerProvider,animationProgress, light);
        }
    }

    protected void renderInventory(BubbleEntity entity, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider,float animationProgress, int light) {
        DefaultedList<ItemStack> inventory = entity.getInventoryLive();
        Random random = Random.create((long)entity.getId());    // This might be a bad idea performance-wise? Generates a lot of random numbers per frame per bubble being rendered.
        int posIndex = 0;
        Vec3d pos;
        List<Integer> selectedPos = new ArrayList<>();
        float spinSpeed;


        matrixStack.push();
        matrixStack.scale(0.3f, 0.3f, 0.3f);
        matrixStack.translate(0.0f, 1.4f, 0.0f);
        for (ItemStack itemStack : inventory) {
            if(itemStack == ItemStack.EMPTY){
                continue;
            }
            matrixStack.push();
            posIndex = random.nextBetween(1, 27);
            while(selectedPos.contains(posIndex)){
                posIndex = random.nextBetween(1, 27);
            }
            selectedPos.add(posIndex);  
            pos = indexedPointInUnitCube(posIndex-1, 3);
            pos = pos.multiply(1.2d);
            matrixStack.translate(pos.x,pos.y,pos.z);

            spinSpeed = (((float)((posIndex^entity.getId())) % 10f) - 5f)/40f; // using bitwise XOR as a getto hash function
            // Hexbubbles.LOGGER.info(String.valueOf(posIndex) + " : " + String.valueOf(spinSpeed));
            matrixStack.multiply(RotationAxis.POSITIVE_Y.rotation(animationProgress*spinSpeed));

            itemRenderer.renderItem(itemStack,ModelTransformationMode.FIXED,light,OverlayTexture.DEFAULT_UV,matrixStack,vertexConsumerProvider,entity.getWorld(),entity.getId());
            matrixStack.pop();
        }
        matrixStack.pop();
    }

    protected M getModel(EntityRendererFactory.Context ctx) {
        EntityModelLayer entityModelLayer = ModModelLayers.BUBBLE;
        ModelPart modelPart = ctx.getPart(entityModelLayer);
        return (M)(new BubbleEntityModel<BubbleEntity>(modelPart));
    }

    protected float getAnimationProgress(BubbleEntity entity, float tickDelta) {
        return (float)entity.age + tickDelta;
    }
   
    private Vec3d indexedPointInUnitCube(int index, int size){
        if(size < 1){
            return new Vec3d(0,0,0);
        }
        int vol = size*size*size;
        index = index%vol;

        double x = index%size;
        double y = MathHelper.floor(index/size)%size;
        double z = MathHelper.floor(index/(size*size))%size;

        x = (x/(size-1))-0.5d;
        y = (y/(size-1))-0.5d;
        z = (z/(size-1))-0.5d;

        Vec3d pos = new Vec3d(x,y,z);
        // Hexbubbles.LOGGER.info("Index: " + index + " | Size: " + size + " | Position: " + pos.toString());

        return pos;
    }
}
