package com.the7thcircle.fineredge.fundamentals.items.crafting;

import com.the7thcircle.fineredge.fundamentals.blocks.FEFBlocks;
import com.the7thcircle.fineredge.fundamentals.items.FEFItems;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod.EventBusSubscriber
public class FEFRecipes {

	@SubscribeEvent
	public static void registerRecipes(RegistryEvent.Register<Item> event){
		GameRegistry.addShapelessRecipe(new ItemStack(FEFItems.ACTIVATED_ENDER_EYE, 1), Items.ENDER_EYE, Items.DIAMOND);
		GameRegistry.addShapelessRecipe(new ItemStack(FEFItems.UPGRADE_LIQUID_PUMP, 1), FEFItems.UPGRADE_BASE, Items.BUCKET);
		GameRegistry.addShapelessRecipe(new ItemStack(FEFBlocks.EXCAVATION_MARKER, 16), FEFItems.ACTIVATED_ENDER_EYE, Blocks.TORCH, FEFBlocks.SCORCHED_STONE);

		GameRegistry.addShapedRecipe(new ItemStack(FEFBlocks.DARKENED_STONE, 8),
				"sss",
				"sfs",
				"sss",
				's', Blocks.STONE, 'f', Items.FIRE_CHARGE);		
		GameRegistry.addShapedRecipe(new ItemStack(FEFItems.UPGRADE_BASE, 4),
				"sss",
				"sds",
				's', FEFBlocks.SCORCHED_STONE, 'd', Items.DIAMOND);		
		GameRegistry.addShapedRecipe(new ItemStack(FEFItems.UPGRADE_STONE_FILL, 1),
				"sss",
				"wul",
				"sss",
				's', FEFBlocks.SCORCHED_STONE, 'u', FEFItems.UPGRADE_BASE, 'w', Items.WATER_BUCKET, 'l', Items.LAVA_BUCKET);		
		GameRegistry.addShapedRecipe(new ItemStack(FEFItems.UPGRADE_SPEED, 2),
				"u",
				"d",
				"u",
				'u', FEFItems.UPGRADE_BASE, 'd', Items.DIAMOND);		
		GameRegistry.addShapedRecipe(new ItemStack(FEFItems.UPGRADE_SPEED_DOWN, 2),
				"u",
				"s",
				"u",
				'u', FEFItems.UPGRADE_BASE, 's', Items.SLIME_BALL);		
		GameRegistry.addShapedRecipe(new ItemStack(FEFItems.UPGRADE_RANGE, 1),
				" g ",
				"gug",
				" g ",
				'u', FEFItems.UPGRADE_BASE, 'g', Items.GOLD_INGOT);		
		GameRegistry.addShapedRecipe(new ItemStack(FEFItems.UPGRADE_RANGE_DOWN, 1),
				" c ",
				"cuc",
				" c ",
				'u', FEFItems.UPGRADE_BASE, 'c', Blocks.COBBLESTONE);		
		GameRegistry.addShapedRecipe(new ItemStack(FEFItems.UPGRADE_FORTUNE, 1),
				"lll",
				"lul",
				"lll",
				'u', FEFItems.UPGRADE_BASE, 'l', Blocks.LAPIS_BLOCK);		
		GameRegistry.addShapedRecipe(new ItemStack(FEFItems.UPGRADE_SILK_TOUCH, 1),
				"ggg",
				"gug",
				"ggg",
				'u', FEFItems.UPGRADE_BASE, 'g', Blocks.GOLD_BLOCK);		
		GameRegistry.addShapedRecipe(new ItemStack(FEFBlocks.EXCAVATOR, 1),
				"sss",
				"sps",
				"ses",
				's', FEFBlocks.SCORCHED_STONE, 'p', Items.DIAMOND_PICKAXE, 'e', FEFItems.ACTIVATED_ENDER_EYE);
		GameRegistry.addShapedRecipe(new ItemStack(FEFBlocks.LOGGER, 1),
				"sss",
				"sas",
				"ses",
				's', FEFBlocks.SCORCHED_STONE, 'a', Items.DIAMOND_AXE, 'e', FEFItems.ACTIVATED_ENDER_EYE);
		GameRegistry.addShapedRecipe(new ItemStack(FEFBlocks.GARDENER, 1),
				"sss",
				"shs",
				"ses",
				's', FEFBlocks.SCORCHED_STONE, 'h', Items.DIAMOND_HOE, 'e', FEFItems.ACTIVATED_ENDER_EYE);
		
		GameRegistry.addSmelting(FEFBlocks.DARKENED_STONE, new ItemStack(FEFBlocks.SCORCHED_STONE, 1), 0);
	}
}
