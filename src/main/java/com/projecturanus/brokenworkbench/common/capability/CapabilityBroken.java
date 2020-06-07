package com.projecturanus.brokenworkbench.common.capability;

import com.sun.istack.internal.NotNull;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

import javax.annotation.Nullable;

public class CapabilityBroken {
    @CapabilityInject(BrokenStorage.class)
    public static final Capability<BrokenStorage> BROKEN_STORAGE_CAPABILITY = null;

    public static class BrokenStorageProvider implements ICapabilitySerializable<NBTTagInt> {
        public BrokenStorage brokenStorage = new BrokenStorage();

        @Override
        public boolean hasCapability(@NotNull Capability<?> capability, @Nullable EnumFacing facing) {
            return capability == BROKEN_STORAGE_CAPABILITY;
        }

        @Nullable
        @Override
        public <T> T getCapability(@NotNull Capability<T> capability, @Nullable EnumFacing facing) {
            return capability == BROKEN_STORAGE_CAPABILITY ? (T) brokenStorage : null;
        }

        @Override
        public NBTTagInt serializeNBT() {
            return new NBTTagInt(brokenStorage.brokenSlots);
        }

        @Override
        public void deserializeNBT(NBTTagInt nbt) {
            brokenStorage.brokenSlots = nbt.getInt();
        }
    }
}
