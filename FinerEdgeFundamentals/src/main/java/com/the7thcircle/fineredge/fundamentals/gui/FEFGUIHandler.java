package com.the7thcircle.fineredge.fundamentals.gui;

import com.the7thcircle.fineredge.fundamentals.client.gui.GUIFEFMachine;
import com.the7thcircle.fineredge.fundamentals.tileentity.TileEntityFEFMachine;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class FEFGUIHandler implements IGuiHandler{

	public static final int FEF_MACHINE_GUI = 0;

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		switch(ID){
		case FEF_MACHINE_GUI:
			return ((TileEntityFEFMachine) world.getTileEntity(new BlockPos(x, y, z))).createContainer(player.inventory, player);
		default:
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		switch(ID){
		case FEF_MACHINE_GUI:
			return new GUIFEFMachine(player.inventory, ((TileEntityFEFMachine) world.getTileEntity(new BlockPos(x, y, z))));
		default:
		}
		return null;
	}

}
