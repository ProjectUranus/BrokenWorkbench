package com.projecturanus.brokenworkbench;

import com.projecturanus.brokenworkbench.common.BWGuiHandler;
import com.projecturanus.brokenworkbench.common.block.TileEntityBrokenWorkbench;
import com.projecturanus.brokenworkbench.common.capability.CapabilityBroken;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistryModifiable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

@Mod(modid = BrokenWorkbench.MODID)
@Mod.EventBusSubscriber
public class BrokenWorkbench {
    public static final String MODID = "brokenworkbench";
    public static final Logger log = LogManager.getLogger(MODID);

    @Mod.Instance
    public static BrokenWorkbench instance;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        GameRegistry.registerTileEntity(TileEntityBrokenWorkbench.class, new ResourceLocation(MODID, "broken_workbench"));
        NetworkRegistry.INSTANCE.registerGuiHandler(this, BWGuiHandler.INSTANCE);
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void setCustomModels(ModelRegistryEvent event) {
        ModelLoader.setCustomStateMapper(BWObjects.BLOCK_BROKEN_WORKBENCH, block -> {
            ModelResourceLocation resourceLocation = new ModelResourceLocation("minecraft:blocks/crafting_table");
            return Collections.singletonMap(BWObjects.BLOCK_BROKEN_WORKBENCH.getDefaultState(), resourceLocation);
        });

        ModelLoader.setCustomModelResourceLocation(BWObjects.ITEM_BROKEN_WORKBENCH, 0, new ModelResourceLocation(new ResourceLocation("minecraft", "block/crafting_table"), "inventory"));
    }

    @SubscribeEvent
    public static void attachCapabilities(AttachCapabilitiesEvent<ItemStack> event) {
        if (event.getObject().getItem() == BWObjects.ITEM_BROKEN_WORKBENCH)
            event.addCapability(new ResourceLocation(MODID, "broken_storage"), new CapabilityBroken.BrokenStorageProvider());
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        event.getRegistry().register(BWObjects.BLOCK_BROKEN_WORKBENCH);
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().register(BWObjects.ITEM_BROKEN_WORKBENCH);
    }

    @SubscribeEvent
    public static void removeRecipes(RegistryEvent.Register<IRecipe> event) {
        IForgeRegistryModifiable<IRecipe> registry = (IForgeRegistryModifiable<IRecipe>)event.getRegistry();
        if (BWConfig.removeVanillaTableRecipe)
            registry.remove(new ResourceLocation("minecraft:crafting_table"));

        if (BWConfig.removeAllTableRecipe) {
            // Copy an arraylist to avoid concurrent modification
            List<Map.Entry<ResourceLocation, IRecipe>> list = new CopyOnWriteArrayList<>(registry.getEntries());

            // Search for recipes and remove them
            list.stream()
                .filter(entry -> entry.getValue().getRecipeOutput().getItem() == Item.getItemFromBlock(Blocks.CRAFTING_TABLE))
                .forEach(entry -> registry.remove(entry.getKey()));

            // Clear the list, later collected by GC
            list.clear();
        }
    }
}
