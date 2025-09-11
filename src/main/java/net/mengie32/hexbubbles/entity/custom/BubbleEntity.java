package net.mengie32.hexbubbles.entity.custom;

import net.minecraft.entity.AnimationState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WanderAroundGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;

public class BubbleEntity extends AnimalEntity{

    //Animation States:
    private int rippleAnimationCooldown = 0;
    public final AnimationState rippleAnimationState = new AnimationState();
    public final AnimationState idleAnimationState = new AnimationState();

    public BubbleEntity(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
        //TODO Auto-generated constructor stub
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new WanderAroundGoal(this, 1.0));
    }

    public static DefaultAttributeContainer.Builder createBubbleAttributes(){
        return MobEntity.createMobAttributes()
            .add(EntityAttributes.GENERIC_MAX_HEALTH,2.0)
            .add(EntityAttributes.GENERIC_MOVEMENT_SPEED,0.2);
        }
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createChild'");
    }

    @Override
    public void tick() {
        super.tick();
        if (this.getWorld().isClient()) {
			this.updateAnimations();
		}
    }

    private void updateAnimations() {
        if(rippleAnimationCooldown <= 0){
            rippleAnimationCooldown = 100;
            this.rippleAnimationState.start(this.age);
        }else{
            this.rippleAnimationCooldown--;
        }

        this.idleAnimationState.startIfNotRunning(this.age);
    }

}
