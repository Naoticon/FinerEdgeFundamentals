package com.the7thcircle.fineredge.fundamentals.tileentity;

import com.the7thcircle.fineredge.fundamentals.blocks.BlockFEFLogger;
import com.the7thcircle.fineredge.fundamentals.blocks.BlockFEFMachine;
import com.the7thcircle.fineredge.fundamentals.inventory.ContainerFEFMachineInput;
import com.the7thcircle.fineredge.fundamentals.items.FEFItems;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.oredict.OreDictionary;

public class TileEntityFEFLogger extends TileEntityFEFMachineInput {

	private int currBlockNum, boundarySize, currBranchRange = 1, currBranchNum = 0;
	private BlockPos currBlock = BlockPos.ORIGIN, firstBlock = BlockPos.ORIGIN, trunkBlock = BlockPos.ORIGIN;
	private boolean hasTree = false, hasBranch = false;

	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		super.readFromNBT(compound);

		this.currBlockNum = compound.getInteger("CurrentBlockNumber");
		this.boundarySize = compound.getInteger("BoundarySize");
		this.currBranchRange = compound.getInteger("CurrentBranchRange");
		this.currBranchNum = compound.getInteger("CurrentBranchNumber");
		this.hasTree = compound.getBoolean("HasTree");
		this.hasBranch = compound.getBoolean("HasBranch");
		this.currBlock = new BlockPos(compound.getInteger("CurrentBlockPosX"), compound.getInteger("CurrentBlockPosY"), compound.getInteger("CurrentBlockPosZ"));
		this.firstBlock = new BlockPos(compound.getInteger("FirstBlockPosX"), compound.getInteger("FirstBlockPosY"), compound.getInteger("FirstBlockPosZ"));
		this.trunkBlock = new BlockPos(compound.getInteger("TrunkBlockPosX"), compound.getInteger("TrunkBlockPosY"), compound.getInteger("TrunkBlockPosZ"));
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		super.writeToNBT(compound);
		ItemStackHelper.saveAllItems(compound, this.machineItemStacks);

		compound.setInteger("CurrentBlockNumber", currBlockNum);
		compound.setInteger("BoundarySize", boundarySize);
		compound.setInteger("CurrentBranchRange", currBranchRange);
		compound.setInteger("CurrentBranchNumber", currBranchNum);
		compound.setBoolean("HasTree", hasTree);
		compound.setBoolean("HasBranch", hasBranch);
		compound.setInteger("CurrentBlockPosX", currBlock.getX());
		compound.setInteger("CurrentBlockPosY", currBlock.getY());
		compound.setInteger("CurrentBlockPosZ", currBlock.getZ());
		compound.setInteger("FirstBlockPosX", firstBlock.getX());
		compound.setInteger("FirstBlockPosY", firstBlock.getY());
		compound.setInteger("FirstBlockPosZ", firstBlock.getZ());
		compound.setInteger("TrunkBlockPosX", trunkBlock.getX());
		compound.setInteger("TrunkBlockPosY", trunkBlock.getY());
		compound.setInteger("TrunkBlockPosZ", trunkBlock.getZ());

