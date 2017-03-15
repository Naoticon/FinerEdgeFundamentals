package com.the7thcircle.fineredge.fundamentals.items;

import com.the7thcircle.fineredge.fundamentals.client.gui.FEFCreativeTabs;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class FEFItems {
	
	public static final ItemFEFMachineUpgrade UPGRADE_BASE = (ItemFEFMachineUpgrade) new ItemFEFMachineUpgrade().setRegistryName("upgrade_base").setUnlocalizedName("upgrade_base").setCreativeTab(FEFCreativeTabs.FEFUNDAMENTALS);
	public static final ItemFEFMachineUpgrade UPGRADE_SPEED = (ItemFEFMachineUpgrade) new ItemFEFMachineUpgrade().setRegistryName("upgrade_speed").setUnlocalizedName("upgrade_speed").setCreativeTab(FEFCreativeTabs.FEFUNDAMENTALS);
	public static final ItemFEFMachineUpgrade UPGRADE_SPEED_DOWN = (ItemFEFMachineUpgrade) new ItemFEFMachineUpgrade().setRegistryName("upgrade_speed_down").setUnlocalizedName("upgrade_speed_down").setCreativeTab(FEFCreativeTabs.FEFUNDAMENTALS).setMaxStackSize(8);
	public static final ItemFEFMachineUpgrade UPGRADE_RANGE = (ItemFEFMachineUpgrade) new ItemFEFMachineUpgrade().setRegistryName("upgrade_range").setUnlocalizedName("upgrade_range").setCreativeTab(FEFCreativeTabs.FEFUNDAMENTALS).setMaxStackSize(8);
	public static final ItemFEFMachineUpgrade UPGRADE_RANGE_DOWN = (ItemFEFMachineUpgrade) new ItemFEFMachineUpgrade().setRegistryName("upgrade_range_down").setUnlocalizedName("upgrade_range_down").setCreativeTab(FEFCreativeTabs.FEFUNDAMENTALS).setMaxStackSize(8);
	public static final ItemFEFMachineUpgrade UPGRADE_SILK_TOUCH = (ItemFEFMachineUpgrade) new ItemFEFMachineUpgrade().setRegistryName("upgrade_silk_touch").setUnlocalizedName("upgrade_silk_touch").setCreativeTab(FEFCreativeTabs.FEFUNDAMENTALS).setMaxStackSize(1);
	public static final ItemFEFMachineUpgrade UPGRADE_FORTUNE = (ItemFEFMachineUpgrade) new ItemFEFMachineUpgrade().setRegistryName("upgrade_fortune").setUnlocalizedName("upgrade_fortune").setCreativeTab(FEFCreativeTabs.FEFUNDAMENTALS).setMaxStackSize(1);
	public static final ItemFEFMachineUpgrade UPGRADE_LIQUID_PUMP = (ItemFEFMachineUpgrade) new ItemFEFMachineUpgrade().setRegistryName("upgrade_liquid_pump").setUnlocalizedName("upgrade_liquid_pump").setCreativeTab(FEFCreativeTabs.FEFUNDAMENTALS).setMaxStackSize(1);
	public static final ItemFEFMachineUpgrade UPGRADE_STONE_FILL = (ItemFEFMachineUpgrade) new ItemFEFMachineUpgrade().setRegistryName("upgrade_stone_fill").setUnlocalizedName("upgrade_stone_fill").setCreativeTab(FEFCreativeTabs.FEFUNDAMENTALS).setMaxStackSize(1);
	public static final Item ACTIVATED_ENDER_EYE = new Item().setRegistryName("activated_ender_eye").setUnlocalizedName("activated_ender_eye").setCreativeTab(FEFCreativeTabs.FEFUNDAMENTALS);
	
	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event){
		event.getRegistry().registerAll(UPGRADE_BASE, UPGRADE_SPEED, UPGRADE_SPEED_DOWN, UPGRADE_RANGE, UPGRADE_RANGE_DOWN, UPGRADE_SILK_TOUCH, UPGRADE_FORTUNE, UPGRADE_LIQUID_PUMP, UPGRADE_STONE_FILL, ACTIVATED_ENDER_EYE);
	}
	
	public static void registerRenderers(){
		ModelLoader.setCustomModelResourceLocation(UPGRADE_BASE, 0, new ModelResourceLocation("fineredgefundamentals:upgrade_base", "inventory"));
		ModelLoader.setCustomModelResourceLocation(UPGRADE_SPEED, 0, new ModelResourceLocation("fineredgefundamentals:upgrade_speed", "inventory"));
		ModelLoader.setCustomModelResourceLocation(UPGRADE_SPEED_DOWN, 0, new ModelResourceLocation("fineredgefundamentals:upgrade_speed_down", "inventory"));
		ModelLoader.setCustomModelResourceLocation(UPGRADE_RANGE, 0, new ModelResourceLocation("fineredgefundamentals:upgrade_range", "inventory"));
		ModelLoader.setCustomModelResourceLocation(UPGRADE_RANGE_DOWN, 0, new ModelResourceLocation("fineredgefundamentals:upgrade_range_down", "inventory"));
		ModelLoader.setCustomModelResourceLocation(UPGRADE_SILK_TOUCH, 0, new ModelResourceLocation("fineredgefundamentals:upgrade_silk_touch", "inventory"));
		ModelLoader.setCustomModelResourceLocation(UPGRADE_FORTUNE, 0, new ModelResourceLocation("fineredgefundamentals:upgrade_fortune", "inventory"));
		ModelLoader.setCustomModelResourceLocation(UPGRADE_LIQUID_PUMP, 0, new ModelResourceLocation("fineredgefundamentals:upgrade_liquid_pump", "inventory"));
		ModelLoader.setCustomModelResourceLocation(UPGRADE_STONE_FILL, 0, new ModelResourceLocation("fineredgefundamentals:upgrade_stone_fill", "inventory"));
		ModelLoader.setCustomModelResourceLocation(ACTIVATED_ENDER_EYE, 0, new ModelResourceLocation("fineredgefundamentals:activated_ender_eye"));
	}
}
