package com.projecturanus.brokenworkbench.common.block;

import com.projecturanus.brokenworkbench.BrokenWorkbench;
import com.projecturanus.brokenworkbench.common.capability.CapabilityBroken;
import net.minecraft.block.BlockWorkbench;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class BlockBrokenWorkbench extends BlockWorkbench implements ITileEntityProvider {

    public BlockBrokenWorkbench() {
        setRegistryName(BrokenWorkbench.MODID, "broken_workbench");
        setTranslationKey("brokenworkbench.broken_workbench");
    }

    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        super.breakBlock(worldIn, pos, state);
        worldIn.removeTileEntity(pos);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(@Nonnull ItemStack stack, @Nullable World worldIn, @Nonnull List<String> tooltip, @Nonnull ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        TileEntityBrokenWorkbench tileEntity = new TileEntityBrokenWorkbench();
        if (stack.hasCapability(CapabilityBroken.BROKEN_STORAGE_CAPABILITY, null)) {
            if (stack.getTagCompound() != null && stack.getTagCompound().getInteger("brokenSlots") != 0)
                tileEntity.disabledSlots = stack.getTagCompound().getInteger("brokenSlots");
            else
                tileEntity.disabledSlots = stack.getCapability(CapabilityBroken.BROKEN_STORAGE_CAPABILITY, null).brokenSlots;
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
