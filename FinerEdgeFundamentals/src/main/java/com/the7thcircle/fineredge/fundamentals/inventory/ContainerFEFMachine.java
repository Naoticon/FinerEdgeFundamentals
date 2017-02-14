package com.the7thcircle.fineredge.fundamentals.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;

public class ContainerFEFMachine extends Container {

    private final IInventory tileMachine;
	
	public ContainerFEFMachine(InventoryPlayer playerInventory, IInventory machineInventory){
		this.tileMachine = machineInventory;
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
        return this.tileMachine.isUsableByPlayer(playerIn);
	}

}
