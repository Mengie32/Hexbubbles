package net.mengie32.hexbubbles.blocks.circles.impeti;

import java.util.List;

import at.petrak.hexcasting.api.casting.circles.BlockEntityAbstractImpetus;
import net.mengie32.hexbubbles.Hexbubbles;
import net.mengie32.hexbubbles.blocks.HexbubblesBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockEntityScanningImpetus extends BlockEntityAbstractImpetus{

    public BlockEntityScanningImpetus(BlockPos pWorldPosition, BlockState pBlockState) {
        super(HexbubblesBlockEntities.IMPETUS_SCANNING, pWorldPosition, pBlockState);
    }

    public static <T extends BlockEntity> void tick(World world, BlockPos pos, BlockState state, BlockEntityScanningImpetus self) {
        // seems to still be ticking well outside entity processing chunks
        // TODO: stop ticking outside entity processing chunks?

        // Hexbubbles.LOGGER.info("Scanning impetus ticked on " + (world.isClient()?"client!":"world!"));

        // executionState is only used while a circle is casting. Need to implement the spell holding first...
        if(self.executionState != null){
            List<Entity> entitiesInBounds = world.getNonSpectatingEntities(Entity.class, self.executionState.bounds);; 
            Hexbubbles.LOGGER.info(entitiesInBounds.toString());
        }
    }

}
