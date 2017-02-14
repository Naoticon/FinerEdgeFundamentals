package com.the7thcircle.fineredge.fundamentals.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnaceFuel;
import net.minecraft.inventory.SlotFurnaceOutput;

public class ContainerFEFMachine extends Container {

    private final IInventory tileMachine;
	
	public ContainerFEFMachine(InventoryPlayer playerInventory, IInventory machineInventory){
		this.tileMachine = machineInventory;
		this.addSlotToContainer(new Slot(machineInventory, 0, 56, 17));
        this.addSlotToContainer(new SlotFurnaceFuel(machineInventory, 1, 56, 53));
        this.addSlotToContainer(new SlotFurnaceOutput(playerInventory.player, machineInventory, 2, 116, 35));

        for (int i = 0; i < 3; ++i)
        {
            for (int j = 0; j < 9; ++j)
            {
                this.addSlotToContainer(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for (int k = 0; k < 9; ++k)
        {
            this.addSlotToContainer(new Slot(playerInventory, k, 8 + k * 18, 142));
        }
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
        return this.tileMachine.isUsableByPlayer(playerIn);
	}

}
