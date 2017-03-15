package com.the7thcircle.fineredge.fundamentals.blocks;

import com.the7thcircle.fineredge.fundamentals.client.gui.FEFCreativeTabs;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class FEFBlocks {
	
	public static final BlockFEFExcavator EXCAVATOR = (BlockFEFExcavator) new BlockFEFExcavator(Material.IRON, MapColor.GRAY).setRegistryName("excavator").setUnlocalizedName("excavator").setCreativeTab(FEFCreativeTabs.FEFUNDAMENTALS);
	public static final BlockFEFExcavationMarker EXCAVATION_MARKER = (BlockFEFExcavationMarker) new BlockFEFExcavationMarker().setRegistryName("excavation_marker").setUnlocalizedName("excavation_marker").setCreativeTab(FEFCreativeTabs.FEFUNDAMENTALS);
	public static final BlockFEFLogger LOGGER = (BlockFEFLogger) new BlockFEFLogger(Material.IRON, MapColor.GRAY).setRegistryName("logger").setUnlocalizedName("logger").setCreativeTab(FEFCreativeTabs.FEFUNDAMENTALS);
	public static final BlockFEFGardener GARDENER = (BlockFEFGardener) new BlockFEFGardener(Material.IRON, MapColor.GRAY).setRegistryName("gardener").setUnlocalizedName("gardener").setCreativeTab(FEFCreativeTabs.FEFUNDAMENTALS);
	public static final Block DARKENED_STONE = new Block(Material.ROCK, MapColor.BLACK).setRegistryName("darkened_stone").setUnlocalizedName("darkened_stone").setCreativeTab(FEFCreativeTabs.FEFUNDAMENTALS).setHardness(1.5f).setResistance(10.f);
	public static final Block SCORCHED_STONE = new Block(Material.ROCK, MapColor.BLACK).setRegistryName("scorched_stone").setUnlocalizedName("scorched_stone").setCreativeTab(FEFCreativeTabs.FEFUNDAMENTALS).setHardness(3.f).setResistance(20.f);
	
	private static final ItemBlock EXCAVATOR_ITEM = (ItemBlock) new ItemBlock(EXCAVATOR).setRegistryName("excavator").setUnlocalizedName("excavator");
	private static final ItemBlock EXCAVATION_MARKER_ITEM = (ItemBlock) new ItemBlock(EXCAVATION_MARKER).setRegistryName("excavation_marker").setUnlocalizedName("excavation_marker").setMaxStackSize(16);
	private static final ItemBlock LOGGER_ITEM = (ItemBlock) new ItemBlock(LOGGER).setRegistryName("logger").setUnlocalizedName("logger");
	private static final ItemBlock GARDENER_ITEM = (ItemBlock) new ItemBlock(GARDENER).setRegistryName("gardener").setUnlocalizedName("gardener");
	private static final ItemBlock DARKENED_STONE_ITEM = (ItemBlock) new ItemBlock(DARKENED_STONE).setRegistryName("darkened_stone").setUnlocalizedName("darkened_stone");
	private static final ItemBlock SCORCHED_STONE_ITEM = (ItemBlock) new ItemBlock(SCORCHED_STONE).setRegistryName("scorched_stone").setUnlocalizedName("scorched_stone");
	
	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event){
		event.getRegistry().registerAll(EXCAVATOR, EXCAVATION_MARKER, LOGGER, GARDENER, DARKENED_STONE, SCORCHED_STONE);
	}
	
	@SubscribeEvent
	public static void registerBlockItems(RegistryEvent.Register<Item> event){
		event.getRegistry().registerAll(EXCAVATOR_ITEM, EXCAVATION_MARKER_ITEM, LOGGER_ITEM, GARDENER_ITEM, DARKENED_STONE_ITEM, SCORCHED_STONE_ITEM);
	}
	
	public static void registerRenderers(){
		ModelLoader.setCustomModelResourceLocation(EXCAVATOR_ITEM, 0, new ModelResourceLocation("fineredgefundamentals:excavator", "inventory"));
		ModelLoader.setCustomModelResourceLocation(EXCAVATION_MARKER_ITEM, 0, new ModelResourceLocation("fineredgefundamentals:excavation_marker", "inventory"));
		ModelLoader.setCustomModelResourceLocation(LOGGER_ITEM, 0, new ModelResourceLocation("fineredgefundamentals:logger", "inventory"));
		ModelLoader.setCustomModelResourceLocation(GARDENER_ITEM, 0, new ModelResourceLocation("fineredgefundamentals:gardener", "inventory"));
		ModelLoader.setCustomModelResourceLocation(DARKENED_STONE_ITEM, 0, new ModelResourceLocation("fineredgefundamentals:darkened_stone", "inventory"));
		ModelLoader.setCustomModelResourceLocation(SCORCHED_STONE_ITEM, 0, new ModelResourceLocation("fineredgefundamentals:scorched_stone", "inventory"));
	}
}
