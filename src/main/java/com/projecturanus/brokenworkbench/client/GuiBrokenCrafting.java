package com.projecturanus.brokenworkbench.client;

import com.projecturanus.brokenworkbench.common.container.ContainerBrokenWorkbench;
import net.minecraft.client.gui.inventory.GuiCrafting;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.world.World;

public class GuiBrokenCrafting extends GuiCrafting {
    public GuiBrokenCrafting(ContainerBrokenWorkbench container, InventoryPlayer playerInv, World worldIn) {
        super(playerInv, worldIn);
        inventorySlots = container;
    }
}
