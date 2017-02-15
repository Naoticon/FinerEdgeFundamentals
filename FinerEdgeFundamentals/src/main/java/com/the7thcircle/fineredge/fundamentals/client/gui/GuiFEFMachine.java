package com.the7thcircle.fineredge.fundamentals.client.gui;

import org.lwjgl.util.glu.Project;

import com.the7thcircle.fineredge.fundamentals.inventory.ContainerFEFMachine;
import com.the7thcircle.fineredge.fundamentals.tileentity.TileEntityFEFMachine;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerEnchantment;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.EnchantmentNameParts;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class GuiFEFMachine extends GuiContainer{
 
    private final ContainerFEFMachine container;
	
	protected final InventoryPlayer playerInventory;
	protected final IInventory tileMachine;
	
	public GuiFEFMachine(Container inventorySlotsIn, InventoryPlayer playerInv, IInventory machineInventory){
		super(inventorySlotsIn);

		this.playerInventory = playerInv;
        this.tileMachine = machineInventory;
		this.container = (ContainerFEFMachine) this.inventorySlots;

		this.xSize = 176;
		this.ySize = 166;
	}

	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
    }
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {        
	}
}
