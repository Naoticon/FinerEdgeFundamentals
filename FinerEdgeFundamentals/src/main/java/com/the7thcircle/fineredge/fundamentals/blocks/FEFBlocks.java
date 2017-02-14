package com.the7thcircle.fineredge.fundamentals.blocks;

import com.the7thcircle.fineredge.fundamentals.client.gui.FEFCreativeTabs;

import net.minecraft.block.Block;
import net.minecraft.block.BlockOre;
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
	
	public static BlockOre amanditeOre = (BlockOre) new BlockOre(MapColor.PURPLE).setRegistryName("amanditeOre");
	
	public static ItemBlock amanditeOreItem = (ItemBlock) new ItemBlock(amanditeOre).setRegistryName("amanditeOre");
	
	public static BlockFEFMachine excavator = (BlockFEFMachine) new BlockFEFMachine(Material.IRON, MapColor.GRAY).setRegistryName("excavator").setUnlocalizedName("excavator").setCreativeTab(FEFCreativeTabs.FEFUNDAMENTALS);
	
	public static ItemBlock excavatorItem = (ItemBlock) new ItemBlock(excavator).setRegistryName("excavator").setUnlocalizedName("excavator");
	
	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event){
		event.getRegistry().registerAll(amanditeOre, excavator);
	}
	
	@SubscribeEvent
	public static void registerBlockItems(RegistryEvent.Register<Item> event){
		event.getRegistry().registerAll(amanditeOreItem, excavatorItem);
	}
	
	public static void registerRenderers(){
		ModelLoader.setCustomModelResourceLocation(excavatorItem, 0, new ModelResourceLocation("fineredgefundamentals:excavator", "inventory"));
	}
}
