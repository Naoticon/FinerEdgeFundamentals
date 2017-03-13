package com.the7thcircle.fineredge.fundamentals.inventory;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

public class ContainerFEFMachineInput extends ContainerFEFMachine {

	public ContainerFEFMachineInput(InventoryPlayer playerInventory, IInventory machineInventory) {
		super(playerInventory, machineInventory);
		for (int i = 0; i < 9; ++i){
			this.addSlotToContainer(new Slot(machineInventory, i + 13, 8 + i * 18, 89));
		}
	}

	@Override
	protected void addPlayerSlots(InventoryPlayer playerInventory){
		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 9; ++j) {
				this.addSlotToContainer(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 100 + i * 18 + 21));
			}
		}

		for (int k = 0; k < 9; ++k) {
			this.addSlotToContainer(new Slot(playerInventory, k, 8 + k * 18, 158 + 21));
		}
	}
}
