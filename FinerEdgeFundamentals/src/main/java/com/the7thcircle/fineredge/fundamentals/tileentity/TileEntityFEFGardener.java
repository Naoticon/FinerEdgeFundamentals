package com.the7thcircle.fineredge.fundamentals.tileentity;

import java.util.List;

import com.the7thcircle.fineredge.fundamentals.blocks.BlockFEFGardener;
import com.the7thcircle.fineredge.fundamentals.blocks.BlockFEFMachine;
import com.the7thcircle.fineredge.fundamentals.items.FEFItems;

import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.IGrowable;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemSeedFood;
import net.minecraft.item.ItemSeeds;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;

public class TileEntityFEFGardener extends TileEntityFEFMachineInput {

	private int currBlockNum, boundarySize;
	private BlockPos currBlock = BlockPos.ORIGIN, firstBlock = BlockPos.ORIGIN;

	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		super.readFromNBT(compound);

		this.currBlockNum = compound.getInteger("CurrentBlockNumber");
		this.boundarySize = compound.getInteger("BoundarySize");
		this.currBlock = new BlockPos(compound.getInteger("CurrentBlockPosX"), compound.getInteger("CurrentBlockPosY"), compound.getInteger("CurrentBlockPosZ"));
		this.firstBlock = new BlockPos(compound.getInteger("FirstBlockPosX"), compound.getInteger("FirstBlockPosY"), compound.getInteger("FirstBlockPosZ"));
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		super.writeToNBT(compound);

		compound.setInteger("CurrentBlockNumber", currBlockNum);
		compound.setInteger("CurrentBlockPosX", currBlock.getX());
		compound.setInteger("CurrentBlockPosY", currBlock.getY());
		compound.setInteger("CurrentBlockPosZ", currBlock.getZ());
		compound.setInteger("FirstBlockPosX", firstBlock.getX());
		compound.setInteger("FirstBlockPosY", firstBlock.getY());
		compound.setInteger("FirstBlockPosZ", firstBlock.getZ());

