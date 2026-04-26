package de.cxlledjay.roadsideaddons.block.sign.generic;

import net.minecraft.util.StringIdentifiable;

public interface SignVariant extends StringIdentifiable {

    @Override
    String asString();

    /// get variant category for gui grouping
    String getCategory();
}

