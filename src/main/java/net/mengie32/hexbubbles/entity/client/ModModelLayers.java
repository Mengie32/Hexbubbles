package net.mengie32.hexbubbles.entity.client;

import net.mengie32.hexbubbles.Hexbubbles;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

public class ModModelLayers {
    public static final EntityModelLayer BUBBLE = 
    new EntityModelLayer(new Identifier(Hexbubbles.MOD_ID,"bubble"), "main");
}
