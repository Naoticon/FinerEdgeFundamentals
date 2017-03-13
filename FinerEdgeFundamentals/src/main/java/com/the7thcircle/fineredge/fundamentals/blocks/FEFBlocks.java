package com.the7thcircle.fineredge.fundamentals.blocks;

import com.the7thcircle.fineredge.fundamentals.client.gui.FEFCreativeTabs;

import net.minecraft.block.Block;
import net.minecraft.block.BlockTorch;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod.EventBusSubscriber
public class FEFBlocks {
	
	public static BlockFEFExcavator excavator = (BlockFEFExcavator) new BlockFEFExcavator(Material.IRON, MapColor.GRAY).setRegistryName("excavator").setUnlocalizedName("excavator").setCreativeTab(FEFCreativeTabs.FEFUNDAMENTALS);
	public static BlockFEFExcavationMarker excavationMarker = (BlockFEFExcavationMarker) new BlockFEFExcavationMarker().setRegistryName("excavation_marker").setUnlocalizedName("excavation_marker").setCreativeTab(FEFCreativeTabs.FEFUNDAMENTALS);
	public static BlockFEFLogger logger = (BlockFEFLogger) new BlockFEFLogger(Material.IRON, MapColor.GRAY).setRegistryName("logger").setUnlocalizedName("logger").setCreativeTab(FEFCreativeTabs.FEFUNDAMENTALS);
	public static BlockFEFGardener gardener = (BlockFEFGardener) new BlockFEFGardener(Material.IRON, MapColor.GRAY).setRegistryName("gardener").setUnlocalizedName("gardener").setCreativeTab(FEFCreativeTabs.FEFUNDAMENTALS);
	
	public static ItemBlock excavatorItem = (ItemBlock) new ItemBlock(excavator).setRegistryName("excavator").setUnlocalizedName("excavator");
	public static ItemBlock excavationMarkerItem = (ItemBlock) new ItemBlock(excavationMarker).setRegistryName("excavation_marker").setUnlocalizedName("excavation_marker").setMaxStackSize(16);
	public static ItemBlock loggerItem = (ItemBlock) new ItemBlock(logger).setRegistryName("logger").setUnlocalizedName("logger");
	public static ItemBlock gardenerItem = (ItemBlock) new ItemBlock(gardener).setRegistryName("gardener").setUnlocalizedName("gardener");
	
	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event){
		event.getRegistry().registerAll(excavator, excavationMarker, logger, gardener);
	}
	
	@SubscribeEvent
	public static void registerBlockItems(RegistryEvent.Register<Item> event){
		event.getRegistry().registerAll(excavatorItem, excavationMarkerItem, loggerItem, gardenerItem);
	}
	
	public static void registerRenderers(){
		ModelLoader.setCustomModelResourceLocation(excavatorItem, 0, new ModelResourceLocation("fineredgefundamentals:excavator", "inventory"));
		ModelLoader.setCustomModelResourceLocation(excavationMarkerItem, 0, new ModelResourceLocation("fineredgefundamentals:excavation_marker", "inventory"));
		ModelLoader.setCustomModelResourceLocation(loggerItem, 0, new ModelResourceLocation("fineredgefundamentals:logger", "inventory"));
		ModelLoader.setCustomModelResourceLocation(gardenerItem, 0, new ModelResourceLocation("fineredgefundamentals:gardener", "inventory"));
	}
}
