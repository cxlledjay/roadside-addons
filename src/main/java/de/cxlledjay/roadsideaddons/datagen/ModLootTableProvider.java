package de.cxlledjay.roadsideaddons.datagen;

import de.cxlledjay.roadsideaddons.registry.ModBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class ModLootTableProvider extends FabricBlockLootTableProvider {
    public ModLootTableProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generate() {
        // sign posts
        addDrop(ModBlocks.SIGN_POST);
        addDrop(ModBlocks.SIGN_POST_BASE);
        addDrop(ModBlocks.SIGN_POST_BASE_CONSTRUCTION);
        // sign variants
        addDrop(ModBlocks.SIGN_DANGER);
        addDrop(ModBlocks.SIGN_YIELD);
        addDrop(ModBlocks.SIGN_STOP);
        // construction
        addDrop(ModBlocks.TRAFFIC_CONE);
        addDrop(ModBlocks.BOLLARD_CONSTRUCTION);
    }
}
