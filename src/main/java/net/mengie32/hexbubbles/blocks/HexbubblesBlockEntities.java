package net.mengie32.hexbubbles.blocks;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.mengie32.hexbubbles.Hexbubbles;
import net.mengie32.hexbubbles.blocks.circles.impeti.BlockEntityScanningImpetus;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;   
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class HexbubblesBlockEntities {
    public static final BlockEntityType<BlockEntityScanningImpetus> IMPETUS_SCANNING = register(
        "impetus/scanning",
        FabricBlockEntityTypeBuilder.create(BlockEntityScanningImpetus::new, HexbubblesBlocks.IMPETUS_SCANNING).build()
    );

    public static <T extends BlockEntityType<?>> T register(String name, T blockEntityType) {
    return Registry.register(
        Registries.BLOCK_ENTITY_TYPE, 
        Identifier.of(Hexbubbles.MOD_ID, name), 
        blockEntityType
    );
  }

  public static void registerHexbubbleBlockEtities(){
        Hexbubbles.LOGGER.info("Registering block entities for " + Hexbubbles.MOD_ID);
    }
}
