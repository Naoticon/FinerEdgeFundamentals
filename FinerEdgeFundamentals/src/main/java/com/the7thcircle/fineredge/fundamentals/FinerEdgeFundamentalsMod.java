package com.the7thcircle.fineredge.fundamentals;

import com.the7thcircle.fineredge.fundamentals.blocks.FEFBlocks;
import com.the7thcircle.fineredge.fundamentals.items.FEFItems;

import net.minecraft.init.Blocks;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = FEFModProperties.MODID, version = FEFModProperties.VERSION)
public class FinerEdgeFundamentalsMod {
	
	@Instance(FEFModProperties.MODID)
	public static FinerEdgeFundamentalsMod INSTANCE;
	
	@SidedProxy(clientSide="com.the7thcircle.fineredge.fundamentals.FEFClientProxy", serverSide="com.the7thcircle.fineredge.fundamentals.FEFServerProxy")
	public static FEFCommonProxy proxy;
	
    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
    	this.proxy.preInit(event);
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event) {
    	this.proxy.init(event);
    }
    
    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
    	this.proxy.postInit(event);
    }
}
