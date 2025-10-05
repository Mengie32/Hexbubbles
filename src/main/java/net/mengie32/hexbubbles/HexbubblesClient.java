package net.mengie32.hexbubbles;

import net.mengie32.hexbubbles.entity.HexbubblesEntities;
import net.mengie32.hexbubbles.entity.client.BubbleEntityModel;
import net.mengie32.hexbubbles.entity.client.BubbleEntityRenderer;
import net.mengie32.hexbubbles.entity.client.ModModelLayers;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class HexbubblesClient implements ClientModInitializer{

    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(HexbubblesEntities.BUBBLE, BubbleEntityRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.BUBBLE, BubbleEntityModel::getTexturedModelData);
    }

}
