package net.mengie32.hexbubbles.patterns;

import java.util.List;

import org.jetbrains.annotations.NotNull;

import at.petrak.hexcasting.api.casting.OperatorUtils;
import at.petrak.hexcasting.api.casting.castables.ConstMediaAction;
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment;
import at.petrak.hexcasting.api.casting.eval.OperationResult;
import at.petrak.hexcasting.api.casting.eval.vm.CastingImage;
import at.petrak.hexcasting.api.casting.eval.vm.SpellContinuation;
import at.petrak.hexcasting.api.casting.iota.Iota;
import at.petrak.hexcasting.api.casting.math.HexPattern;
import at.petrak.hexcasting.api.casting.mishaps.MishapBadBlock;
import at.petrak.hexcasting.common.blocks.circles.BlockEntitySlate;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public final class WriteSlate implements ConstMediaAction{
   private static final int argc = 2;

   @Override
   public List<Iota> execute(List<? extends Iota> iotas, CastingEnvironment env){
      final Vec3d vec = OperatorUtils.getVec3(iotas, 0, argc);
      final BlockPos pos = OperatorUtils.getBlockPos(iotas, 0, argc);
      final HexPattern pattern = OperatorUtils.getPattern(iotas, 1, argc);

      // Ambit & in-world Check:
      MishapWrapper.assertVecInRange(env, vec);

      // Check that the targeted pos can accept a pattern:
      try{
         final BlockEntitySlate slate = (BlockEntitySlate)env.getWorld().getBlockEntity(pos);
         slate.pattern = pattern;
         slate.sync();
      }catch(Exception e){
         MishapWrapper.throwMishap(MishapBadBlock.of(pos, "pattern_writable_block"));
      }
      return List.of();
   }

   public long getMediaCost() {
      return DefaultImpls.getMediaCost(this);
   }

   @NotNull
   public ConstMediaAction.CostMediaActionResult executeWithOpCount(@NotNull List<? extends Iota> args, @NotNull CastingEnvironment env) {
      return DefaultImpls.executeWithOpCount(this, args, env);
   }

   @NotNull
   public OperationResult operate(@NotNull CastingEnvironment env, @NotNull CastingImage image, @NotNull SpellContinuation continuation) {
      return DefaultImpls.operate(this, env, image, continuation);
   }

   @Override
   public int getArgc() {
      return argc;
   }

}
