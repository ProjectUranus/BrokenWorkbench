package com.projecturanus.brokenworkbench.common.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class DisabledSlot extends Slot {
    public DisabledSlot(IInventory inventoryIn, int index, int xPosition, int yPosition) {
        super(inventoryIn, index, xPosition, yPosition);
    }

    @Nullable
    @Override
    public String getSlotTexture() {
        return "minecraft:items/barrier";
    }

    public boolean isItemValid(@Nonnull ItemStack stack) {
        return false;
    }

    @Override
    public boolean canTakeStack(EntityPlayer playerIn) {
        return false;
    }

    @Override
    public int getSlotStackLimit() {
        return 0;
    }

    @SideOnly(Side.CLIENT)
    public boolean isEnabled() {
        return true;
    }
}
