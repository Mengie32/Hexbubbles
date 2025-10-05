package net.mengie32.hexbubbles.blocks.circles.impeti;

import at.petrak.hexcasting.api.block.circle.BlockAbstractImpetus;
import net.mengie32.hexbubbles.Hexbubbles;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockScanningImpetus extends BlockAbstractImpetus{

    public BlockScanningImpetus(Settings p_49795_) {
        super(p_49795_);
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new BlockEntityScanningImpetus(pos, state);
    }

    // Temporarily copying right click impetus functoinality for testing
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        Hexbubbles.LOGGER.info("Interacted with Scanning Impetus!");
        if (!player.isSneaking()) {
            var tile = world.getBlockEntity(pos);
            if (tile instanceof BlockEntityScanningImpetus impetus) {
                if (player instanceof ServerPlayerEntity serverPlayer) {
                    impetus.startExecution(serverPlayer);
                }
                return ActionResult.SUCCESS;
            }
        }
        return ActionResult.PASS;
    }
}
