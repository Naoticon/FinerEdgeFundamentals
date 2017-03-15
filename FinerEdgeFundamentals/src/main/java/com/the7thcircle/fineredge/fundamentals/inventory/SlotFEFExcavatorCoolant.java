package com.the7thcircle.fineredge.fundamentals.inventory;

import com.the7thcircle.fineredge.fundamentals.tileentity.TileEntityFEFMachine;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotFEFExcavatorCoolant extends Slot{

	public SlotFEFExcavatorCoolant(IInventory inventoryIn, int index, int xPosition, int yPosition) {
		super(inventoryIn, index, xPosition, yPosition);
	}

	@Override
	public boolean isItemValid(ItemStack stack) {
        return TileEntityFEFMachine.isItemCoolant(stack);
    }
}
