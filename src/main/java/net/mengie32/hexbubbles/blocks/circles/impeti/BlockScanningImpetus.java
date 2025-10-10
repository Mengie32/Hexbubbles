package net.mengie32.hexbubbles.blocks.circles.impeti;

import org.jetbrains.annotations.Nullable;

import at.petrak.hexcasting.api.block.circle.BlockAbstractImpetus;
import at.petrak.hexcasting.common.blocks.circles.impetuses.BlockEntityLookingImpetus;
import at.petrak.hexcasting.common.lib.HexBlockEntities;
import net.mengie32.hexbubbles.Hexbubbles;
import net.mengie32.hexbubbles.blocks.HexbubblesBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockScanningImpetus extends BlockAbstractImpetus{
    public static final BooleanProperty ENERGIZED = BooleanProperty.of("energized");

    public BlockScanningImpetus(Settings p_49795_) {
        super(p_49795_);
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new BlockEntityScanningImpetus(pos, state);
    }

    // {@link BlockLookingImpetus}
    // uegh <- same
    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World pLevel, BlockState pState, BlockEntityType<T> type) {
        if (!pLevel.isClient) {
            return createTickerHelper(type, HexbubblesBlockEntities.IMPETUS_SCANNING, BlockEntityScanningImpetus::tick);
        } else {
            return null;
        }
    }
        
    @Nullable
    @SuppressWarnings("unchecked")
    protected static <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> 
    createTickerHelper(BlockEntityType<A> type, BlockEntityType<E> targetType, BlockEntityTicker<? super E> ticker) {
        return targetType == type ? (BlockEntityTicker<A>) ticker : null;
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
