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
import net.mengie32.hexbubbles.entity.HexbubblesEntities;
import net.mengie32.hexbubbles.entity.custom.BubbleEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public final class BubbleSpawnEmpty implements ConstMediaAction{
   private static final int argc = 1;

   @Override
   public List<Iota> execute(List<? extends Iota> iotas, CastingEnvironment env){
      final World world = env.getWorld();
      final Vec3d vec = OperatorUtils.getVec3(iotas, 0, argc);
      //final BlockPos pos = OperatorUtils.getBlockPos(iotas, 0, argc);

      // Ambit & in-world Check:
      MishapWrapper.assertVecInRange(env, vec);

      BubbleEntity bubble = new BubbleEntity(HexbubblesEntities.BUBBLE, world);
      bubble.setPosition(vec);
      world.spawnEntity(bubble);
      return List.of();

   }

   public long getMediaCost() {
      return 20000L;
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
