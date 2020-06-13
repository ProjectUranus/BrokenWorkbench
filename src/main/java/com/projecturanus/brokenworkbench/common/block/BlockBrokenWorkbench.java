package com.projecturanus.brokenworkbench.common.block;

import com.projecturanus.brokenworkbench.BrokenWorkbench;
import com.projecturanus.brokenworkbench.common.util.BitwiseUtils;
import com.projecturanus.brokenworkbench.common.capability.CapabilityBroken;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class    BlockBrokenWorkbench extends BlockContainer implements ITileEntityProvider {

    public BlockBrokenWorkbench() {
        super(Material.WOOD);
        setRegistryName(BrokenWorkbench.MODID, "broken_crafting_table");
        setTranslationKey("brokenworkbench.broken_crafting_table");
    }

    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        super.breakBlock(worldIn, pos, state);
        worldIn.removeTileEntity(pos);
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state)
    {
        return EnumBlockRenderType.MODEL;
    }

    @Override
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(@Nonnull ItemStack stack, @Nullable World worldIn, @Nonnull List<String> tooltip, @Nonnull ITooltipFlag flagIn) {
        int brokenSlots = stack.getTagCompound().getInteger("brokenSlots");
        if (brokenSlots == -1) {
            tooltip.add(I18n.format("tile.brokenworkbench.broken_slots", I18n.format("tile.brokenworkbench.broken_slots.random")));
        } else if (brokenSlots == 0) {
            tooltip.add(I18n.format("tile.brokenworkbench.broken_slots", I18n.format("tile.brokenworkbench.broken_slots.unknown")));
        } else {
            tooltip.add(I18n.format("tile.brokenworkbench.broken_slots", BitwiseUtils.disabledSlotDescription(brokenSlots)));
        }
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        TileEntityBrokenWorkbench tileEntity = new TileEntityBrokenWorkbench();
        if (stack.hasCapability(CapabilityBroken.BROKEN_STORAGE_CAPABILITY, null)) {
            if (stack.getTagCompound() != null && stack.getTagCompound().getInteger("brokenSlots") != 0) {

                if (stack.getTagCompound().getInteger("brokenSlots") == -1)
                    tileEntity.disabledSlots = worldIn.rand.nextInt(512);
                else
                    tileEntity.disabledSlots = stack.getTagCompound().getInteger("brokenSlots");
            } else
                tileEntity.disabledSlots = stack.getCapability(CapabilityBroken.BROKEN_STORAGE_CAPABILITY, null).brokenSlots;

            if (tileEntity.disabledSlots == 0) {
                // If no disabled slots are present then set to vanilla crafting table
                worldIn.removeTileEntity(pos);
                worldIn.setBlockState(pos, Blocks.CRAFTING_TABLE.getDefaultState());
            }
        }
        worldIn.setTileEntity(pos, tileEntity);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote && worldIn.getTileEntity(pos) instanceof TileEntityBrokenWorkbench) { // Display Gui on client side
            playerIn.openGui(BrokenWorkbench.instance, 0, worldIn, ((TileEntityBrokenWorkbench) worldIn.getTileEntity(pos)).disabledSlots, 0, 0);
            playerIn.addStat(StatList.CRAFTING_TABLE_INTERACTION); // Since we are a kind of crafting table LOL
        }
        return true;
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityBrokenWorkbench();
    }
}
