package com.the7thcircle.fineredge.fundamentals;

import com.the7thcircle.fineredge.fundamentals.gui.FEFGuiHandler;
import com.the7thcircle.fineredge.fundamentals.tileentity.FEFTileEntities;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class FEFCommonProxy {

	public void preInit(FMLPreInitializationEvent event){
		FEFTileEntities.registerTileEntities();
	}

	public void init(FMLInitializationEvent event){
		NetworkRegistry.INSTANCE.registerGuiHandler(FinerEdgeFundamentalsMod.INSTANCE, new FEFGuiHandler());
	}

	public void postInit(FMLPostInitializationEvent event){
	}
}
