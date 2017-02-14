package com.the7thcircle.fineredge.fundamentals.tileentity;

import com.the7thcircle.fineredge.fundamentals.inventory.ContainerFEFMachine;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerFurnace;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityLockable;
import net.minecraft.util.NonNullList;

public class TileEntityFEFMachine extends TileEntityLockable {

    private NonNullList<ItemStack> machineItemStacks = NonNullList.<ItemStack>withSize(3, ItemStack.EMPTY);
    private String machineCustomName;

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
        return (ItemStack)this.machineItemStacks.get(index);
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUsableByPlayer(EntityPlayer player) {
        return this.world.getTileEntity(this.pos) != this ? false : player.getDistanceSq((double)this.pos.getX() + 0.5D, (double)this.pos.getY() + 0.5D, (double)this.pos.getZ() + 0.5D) <= 64.0D;
	}

	@Override
	public void openInventory(EntityPlayer player) {
	}

	@Override
	public void closeInventory(EntityPlayer player) {
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getField(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setField(int id, int value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getFieldCount() {
		// TODO Auto-generated method stub
		return 0;
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
		// TODO Auto-generated method stub
		return null;
	}

}
