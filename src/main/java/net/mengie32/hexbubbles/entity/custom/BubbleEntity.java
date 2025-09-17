package net.mengie32.hexbubbles.entity.custom;

import org.joml.Math;

import net.minecraft.entity.AnimationState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.PiglinBrain;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.vehicle.VehicleInventory;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Arm;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

public class BubbleEntity extends LivingEntity implements VehicleInventory {
    DefaultedList<ItemStack> inventory;
    private static final int INVENTORY_SIZE = 27;
    private Identifier lootTableId;
    private long lootTableSeed;

    public BubbleEntity(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
        this.setNoGravity(true);
        this.setNoDrag(true);
        this.setBoundingBox(this.calculateBoundingBox());
        this.inventory = DefaultedList.ofSize(INVENTORY_SIZE, ItemStack.EMPTY);
    }

    public static DefaultAttributeContainer.Builder createBubbleAttributes() {
        return DefaultAttributeContainer.builder()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 1f)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1f)
                .add(EntityAttributes.GENERIC_ARMOR, 0f)
                .add(EntityAttributes.GENERIC_ARMOR_TOUGHNESS, 0f);
    }

    // Animation States:
    public final AnimationState rippleAnimationState = new AnimationState();
    public final AnimationState idleAnimationState = new AnimationState();

    @Override
    public void tick() {
        super.tick();

        // Set bubbles to look in the direction they're moving for compatability with blink
        Vec3d VelDir = this.getVelocity().normalize();
        Vec3d horzVelDir = new Vec3d(VelDir.x, 0, VelDir.z);
        Vec3d south = new Vec3d(0d, 0d, 1d);
        boolean stopped = (horzVelDir == Vec3d.ZERO);

        Float yaw = stopped ? 0f : (float) Math.toDegrees(Math.acos(horzVelDir.dotProduct(south)));
        yaw = yaw.isNaN() ? 0f : yaw;
        yaw = horzVelDir.x > 0 ? 360 - yaw : yaw;

        Float pitch = stopped ? 0f : (float) Math.toDegrees(Math.acos(VelDir.dotProduct(horzVelDir)));
        pitch = pitch.isNaN() ? 0f : pitch;
        pitch = VelDir.z > 0 ? -pitch : pitch;

        this.setYaw(yaw);
        this.setPitch(pitch);
        this.setRotation(this.getYaw(), this.getPitch());

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
        return DefaultedList.ofSize(1, ItemStack.EMPTY);
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

    // Inventory functions (copied from chest boat)

    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        this.writeInventoryToNbt(nbt);
    }

    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.readInventoryFromNbt(nbt);
    }

    public void dropItems(DamageSource source) {
        this.onBroken(source, this.getWorld(), this);
    }

    public void remove(RemovalReason reason) {
        if (!this.getWorld().isClient && reason.shouldDestroy()) {
            ItemScatterer.spawn(this.getWorld(), this, this);
        }

        super.remove(reason);
    }

    public ActionResult interact(PlayerEntity player, Hand hand) {
        ActionResult actionResult = this.open(player);
        if (actionResult.isAccepted()) {
            this.emitGameEvent(GameEvent.CONTAINER_OPEN, player);
            PiglinBrain.onGuardedBlockInteracted(player, true);
        }
        return actionResult;
    }

    public void openInventory(PlayerEntity player) {
        player.openHandledScreen(this);
    }

    public void clear() {
        this.clearInventory();
    }

    public int size() {
        return INVENTORY_SIZE;
    }

    public ItemStack getStack(int slot) {
        return this.getInventoryStack(slot);
    }

    public ItemStack removeStack(int slot, int amount) {
        return this.removeInventoryStack(slot, amount);
    }

    public ItemStack removeStack(int slot) {
        return this.removeInventoryStack(slot);
    }

    public void setStack(int slot, ItemStack stack) {
        this.setInventoryStack(slot, stack);
    }

    public StackReference getStackReference(int mappedIndex) {
        return this.getInventoryStackReference(mappedIndex);
    }

    public void markDirty() {
    }

    public boolean canPlayerUse(PlayerEntity player) {
        return this.canPlayerAccess(player);
    }

    public ScreenHandler createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        if (this.lootTableId != null && playerEntity.isSpectator()) {
            return null;
        } else {
            this.generateLoot(playerInventory.player);
            return GenericContainerScreenHandler.createGeneric9x3(i, playerInventory, this);
        }
    }

    public void generateLoot(PlayerEntity player) {
        this.generateInventoryLoot(player);
    }

    public Identifier getLootTableId() {
        return this.lootTableId;
    }

    public void setLootTableId(Identifier lootTableId) {
        this.lootTableId = lootTableId;
    }

    public long getLootTableSeed() {
        return this.lootTableSeed;
    }

    public void setLootTableSeed(long lootTableSeed) {
        this.lootTableSeed = lootTableSeed;
    }

    public DefaultedList<ItemStack> getInventory() {
        return this.inventory;
    }

    public void resetInventory() {
        this.inventory = DefaultedList.ofSize(this.size(), ItemStack.EMPTY);
    }

    public void onClose(PlayerEntity player) {
    }
}
