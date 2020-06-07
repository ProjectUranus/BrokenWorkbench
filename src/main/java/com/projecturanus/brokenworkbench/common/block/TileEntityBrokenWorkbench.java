package com.projecturanus.brokenworkbench.common.block;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TileEntityBrokenWorkbench extends TileEntity {
    /**
     * Disabled slots in the workbench.
     * As we know there are 9 slots in the workbench, indexed 0-8.
     * Our binary data will storage the disabled status in 0 or 1 each bit
     * So, a workbench disabling slot 4 and slot 6, we switch the fifth and the seventh bit to 1
     * In this case we get 000010100 or 4160 in decimal
     * From 0b000000000 to 0b111111111, 511 types of workbench can be created.
     */
    public int disabledSlots = 0;

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        disabledSlots = compound.getInteger("disabledSlots");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setInteger("disabledSlots", disabledSlots);
        return super.writeToNBT(compound);
    }
}
