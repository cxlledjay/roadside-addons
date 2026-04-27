package de.cxlledjay.roadsideaddons.registry;

import de.cxlledjay.roadsideaddons.RoadsideAddons;
import de.cxlledjay.roadsideaddons.block.construction.BollardConstruction;
import de.cxlledjay.roadsideaddons.block.sign.post.SignPostBase;
import de.cxlledjay.roadsideaddons.block.sign.post.SignPostBaseConstruction;
import de.cxlledjay.roadsideaddons.block.construction.TrafficCone;
import de.cxlledjay.roadsideaddons.block.sign.variants.*;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.MapColor;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

public class ModBlocks {

    // ---------------------------- <new blocks> ----------------------------

    // ---------------------------- <sign poles> ----------------------------
    public static final Block SIGN_POST= registerBlock("sign_post",
            new SignPostBase(AbstractBlock.Settings.create().nonOpaque().strength(2f).requiresTool().mapColor(MapColor.GRAY).sounds(BlockSoundGroup.COPPER)));

    public static final Block SIGN_POST_BASE = registerBlock("sign_post_base",
            new SignPostBase(AbstractBlock.Settings.create().nonOpaque().strength(2f).requiresTool().mapColor(MapColor.GRAY).sounds(BlockSoundGroup.COPPER)));

    public static final Block SIGN_POST_BASE_CONSTRUCTION = registerBlock("sign_post_base_construction",
            new SignPostBaseConstruction(AbstractBlock.Settings.create().nonOpaque().strength(2f).requiresTool().mapColor(MapColor.GRAY).sounds(BlockSoundGroup.METAL)));


    // ---------------------------- <sign variants> ----------------------------
    public static final Block SIGN_DANGER = registerBlock("sign_danger",
            new SignDanger(AbstractBlock.Settings.create().nonOpaque().strength(2f).requiresTool().mapColor(MapColor.GRAY).sounds(BlockSoundGroup.COPPER)));

    public static final Block SIGN_STOP = registerBlock("sign_stop",
            new SignStop(AbstractBlock.Settings.create().nonOpaque().strength(2f).requiresTool().mapColor(MapColor.GRAY).sounds(BlockSoundGroup.COPPER)));

    public static final Block SIGN_YIELD = registerBlock("sign_yield",
            new SignYield(AbstractBlock.Settings.create().nonOpaque().strength(2f).requiresTool().mapColor(MapColor.GRAY).sounds(BlockSoundGroup.COPPER)));

    public static final Block SIGN_REGULATORY = registerBlock("sign_regulatory",
            new SignRegulatory(AbstractBlock.Settings.create().nonOpaque().strength(2f).requiresTool().mapColor(MapColor.GRAY).sounds(BlockSoundGroup.COPPER)));

    public static final Block SIGN_PRIORITY_ROAD= registerBlock("sign_priority_road",
            new SignPriorityRoad(AbstractBlock.Settings.create().nonOpaque().strength(2f).requiresTool().mapColor(MapColor.GRAY).sounds(BlockSoundGroup.COPPER)));


    // ---------------------------- <construction> ----------------------------
     public static final Block TRAFFIC_CONE = registerBlock("traffic_cone",
             new TrafficCone(AbstractBlock.Settings.create().nonOpaque().strength(0.5f).mapColor(MapColor.ORANGE).sounds(BlockSoundGroup.POINTED_DRIPSTONE)));

    public static final Block BOLLARD_CONSTRUCTION = registerBlock("bollard_construction",
            new BollardConstruction(AbstractBlock.Settings.create().nonOpaque().strength(1.5f).mapColor(MapColor.ORANGE).sounds(BlockSoundGroup.METAL)));




    // ---------------------------- <transparency fix> ----------------------------
    public static void registerTransparency(){
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.SIGN_DANGER, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.SIGN_STOP, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.SIGN_YIELD, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.SIGN_REGULATORY, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.SIGN_PRIORITY_ROAD, RenderLayer.getCutout());
    }



    // ---------------------------- <helper methods> ----------------------------

    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, Identifier.of(RoadsideAddons.MOD_ID, name), block);
    }

    private static void registerBlockItem(String name, Block block) {
        Registry.register(Registries.ITEM, Identifier.of(RoadsideAddons.MOD_ID, name),
                new BlockItem(block, new Item.Settings()));
    }

    public static void registerModBlocks() {
        RoadsideAddons.LOGGER.info("Registering Mod Blocks for " + RoadsideAddons.MOD_ID);
    }

}
