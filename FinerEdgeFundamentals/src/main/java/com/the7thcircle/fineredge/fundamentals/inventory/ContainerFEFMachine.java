package com.the7thcircle.fineredge.fundamentals.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnaceFuel;
import net.minecraft.inventory.SlotFurnaceOutput;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.tileentity.TileEntityFurnace;

public class ContainerFEFMachine extends Container {

    protected final IInventory tileMachine;
	
	public ContainerFEFMachine(InventoryPlayer playerInventory, IInventory machineInventory){
		this.tileMachine = machineInventory;
	}
	
	@Override
	public void addListener(IContainerListener listener) {
        super.addListener(listener);
        listener.sendAllWindowProperties(this, this.tileMachine);
    }
	
	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
        return this.tileMachine.isUsableByPlayer(playerIn);
	}
}
