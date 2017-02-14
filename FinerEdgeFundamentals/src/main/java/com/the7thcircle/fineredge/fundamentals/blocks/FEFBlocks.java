package com.the7thcircle.fineredge.fundamentals.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockOre;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod.EventBusSubscriber
public class FEFBlocks {
	
	public static BlockOre amanditeOre = (BlockOre) new BlockOre(MapColor.PURPLE).setRegistryName("amanditeOre");
	
	public static ItemBlock amanditeOreItem = (ItemBlock) new ItemBlock(amanditeOre).setRegistryName("amanditeOre");
	
	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event){
		event.getRegistry().registerAll(amanditeOre);
	}
	
	@SubscribeEvent
	public static void registerBlockItems(RegistryEvent.Register<Item> event){
		event.getRegistry().registerAll(amanditeOreItem);
	}
}
