package com.the7thcircle.fineredge.fundamentals.tileentity;

import org.lwjgl.opengl.GL45;

import com.the7thcircle.fineredge.fundamentals.blocks.BlockFEFExcavationMarker;
import com.the7thcircle.fineredge.fundamentals.blocks.BlockFEFExcavator;
import com.the7thcircle.fineredge.fundamentals.blocks.FEFBlocks;
import com.the7thcircle.fineredge.fundamentals.inventory.ContainerFEFExcavator;
import com.the7thcircle.fineredge.fundamentals.inventory.ContainerFEFMachine;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFurnace;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;

public class TileEntityFEFExcavator extends TileEntityFEFMachine {
	
	protected static final int MACHINE_SLOTS = 13;
    protected NonNullList<ItemStack> machineItemStacks = NonNullList.<ItemStack>withSize(MACHINE_SLOTS, ItemStack.EMPTY);

    private int remainingCoolTime, currentItemCoolTime;
    private float temperature, progress, miningSpeed;
    private boolean hasBoundary;
    private BlockPos firstBlock, lastBlock;
    
	@Override
	public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
        return new ContainerFEFExcavator(playerInventory, this);
	}
	
	@Override
	public int getSizeInventory() {
		return this.machineItemStacks.size();
	}

	@Override
	public boolean isEmpty() {
		for (ItemStack itemstack : this.machineItemStacks)
        {
            if (!itemstack.isEmpty())
            {
                return false;
            }
        }

        return true;
	}

	@Override
	public ItemStack getStackInSlot(int index) {
        return (ItemStack)this.machineItemStacks.get(index);
	}

	@Override
	public ItemStack decrStackSize(int index, int count) {
        return ItemStackHelper.getAndSplit(this.machineItemStacks, index, count);
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {
        return ItemStackHelper.getAndRemove(this.machineItemStacks, index);
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		ItemStack itemstack = (ItemStack)this.machineItemStacks.get(index);
        boolean flag = !stack.isEmpty() && stack.isItemEqual(itemstack) && ItemStack.areItemStackTagsEqual(stack, itemstack);
        this.machineItemStacks.set(index, stack);

        if (stack.getCount() > this.getInventoryStackLimit()) {
            stack.setCount(this.getInventoryStackLimit());
        }
	}

	@Override
	public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        this.machineItemStacks = NonNullList.<ItemStack>withSize(this.getSizeInventory(), ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(compound, this.machineItemStacks);

        if (compound.hasKey("CustomName", 8))
        {
            this.machineCustomName = compound.getString("CustomName");
        }
    }

	@Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        ItemStackHelper.saveAllItems(compound, this.machineItemStacks);

        if (this.hasCustomName())
        {
            compound.setString("CustomName", this.machineCustomName);
        }

        return compound;
    }
	
	@Override
	public String getName() {
        return this.hasCustomName() ? this.machineCustomName : "container.excavator";
	}
	
	public boolean isMining(){
		return (this.temperature < 95.f && this.machineItemStacks.get(9).getItem() == Item.getItemFromBlock(Blocks.REDSTONE_TORCH));
	}
	
	@Override
	public int getField(int id) {
		switch(id){
		case 0: return remainingCoolTime;
		case 1: return currentItemCoolTime;
		case 2: return (int) temperature;
		case 3: return (int) progress;
		case 4: return (int) miningSpeed;
		}
		return 0;
	}
	
	@Override
	public void setField(int id, int value){
		switch (id)
        {
            case 0:
                this.remainingCoolTime = value;
                break;
            case 1:
                this.currentItemCoolTime = value;
                break;
            case 2:
                this.temperature = value;
                break;
        }
	}
	
	public static int getItemCoolTime(ItemStack stack) {
        if (stack.isEmpty()) {
            return 0;
        }
        else {
            Item item = stack.getItem();

            if (item instanceof net.minecraft.item.ItemBlock && Block.getBlockFromItem(item) != Blocks.AIR) {
                Block block = Block.getBlockFromItem(item);

                if (block == Blocks.SNOW) {
                    return 2000;
                }
                
                if (block == Blocks.ICE) {
                    return 8000;
                }
                

                if (block == Blocks.PACKED_ICE) {
                    return 16000;
                }
            }
            if (item == Items.SNOWBALL) return 500;
            return 0;
        }
    }
	
	public static boolean isItemCoolant(ItemStack stack){
        return getItemCoolTime(stack) > 0;
	}
	
	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		if(index == 11) return isItemCoolant(stack);
        return false;
    }
	
	@Override
	public void update(){

		boolean wasMining = this.isMining();
		boolean shouldUpdate = false;

		if (this.isMining()){
			if (this.remainingCoolTime > 0){
				if(this.temperature > 0) this.temperature -= 0.05f;
				--this.remainingCoolTime;
			}
			else if(this.temperature < 95.f) this.temperature += 0.025f;
		}
		else if(this.temperature > 0.f) this.temperature -= 0.05f;
		
		if(!this.world.isRemote){
            ItemStack itemstack = (ItemStack)this.machineItemStacks.get(11);
			if(this.isMining() && this.remainingCoolTime == 0 && !itemstack.isEmpty()){
				
				this.remainingCoolTime = getItemCoolTime(itemstack);
				this.currentItemCoolTime = this.remainingCoolTime;
				
				shouldUpdate = true;
				
                Item item = itemstack.getItem();
				itemstack.shrink(1);
				if (itemstack.isEmpty())
                {
                    ItemStack item1 = item.getContainerItem(itemstack);
                    this.machineItemStacks.set(11, item1);
                }
			}
			if (wasMining != this.isMining())
            {
				shouldUpdate = true;
                BlockFEFExcavator.setState(this.isMining(), this.world, this.pos);
            }
		}
		
		if(!this.hasBoundary && this.isMining() && !this.world.isRemote){
			//Boundary checking, step one, start with the marker directly behind the excavator:
			EnumFacing startDirection = this.world.getBlockState(this.pos).getValue(((BlockFEFExcavator)this.blockType).FACING).getOpposite(), currDirection = startDirection.getOpposite();
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
					int minX = markerPos[0].getX(), minY = 0, minZ = markerPos[0].getZ(), maxX = markerPos[0].getX(), maxY = markerPos[0].getY(), maxZ = markerPos[0].getZ();
					for(int i = 1; i < currMarker; ++i){
						if(markerPos[i].getX() < minX) minX = markerPos[i].getX();
						if(markerPos[i].getX() > maxX) maxX = markerPos[i].getX();
						if(markerPos[i].getX() < minZ) minZ = markerPos[i].getZ();
						if(markerPos[i].getX() > maxZ) maxZ = markerPos[i].getZ();
					}
					//Now we just need to set the first and last blocks for the excavator to go through:
					BlockPos newStartPos = new BlockPos(minX, maxY, minZ), newFinishPos = new BlockPos(maxX, minY, maxZ);
					this.firstBlock = newStartPos;
					this.lastBlock = newFinishPos;
					this.hasBoundary = true;
					System.out.println("Established Boundaries: " + minX + ", " + minY + ", " + minZ + "; " + maxX + ", " + maxY + ", " + maxZ);
				}
			}
		}
		
		if(shouldUpdate){
			this.markDirty();
		}
	}
}
