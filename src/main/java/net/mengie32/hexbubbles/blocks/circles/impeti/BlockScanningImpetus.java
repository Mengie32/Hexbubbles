package net.mengie32.hexbubbles.blocks.circles.impeti;

import at.petrak.hexcasting.api.block.circle.BlockAbstractImpetus;
import at.petrak.hexcasting.common.blocks.circles.impetuses.BlockEntityRightClickImpetus;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;

public class BlockScanningImpetus extends BlockAbstractImpetus{

    public BlockScanningImpetus(Settings blockSettings) {
        super(blockSettings);
        //TODO Auto-generated constructor stub
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new BlockEntityScanningImpetus(pos, state);
    }
    
}
