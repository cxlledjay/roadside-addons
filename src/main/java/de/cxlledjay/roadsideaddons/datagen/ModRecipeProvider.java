package de.cxlledjay.roadsideaddons.datagen;

import de.cxlledjay.roadsideaddons.registry.ModBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends FabricRecipeProvider {
    public ModRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void generate(RecipeExporter exporter) {

        // ------------------------- <sign posts> -------------------------
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModBlocks.SIGN_POST_BASE,3)
                .pattern(" I ")
                .pattern(" I ")
                .pattern("NIN")
                .input('I', Items.IRON_INGOT)
                .input('N', Items.IRON_NUGGET)
                .criterion(hasItem(Items.IRON_INGOT), conditionsFromItem(Items.IRON_INGOT))
                .criterion(hasItem(Items.IRON_NUGGET), conditionsFromItem(Items.IRON_NUGGET))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModBlocks.SIGN_POST,3)
                .pattern("I")
                .pattern("I")
                .pattern("I")
                .input('I', Items.IRON_INGOT)
                .criterion(hasItem(Items.IRON_INGOT), conditionsFromItem(Items.IRON_INGOT))
                .offerTo(exporter);


        // ------------------------- <signs> -------------------------

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModBlocks.SIGN_DANGER,1)
                .pattern(" N ")
                .pattern("NFN")
                .pattern("NNN")
                .input('N', Items.IRON_NUGGET)
                .input('F', Items.ITEM_FRAME)
                .criterion(hasItem(Items.IRON_NUGGET), conditionsFromItem(Items.IRON_NUGGET))
                .criterion(hasItem(Items.ITEM_FRAME), conditionsFromItem(Items.ITEM_FRAME))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModBlocks.SIGN_YIELD,1)
                .pattern("NNN")
                .pattern("NFN")
                .pattern(" N ")
                .input('N', Items.IRON_NUGGET)
                .input('F', Items.ITEM_FRAME)
                .criterion(hasItem(Items.IRON_NUGGET), conditionsFromItem(Items.IRON_NUGGET))
                .criterion(hasItem(Items.ITEM_FRAME), conditionsFromItem(Items.ITEM_FRAME))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModBlocks.SIGN_STOP,3)
                .pattern("NIN")
                .pattern("IFI")
                .pattern("NIN")
                .input('N', Items.IRON_NUGGET)
                .input('F', Items.ITEM_FRAME)
                .input('I', Items.IRON_INGOT)
                .criterion(hasItem(Items.IRON_INGOT), conditionsFromItem(Items.IRON_INGOT))
                .criterion(hasItem(Items.IRON_NUGGET), conditionsFromItem(Items.IRON_NUGGET))
                .criterion(hasItem(Items.ITEM_FRAME), conditionsFromItem(Items.ITEM_FRAME))
                .offerTo(exporter);
    }
}
