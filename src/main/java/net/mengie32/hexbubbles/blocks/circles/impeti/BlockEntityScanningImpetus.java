package net.mengie32.hexbubbles.blocks.circles.impeti;

import at.petrak.hexcasting.api.casting.circles.BlockEntityAbstractImpetus;
import net.mengie32.hexbubbles.blocks.HexbubbleBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

public class BlockEntityScanningImpetus extends BlockEntityAbstractImpetus{

    public BlockEntityScanningImpetus(BlockPos worldPosition, BlockState blockState) {
        super(HexbubbleBlockEntities.IMPETUS_SCANNING_TILE, worldPosition, blockState);
    }

}
