package com.the7thcircle.fineredge.fundamentals.inventory;

import com.the7thcircle.fineredge.fundamentals.tileentity.TileEntityFEFMachine;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

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
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot sourceSlot = this.inventorySlots.get(index);

        if (sourceSlot != null && sourceSlot.getHasStack()) {
            ItemStack itemstack1 = sourceSlot.getStack();
            itemstack = itemstack1.copy();

            if (index >= 0 && index < 9) {
                if (!this.mergeItemStack(itemstack1, 22, 58, true)) {
                    return ItemStack.EMPTY;
                }

                sourceSlot.onSlotChange(itemstack1, itemstack);
            }
            else if (index >= 13 && index < 22) {
                if (!this.mergeItemStack(itemstack1, 22, 58, true)) {
                    return ItemStack.EMPTY;
                }

                sourceSlot.onSlotChange(itemstack1, itemstack);
            }
            else if (index >= 22) {
                //This is a shift-click from player inventory
            	if(TileEntityFEFMachine.isItemCoolant(itemstack)) {
            		if(this.mergeItemStack(itemstack1, 11, 12, false)) return ItemStack.EMPTY;
            	}
            	if (TileEntityFEFMachine.isItemUpgrade(itemstack)){
            		if(this.mergeItemStack(itemstack1, 9, 11, false)) return ItemStack.EMPTY;
            	}
            	else if(this.mergeItemStack(itemstack1, 13, 22, false)) return ItemStack.EMPTY;
            }
            else if (!this.mergeItemStack(itemstack1, 22, 58, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                sourceSlot.putStack(ItemStack.EMPTY);
            }
            else {
                sourceSlot.onSlotChanged();
            }

            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            sourceSlot.onTake(playerIn, itemstack1);
        }

        return itemstack;
    }
}
