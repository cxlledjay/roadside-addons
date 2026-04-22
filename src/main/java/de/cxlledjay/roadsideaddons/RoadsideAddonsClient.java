package de.cxlledjay.roadsideaddons;

import de.cxlledjay.roadsideaddons.registry.ModBlocks;
import net.fabricmc.api.ClientModInitializer;

public class RoadsideAddonsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ModBlocks.registerTransparency();
    }
}
