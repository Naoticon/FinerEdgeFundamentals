package com.the7thcircle.fineredge.fundamentals.client.gui;

import java.util.List;

import org.lwjgl.util.glu.Project;

import com.google.common.collect.Lists;
import com.the7thcircle.fineredge.fundamentals.inventory.ContainerFEFMachine;
import com.the7thcircle.fineredge.fundamentals.tileentity.TileEntityFEFMachine;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerEnchantment;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.EnchantmentNameParts;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;

public class GuiFEFMachine extends GuiContainer{
 
	private static final ResourceLocation MACHINE_GUI_TEXTURE = new ResourceLocation("fineredgefundamentals", "textures/gui/container/machine.png");
	private final ContainerFEFMachine container;
    protected int progress, temperature, energy;
	
	protected InventoryPlayer playerInventory;
	protected IInventory tileMachine;

	public GuiFEFMachine(Container inventorySlotsIn, InventoryPlayer playerInv, IInventory machineInventory){
		super(inventorySlotsIn);

		this.playerInventory = playerInv;
        this.tileMachine = machineInventory;
		this.container = (ContainerFEFMachine) this.inventorySlots;

		this.xSize = 176;
		this.ySize = 182;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        String s = this.tileMachine.getDisplayName().getUnformattedText();
        this.fontRendererObj.drawString(s, this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2, 6, 4210752);
        this.fontRendererObj.drawString(this.playerInventory.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, 4210752);
    }
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(MACHINE_GUI_TEXTURE);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);

        int tempSize = this.getTemperatureScaled(38);
        this.drawTexturedModalRect(i + 137, j + 55 - tempSize, 10, 225 - tempSize, 10, tempSize);
        
        int progressSize = this.getProgressScaled(160);
        this.drawTexturedModalRect(i + 8, j + 59, 0, 182, progressSize, 5);
        
        int energySize = this.getEnergyScaled(38);
        this.drawTexturedModalRect(i + 29, j + 55 - energySize, 0, 225 - energySize, 10, energySize);
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		super.drawScreen(mouseX, mouseY, partialTicks);
		this.temperature = this.tileMachine.getField(2);
		this.progress = this.tileMachine.getField(3);
		this.energy = ((TileEntityFEFMachine) this.tileMachine).getEnergyStored();
		
		if (this.isPointInRegion(137, 17, 10, 38, mouseX, mouseY)) {
            List<String> list = Lists.<String>newArrayList();
            TextFormatting temperatureColor;
            if(this.temperature < 12) temperatureColor = TextFormatting.DARK_BLUE;
            else if(this.temperature < 24) temperatureColor = TextFormatting.BLUE;
            else if(this.temperature < 36) temperatureColor = TextFormatting.DARK_AQUA;
            else if(this.temperature < 48) temperatureColor = TextFormatting.AQUA;
            else if(this.temperature < 60) temperatureColor = TextFormatting.GREEN;
            else if(this.temperature < 72) temperatureColor = TextFormatting.YELLOW;
            else if(this.temperature < 84) temperatureColor = TextFormatting.GOLD;
            else temperatureColor = TextFormatting.RED;
            list.add("" + TextFormatting.WHITE + I18n.format("container.machine.temperature_indicator") + ": " + temperatureColor + TextFormatting.BOLD + this.temperature + "°C");

            this.drawHoveringText(list, mouseX, mouseY);
        }
		else if(this.isPointInRegion(8, 59, 160, 5, mouseX, mouseY)){
			List<String> list = Lists.<String>newArrayList();
            list.add("" + TextFormatting.WHITE + I18n.format("container.machine.progress_indicator") + ": " + TextFormatting.BOLD + this.progress + "%");

            this.drawHoveringText(list, mouseX, mouseY);
		}
		else if(this.isPointInRegion(29, 17, 10, 38, mouseX, mouseY)){
			List<String> list = Lists.<String>newArrayList();
            list.add("" + TextFormatting.YELLOW + TextFormatting.BOLD + this.energy + "RF");
            list.add("" + TextFormatting.GRAY + I18n.format("container.machine.energy_consumption") + " " + "XX" + "RF/t");

            this.drawHoveringText(list, mouseX, mouseY);
		}
	}

    protected int getTemperatureScaled(int pixels)
    {
        int i = this.tileMachine.getField(2);
        return (int) ((float)pixels * (((float)i) / 94.f));
    }
    
    protected int getProgressScaled(int pixels){
        int i = this.tileMachine.getField(3);
        return (int) ((float)pixels * (((float)i) / 99.f));
    }
    
    protected int getEnergyScaled(int pixels){
    	int i = ((TileEntityFEFMachine) this.tileMachine).getEnergyStored();
    	return (int) ((float)pixels * (((float)i) / ((TileEntityFEFMachine) this.tileMachine).getMaxEnergyStored()));
    }
}
