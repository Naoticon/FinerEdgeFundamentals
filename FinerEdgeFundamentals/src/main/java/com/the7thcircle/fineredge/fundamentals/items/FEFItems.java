package com.the7thcircle.fineredge.fundamentals.items;

import com.the7thcircle.fineredge.fundamentals.client.gui.FEFCreativeTabs;

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
	
	public static Item upgradeBase = new Item().setRegistryName("upgrade_base").setUnlocalizedName("upgrade_base").setCreativeTab(FEFCreativeTabs.FEFUNDAMENTALS);
	public static Item upgradeSpeed = new Item().setRegistryName("upgrade_speed").setUnlocalizedName("upgrade_speed").setCreativeTab(FEFCreativeTabs.FEFUNDAMENTALS);
	public static Item upgradeSpeedDown = new Item().setRegistryName("upgrade_speed_down").setUnlocalizedName("upgrade_speed_down").setCreativeTab(FEFCreativeTabs.FEFUNDAMENTALS);
	public static Item upgradeSilkTouch = new Item().setRegistryName("upgrade_silk_touch").setUnlocalizedName("upgrade_silk_touch").setCreativeTab(FEFCreativeTabs.FEFUNDAMENTALS);
	public static Item upgradeFortune = new Item().setRegistryName("upgrade_fortune").setUnlocalizedName("upgrade_fortune").setCreativeTab(FEFCreativeTabs.FEFUNDAMENTALS);
	public static Item upgradeLiquidPump = new Item().setRegistryName("upgrade_liquid_pump").setUnlocalizedName("upgrade_liquid_pump").setCreativeTab(FEFCreativeTabs.FEFUNDAMENTALS);
	public static Item upgradeStoneFill = new Item().setRegistryName("upgrade_stone_fill").setUnlocalizedName("upgrade_stone_fill").setCreativeTab(FEFCreativeTabs.FEFUNDAMENTALS);

	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event){
		event.getRegistry().registerAll(upgradeBase, upgradeSpeed, upgradeSpeedDown, upgradeSilkTouch, upgradeFortune, upgradeLiquidPump, upgradeStoneFill);
	}
	
	public static void registerRenderers(){
		ModelLoader.setCustomModelResourceLocation(upgradeBase, 0, new ModelResourceLocation("fineredgefundamentals:upgrade_base", "inventory"));
		ModelLoader.setCustomModelResourceLocation(upgradeSpeed, 0, new ModelResourceLocation("fineredgefundamentals:upgrade_speed", "inventory"));
		ModelLoader.setCustomModelResourceLocation(upgradeSpeedDown, 0, new ModelResourceLocation("fineredgefundamentals:upgrade_speed_down", "inventory"));
		ModelLoader.setCustomModelResourceLocation(upgradeSilkTouch, 0, new ModelResourceLocation("fineredgefundamentals:upgrade_silk_touch", "inventory"));
		ModelLoader.setCustomModelResourceLocation(upgradeFortune, 0, new ModelResourceLocation("fineredgefundamentals:upgrade_fortune", "inventory"));
		ModelLoader.setCustomModelResourceLocation(upgradeLiquidPump, 0, new ModelResourceLocation("fineredgefundamentals:upgrade_liquid_pump", "inventory"));
		ModelLoader.setCustomModelResourceLocation(upgradeStoneFill, 0, new ModelResourceLocation("fineredgefundamentals:upgrade_stone_fill", "inventory"));
	}
}
