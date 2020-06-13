package com.projecturanus.brokenworkbench;

import com.projecturanus.brokenworkbench.common.block.BlockBrokenWorkbench;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;

public class BWObjects {
    public static final BlockBrokenWorkbench BLOCK_BROKEN_WORKBENCH = new BlockBrokenWorkbench();
    public static final Item ITEM_BROKEN_WORKBENCH = new ItemBlock(BLOCK_BROKEN_WORKBENCH).setRegistryName(BrokenWorkbench.MODID, "broken_crafting_table");
    public static final Item ITEM_WORKBENCH_CONSOLE = new Item().setTranslationKey("brokenworkbench.broken_workbench_console").setRegistryName(BrokenWorkbench.MODID, "broken_workbench_console");
}
