package com.the7thcircle.fineredge.fundamentals.tileentity;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import com.the7thcircle.fineredge.fundamentals.blocks.BlockFEFExcavator;
import com.the7thcircle.fineredge.fundamentals.blocks.BlockFEFMachine;
import com.the7thcircle.fineredge.fundamentals.inventory.ContainerFEFMachine;
import com.the7thcircle.fineredge.fundamentals.items.FEFItems;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityLockable;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.energy.IEnergyStorage;

public class TileEntityFEFMachine extends TileEntityLockable implements ITickable, ISidedInventory, IEnergyStorage{

	protected static final int MACHINE_SLOTS = 13;
	private static final int[] SLOTS_ALL = new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8};
	protected NonNullList<ItemStack> machineItemStacks = NonNullList.<ItemStack>withSize(MACHINE_SLOTS, ItemStack.EMPTY);

	protected String machineCustomName;
	protected int remainingCoolTime, storedEnergy, maxStoredEnergy = 50000, energyUseRate = 20;
	protected boolean isJammed = false;
	protected float temperature, progress, miningSpeed, currBlockProgress;
	protected ItemStack jammedStack = ItemStack.EMPTY;

	@Override
	public int getSizeInventory() {
		return this.machineItemStacks.size();
	}

	@Override
	public boolean isEmpty() {
		for (ItemStack itemstack : this.machineItemStacks)
		{
			if (!itemstack.isEmpty())
			{
				return false;
			}
		}

		return true;
	}

	@Override
	public ItemStack getStackInSlot(int index) {
		return this.machineItemStacks.get(index);
	}

	@Override
	public ItemStack decrStackSize(int index, int count) {
		return ItemStackHelper.getAndSplit(this.machineItemStacks, index, count);
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {
		return ItemStackHelper.getAndRemove(this.machineItemStacks, index);
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		ItemStack itemstack = this.machineItemStacks.get(index);
		boolean flag = !stack.isEmpty() && stack.isItemEqual(itemstack) && ItemStack.areItemStackTagsEqual(stack, itemstack);
		this.machineItemStacks.set(index, stack);

		if (stack.getCount() > this.getInventoryStackLimit()) {
			stack.setCount(this.getInventoryStackLimit());
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		super.readFromNBT(compound);
		this.machineItemStacks = NonNullList.<ItemStack>withSize(this.getSizeInventory(), ItemStack.EMPTY);
		ItemStackHelper.loadAllItems(compound, this.machineItemStacks);

		this.remainingCoolTime = compound.getInteger("RemainingCoolTime");
		this.storedEnergy = compound.getInteger("StoredEnergy");
		this.temperature = compound.getFloat("Temperature");
		this.progress = compound.getFloat("Progress");
		this.miningSpeed = compound.getFloat("MiningSpeed");
		this.isJammed = compound.getBoolean("IsJammed");

		if(compound.hasKey("JammedStack")) {
			this.jammedStack = new ItemStack(compound.getCompoundTag("JammedStack"));
		}

		if (compound.hasKey("CustomName", 8))
		{
			this.machineCustomName = compound.getString("CustomName");
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		super.writeToNBT(compound);
		
		ItemStackHelper.saveAllItems(compound, this.machineItemStacks);

		compound.setInteger("RemainingCoolTime", this.remainingCoolTime);
		compound.setInteger("StoredEnergy", this.storedEnergy);
		compound.setFloat("Temperature", this.temperature);
		compound.setFloat("Progress", this.progress);
		compound.setFloat("MiningSpeed", this.miningSpeed);
		compound.setBoolean("IsJammed", this.isJammed);

		if(!this.jammedStack.isEmpty()) {
			NBTTagCompound jammedStackCompound = new NBTTagCompound();
			this.jammedStack.writeToNBT(jammedStackCompound);
			compound.setTag("JammedStack", jammedStackCompound);
		}

		if (this.hasCustomName())
		{
			compound.setString("CustomName", this.machineCustomName);
		}

		return compound;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUsableByPlayer(EntityPlayer player) {
		return this.world.getTileEntity(this.pos) != this ? false : player.getDistanceSq(this.pos.getX() + 0.5D, this.pos.getY() + 0.5D, this.pos.getZ() + 0.5D) <= 64.0D;
	}

	@Override
	public void openInventory(EntityPlayer player) {
	}

	@Override
	public void closeInventory(EntityPlayer player) {
	}

	@Override
	public int getField(int id) {
		switch(id){
		case 0: return this.remainingCoolTime;
		case 1: return (int) this.miningSpeed;
		case 2: return (int) this.temperature;
		case 3: return (int) this.progress;
		case 4: return this.storedEnergy;
		case 5: return this.energyUseRate;
		}
		return 0;
	}

	@Override
	public void setField(int id, int value){
		switch (id)
		{
		case 0:
			this.remainingCoolTime = value;
			break;
		case 1:
			this.miningSpeed = value;
			break;
		case 2:
			this.temperature = value;
			break;
		case 3:
			this.progress = value;
			break;
		case 4:
			this.storedEnergy = value;
			break;
		case 5:
			this.energyUseRate = value;
			break;
		}
	}

	@Override
	public int getFieldCount() {
		return 6;
	}

	@Override
	public void clear() {
		this.machineItemStacks.clear();
	}

	@Override
	public String getName() {
		return this.hasCustomName() ? this.machineCustomName : "container.machine";
	}

	@Override
	public boolean hasCustomName() {
		return this.machineCustomName != null && !this.machineCustomName.isEmpty();
	}

	public void setCustomInventoryName(String name){
		this.machineCustomName = name;
	}

	@Override
	public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
		return new ContainerFEFMachine(playerInventory, this);
	}

	@Override
	public String getGuiID() {
		return null;
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		if(index == 11) return isItemCoolant(stack);
		return false;
	}

	@Override
	public int[] getSlotsForFace(EnumFacing side)
	{
		if(side == this.world.getBlockState(this.pos).getValue(BlockFEFMachine.FACING) || side == this.world.getBlockState(this.pos).getValue(BlockFEFMachine.FACING).getOpposite()) return null;
		return SLOTS_ALL;
	}

	@Override
	public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction)
	{
		if(index > 8) return false;
		return true;
	}

	@Override
	public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction)
	{
		if(index >= 9 && (index != 9 && index != 10 && index != 12)) return this.isItemValidForSlot(index, itemStackIn);
		return false;
	}

	public boolean isActive(){
		if(this.storedEnergy == 0 || this.energyUseRate == 0) return false;
		return (this.temperature < 95.f && this.storedEnergy >= this.energyUseRate && !this.isJammed);
	}
	
	protected void handleJammedItem(NonNullList<ItemStack> machineStacks){
		if(this.jammedStack != ItemStack.EMPTY) this.isJammed = true;
		if(this.isJammed){
			if(!this.world.isRemote){
				for(int i = 0; i < 9; ++i){
					if(this.jammedStack.getCount() > 0){
						if(machineStacks.get(i).isEmpty()){
							machineStacks.set(i, this.jammedStack);
							this.jammedStack = ItemStack.EMPTY;
							break;
						}
						if(machineStacks.get(i).isItemEqual(this.jammedStack)){
							if(machineStacks.get(i).getCount() + this.jammedStack.getCount() <= machineStacks.get(i).getMaxStackSize()){
								machineStacks.get(i).setCount(machineStacks.get(i).getCount() + this.jammedStack.getCount());
								this.jammedStack = ItemStack.EMPTY;
								break;
							}
							else{
								int toSplit = machineStacks.get(i).getMaxStackSize() - machineStacks.get(i).getCount();
								machineStacks.get(i).setCount(machineStacks.get(i).getMaxStackSize());
								this.jammedStack.shrink(toSplit);
							}
						}
					}
				}
				if(this.jammedStack.getCount() == 0){
					this.isJammed = false;
					this.jammedStack = ItemStack.EMPTY;
				}
			}
		}
	}
	
	protected boolean checkUpgradesUpdateProperties(NonNullList<ItemStack> machineStacks){
		boolean shouldUpdate = false;
		if(!this.world.isRemote){
			boolean wasActive = this.world.getBlockState(this.pos).getValue(BlockFEFMachine.ACTIVE);
			int numSpeedUpgrades = (machineStacks.get(9).getItem().equals(FEFItems.UPGRADE_SPEED) ? machineStacks.get(9).getCount() : 0) + (machineStacks.get(10).getItem().equals(FEFItems.UPGRADE_SPEED) ? machineStacks.get(10).getCount() : 0);
			int numSpeedDownUpgrades = (machineStacks.get(9).getItem().equals(FEFItems.UPGRADE_SPEED_DOWN) ? machineStacks.get(9).getCount() : 0) + (machineStacks.get(10).getItem().equals(FEFItems.UPGRADE_SPEED_DOWN) ? machineStacks.get(10).getCount() : 0);
			this.miningSpeed = (float) ((1.f + (1.f * numSpeedUpgrades)) * (Math.pow(0.9f, numSpeedDownUpgrades)));
			this.energyUseRate = (int) (20.f * (numSpeedUpgrades + 1) * (Math.pow(0.95f, numSpeedDownUpgrades)));
			if(this.storedEnergy < this.energyUseRate) this.energyUseRate = 0;

			if (this.isActive()){
				if (this.storedEnergy >= this.energyUseRate) this.storedEnergy -= this.energyUseRate;
				if (this.remainingCoolTime > 0){
					if(this.temperature > 0) this.temperature -= 0.05f;
					--this.remainingCoolTime;
				}
				else if(this.temperature < 95.f) this.temperature += 0.025f;
			}
			else if(this.temperature > 0.f) this.temperature -= 0.05f;
			
			ItemStack itemstack = machineStacks.get(11);
			if(this.isActive() && this.remainingCoolTime == 0 && !itemstack.isEmpty()){

				this.remainingCoolTime = getItemCoolTime(itemstack);

				shouldUpdate = true;

				Item item = itemstack.getItem();
				itemstack.shrink(1);
				if (itemstack.isEmpty())
				{
					ItemStack item1 = item.getContainerItem(itemstack);
					machineStacks.set(11, item1);
				}
			}

			
			if (wasActive != this.isActive())
			{
				shouldUpdate = true;
				BlockFEFMachine.setMachineActive(this.isActive(), this.world, this.pos);
			}
		}
		return shouldUpdate;
	}

	@Override
	public void update() {
	}

	protected void harvestBlock(BlockPos blockPos, int fortuneLevel, boolean silkTouch, NonNullList<ItemStack> machineStacks){

		if(silkTouch){
			ItemStack blockItemStack = TileEntityFEFMachine.getSilkDrop(this.world, blockPos);
			for(int i = 0; i < 9; ++i){
				if(blockItemStack.getCount() > 0){
					if(machineStacks.get(i).isEmpty()){
						machineStacks.set(i, blockItemStack);
						blockItemStack = ItemStack.EMPTY;
						break;
					}
					if(machineStacks.get(i).isItemEqual(blockItemStack)){
						if(machineStacks.get(i).getCount() + blockItemStack.getCount() <= machineStacks.get(i).getMaxStackSize()){
							machineStacks.get(i).setCount(machineStacks.get(i).getCount() + blockItemStack.getCount());
							blockItemStack = ItemStack.EMPTY;
							break;
						}
						else{
							int toSplit = machineStacks.get(i).getMaxStackSize() - machineStacks.get(i).getCount();
							machineStacks.get(i).setCount(machineStacks.get(i).getMaxStackSize());
							blockItemStack.shrink(toSplit);
						}
					}
				}
			}
			if(blockItemStack.getCount() > 0){
				this.isJammed = true;
				this.jammedStack = blockItemStack;
			}
		}

		else{
			List<ItemStack> blockDrops = this.world.getBlockState(blockPos).getBlock().getDrops(world, blockPos, this.world.getBlockState(blockPos), fortuneLevel);
			for(int i = 0; i < blockDrops.size(); ++i){
				ItemStack currItemStack = blockDrops.get(i);
				for(int i2 = 0; i2 < 9; ++i2){
					if(currItemStack.getCount() > 0){
						if(machineStacks.get(i2).isEmpty()){
							machineStacks.set(i2, currItemStack);
							currItemStack = ItemStack.EMPTY;
							break;
						}
						if(machineStacks.get(i2).isItemEqual(currItemStack)){
							if(machineStacks.get(i2).getCount() + currItemStack.getCount() <= machineStacks.get(i2).getMaxStackSize()){
								machineStacks.get(i2).setCount(machineStacks.get(i2).getCount() + currItemStack.getCount());
								currItemStack = ItemStack.EMPTY;
								break;
							}
							else{
								int toSplit = machineStacks.get(i2).getMaxStackSize() - machineStacks.get(i2).getCount();
								machineStacks.get(i2).setCount(machineStacks.get(i2).getMaxStackSize());
								currItemStack.shrink(toSplit);
							}
						}
					}
				}
				if(currItemStack.getCount() > 0){
					this.isJammed = true;
					this.jammedStack = currItemStack;
				}
			}
		}

		if(machineStacks.get(9).getItem().equals(FEFItems.UPGRADE_STONE_FILL) || machineStacks.get(10).getItem().equals(FEFItems.UPGRADE_STONE_FILL)){
			this.world.setBlockState(blockPos, Blocks.STONE.getDefaultState(), world.isRemote ? 11 : 3);
		}
		else{
			world.setBlockState(blockPos, net.minecraft.init.Blocks.AIR.getDefaultState(), world.isRemote ? 11 : 3);
		}
	}

	net.minecraftforge.items.IItemHandler handlerTop = new net.minecraftforge.items.wrapper.SidedInvWrapper(this, net.minecraft.util.EnumFacing.UP);
	net.minecraftforge.items.IItemHandler handlerBottom = new net.minecraftforge.items.wrapper.SidedInvWrapper(this, net.minecraft.util.EnumFacing.DOWN);
	net.minecraftforge.items.IItemHandler handlerSide = new net.minecraftforge.items.wrapper.SidedInvWrapper(this, net.minecraft.util.EnumFacing.WEST);
	
	@Override
	public <T> T getCapability(net.minecraftforge.common.capabilities.Capability<T> capability, @javax.annotation.Nullable net.minecraft.util.EnumFacing facing)
	{
		if (facing != null && capability == net.minecraftforge.items.CapabilityItemHandler.ITEM_HANDLER_CAPABILITY){
			if (facing == EnumFacing.DOWN) return (T) handlerBottom;
			else if (facing == EnumFacing.UP) return (T) handlerTop;
			else return (T) handlerSide;
		}
		else if (capability == net.minecraftforge.energy.CapabilityEnergy.ENERGY){
			return (T) this;
		}
		return super.getCapability(capability, facing);
	}
	
	@Override
    public boolean hasCapability(net.minecraftforge.common.capabilities.Capability<?> capability, @javax.annotation.Nullable net.minecraft.util.EnumFacing facing)
    {
        return capability == net.minecraftforge.energy.CapabilityEnergy.ENERGY || super.hasCapability(capability, facing);
    }

	public static int getItemCoolTime(ItemStack stack) {
		if (stack.isEmpty()) {
			return 0;
		}
		else {
			Item item = stack.getItem();

			if (item instanceof net.minecraft.item.ItemBlock && Block.getBlockFromItem(item) != Blocks.AIR) {
				Block block = Block.getBlockFromItem(item);

				if (block == Blocks.SNOW) {
					return 2000;
				}

				if (block == Blocks.ICE) {
					return 8000;
				}


				if (block == Blocks.PACKED_ICE) {
					return 16000;
				}
			}
			if (item == Items.SNOWBALL) return 500;
			return 0;
		}
	}

	public static boolean isItemCoolant(ItemStack stack){
		return getItemCoolTime(stack) > 0;
	}

	public static ItemStack getSilkDrop(World world, BlockPos pos)
	{
		Class block_class = world.getBlockState(pos).getBlock().getClass();
		Method getSilkTouchDrop = null;
		try {
			getSilkTouchDrop = block_class.getDeclaredMethod("getSilkTouchDrop", IBlockState.class);
		} catch (NoSuchMethodException e1) {
			boolean noMethod = true;
			while(noMethod){
				noMethod = false;
				block_class = block_class.getSuperclass();
				try {
					getSilkTouchDrop = block_class.getDeclaredMethod("getSilkTouchDrop", IBlockState.class);
				} catch (NoSuchMethodException e) {
					noMethod = true;
				} catch (SecurityException e) {
					e.printStackTrace();
				}
			}
		} catch (SecurityException e1) {
			e1.printStackTrace();
			return ItemStack.EMPTY;
		}

		if(!getSilkTouchDrop.isAccessible()) getSilkTouchDrop.setAccessible(true);
		IBlockState state = world.getBlockState(pos);
		ItemStack silk_drops = ItemStack.EMPTY;
		try {
			silk_drops = (ItemStack) getSilkTouchDrop.invoke(world.getBlockState(pos).getBlock(), state);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			return ItemStack.EMPTY;
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			return ItemStack.EMPTY;
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			return ItemStack.EMPTY;
		}
		return silk_drops;
	}
	
	public int getEnergyUseRate(){
		return this.energyUseRate;
	}

	@Override
	public int receiveEnergy(int maxReceive, boolean simulate) {
		if(this.storedEnergy >= this.maxStoredEnergy) return 0;
		else if(this.maxStoredEnergy - this.storedEnergy < maxReceive){
			int oldStoredEnergy = this.storedEnergy;
			if(!simulate) this.storedEnergy = this.maxStoredEnergy;
			return this.maxStoredEnergy - oldStoredEnergy;
		}
		else {
			if(!simulate) this.storedEnergy += maxReceive;
		}
		return maxReceive;
	}

	@Override
	public int extractEnergy(int maxExtract, boolean simulate) {
		return 0;
	}

	@Override
	public int getEnergyStored() {
		return this.storedEnergy;
	}

	@Override
	public int getMaxEnergyStored() {
		return this.maxStoredEnergy;
	}

	@Override
	public boolean canExtract() {
		return false;
	}

	@Override
	public boolean canReceive() {
		return this.storedEnergy < this.maxStoredEnergy;
	}
}
