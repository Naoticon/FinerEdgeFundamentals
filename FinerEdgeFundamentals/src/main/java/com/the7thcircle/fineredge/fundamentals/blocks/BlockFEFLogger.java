package com.the7thcircle.fineredge.fundamentals.blocks;

import java.util.Random;

import com.the7thcircle.fineredge.fundamentals.FinerEdgeFundamentalsMod;
import com.the7thcircle.fineredge.fundamentals.gui.FEFGuiHandler;
import com.the7thcircle.fineredge.fundamentals.tileentity.TileEntityFEFLogger;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockFEFLogger extends BlockFEFMachine {

	public BlockFEFLogger(Material blockMaterialIn, MapColor blockMapColorIn) {
		super(blockMaterialIn, blockMapColorIn);
	}
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return Item.getItemFromBlock(FEFBlocks.LOGGER);
    }
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityFEFLogger();
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (worldIn.isRemote) {
            return true;
        }
        else {
            TileEntity tileentity = worldIn.getTileEntity(pos);

            if (tileentity instanceof TileEntityFEFLogger) {
                playerIn.openGui(FinerEdgeFundamentalsMod.INSTANCE, FEFGuiHandler.FEF_LOGGER_GUI, worldIn, pos.getX(), pos.getY(), pos.getZ());
            }

            return true;
        }
    }
}
