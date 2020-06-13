package com.projecturanus.brokenworkbench.common.container;

import com.projecturanus.brokenworkbench.common.util.BitwiseUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ContainerWorkbench;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

public class ContainerBrokenWorkbench extends ContainerWorkbench {
    private final World world;
    private int disabledSlots;

    public ContainerBrokenWorkbench(InventoryPlayer playerInventory, World worldIn, int disabledSlots) {
        super(playerInventory, worldIn, new BlockPos(0, 0, 0));
        this.disabledSlots = disabledSlots;
        this.world = worldIn;
        inventoryItemStacks.clear();
        inventorySlots.clear();

        this.addSlotToContainer(new SlotCrafting(playerInventory.player, this.craftMatrix, this.craftResult, 0, 124, 35));

        for (int i = 0; i < 3; ++i)
        {
            for (int j = 0; j < 3; ++j)
            {
                if (BitwiseUtils.isTrueOnIndex(j + i * 3, disabledSlots)) {
                    addSlotToContainer(new DisabledSlot(craftMatrix, j + i * 3, 30 + j * 18, 17 + i * 18));
                } else
                    this.addSlotToContainer(new Slot(this.craftMatrix, j + i * 3, 30 + j * 18, 17 + i * 18));
            }
        }

        for (int k = 0; k < 3; ++k)
        {
            for (int i1 = 0; i1 < 9; ++i1)
            {
                this.addSlotToContainer(new Slot(playerInventory, i1 + k * 9 + 9, 8 + i1 * 18, 84 + k * 18));
            }
        }

        for (int l = 0; l < 9; ++l)
        {
            this.addSlotToContainer(new Slot(playerInventory, l, 8 + l * 18, 142));
        }
    }

    public void detectAndSendChanges() {
        super.detectAndSendChanges();

        for (IContainerListener icontainerlistener : this.listeners) {
            icontainerlistener.sendWindowProperty(this, 0, disabledSlots);
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void updateProgressBar(int id, int data) {
        super.updateProgressBar(id, data);
        if (id == 0) disabledSlots = data;
    }

    public boolean canInteractWith(@Nonnull EntityPlayer playerIn) {
        return true;
    }


}
