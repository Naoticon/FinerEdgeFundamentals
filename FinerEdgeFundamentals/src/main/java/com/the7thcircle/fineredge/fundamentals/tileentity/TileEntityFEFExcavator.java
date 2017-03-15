package com.the7thcircle.fineredge.fundamentals.tileentity;

import com.the7thcircle.fineredge.fundamentals.blocks.BlockFEFExcavationMarker;
import com.the7thcircle.fineredge.fundamentals.blocks.BlockFEFMachine;
import com.the7thcircle.fineredge.fundamentals.blocks.FEFBlocks;
import com.the7thcircle.fineredge.fundamentals.items.FEFItems;

import net.minecraft.block.BlockLiquid;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;

public class TileEntityFEFExcavator extends TileEntityFEFMachine {

	private int blocksToMine, currBlockNum, boundaryX, boundaryY, boundaryZ;
	private boolean hasBoundary = false, isFinished = false;
	private BlockPos firstBlock = BlockPos.ORIGIN, lastBlock = BlockPos.ORIGIN, currBlock = BlockPos.ORIGIN;

	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		super.readFromNBT(compound);
		this.machineItemStacks = NonNullList.<ItemStack>withSize(this.getSizeInventory(), ItemStack.EMPTY);
		ItemStackHelper.loadAllItems(compound, this.machineItemStacks);

		this.blocksToMine = compound.getInteger("BlocksToMine");
		this.currBlockNum = compound.getInteger("CurrentBlockNumber");
		this.boundaryX = compound.getInteger("BoundaryX");
		this.boundaryY = compound.getInteger("BoundaryY");
		this.boundaryZ = compound.getInteger("BoundaryZ");
		this.hasBoundary = compound.getBoolean("HasBoundary");
		this.isFinished = compound.getBoolean("IsFinished");
		this.firstBlock = new BlockPos(compound.getInteger("FirstBlockPosX"), compound.getInteger("FirstBlockPosY"), compound.getInteger("FirstBlockPosZ"));
		this.lastBlock = new BlockPos(compound.getInteger("LastBlockPosX"), compound.getInteger("LastBlockPosY"), compound.getInteger("LastBlockPosZ"));
		this.currBlock = new BlockPos(compound.getInteger("CurrentBlockPosX"), compound.getInteger("CurrentBlockPosY"), compound.getInteger("CurrentBlockPosZ"));
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		super.writeToNBT(compound);
		ItemStackHelper.saveAllItems(compound, this.machineItemStacks);

		compound.setInteger("BlocksToMine", blocksToMine);
		compound.setInteger("CurrentBlockNumber", currBlockNum);
		compound.setInteger("BoundaryX", boundaryX);
		compound.setInteger("BoundaryY", boundaryY);
		compound.setInteger("BoundaryZ", boundaryZ);
		compound.setBoolean("HasBoundary", hasBoundary);
		compound.setBoolean("IsFinished", isFinished);
		compound.setInteger("FirstBlockPosX", firstBlock.getX());
		compound.setInteger("FirstBlockPosY", firstBlock.getY());
		compound.setInteger("FirstBlockPosZ", firstBlock.getZ());
		compound.setInteger("LastBlockPosX", lastBlock.getX());
		compound.setInteger("LastBlockPosY", lastBlock.getY());
		compound.setInteger("LastBlockPosZ", lastBlock.getZ());
		compound.setInteger("CurrentBlockPosX", currBlock.getX());
		compound.setInteger("CurrentBlockPosY", currBlock.getY());
		compound.setInteger("CurrentBlockPosZ", currBlock.getZ());

