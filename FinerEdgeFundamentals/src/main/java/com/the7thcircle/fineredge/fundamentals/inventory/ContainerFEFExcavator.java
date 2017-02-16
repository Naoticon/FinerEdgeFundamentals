package com.the7thcircle.fineredge.fundamentals.inventory;

import com.the7thcircle.fineredge.fundamentals.tileentity.TileEntityFEFExcavator;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerFEFExcavator extends ContainerFEFMachine {

	private int remainingCoolTime, currentItemCoolTime;
    private float temperature, progress, miningSpeed;
	
	public ContainerFEFExcavator(InventoryPlayer playerInventory, IInventory machineInventory) {
		super(playerInventory, machineInventory);
		for (int i = 0; i < 9; ++i){
        	this.addSlotToContainer(new SlotFEFMachineOutput(playerInventory.player, machineInventory, i, 8 + i * 18, 68));
        }
        
        this.addSlotToContainer(new SlotFEFMachineUpgrade(machineInventory, 9, 8, 17));
        this.addSlotToContainer(new SlotFEFMachineUpgrade(machineInventory, 10, 8, 39));
        this.addSlotToContainer(new SlotFEFExcavatorCoolant(machineInventory, 11, 152, 17));
        this.addSlotToContainer(new SlotFEFMachineOutput(playerInventory.player, machineInventory, 12, 152, 39));

        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlotToContainer(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 100 + i * 18));
            }
        }

        for (int k = 0; k < 9; ++k) {
            this.addSlotToContainer(new Slot(playerInventory, k, 8 + k * 18, 158));
        }
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = (Slot)this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (index >= 0 && index < 9) {
                if (!this.mergeItemStack(itemstack1, 13, 49, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onSlotChange(itemstack1, itemstack);
            }
            else if (index >= 13) {
                //This is a shift-click from player inventory
            	if(TileEntityFEFExcavator.isItemCoolant(itemstack)) {
            		Slot slot1 = (Slot)this.inventorySlots.get(11);
            		if(!slot1.getHasStack() || slot1.isItemValid(itemstack)){
            		}
            	}
            }
            else if (!this.mergeItemStack(itemstack1, 13, 49, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            }
            else {
                slot.onSlotChanged();
            }

            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(playerIn, itemstack1);
        }

        return itemstack;
    }
	
	public void detectAndSendChanges() {
        super.detectAndSendChanges();

        for (int i = 0; i < this.listeners.size(); ++i) {
            IContainerListener icontainerlistener = (IContainerListener)this.listeners.get(i);

            if (this.remainingCoolTime != this.tileMachine.getField(0)) {
                icontainerlistener.sendProgressBarUpdate(this, 0, this.tileMachine.getField(0));
            }

            if (this.temperature != this.tileMachine.getField(2)) {
                icontainerlistener.sendProgressBarUpdate(this, 2, this.tileMachine.getField(2));
            }
            
            if (this.progress != this.tileMachine.getField(3)) {
                icontainerlistener.sendProgressBarUpdate(this, 3, this.tileMachine.getField(3));
            }
        }

        this.remainingCoolTime = this.tileMachine.getField(0);
        this.currentItemCoolTime = this.tileMachine.getField(1);
        this.temperature = this.tileMachine.getField(2);
        this.progress = this.tileMachine.getField(3);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void updateProgressBar(int id, int data)
    {
        this.tileMachine.setField(id, data);
    }
}
