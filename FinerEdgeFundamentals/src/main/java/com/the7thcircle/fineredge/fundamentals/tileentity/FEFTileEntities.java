package com.the7thcircle.fineredge.fundamentals.tileentity;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class FEFTileEntities {

	public static void registerTileEntities(){
		GameRegistry.registerTileEntity(TileEntityFEFMachine.class, "machine");
		GameRegistry.registerTileEntity(TileEntityFEFMachineInput.class, "machine_input");
		GameRegistry.registerTileEntity(TileEntityFEFExcavator.class, "excavator");
		GameRegistry.registerTileEntity(TileEntityFEFLogger.class, "logger");
		GameRegistry.registerTileEntity(TileEntityFEFGardener.class, "gardener");
	}
}
