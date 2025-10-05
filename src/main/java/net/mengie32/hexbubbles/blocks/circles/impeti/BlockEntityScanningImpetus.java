package net.mengie32.hexbubbles.blocks.circles.impeti;

import at.petrak.hexcasting.api.casting.circles.BlockEntityAbstractImpetus;
import net.mengie32.hexbubbles.blocks.HexbubblesBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

public class BlockEntityScanningImpetus extends BlockEntityAbstractImpetus{

    public BlockEntityScanningImpetus(BlockPos pWorldPosition, BlockState pBlockState) {
        super(HexbubblesBlockEntities.IMPETUS_SCANNING, pWorldPosition, pBlockState);
    }

}
