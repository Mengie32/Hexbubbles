package net.mengie32.hexbubbles.entity;

import net.mengie32.hexbubbles.Hexbubbles;
import net.mengie32.hexbubbles.entity.custom.BubbleEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
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
            FabricEntityTypeBuilder.create(SpawnGroup.MISC, BubbleEntity::new)
                .dimensions(EntityDimensions.fixed(1f, 1f))
            .build()
        );

    public static void registerModEntities() {
        Hexbubbles.LOGGER.info("Registering entities for " + Hexbubbles.MOD_ID);
    }
}
