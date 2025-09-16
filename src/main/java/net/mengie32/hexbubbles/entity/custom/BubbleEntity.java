package net.mengie32.hexbubbles.entity.custom;

import java.util.Vector;

import org.joml.Math;

import at.petrak.hexcasting.api.utils.MathUtils;
import net.mengie32.hexbubbles.Hexbubbles;
import net.minecraft.entity.AnimationState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Arm;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class BubbleEntity extends LivingEntity{

    public BubbleEntity(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
        this.setNoGravity(true);
        this.setNoDrag(true);
        this.setBoundingBox(this.calculateBoundingBox());
    }

    public static DefaultAttributeContainer.Builder createBubbleAttributes(){
        return DefaultAttributeContainer.builder()
            .add(EntityAttributes.GENERIC_MAX_HEALTH, 1f)
            .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1f)
            .add(EntityAttributes.GENERIC_ARMOR, 0f)
            .add(EntityAttributes.GENERIC_ARMOR_TOUGHNESS, 0f);
    }

    //Animation States:
    public final AnimationState rippleAnimationState = new AnimationState();
    public final AnimationState idleAnimationState = new AnimationState();


    @Override
    public void tick() {
        super.tick();

        // Set bubbles to look in the direction they're moving
        Vec3d VelDir = this.getVelocity().normalize();
        Vec3d horzVelDir = new Vec3d(VelDir.x,0,VelDir.z);
        Vec3d south = new Vec3d(0d,0d,1d);

        float yaw = horzVelDir == Vec3d.ZERO ? 0f : (float)Math.toDegrees(Math.acos(horzVelDir.dotProduct(south)));
        yaw = horzVelDir.x>0 ? 360-yaw : yaw;
        float pitch = (float)Math.toDegrees(Math.acos(VelDir.dotProduct(horzVelDir)));
        pitch = VelDir.z>0 ? -pitch : pitch;
        // Hexbubbles.LOGGER.info("velDir="+ String.valueOf(velDir)+"  Yaw=" + String.valueOf(yaw));

        this.setYaw(yaw);
        this.setPitch(pitch);
        this.setRotation(this.getYaw(),this.getPitch());

        // Disable fall damage (there may be a better way to do this?)
        this.fallDistance = 0;

        if (this.getWorld().isClient()) {
			this.updateAnimations();
		}
    }

    private void updateAnimations() {
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
