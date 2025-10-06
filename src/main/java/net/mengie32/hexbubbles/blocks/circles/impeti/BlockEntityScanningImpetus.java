package net.mengie32.hexbubbles.blocks.circles.impeti;

import at.petrak.hexcasting.api.casting.circles.BlockEntityAbstractImpetus;
import net.mengie32.hexbubbles.blocks.HexbubblesBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockEntityScanningImpetus extends BlockEntityAbstractImpetus{

    public BlockEntityScanningImpetus(BlockPos pWorldPosition, BlockState pBlockState) {
        super(HexbubblesBlockEntities.IMPETUS_SCANNING, pWorldPosition, pBlockState);
    }

    public static <T extends BlockEntity> void tick(World world, BlockPos pos, BlockState state, T blockEntity) {
        
    }

}
