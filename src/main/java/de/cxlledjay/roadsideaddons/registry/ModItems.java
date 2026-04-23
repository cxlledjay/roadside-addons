package de.cxlledjay.roadsideaddons.registry;

import de.cxlledjay.roadsideaddons.RoadsideAddons;
import de.cxlledjay.roadsideaddons.item.SignBrush;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

public class ModItems {

    // ---------------------------- <new items> ----------------------------

    // sign brush
    public static final Item SIGN_BRUSH = registerItem("sign_brush",
            new SignBrush(new Item.Settings().maxDamage(64).maxCount(1)));







    // ---------------------------- <helper methods> ----------------------------

    private static Item registerItem(String name, Item item){
        return Registry.register(Registries.ITEM, Identifier.of(RoadsideAddons.MOD_ID, name), item);
    }

    public static void registerModItems() {
        RoadsideAddons.LOGGER.info("Registering Mod Items for " + RoadsideAddons.MOD_ID);
    }

}
