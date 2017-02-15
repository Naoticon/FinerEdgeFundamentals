package com.the7thcircle.fineredge.fundamentals.blocks;

import java.util.Random;

import com.the7thcircle.fineredge.fundamentals.client.gui.FEFCreativeTabs;

import net.minecraft.block.BlockTorch;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockFEFExcavationMarker extends BlockTorch {
	public BlockFEFExcavationMarker(){
		super();
		this.setTickRandomly(false);
		this.setCreativeTab(FEFCreativeTabs.FEFUNDAMENTALS);
		this.setLightLevel(0.9375F);
	}
	
	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
        return true;
    }
	
	@Override
	protected boolean checkForDrop(World worldIn, BlockPos pos, IBlockState state) {
        return false;
    }
	
	@SideOnly(Side.CLIENT)
	@Override
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
    }
	
	@Override
	public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
		if(facing == EnumFacing.DOWN) return this.getDefaultState();
		return this.getDefaultState().withProperty(FACING, facing);
    }
}
