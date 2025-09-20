package net.mengie32.hexbubbles.entity.custom;

import org.joml.Math;

import net.mengie32.hexbubbles.Hexbubbles;
import net.minecraft.entity.AnimationState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.PiglinBrain;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.vehicle.VehicleInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.Generic3x3ContainerScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

public class BubbleEntity extends Entity implements VehicleInventory {
    private static final TrackedData<NbtCompound> INVENTORY = DataTracker.registerData(BubbleEntity.class, TrackedDataHandlerRegistry.NBT_COMPOUND);
    private static final int INVENTORY_SIZE = 9; // Also need to change ScreenHandler
    private DefaultedList<ItemStack> inventory;
    private Identifier lootTableId;
    private long lootTableSeed;
    private boolean firstServerTick = true;

    public BubbleEntity(EntityType<? extends Entity> entityType, World world) {
        super(entityType, world);
        this.setNoGravity(true);
        this.setBoundingBox(this.calculateBoundingBox());
        this.inventory = DefaultedList.ofSize(INVENTORY_SIZE, ItemStack.EMPTY);
        this.intersectionChecked = true;
        
        if (this.getWorld().isClient()) {
            this.updateAnimations();
        }
    }

    @Override
    public boolean canHit(){
        return !this.isRemoved();
    }

    // Animation States:
    public final AnimationState rippleAnimationState = new AnimationState();
    public final AnimationState idleAnimationState = new AnimationState();

    @Override
    public void tick() {
        super.tick();

        if(firstServerTick && !this.getWorld().isClient()){
            firstServerTick = false;
            updateInvetoryDataTracker();
        }

        // if(this.getWorld().isClient()){
        //     Hexbubbles.LOGGER.info("Inventory Data Tracker (Client): " + this.dataTracker.get(INVENTORY).asString());
        //     Hexbubbles.LOGGER.info("Inventory (Client): " + this.getInventory().toString());
        // }else{
        //     Hexbubbles.LOGGER.info("Inventory Data Tracker (Server): " + this.dataTracker.get(INVENTORY).asString());
        //     Hexbubbles.LOGGER.info("Inventory (Server): " + this.getInventory().toString());
        // }

        updateLookDirection();
        
        updateVelocity();



        this.move(MovementType.SELF, this.getVelocity());
    }

    private void updateAnimations() {
        // Randomises bubble animation start to prevent synced animations on rejoin
        this.idleAnimationState.startIfNotRunning(this.age - Random.create().nextBetween(0,30));
    }

    private void updateVelocity(){
        Vec3d velocity = this.getVelocity();
        double speed = velocity.length(); 
        double dragMultiplier = speed > 0.25 ? 0.98 : 1;    // Slow down bubble only if speed is above 5 blocks/s
        this.setVelocity(velocity.multiply(dragMultiplier));
    }

    private void updateLookDirection(){
        // Set bubbles to 'look' in the direction they're moving for compatability with blink
        Vec3d velDir = this.getVelocity().normalize();
        Vec3d horzVelDir = new Vec3d(velDir.x, 0, velDir.z);
        Vec3d south = new Vec3d(0d, 0d, 1d);
        boolean stopped = (horzVelDir == Vec3d.ZERO);

        Float yaw = stopped ? 0f : (float) Math.toDegrees(Math.acos(horzVelDir.dotProduct(south)));
        yaw = yaw.isNaN() ? 0f : yaw;
        yaw = horzVelDir.x > 0 ? 360 - yaw : yaw;

        Float pitch = stopped ? 0f : (float) Math.toDegrees(Math.acos(velDir.dotProduct(horzVelDir)));
        pitch = pitch.isNaN() ? 0f : pitch;
        pitch = velDir.y > 0 ? -pitch : pitch;

        this.setYaw(yaw);
        this.setPitch(pitch);
        this.setRotation(this.getYaw(), this.getPitch());
    }

    public boolean damage(DamageSource source, float amount) {
        if (!this.getWorld().isClient && !this.isRemoved()) {
            this.discard();
            return true;
        }else{
            return true;
        }
    }

    // Inventory functions (copied & modified from chest boat)
    public void writeCustomDataToNbt(NbtCompound nbt) {
        this.writeInventoryToNbt(nbt);
    }

    public void readCustomDataFromNbt(NbtCompound nbt) {
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
        updateInvetoryDataTracker();
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
            return new Generic3x3ContainerScreenHandler(i, playerInventory, this);
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
        return inventory;
    }

    public void resetInventory() {
        this.inventory = DefaultedList.ofSize(this.size(), ItemStack.EMPTY);
        updateInvetoryDataTracker();
    }

    public void onClose(PlayerEntity player) {
    }

    @Override
    protected void initDataTracker() {
        this.getDataTracker().startTracking(INVENTORY, new NbtCompound());
    }


    // Data tracker access functions:
    // seperate functions to minimise packets being sent
    // the inventory variable should always take precedence over the data tracker
    // because there are too many methods that directly modify it
    private void updateInvetoryDataTracker(){
        if(!this.getWorld().isClient()){    // only server should be updating the data tracker
            NbtCompound nbt = Inventories.writeNbt(new NbtCompound(), inventory);
            // Hexbubbles.LOGGER.info("Bubble is sending a data packet: " + nbt.toString());
            this.getDataTracker().set(INVENTORY,nbt);
        }
    }

    public DefaultedList<ItemStack> getInventoryLive(){
        if(this.getWorld().isClient()){     // only client should bother reading the data tracker
            DefaultedList<ItemStack> liveInvetory = DefaultedList.ofSize(INVENTORY_SIZE, ItemStack.EMPTY);
            Inventories.readNbt(this.dataTracker.get(INVENTORY), liveInvetory);
            return liveInvetory;
        }else{
            return inventory;
        }
        
    }
}
