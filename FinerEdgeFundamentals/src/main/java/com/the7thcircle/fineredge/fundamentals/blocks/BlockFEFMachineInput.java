package com.the7thcircle.fineredge.fundamentals.blocks;

import com.the7thcircle.fineredge.fundamentals.tileentity.TileEntityFEFMachineInput;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockFEFMachineInput extends BlockFEFMachine {

	public BlockFEFMachineInput(Material blockMaterialIn, MapColor blockMapColorIn) {
		super(blockMaterialIn, blockMapColorIn);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityFEFMachineInput();
	}
}
