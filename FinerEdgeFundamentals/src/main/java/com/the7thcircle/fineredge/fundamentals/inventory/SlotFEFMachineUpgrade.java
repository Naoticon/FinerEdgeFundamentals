package com.the7thcircle.fineredge.fundamentals.inventory;

import com.the7thcircle.fineredge.fundamentals.items.ItemFEFMachineUpgrade;

import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class SlotFEFMachineUpgrade extends Slot {

	public SlotFEFMachineUpgrade(IInventory inventoryIn, int index, int xPosition, int yPosition) {
		super(inventoryIn, index, xPosition, yPosition);
	}

	@Override
	public boolean isItemValid(ItemStack stack) {
        return stack.getItem() instanceof ItemFEFMachineUpgrade || stack.getItem() == Item.getItemFromBlock(Blocks.REDSTONE_TORCH);
    }
}
