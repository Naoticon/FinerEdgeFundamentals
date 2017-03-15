package com.the7thcircle.fineredge.fundamentals.client.gui;


import com.the7thcircle.fineredge.fundamentals.inventory.ContainerFEFMachineInput;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

public class GuiFEFMachineInput extends GuiFEFMachine {
	private static final ResourceLocation MACHINE_INPUT_GUI_TEXTURE = new ResourceLocation("fineredgefundamentals", "textures/gui/container/machine_input.png");
	private final ContainerFEFMachineInput container;

	public GuiFEFMachineInput(Container inventorySlotsIn, InventoryPlayer playerInv, IInventory machineInventory){
		super(inventorySlotsIn, playerInv, machineInventory);

		this.playerInventory = playerInv;
        this.tileMachine = machineInventory;
		this.container = (ContainerFEFMachineInput) this.inventorySlots;

		this.xSize = 176;
		this.ySize = 203;
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(MACHINE_INPUT_GUI_TEXTURE);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);

        int tempSize = this.getTemperatureScaled(38);
        this.drawTexturedModalRect(i + 137, j + 55 - tempSize, 10, 246 - tempSize, 10, tempSize);
        
        int progressSize = this.getProgressScaled(160);
        this.drawTexturedModalRect(i + 8, j + 59, 0, 208, progressSize, 5);
        
        int energySize = this.getEnergyScaled(38);
        this.drawTexturedModalRect(i + 29, j + 55 - energySize, 0, 246 - energySize, 10, energySize);
	}
}