		return compound;
	}

	@Override
	public String getName() {
		return this.hasCustomName() ? this.machineCustomName : "container.excavator";
	}
	
	@Override
	public boolean isActive(){
		return this.isFinished ? false : super.isActive();
	}

	@Override
	public void update(){
		boolean shouldUpdate = this.checkUpgradesUpdateProperties(this.machineItemStacks);

		if(!this.hasBoundary && this.isActive() && !this.world.isRemote){
			this.establishBoundary();
		}

		else if(this.hasBoundary && this.isActive() && !this.world.isRemote && currBlockNum < blocksToMine){
			this.progress = 100.f * ((float) currBlockNum + 1) / blocksToMine;
			this.currBlock = new BlockPos(currBlockNum % boundaryX + firstBlock.getX(), firstBlock.getY() - currBlockNum / (boundaryX * boundaryZ), ((currBlockNum % (boundaryX * boundaryZ))) / boundaryX + firstBlock.getZ());
			//Skip all air blocks until we find the next solid block, this reduces time if an excavator is reinstalled or a cave found
			while(this.world.getBlockState(currBlock).getBlock() == Blocks.AIR && currBlockNum < blocksToMine){
				++currBlockNum;
				this.currBlock = new BlockPos(currBlockNum % boundaryX + firstBlock.getX(), firstBlock.getY() - currBlockNum / (boundaryX * boundaryZ), ((currBlockNum % (boundaryX * boundaryZ))) / boundaryX + firstBlock.getZ());
			}

			this.currBlockProgress += 0.025f * this.miningSpeed;
			if(currBlockProgress >= 1.f){
				if(this.world.getBlockState(currBlock).getBlock() != Blocks.AIR && this.world.getBlockState(currBlock).getBlock() != Blocks.BEDROCK && this.world.getBlockState(currBlock).getBlock() != FEFBlocks.EXCAVATOR){
					//If we don't have a liquid upgrade, don't evacuate liquids
					if(!(this.world.getBlockState(currBlock).getBlock() instanceof BlockLiquid) || this.machineItemStacks.get(9).getItem().equals(FEFItems.UPGRADE_LIQUID_PUMP) || this.machineItemStacks.get(10).getItem().equals(FEFItems.UPGRADE_LIQUID_PUMP)){
						int fortuneLevel = (this.machineItemStacks.get(9).getItem().equals(FEFItems.UPGRADE_FORTUNE) || this.machineItemStacks.get(10).getItem().equals(FEFItems.UPGRADE_FORTUNE)) ? 3 : 0;
						boolean silkTouch = (this.machineItemStacks.get(9).getItem().equals(FEFItems.UPGRADE_SILK_TOUCH) || this.machineItemStacks.get(10).getItem().equals(FEFItems.UPGRADE_SILK_TOUCH));
						this.harvestBlock(currBlock, fortuneLevel, silkTouch, this.machineItemStacks);
						
					}
					this.currBlockProgress = 0.f;
				}
				if(currBlockNum < blocksToMine) ++currBlockNum;
			}

			if(currBlockNum == blocksToMine){
				this.isFinished = true;
			}
		}

		this.handleJammedItem(this.machineItemStacks);

		if(shouldUpdate){
			this.markDirty();
		}
	}
	
	private void establishBoundary(){
		//Boundary checking, step one, start with the marker directly behind the excavator:
		EnumFacing startDirection = this.world.getBlockState(this.pos).getValue(BlockFEFMachine.FACING).getOpposite(), currDirection = startDirection.getOpposite();
		BlockPos startPos = this.getPos().offset(startDirection, 1), check2, check3, check4, currPos = startPos;
		BlockPos[] markerPos = new BlockPos[16];
		int currMarker = 0;
		if(this.world.getBlockState(startPos).getBlock() instanceof BlockFEFExcavationMarker){
			//It is a marker, so we can continue.  We check up to 64 blocks in distance, branching from the 3 sides other than where we came from, up to a total of 16 markers:
			boolean backAtStart = false, deadEnd = false, isRepeatMarker = false;
			while(!backAtStart && !deadEnd){
				for(int i = 1; i < 65; ++i){
					currDirection = currDirection.rotateY();
					check2 = currPos.offset(currDirection, i);
					if(this.world.getBlockState(check2).getBlock() instanceof BlockFEFExcavationMarker){
						//We've found another marker, but need to check if it's been looped backed and need to ignore it:
						for(int i2 = 0; i2 < currMarker; ++i2){
							if(markerPos[i2].equals(check2)){
								isRepeatMarker = true;
								break;
							}
						}
						if(!isRepeatMarker){
							//If it isn't a repeat marker, we can add it to the list of markers and continue from it:
							markerPos[currMarker] = check2;
							++currMarker;
							currPos = check2;
							currDirection = currDirection.getOpposite();
							break;
						}
						//If it is a repeat marker, we simply ignore it and continue.
						else isRepeatMarker = false;
					}
					currDirection = currDirection.rotateY();
					check3 = currPos.offset(currDirection, i);
					if(this.world.getBlockState(check3).getBlock() instanceof BlockFEFExcavationMarker){
						for(int i2 = 0; i2 < currMarker; ++i2){
							if(markerPos[i2].equals(check3)){
								isRepeatMarker = true;
								break;
							}
						}
						if(!isRepeatMarker){
							markerPos[currMarker] = check3;
							++currMarker;
							currPos = check3;
							currDirection = currDirection.getOpposite();
							break;
						}
						else isRepeatMarker = false;
					}
					currDirection = currDirection.rotateY();
					check4 = currPos.offset(currDirection, i);
					if(this.world.getBlockState(check4).getBlock() instanceof BlockFEFExcavationMarker){
						for(int i2 = 0; i2 < currMarker; ++i2){
							if(markerPos[i2].equals(check4)){
								isRepeatMarker = true;
								break;
							}
						}
						if(!isRepeatMarker){
							markerPos[currMarker] = check4;
							++currMarker;
							currPos = check4;
							currDirection = currDirection.getOpposite();
							break;
						}
						else isRepeatMarker = false;
					}
					//We have to rotate one extra time to ignore the original direction before looping.
					currDirection = currDirection.rotateY();

					if((i == 64 || currMarker == 15) && (!currPos.equals(startPos) || currMarker == 0)){
						//If we've reached the maximum distance of number of markers, but not the beginning, it's a dead-end
						deadEnd = true;
					}
				}
				if(currPos.equals(startPos) && currMarker > 0){
					//We've reached the beginning marker again.
					backAtStart = true;
				}
			}
			if(backAtStart){
				//We've found the start point, time to go through all of the markers and find our bounds:
				int minX = markerPos[0].getX(), minY = 0, minZ = markerPos[0].getZ(), maxX = markerPos[0].getX(), maxY = markerPos[0].getY() - 1, maxZ = markerPos[0].getZ();
				for(int i = 1; i < currMarker; ++i){
					if(markerPos[i].getX() < minX) minX = markerPos[i].getX();
					if(markerPos[i].getX() > maxX) maxX = markerPos[i].getX();
					if(markerPos[i].getZ() < minZ) minZ = markerPos[i].getZ();
					if(markerPos[i].getZ() > maxZ) maxZ = markerPos[i].getZ();
				}
				//Now we just need to set the first and last blocks for the excavator to go through:
				BlockPos newStartPos = new BlockPos(minX, maxY, minZ), newFinishPos = new BlockPos(maxX, minY, maxZ);
				this.firstBlock = newStartPos;
				this.lastBlock = newFinishPos;
				this.hasBoundary = true;

				this.boundaryX = maxX - minX + 1;
				this.boundaryY = maxY - minY + 1;
				this.boundaryZ = maxZ - minZ + 1;
				this.blocksToMine = boundaryX * boundaryY * boundaryZ;
				this.currBlock = firstBlock;
			}
		}
	}
}
