package net.mengie32.hexbubbles;

import net.mengie32.hexbubbles.entity.ModEntities;
import net.mengie32.hexbubbles.entity.client.BubbleModel;
import net.mengie32.hexbubbles.entity.client.BubbleEntityRenderer;
import net.mengie32.hexbubbles.entity.client.ModModelLayers;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class HexbubblesClient implements ClientModInitializer{

    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(ModEntities.BUBBLE, BubbleEntityRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.BUBBLE, BubbleModel::getTexturedModelData);
    }

}
