package com.the7thcircle.fineredge.fundamentals.inventory;

import com.the7thcircle.fineredge.fundamentals.tileentity.TileEntityFEFMachine;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerFEFMachine extends Container {

	protected final IInventory tileMachine;
	protected int remainingCoolTime, currentItemCoolTime, storedEnergy, energyUseRate;
    protected float temperature, progress, miningSpeed;
	

	public ContainerFEFMachine(InventoryPlayer playerInventory, IInventory machineInventory){
		this.tileMachine = machineInventory;
		for (int i = 0; i < 9; ++i){
			this.addSlotToContainer(new SlotFEFMachineOutput(playerInventory.player, machineInventory, i, 8 + i * 18, 68));
		}

		this.addSlotToContainer(new SlotFEFMachineUpgrade(machineInventory, 9, 8, 17));
		this.addSlotToContainer(new SlotFEFMachineUpgrade(machineInventory, 10, 8, 39));
		this.addSlotToContainer(new SlotFEFExcavatorCoolant(machineInventory, 11, 152, 17));
		this.addSlotToContainer(new SlotFEFMachineOutput(playerInventory.player, machineInventory, 12, 152, 39));
		
		this.addPlayerSlots(playerInventory);
	}
	
	protected void addPlayerSlots(InventoryPlayer playerInventory){
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
	public void addListener(IContainerListener listener) {
		super.addListener(listener);
		listener.sendAllWindowProperties(this, this.tileMachine);
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return this.tileMachine.isUsableByPlayer(playerIn);
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot sourceSlot = this.inventorySlots.get(index);

        if (sourceSlot != null && sourceSlot.getHasStack()) {
            ItemStack itemstack1 = sourceSlot.getStack();
            itemstack = itemstack1.copy();

            if (index >= 0 && index < 9) {
                if (!this.mergeItemStack(itemstack1, 13, 49, true)) {
                    return ItemStack.EMPTY;
                }

                sourceSlot.onSlotChange(itemstack1, itemstack);
            }
            else if (index >= 13) {
                //This is a shift-click from player inventory
            	if(TileEntityFEFMachine.isItemCoolant(itemstack)) {
            		if(this.mergeItemStack(itemstack1, 11, 12, false)) return ItemStack.EMPTY;
            	}
            	if (TileEntityFEFMachine.isItemUpgrade(itemstack)){
            		if(this.mergeItemStack(itemstack1, 9, 11, false)) return ItemStack.EMPTY;
            	}
            }
            else if (!this.mergeItemStack(itemstack1, 13, 49, false)) {
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
	
	@Override
	public void detectAndSendChanges() {
        super.detectAndSendChanges();

        for (int i = 0; i < this.listeners.size(); ++i) {
            IContainerListener icontainerlistener = this.listeners.get(i);

            if (this.remainingCoolTime != this.tileMachine.getField(0)) {
                icontainerlistener.sendProgressBarUpdate(this, 0, this.tileMachine.getField(0));
            }

            if (this.temperature != this.tileMachine.getField(2)) {
                icontainerlistener.sendProgressBarUpdate(this, 2, this.tileMachine.getField(2));
            }
            
            if (this.progress != this.tileMachine.getField(3)) {
                icontainerlistener.sendProgressBarUpdate(this, 3, this.tileMachine.getField(3));
            }
            
            if (this.storedEnergy != this.tileMachine.getField(4)){
            	icontainerlistener.sendProgressBarUpdate(this, 4, this.tileMachine.getField(4));
            }
            
            if (this.energyUseRate != this.tileMachine.getField(5)){
            	icontainerlistener.sendProgressBarUpdate(this, 5, this.tileMachine.getField(5));
            }
        }

        this.remainingCoolTime = this.tileMachine.getField(0);
        this.currentItemCoolTime = this.tileMachine.getField(1);
        this.temperature = this.tileMachine.getField(2);
        this.progress = this.tileMachine.getField(3);
        this.storedEnergy = this.tileMachine.getField(4);
        this.energyUseRate = this.tileMachine.getField(5);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void updateProgressBar(int id, int data)
    {
        this.tileMachine.setField(id, data);
    }
}
