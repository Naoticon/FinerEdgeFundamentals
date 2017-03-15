package com.the7thcircle.fineredge.fundamentals.tileentity;

import com.the7thcircle.fineredge.fundamentals.blocks.BlockFEFExcavator;
import com.the7thcircle.fineredge.fundamentals.blocks.BlockFEFMachine;
import com.the7thcircle.fineredge.fundamentals.inventory.ContainerFEFMachineInput;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;

public class TileEntityFEFMachineInput extends TileEntityFEFMachine {
	
	protected static final int MACHINE_SLOTS = 22;
	protected NonNullList<ItemStack> machineItemStacks = NonNullList.<ItemStack>withSize(MACHINE_SLOTS, ItemStack.EMPTY);
	protected static final int[] SLOTS_ALL = new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 13, 14, 15, 16, 17, 18, 19, 20, 21};

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
    }

	@Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        ItemStackHelper.saveAllItems(compound, this.machineItemStacks);

        return compound;
    }

	@Override
	public void clear() {
		this.machineItemStacks.clear();
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		if(index == 11) return isItemCoolant(stack);
		return false;
	}

	@Override
	public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
        return new ContainerFEFMachineInput(playerInventory, this);
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
		if((index >= 9 && index < 13) && (index != 9 && index != 10 && index != 12)) return this.isItemValidForSlot(index, itemStackIn);
		else if(index >= 13 && index < 22) return true;
		return false;
	}
}
