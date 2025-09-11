package net.mengie32.hexbubbles.entity.animation;

// Save this class in your mod and generate all required imports

import net.minecraft.client.render.entity.animation.Animation;
import net.minecraft.client.render.entity.animation.AnimationHelper;
import net.minecraft.client.render.entity.animation.Keyframe;
import net.minecraft.client.render.entity.animation.Transformation;

/**
 * Made with Blockbench 4.12.6
 * Exported for Minecraft version 1.19 or later with Yarn mappings
 * @author Author
 */
public class BubbleAnimations {
	public static final Animation IDLE = Animation.Builder.create(2.0F).looping()
		.addBoneAnimation("root", new Transformation(Transformation.Targets.ROTATE, 
			new Keyframe(0.0F, AnimationHelper.createRotationalVector(5.0F, -2.0F, 2.0F), Transformation.Interpolations.CUBIC),
			new Keyframe(0.1667F, AnimationHelper.createRotationalVector(4.4F, -1.59F, 2.15F), Transformation.Interpolations.CUBIC),
			new Keyframe(0.4583F, AnimationHelper.createRotationalVector(1.92F, 0.23F, 0.19F), Transformation.Interpolations.CUBIC),
			new Keyframe(0.7083F, AnimationHelper.createRotationalVector(-0.04F, 1.41F, -1.2F), Transformation.Interpolations.CUBIC),
			new Keyframe(0.9167F, AnimationHelper.createRotationalVector(-1.375F, 1.85F, -1.55F), Transformation.Interpolations.CUBIC),
			new Keyframe(1.125F, AnimationHelper.createRotationalVector(-1.57F, 1.53F, -1.13F), Transformation.Interpolations.CUBIC),
			new Keyframe(1.4167F, AnimationHelper.createRotationalVector(0.82F, 0.13F, -0.46F), Transformation.Interpolations.CUBIC),
			new Keyframe(1.6667F, AnimationHelper.createRotationalVector(3.31F, -1.2F, 0.23F), Transformation.Interpolations.CUBIC),
			new Keyframe(1.8333F, AnimationHelper.createRotationalVector(4.73F, -1.92F, 0.93F), Transformation.Interpolations.CUBIC),
			new Keyframe(2.0F, AnimationHelper.createRotationalVector(5.0F, -2.0F, 2.0F), Transformation.Interpolations.CUBIC)
		))
		.addBoneAnimation("root", new Transformation(Transformation.Targets.TRANSLATE, 
			new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 2.0F, 0.0F), Transformation.Interpolations.CUBIC),
			new Keyframe(0.25F, AnimationHelper.createTranslationalVector(-0.2F, 1.84F, 0.01F), Transformation.Interpolations.CUBIC),
			new Keyframe(0.5417F, AnimationHelper.createTranslationalVector(-0.05F, 1.8F, 0.07F), Transformation.Interpolations.CUBIC),
			new Keyframe(0.75F, AnimationHelper.createTranslationalVector(0.12F, 1.83F, 0.1F), Transformation.Interpolations.CUBIC),
			new Keyframe(1.0F, AnimationHelper.createTranslationalVector(0.0F, 1.96F, 0.1F), Transformation.Interpolations.CUBIC),
			new Keyframe(1.2083F, AnimationHelper.createTranslationalVector(-0.12F, 2.07F, 0.1F), Transformation.Interpolations.CUBIC),
			new Keyframe(1.4167F, AnimationHelper.createTranslationalVector(0.02F, 2.14F, 0.08F), Transformation.Interpolations.CUBIC),
			new Keyframe(1.6667F, AnimationHelper.createTranslationalVector(0.22F, 2.17F, 0.03F), Transformation.Interpolations.CUBIC),
			new Keyframe(2.0F, AnimationHelper.createTranslationalVector(0.0F, 2.0F, 0.0F), Transformation.Interpolations.CUBIC)
		))
		.addBoneAnimation("root", new Transformation(Transformation.Targets.SCALE, 
			new Keyframe(0.0F, AnimationHelper.createScalingVector(0.9F, 1.2F, 1.0F), Transformation.Interpolations.CUBIC),
			new Keyframe(0.125F, AnimationHelper.createScalingVector(0.918F, 1.2052F, 0.9451F), Transformation.Interpolations.CUBIC),
			new Keyframe(0.3333F, AnimationHelper.createScalingVector(0.9932F, 1.0419F, 0.9262F), Transformation.Interpolations.CUBIC),
			new Keyframe(0.625F, AnimationHelper.createScalingVector(1.0902F, 0.8211F, 0.9372F), Transformation.Interpolations.CUBIC),
			new Keyframe(0.8333F, AnimationHelper.createScalingVector(1.1176F, 0.7992F, 0.9621F), Transformation.Interpolations.CUBIC),
			new Keyframe(1.0F, AnimationHelper.createScalingVector(1.1F, 0.9F, 1.0F), Transformation.Interpolations.CUBIC),
			new Keyframe(1.125F, AnimationHelper.createScalingVector(1.0816F, 0.9701F, 1.0305F), Transformation.Interpolations.CUBIC),
			new Keyframe(1.3333F, AnimationHelper.createScalingVector(1.0332F, 1.031F, 1.0775F), Transformation.Interpolations.CUBIC),
			new Keyframe(1.625F, AnimationHelper.createScalingVector(0.9614F, 1.0959F, 1.1136F), Transformation.Interpolations.CUBIC),
			new Keyframe(1.8333F, AnimationHelper.createScalingVector(0.9147F, 1.1482F, 1.0836F), Transformation.Interpolations.CUBIC),
			new Keyframe(2.0F, AnimationHelper.createScalingVector(0.9F, 1.2F, 1.0F), Transformation.Interpolations.CUBIC)
		))
		.build();

	public static final Animation RIPPLE = Animation.Builder.create(1.5F)
		.addBoneAnimation("root", new Transformation(Transformation.Targets.SCALE, 
			new Keyframe(0.0F, AnimationHelper.createScalingVector(1.0F, 1.0F, 1.0F), Transformation.Interpolations.CUBIC),
			new Keyframe(0.0833F, AnimationHelper.createScalingVector(0.6273F, 1.6614F, 0.5619F), Transformation.Interpolations.CUBIC),
			new Keyframe(0.25F, AnimationHelper.createScalingVector(1.0F, 0.8F, 1.0F), Transformation.Interpolations.CUBIC),
			new Keyframe(0.3333F, AnimationHelper.createScalingVector(0.7302F, 1.0295F, 0.8206F), Transformation.Interpolations.CUBIC),
			new Keyframe(0.4167F, AnimationHelper.createScalingVector(0.7122F, 1.1151F, 0.7577F), Transformation.Interpolations.CUBIC),
			new Keyframe(0.5F, AnimationHelper.createScalingVector(1.0F, 0.9F, 1.0F), Transformation.Interpolations.CUBIC),
			new Keyframe(0.5833F, AnimationHelper.createScalingVector(0.803F, 1.0721F, 0.8856F), Transformation.Interpolations.CUBIC),
			new Keyframe(0.6667F, AnimationHelper.createScalingVector(0.9111F, 1.0937F, 0.9388F), Transformation.Interpolations.CUBIC),
			new Keyframe(0.75F, AnimationHelper.createScalingVector(1.0F, 0.9F, 1.0F), Transformation.Interpolations.CUBIC),
			new Keyframe(0.8333F, AnimationHelper.createScalingVector(0.8879F, 1.1208F, 1.0988F), Transformation.Interpolations.CUBIC),
			new Keyframe(1.0F, AnimationHelper.createScalingVector(1.05F, 1.05F, 0.95F), Transformation.Interpolations.CUBIC),
			new Keyframe(1.125F, AnimationHelper.createScalingVector(0.9503F, 1.0F, 0.9994F), Transformation.Interpolations.CUBIC),
			new Keyframe(1.25F, AnimationHelper.createScalingVector(0.95F, 0.95F, 1.05F), Transformation.Interpolations.CUBIC),
			new Keyframe(1.3333F, AnimationHelper.createScalingVector(0.9654F, 0.962F, 1.0357F), Transformation.Interpolations.CUBIC),
			new Keyframe(1.5F, AnimationHelper.createScalingVector(1.0F, 1.0F, 1.0F), Transformation.Interpolations.CUBIC)
		))
		.build();
}