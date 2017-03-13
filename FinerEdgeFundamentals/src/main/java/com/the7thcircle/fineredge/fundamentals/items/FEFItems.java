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
	
	public static ItemFEFMachineUpgrade upgradeBase = (ItemFEFMachineUpgrade) new ItemFEFMachineUpgrade().setRegistryName("upgrade_base").setUnlocalizedName("upgrade_base").setCreativeTab(FEFCreativeTabs.FEFUNDAMENTALS);
	public static ItemFEFMachineUpgrade upgradeSpeed = (ItemFEFMachineUpgrade) new ItemFEFMachineUpgrade().setRegistryName("upgrade_speed").setUnlocalizedName("upgrade_speed").setCreativeTab(FEFCreativeTabs.FEFUNDAMENTALS);
	public static ItemFEFMachineUpgrade upgradeSpeedDown = (ItemFEFMachineUpgrade) new ItemFEFMachineUpgrade().setRegistryName("upgrade_speed_down").setUnlocalizedName("upgrade_speed_down").setCreativeTab(FEFCreativeTabs.FEFUNDAMENTALS).setMaxStackSize(8);
	public static ItemFEFMachineUpgrade upgradeRange = (ItemFEFMachineUpgrade) new ItemFEFMachineUpgrade().setRegistryName("upgrade_range").setUnlocalizedName("upgrade_range").setCreativeTab(FEFCreativeTabs.FEFUNDAMENTALS).setMaxStackSize(8);
	public static ItemFEFMachineUpgrade upgradeRangeDown = (ItemFEFMachineUpgrade) new ItemFEFMachineUpgrade().setRegistryName("upgrade_range_down").setUnlocalizedName("upgrade_range_down").setCreativeTab(FEFCreativeTabs.FEFUNDAMENTALS).setMaxStackSize(8);
	public static ItemFEFMachineUpgrade upgradeSilkTouch = (ItemFEFMachineUpgrade) new ItemFEFMachineUpgrade().setRegistryName("upgrade_silk_touch").setUnlocalizedName("upgrade_silk_touch").setCreativeTab(FEFCreativeTabs.FEFUNDAMENTALS).setMaxStackSize(1);
	public static ItemFEFMachineUpgrade upgradeFortune = (ItemFEFMachineUpgrade) new ItemFEFMachineUpgrade().setRegistryName("upgrade_fortune").setUnlocalizedName("upgrade_fortune").setCreativeTab(FEFCreativeTabs.FEFUNDAMENTALS).setMaxStackSize(1);
	public static ItemFEFMachineUpgrade upgradeLiquidPump = (ItemFEFMachineUpgrade) new ItemFEFMachineUpgrade().setRegistryName("upgrade_liquid_pump").setUnlocalizedName("upgrade_liquid_pump").setCreativeTab(FEFCreativeTabs.FEFUNDAMENTALS).setMaxStackSize(1);
	public static ItemFEFMachineUpgrade upgradeStoneFill = (ItemFEFMachineUpgrade) new ItemFEFMachineUpgrade().setRegistryName("upgrade_stone_fill").setUnlocalizedName("upgrade_stone_fill").setCreativeTab(FEFCreativeTabs.FEFUNDAMENTALS).setMaxStackSize(1);
	
	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event){
		event.getRegistry().registerAll(upgradeBase, upgradeSpeed, upgradeSpeedDown, upgradeRange, upgradeRangeDown, upgradeSilkTouch, upgradeFortune, upgradeLiquidPump, upgradeStoneFill);
	}
	
	public static void registerRenderers(){
		ModelLoader.setCustomModelResourceLocation(upgradeBase, 0, new ModelResourceLocation("fineredgefundamentals:upgrade_base", "inventory"));
		ModelLoader.setCustomModelResourceLocation(upgradeSpeed, 0, new ModelResourceLocation("fineredgefundamentals:upgrade_speed", "inventory"));
		ModelLoader.setCustomModelResourceLocation(upgradeSpeedDown, 0, new ModelResourceLocation("fineredgefundamentals:upgrade_speed_down", "inventory"));
		ModelLoader.setCustomModelResourceLocation(upgradeRange, 0, new ModelResourceLocation("fineredgefundamentals:upgrade_range", "inventory"));
		ModelLoader.setCustomModelResourceLocation(upgradeRangeDown, 0, new ModelResourceLocation("fineredgefundamentals:upgrade_range_down", "inventory"));
		ModelLoader.setCustomModelResourceLocation(upgradeSilkTouch, 0, new ModelResourceLocation("fineredgefundamentals:upgrade_silk_touch", "inventory"));
		ModelLoader.setCustomModelResourceLocation(upgradeFortune, 0, new ModelResourceLocation("fineredgefundamentals:upgrade_fortune", "inventory"));
		ModelLoader.setCustomModelResourceLocation(upgradeLiquidPump, 0, new ModelResourceLocation("fineredgefundamentals:upgrade_liquid_pump", "inventory"));
		ModelLoader.setCustomModelResourceLocation(upgradeStoneFill, 0, new ModelResourceLocation("fineredgefundamentals:upgrade_stone_fill", "inventory"));
	}
}
