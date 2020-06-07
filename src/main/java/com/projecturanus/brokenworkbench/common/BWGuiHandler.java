package com.projecturanus.brokenworkbench.common;

import com.projecturanus.brokenworkbench.client.GuiBrokenCrafting;
import com.projecturanus.brokenworkbench.common.container.ContainerBrokenWorkbench;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class BWGuiHandler implements IGuiHandler {
    public static final BWGuiHandler INSTANCE = new BWGuiHandler();

    @Nullable
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID == 0)
            return new ContainerBrokenWorkbench(player.inventory, world, x);
        return null;
    }

    @Nullable
    @Override
    @SideOnly(Side.CLIENT)
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID == 0)
            return new GuiBrokenCrafting(new ContainerBrokenWorkbench(player.inventory, world, x), player.inventory, world);
        return null;
    }
}
