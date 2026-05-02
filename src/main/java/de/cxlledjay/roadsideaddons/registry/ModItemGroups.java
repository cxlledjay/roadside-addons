package de.cxlledjay.roadsideaddons.registry;

import de.cxlledjay.roadsideaddons.RoadsideAddons;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroups {

    public static final ItemGroup ROADSIDE_ADDONS_GROUP = Registry.register(Registries.ITEM_GROUP,
            Identifier.of(RoadsideAddons.MOD_ID + "items"),
            FabricItemGroup.builder()
                    .icon(() -> new ItemStack(ModBlocks.SIGN_POST_BASE))
                    .displayName(Text.translatable("itemgroup.roadside-addons.items"))
                    .entries((displayContext, entries) -> {
                        // ----- blocks

                        // sign post
                        entries.add(ModBlocks.SIGN_POST);
                        entries.add(ModBlocks.SIGN_POST_BASE);
                        entries.add(ModBlocks.SIGN_POST_BASE_CONSTRUCTION);
                        // sign variants
                        entries.add(ModBlocks.SIGN_DANGER);
                        entries.add(ModBlocks.SIGN_STOP);
                        entries.add(ModBlocks.SIGN_YIELD);
                        entries.add(ModBlocks.SIGN_REGULATORY);
                        entries.add(ModBlocks.SIGN_PRIORITY_ROAD);
                        entries.add(ModBlocks.SIGN_INFORMATION);
                        entries.add(ModBlocks.SIGN_ONEWAY);
                        entries.add(ModBlocks.SIGN_SUPPLEMENTARY_SQUARE);
                        entries.add(ModBlocks.SIGN_SUPPLEMENTARY_WIDE);

                        // construction
                        entries.add(ModBlocks.TRAFFIC_CONE);
                        entries.add(ModBlocks.BOLLARD_CONSTRUCTION);
                        entries.add(ModBlocks.BOLLARD_CONSTRUCTION_LAMP);

                        // bollard
                        entries.add(ModBlocks.BOLLARD_GERMAN);

                        // ----- items
                        entries.add(ModItems.SIGN_BRUSH);
                    })
            .build());



    public static void registerItemGroups() {
        RoadsideAddons.LOGGER.info("Registering Item Groups for " + RoadsideAddons.MOD_ID);
    }

}
