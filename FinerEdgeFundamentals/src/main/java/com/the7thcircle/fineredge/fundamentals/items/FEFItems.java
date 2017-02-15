package com.the7thcircle.fineredge.fundamentals.items;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod.EventBusSubscriber
public class FEFItems {

	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event){
		event.getRegistry().registerAll();
	}
	
	public static void registerRenderers(){
		//ModelResourceLocation itemModelResourceLocation = new ModelResourceLocation("null", "inventory");
		//ModelLoader.setCustomModelResourceLocation(null, null, null);
	}
}