		return compound;
	}

	@Override
	public String getName() {
		return this.hasCustomName() ? this.machineCustomName : "container.logger";
	}

	@Override
	public void update(){
		//Check current upgrades and update properties accordingly
		boolean shouldUpdate = this.checkUpgradesUpdateProperties(this.machineItemStacks);
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

		//Tree chopping calculations
		if(this.isActive() && !this.world.isRemote){
			//If we're currently harvesting a tree, continue from the state of that tree, otherwise begin looking for another
			if(this.hasTree){
				this.currBlockProgress += 0.1f * this.miningSpeed;
				if(currBlockProgress >= 1.f){
					//If we are currently harvesting a "branch" (x/z layer) continue in an outward spiral
					if(this.hasBranch){
						int fortuneLevel = (this.machineItemStacks.get(9).getItem().equals(FEFItems.upgradeFortune) || this.machineItemStacks.get(10).getItem().equals(FEFItems.upgradeFortune)) ? 3 : 0;
						boolean silkTouch = (this.machineItemStacks.get(9).getItem().equals(FEFItems.upgradeSilkTouch) || this.machineItemStacks.get(10).getItem().equals(FEFItems.upgradeSilkTouch));
						//If we're currently at the center block and it's wood/leaves, harvest it and begin moving outward.  Otherwise, assume top of tree and stop.
						if(this.currBranchRange == 0){
							if(this.isWoodOrLeaves(this.world.getBlockState(this.currBlock).getBlock())){
								this.harvestBlock(currBlock, fortuneLevel, silkTouch, this.machineItemStacks);
								++this.currBranchRange;
							}
							else this.hasTree = false;
						}
						//Otherwise, if we are mid-branch and haven't finished, continue through the branch, skipping air until we reach the end
						else if(this.currBranchNum < 8 * this.currBranchRange){
							while(this.world.getBlockState(currBlock).getBlock() == Blocks.AIR && this.currBranchNum < 8 * this.currBranchRange){
								int xOffset, zOffset;
								//Spiral offset calculations
								if(this.currBranchNum % (4 * this.currBranchRange) < 2 * this.currBranchRange + 1){
									xOffset = this.currBranchNum % (8 * this.currBranchRange) < 4 * this.currBranchRange ? -this.currBranchRange + (this.currBranchNum % (4 * this.currBranchRange)) : this.currBranchRange - (this.currBranchNum % (4 * this.currBranchRange));
									zOffset = this.currBranchNum % (8 * this.currBranchRange) < 4 * this.currBranchRange ? this.currBranchRange : -this.currBranchRange;
								}
								else {
									xOffset = this.currBranchNum % (8 * this.currBranchRange) < 4 * this.currBranchRange ? this.currBranchRange : -this.currBranchRange;
									zOffset = this.currBranchNum % (8 * this.currBranchRange) < 4 * this.currBranchRange ? this.currBranchRange - (this.currBranchNum % (4 * this.currBranchRange) + 1 - (2 * this.currBranchRange + 1)) : -this.currBranchRange + (this.currBranchNum % (4 * this.currBranchRange) + 1 - (2 * this.currBranchRange + 1));
								}
								this.currBlock = new BlockPos(this.trunkBlock.getX() + xOffset, this.currBlock.getY(), this.trunkBlock.getZ() + zOffset);
								if(this.world.getBlockState(currBlock).getBlock() == Blocks.AIR) ++this.currBranchNum;
							}
							//If we've found wood/leaves at current branch point and aren't at the end, harvest the block.
							if(this.currBranchNum < 8 * this.currBranchRange){
								if(this.isWoodOrLeaves(this.world.getBlockState(this.currBlock).getBlock())) this.harvestBlock(currBlock, fortuneLevel, silkTouch, this.machineItemStacks);
								++this.currBranchNum;
							}
						}
						//Otherwise, if we haven't branched past our max range, start another branch check further out, otherwise finish branching on this layer
						else{
							if(this.currBranchRange * 2 + 1 < this.boundarySize){
								++this.currBranchRange;
								this.currBranchNum = 0;
								this.currBlock = new BlockPos(this.trunkBlock.getX(), this.currBlock.getY(), this.trunkBlock.getZ());
								this.hasBranch = true;
							}
							else{
								this.hasBranch = false;
							}
						}
					}
					//If we are not harvesting a branch, move up a layer to check if the tree continues.
					else {
						this.currBlock = new BlockPos(this.trunkBlock.getX(), this.currBlock.getY() + 1, this.trunkBlock.getZ());
						this.currBranchNum = 0;
						this.currBranchRange = 0;
						this.hasBranch = true;
					}
					this.currBlockProgress = 0.f;
				}
			}
			//If we are not harvesting a tree, continue looking for one along the ground and plant any saplings available.
			else{
				if(isWoodOrLeaves(this.world.getBlockState(this.currBlock).getBlock())){
					this.hasTree = true;
					this.hasBranch = true;
					this.currBranchNum = 0;
					this.currBranchRange = 0;
					this.trunkBlock = this.currBlock;
				}
				
				else if(this.world.getBlockState(this.currBlock).getBlock().equals(Blocks.AIR)){
					for(int i = 0; i < 9; ++i){
						if(isSapling(Block.getBlockFromItem(this.machineItemStacks.get(i + 13).getItem())) && currBlock.getY() == this.pos.getY()){
							this.world.setBlockState(currBlock, Block.getBlockFromItem(this.machineItemStacks.get(i + 13).getItem()).getStateFromMeta(this.machineItemStacks.get(i + 13).getItem().getMetadata(this.machineItemStacks.get(i + 13).getItemDamage())), world.isRemote ? 11 : 3);
							this.machineItemStacks.get(i + 13).shrink(1);
							break;
						}
					}
				}

				if(!this.hasTree){
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
		}

		this.handleJammedItem(this.machineItemStacks);

		if(shouldUpdate){
			this.markDirty();
		}
	}

	public static boolean isWoodOrLeaves(Block inBlock){
		if(inBlock.equals(Blocks.LOG)) return true;
		if(inBlock.equals(Blocks.LOG2)) return true;
		if(inBlock instanceof BlockLeaves) return true;
		for(int i = 0; i < OreDictionary.getOreNames().length; ++i){
			if(OreDictionary.getOreNames()[i].startsWith("log")){
				for(int i2 = 0; i2 < OreDictionary.getOres(OreDictionary.getOreNames()[i]).size(); ++i2){
					if(Item.getItemFromBlock(inBlock).equals(OreDictionary.getOres(OreDictionary.getOreNames()[i]).get(i2))) return true;
				}
			}
		}
		for(int i = 0; i < OreDictionary.getOreNames().length; ++i){
			if(OreDictionary.getOreNames()[i].startsWith("leaves")){
				for(int i2 = 0; i2 < OreDictionary.getOres(OreDictionary.getOreNames()[i]).size(); ++i2){
					if(Item.getItemFromBlock(inBlock).equals(OreDictionary.getOres(OreDictionary.getOreNames()[i]).get(i2))) return true;
				}
			}
		}
		return false;
	}
	
	public static boolean isSapling(Block inBlock){
		if(inBlock.equals(Blocks.SAPLING)) return true;
		if(inBlock instanceof BlockLeaves) return true;
		for(int i = 0; i < OreDictionary.getOreNames().length; ++i){
			if(OreDictionary.getOreNames()[i].startsWith("sapling")){
				for(int i2 = 0; i2 < OreDictionary.getOres(OreDictionary.getOreNames()[i]).size(); ++i2){
					if(Item.getItemFromBlock(inBlock).equals(OreDictionary.getOres(OreDictionary.getOreNames()[i]).get(i2))) return true;
				}
			}
		}
		return false;
	}
}
