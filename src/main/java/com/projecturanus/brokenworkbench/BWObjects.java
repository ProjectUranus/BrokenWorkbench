package com.projecturanus.brokenworkbench;

import com.projecturanus.brokenworkbench.common.block.BlockBrokenWorkbench;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;

public class BWObjects {
    public static final BlockBrokenWorkbench BLOCK_BROKEN_WORKBENCH = new BlockBrokenWorkbench();
    public static final Item ITEM_BROKEN_WORKBENCH = new ItemBlock(BLOCK_BROKEN_WORKBENCH).setRegistryName(BrokenWorkbench.MODID, "broken_workbench");
}
