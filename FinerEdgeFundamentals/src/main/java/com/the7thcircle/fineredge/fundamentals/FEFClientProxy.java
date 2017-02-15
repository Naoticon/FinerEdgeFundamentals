package com.the7thcircle.fineredge.fundamentals;

import com.the7thcircle.fineredge.fundamentals.blocks.FEFBlocks;
import com.the7thcircle.fineredge.fundamentals.items.FEFItems;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class FEFClientProxy extends FEFCommonProxy{

	@Override
	public void preInit(FMLPreInitializationEvent event){
		super.preInit(event);
		FEFBlocks.registerRenderers();
		FEFItems.registerRenderers();
	}
	
	@Override
	public void init(FMLInitializationEvent event){
		super.init(event);
	}
	
	@Override
	public void postInit(FMLPostInitializationEvent event){
		super.postInit(event);
	}
}