		return compound;
	}

	@Override
	public String getName() {
		return this.hasCustomName() ? this.machineCustomName : "container.gardener";
	}

	@Override
	public void update(){
		boolean shouldUpdate = this.checkUpgradesUpdateProperties(this.machineItemStacks);

		//Check current upgrades and update properties accordingly
		if(!this.world.isRemote){
			int numRangeUpgrades = (this.machineItemStacks.get(9).getItem().equals(FEFItems.upgradeRange) ? this.machineItemStacks.get(9).getCount() : 0) + (this.machineItemStacks.get(10).getItem().equals(FEFItems.upgradeRange) ? this.machineItemStacks.get(10).getCount() : 0);
			int numRangeDownUpgrades = (this.machineItemStacks.get(9).getItem().equals(FEFItems.upgradeRangeDown) ? this.machineItemStacks.get(9).getCount() : 0) + (this.machineItemStacks.get(10).getItem().equals(FEFItems.upgradeRangeDown) ? this.machineItemStacks.get(10).getCount() : 0);
			int oldBoundarySize = boundarySize;
			this.boundarySize = 5 + 2 * numRangeUpgrades - 2 * numRangeDownUpgrades;
			if(oldBoundarySize != boundarySize || currBlock.equals(BlockPos.ORIGIN)){
				this.firstBlock = new BlockPos(this.pos.getX(), this.pos.getY(), this.pos.getZ());
				this.firstBlock = this.firstBlock.offset(this.world.getBlockState(this.pos).getValue(((BlockFEFMachine)this.blockType).FACING).getOpposite(), 1);
				this.firstBlock = this.firstBlock.offset(this.world.getBlockState(this.pos).getValue(((BlockFEFMachine)this.blockType).FACING).rotateYCCW(), boundarySize / 2);
				this.currBlock = this.firstBlock;
				this.currBlockNum = 0;
			}
		}

		//Gardener Calculations
		if(this.isActive() && !this.world.isRemote){
			if(this.isGrowablePlant(this.world.getBlockState(currBlock).getBlock())){
				this.currBlockProgress += 0.1f * this.miningSpeed;
				if(this.currBlockProgress >= 1.f){
					int fortuneLevel = (this.machineItemStacks.get(9).getItem().equals(FEFItems.upgradeFortune) || this.machineItemStacks.get(10).getItem().equals(FEFItems.upgradeFortune)) ? 3 : 0;

					if(this.world.getBlockState(currBlock).getBlock() instanceof BlockCrops){
						BlockCrops currPlant = (BlockCrops) this.world.getBlockState(currBlock).getBlock();

						if(currPlant.isMaxAge(this.world.getBlockState(currBlock))){
							//Harvest Plant
							List<ItemStack> plantDrops = currPlant.getDrops(this.world, currBlock, this.world.getBlockState(currBlock), fortuneLevel);
							for(int i = 0; i < plantDrops.size(); ++i){
								ItemStack currItemStack = plantDrops.get(i);
								for(int i2 = 0; i2 < 9; ++i2){
									if(currItemStack.getCount() > 0){
										if(this.machineItemStacks.get(i2).isEmpty()){
											this.machineItemStacks.set(i2, currItemStack);
											currItemStack = ItemStack.EMPTY;
											break;
										}
										if(this.machineItemStacks.get(i2).isItemEqual(currItemStack)){
											if(this.machineItemStacks.get(i2).getCount() + currItemStack.getCount() <= this.machineItemStacks.get(i2).getMaxStackSize()){
												this.machineItemStacks.get(i2).setCount(this.machineItemStacks.get(i2).getCount() + currItemStack.getCount());
												currItemStack = ItemStack.EMPTY;
												break;
											}
											else{
												int toSplit = this.machineItemStacks.get(i2).getMaxStackSize() - this.machineItemStacks.get(i2).getCount();
												this.machineItemStacks.get(i2).setCount(this.machineItemStacks.get(i2).getMaxStackSize());
												currItemStack.shrink(toSplit);
											}
										}
									}
								}
								if(currItemStack.getCount() > 0){
									this.isJammed = true;
									this.jammedStack = currItemStack;
								}
							}
							
							this.world.setBlockState(currBlock, currPlant.withAge(1), world.isRemote ? 11 : 3);
						}

						else if(currPlant.canUseBonemeal(this.world, this.world.rand, currBlock, this.world.getBlockState(currBlock))){
							//Apply and drain bonemeal.
							for(int i = 0; i < 9; ++i){
								if(this.machineItemStacks.get(i + 13).getItem().equals(Items.DYE) && this.machineItemStacks.get(i + 13).getItemDamage() == 15){
									ItemDye.applyBonemeal(this.machineItemStacks.get(i + 13), this.world, currBlock);
									break;
								}
							}
						}
						++this.currBlockNum;
						if(this.currBlockNum == this.boundarySize * this.boundarySize){
							this.currBlock = this.firstBlock;
						}
						else if((currBlockNum) % boundarySize == 0){
							this.currBlock = this.currBlock.offset(this.world.getBlockState(this.pos).getValue(((BlockFEFMachine)this.blockType).FACING), this.boundarySize - 1);
							this.currBlock = this.currBlock.offset(this.world.getBlockState(this.pos).getValue(((BlockFEFMachine)this.blockType).FACING).rotateY(), 1);				
						}
						else{
							this.currBlock = this.currBlock.offset(this.world.getBlockState(this.pos).getValue(((BlockFEFMachine)this.blockType).FACING).getOpposite(), 1);
						}
						if(this.currBlockNum >= this.boundarySize * this.boundarySize) this.currBlockNum = 0;
					}
					this.currBlockProgress = 0.f;
				}
			}
			
			else if(this.world.getBlockState(currBlock).getBlock().equals(Blocks.AIR) && this.world.getBlockState(currBlock.down()).getBlock().equals(Blocks.FARMLAND)){
				for(int i = 0; i < 9; ++i){
					if(isGrowablePlant(Block.getBlockFromItem(this.machineItemStacks.get(i + 13).getItem())) && currBlock.getY() == this.pos.getY()){
						this.world.setBlockState(currBlock, Block.getBlockFromItem(this.machineItemStacks.get(i + 13).getItem()).getStateFromMeta(this.machineItemStacks.get(i + 13).getItem().getMetadata(this.machineItemStacks.get(i + 13).getItemDamage())), world.isRemote ? 11 : 3);
						this.machineItemStacks.get(i + 13).shrink(1);
						break;
					}
				}
				++this.currBlockNum;
				if(this.currBlockNum == this.boundarySize * this.boundarySize){
					this.currBlock = this.firstBlock;
				}
				else if((currBlockNum) % boundarySize == 0){
					this.currBlock = this.currBlock.offset(this.world.getBlockState(this.pos).getValue(((BlockFEFMachine)this.blockType).FACING), this.boundarySize - 1);
					this.currBlock = this.currBlock.offset(this.world.getBlockState(this.pos).getValue(((BlockFEFMachine)this.blockType).FACING).rotateY(), 1);				
				}
				else{
					this.currBlock = this.currBlock.offset(this.world.getBlockState(this.pos).getValue(((BlockFEFMachine)this.blockType).FACING).getOpposite(), 1);
				}
				if(this.currBlockNum >= this.boundarySize * this.boundarySize) this.currBlockNum = 0;
			}
			
			else{
				++this.currBlockNum;
				if(this.currBlockNum == this.boundarySize * this.boundarySize){
					this.currBlock = this.firstBlock;
				}
				else if((currBlockNum) % boundarySize == 0){
					this.currBlock = this.currBlock.offset(this.world.getBlockState(this.pos).getValue(((BlockFEFMachine)this.blockType).FACING), this.boundarySize - 1);
					this.currBlock = this.currBlock.offset(this.world.getBlockState(this.pos).getValue(((BlockFEFMachine)this.blockType).FACING).rotateY(), 1);				
				}
				else{
					this.currBlock = this.currBlock.offset(this.world.getBlockState(this.pos).getValue(((BlockFEFMachine)this.blockType).FACING).getOpposite(), 1);
				}
				if(this.currBlockNum >= this.boundarySize * this.boundarySize) this.currBlockNum = 0;
			}
		}

		this.handleJammedItem(this.machineItemStacks);

		if(shouldUpdate){
			this.markDirty();
		}
	}

	public static boolean isGrowablePlant(Block inBlock){
		if(inBlock instanceof IGrowable || ItemBlock.getItemFromBlock(inBlock) instanceof ItemSeedFood || ItemBlock.getItemFromBlock(inBlock) instanceof ItemSeeds) return true;
		return false;
	}
}
