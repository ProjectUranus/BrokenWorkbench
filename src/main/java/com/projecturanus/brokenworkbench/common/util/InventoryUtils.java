package com.projecturanus.brokenworkbench.common.util;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

import java.util.List;
import java.util.ListIterator;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * JAVA SUCKS FOR NOT HAVING EXTENSION METHOD
 */
public class InventoryUtils {
    public static ListIterator<ItemStack> iterator(IInventory inventory) {
        return new ListIterator<ItemStack>() {
            public int index = 0;

            @Override
            public boolean hasNext() {
                return inventory.getSizeInventory() >= index;
            }

            @Override
            public ItemStack next() {
                return inventory.getStackInSlot(index++);
            }

            @Override
            public boolean hasPrevious() {
                return index - 1 < inventory.getSizeInventory();
            }

            @Override
            public ItemStack previous() {
                return inventory.getStackInSlot(--index);
            }

            @Override
            public int nextIndex() {
                return index;
            }

            @Override
            public int previousIndex() {
                return index - 1;
            }

            @Override
            public void remove() {
                inventory.removeStackFromSlot(index);
            }

            @Override
            public void set(ItemStack itemStack) {
                inventory.setInventorySlotContents(index, itemStack);
            }

            @Override
            public void add(ItemStack itemStack) {
                throw new UnsupportedOperationException("add in IInventory is not supported");
            }
        };
    }

    public static List<ItemStack> copyAsList(IInventory inventory) {
        return stream(inventory).collect(Collectors.toList());
    }

    public static Stream<ItemStack> stream(IInventory inventory) {
        return StreamSupport.stream(Spliterators.spliterator(iterator(inventory), inventory.getSizeInventory(), 0), false);
    }

    /**
     * All Minecraft-related actions on this stream should call {@link net.minecraft.util.IThreadListener#addScheduledTask(Runnable)}
     * @throws java.util.ConcurrentModificationException if you perform any action on inventory
     */
    public static Stream<ItemStack> parallelStream(IInventory inventory) {
        return StreamSupport.stream(Spliterators.spliterator(iterator(inventory), inventory.getSizeInventory(), 0), true);
    }
}
