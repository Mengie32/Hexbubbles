package net.mengie32.hexbubbles.entity;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.mengie32.hexbubbles.Hexbubbles;
import net.mengie32.hexbubbles.entity.custom.BubbleEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEntities {
    public static final EntityType<BubbleEntity> BUBBLE = 
        Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(Hexbubbles.MOD_ID,"bubble"),
            EntityType.Builder.create(BubbleEntity::new, SpawnGroup.MISC).setDimensions(0.95f, 0.95f).build("bubble")
        );

    public static void registerModEntities() {
        Hexbubbles.LOGGER.info("Registering entities for " + Hexbubbles.MOD_ID);
        // FabricDefaultAttributeRegistry.register(ModEntities.BUBBLE, BubbleEntity.createBubbleAttributes());  // No longer has default attributes when converted to non-living
    }
}
