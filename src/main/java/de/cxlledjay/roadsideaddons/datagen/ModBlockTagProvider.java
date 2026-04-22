package de.cxlledjay.roadsideaddons.datagen;

import de.cxlledjay.roadsideaddons.registry.ModBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends FabricTagProvider.BlockTagProvider {
    public ModBlockTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup lookup) {
        getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE)
                .add(ModBlocks.SIGN_POST)
                .add(ModBlocks.SIGN_POST_BASE)
                .add(ModBlocks.SIGN_DANGER)
                .add(ModBlocks.SIGN_YIELD)
                .add(ModBlocks.SIGN_STOP);

        getOrCreateTagBuilder(BlockTags.NEEDS_IRON_TOOL)
                .add(ModBlocks.SIGN_POST)
                .add(ModBlocks.SIGN_POST_BASE)
                .add(ModBlocks.SIGN_DANGER)
                .add(ModBlocks.SIGN_YIELD)
                .add(ModBlocks.SIGN_STOP);
    }
}
