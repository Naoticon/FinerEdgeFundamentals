package com.the7thcircle.fineredge.fundamentals.client.gui;

import com.the7thcircle.fineredge.fundamentals.blocks.FEFBlocks;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class FEFCreativeTabs extends CreativeTabs {

	public static final CreativeTabs FEFUNDAMENTALS = new FEFCreativeTabs("fefundamentals") {
		@Override
		@SideOnly(Side.CLIENT)
		public ItemStack getTabIconItem() {
			return new ItemStack(FEFBlocks.excavator);
		}
    };
	
	public FEFCreativeTabs(String label) {
		super(label);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public ItemStack getTabIconItem() {
		return new ItemStack(FEFBlocks.excavator);
	}
}
