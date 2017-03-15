package com.the7thcircle.fineredge.fundamentals.gui;

import com.the7thcircle.fineredge.fundamentals.client.gui.GuiFEFMachine;
import com.the7thcircle.fineredge.fundamentals.client.gui.GuiFEFMachineInput;
import com.the7thcircle.fineredge.fundamentals.inventory.ContainerFEFMachine;
import com.the7thcircle.fineredge.fundamentals.inventory.ContainerFEFMachineInput;
import com.the7thcircle.fineredge.fundamentals.tileentity.TileEntityFEFExcavator;
import com.the7thcircle.fineredge.fundamentals.tileentity.TileEntityFEFGardener;
import com.the7thcircle.fineredge.fundamentals.tileentity.TileEntityFEFLogger;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class FEFGuiHandler implements IGuiHandler{

	public static final int FEF_EXCAVATOR_GUI = 0;
	public static final int FEF_LOGGER_GUI = 1;
	public static final int FEF_GARDENER_GUI = 2;

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		switch(ID){
		case FEF_EXCAVATOR_GUI:
			return ((TileEntityFEFExcavator) world.getTileEntity(new BlockPos(x, y, z))).createContainer(player.inventory, player);
		case FEF_LOGGER_GUI:
			return ((TileEntityFEFLogger) world.getTileEntity(new BlockPos(x, y, z))).createContainer(player.inventory, player);
		case FEF_GARDENER_GUI:
			return((TileEntityFEFGardener) world.getTileEntity(new BlockPos(x, y, z))).createContainer(player.inventory, player);
		default:
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		switch(ID){
		case FEF_EXCAVATOR_GUI:
			return new GuiFEFMachine(new ContainerFEFMachine(player.inventory, (TileEntityFEFExcavator) world.getTileEntity(new BlockPos(x, y, z))), player.inventory, (TileEntityFEFExcavator) world.getTileEntity(new BlockPos(x, y, z)));
		case FEF_LOGGER_GUI:
			return new GuiFEFMachineInput(new ContainerFEFMachineInput(player.inventory, (TileEntityFEFLogger) world.getTileEntity(new BlockPos(x, y, z))), player.inventory, (TileEntityFEFLogger) world.getTileEntity(new BlockPos(x, y, z)));
		case FEF_GARDENER_GUI:
			return new GuiFEFMachineInput(new ContainerFEFMachineInput(player.inventory, (TileEntityFEFGardener) world.getTileEntity(new BlockPos(x, y, z))), player.inventory, (TileEntityFEFGardener) world.getTileEntity(new BlockPos(x, y, z)));
		default:
		}
		return null;
	}

}
