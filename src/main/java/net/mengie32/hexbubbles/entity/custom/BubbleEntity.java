package net.mengie32.hexbubbles.entity.custom;

import java.util.List;

import at.petrak.hexcasting.api.mod.HexTags.Items;
import net.minecraft.entity.AnimationState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Arm;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

public class BubbleEntity extends LivingEntity{

    public BubbleEntity(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
        //TODO Auto-generated constructor stub
    }

    //Animation States:
    private int rippleAnimationCooldown = 0;
    public final AnimationState rippleAnimationState = new AnimationState();
    public final AnimationState idleAnimationState = new AnimationState();


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

    @Override
    public void equipStack(EquipmentSlot slot, ItemStack stack) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'equipStack'");
    }

    @Override
    public Iterable<ItemStack> getArmorItems() {
        return DefaultedList.ofSize(1,ItemStack.EMPTY);
    }

    @Override
    public ItemStack getEquippedStack(EquipmentSlot slot) {
        return ItemStack.EMPTY;
    }

    @Override
    public Arm getMainArm() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getMainArm'");
    }
}
