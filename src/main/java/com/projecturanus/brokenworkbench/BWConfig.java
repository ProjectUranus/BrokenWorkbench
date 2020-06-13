package com.projecturanus.brokenworkbench;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Config(modid = BrokenWorkbench.MODID)
@Mod.EventBusSubscriber(modid = BrokenWorkbench.MODID, value = Side.CLIENT)
public class BWConfig {
    @Config.Name("Remove Vanilla Crafting Table Recipe")
    @Config.Comment("This setting replaces minecraft:crafting_table with custom recipe, change if you have encountered problems")
    @Config.RequiresMcRestart
    public static boolean replaceVanillaTableRecipe = true;

    @Config.Name("Remove All Crafting Table Recipes")
    @Config.Comment("(DANGEROUS) This setting removes all recipes that produce a crafting table, turn on this settings to override CraftTweaker etc")
    @Config.RequiresMcRestart
    public static boolean removeAllTableRecipe = false;

    @SubscribeEvent
    public static void syncConfig(ConfigChangedEvent event) {
        if (event.getModID().equals(BrokenWorkbench.MODID)) {
            ConfigManager.sync(BrokenWorkbench.MODID, Config.Type.INSTANCE);
        }
    }
}
