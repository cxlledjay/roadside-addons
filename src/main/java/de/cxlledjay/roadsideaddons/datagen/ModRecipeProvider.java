package de.cxlledjay.roadsideaddons.datagen;

import de.cxlledjay.roadsideaddons.registry.ModBlocks;
import de.cxlledjay.roadsideaddons.registry.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.block.Blocks;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.ItemGroup;
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
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModBlocks.SIGN_POST_BASE,4)
                .pattern(" I ")
                .pattern(" I ")
                .pattern("NIN")
                .input('I', Items.IRON_INGOT)
                .input('N', Items.IRON_NUGGET)
                .criterion(hasItem(Items.IRON_INGOT), conditionsFromItem(Items.IRON_INGOT))
                .criterion(hasItem(Items.IRON_NUGGET), conditionsFromItem(Items.IRON_NUGGET))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModBlocks.SIGN_POST,4)
                .pattern("I")
                .pattern("I")
                .pattern("I")
                .input('I', Items.IRON_INGOT)
                .criterion(hasItem(Items.IRON_INGOT), conditionsFromItem(Items.IRON_INGOT))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModBlocks.SIGN_POST_BASE_CONSTRUCTION,4)
                .pattern(" I ")
                .pattern(" I ")
                .pattern("CIC")
                .input('I', Items.IRON_INGOT)
                .input('C', Items.CYAN_TERRACOTTA)
                .criterion(hasItem(Items.IRON_INGOT), conditionsFromItem(Items.IRON_INGOT))
                .criterion(hasItem(Items.CYAN_TERRACOTTA), conditionsFromItem(Items.CYAN_TERRACOTTA))
                .offerTo(exporter);


        // ------------------------- <sign variants> -------------------------

        // danger
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModBlocks.SIGN_DANGER,4)
                .pattern(" N ")
                .pattern("NFN")
                .pattern("III")
                .input('N', Items.IRON_NUGGET)
                .input('F', Items.ITEM_FRAME)
                .input('I', Items.IRON_INGOT)
                .criterion(hasItem(Items.IRON_INGOT), conditionsFromItem(Items.IRON_INGOT))
                .criterion(hasItem(Items.IRON_NUGGET), conditionsFromItem(Items.IRON_NUGGET))
                .criterion(hasItem(Items.ITEM_FRAME), conditionsFromItem(Items.ITEM_FRAME))
                .offerTo(exporter);

        // yield
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModBlocks.SIGN_YIELD,4)
                .pattern("III")
                .pattern("NFN")
                .pattern(" N ")
                .input('I', Items.IRON_INGOT)
                .criterion(hasItem(Items.IRON_INGOT), conditionsFromItem(Items.IRON_INGOT))
                .input('N', Items.IRON_NUGGET)
                .input('F', Items.ITEM_FRAME)
                .criterion(hasItem(Items.IRON_NUGGET), conditionsFromItem(Items.IRON_NUGGET))
                .criterion(hasItem(Items.ITEM_FRAME), conditionsFromItem(Items.ITEM_FRAME))
                .offerTo(exporter);

        // stop
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModBlocks.SIGN_STOP,4)
                .pattern("INI")
                .pattern("NFN")
                .pattern("INI")
                .input('N', Items.IRON_NUGGET)
                .input('F', Items.ITEM_FRAME)
                .input('I', Items.IRON_INGOT)
                .criterion(hasItem(Items.IRON_INGOT), conditionsFromItem(Items.IRON_INGOT))
                .criterion(hasItem(Items.IRON_NUGGET), conditionsFromItem(Items.IRON_NUGGET))
                .criterion(hasItem(Items.ITEM_FRAME), conditionsFromItem(Items.ITEM_FRAME))
                .offerTo(exporter);

        // regulatory
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModBlocks.SIGN_REGULATORY,4)
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

        // info
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModBlocks.SIGN_INFORMATION,8)
                .pattern("III")
                .pattern("IFI")
                .pattern("III")
                .input('F', Items.ITEM_FRAME)
                .input('I', Items.IRON_INGOT)
                .criterion(hasItem(Items.IRON_INGOT), conditionsFromItem(Items.IRON_INGOT))
                .criterion(hasItem(Items.ITEM_FRAME), conditionsFromItem(Items.ITEM_FRAME))
                .offerTo(exporter);

        // priority road
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModBlocks.SIGN_PRIORITY_ROAD,4)
                .pattern(" I ")
                .pattern("IFI")
                .pattern(" I ")
                .input('F', Items.ITEM_FRAME)
                .input('I', Items.IRON_INGOT)
                .criterion(hasItem(Items.IRON_INGOT), conditionsFromItem(Items.IRON_INGOT))
                .criterion(hasItem(Items.ITEM_FRAME), conditionsFromItem(Items.ITEM_FRAME))
                .offerTo(exporter);

        // oneway
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModBlocks.SIGN_ONEWAY,4)
                .pattern("III")
                .pattern(" P ")
                .input('I', Items.IRON_INGOT)
                .input('P', ModBlocks.SIGN_POST)
                .criterion(hasItem(ModBlocks.SIGN_POST), conditionsFromItem(ModBlocks.SIGN_POST))
                .criterion(hasItem(Items.IRON_INGOT), conditionsFromItem(Items.IRON_INGOT))
                .offerTo(exporter);

        // sup wide
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModBlocks.SIGN_SUPPLEMENTARY_WIDE,2)
                .pattern("NNN")
                .pattern(" P ")
                .input('N', Items.IRON_NUGGET)
                .input('P', ModBlocks.SIGN_POST)
                .criterion(hasItem(ModBlocks.SIGN_POST), conditionsFromItem(ModBlocks.SIGN_POST))
                .criterion(hasItem(Items.IRON_NUGGET), conditionsFromItem(Items.IRON_NUGGET))
                .offerTo(exporter);

        // sup square
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModBlocks.SIGN_SUPPLEMENTARY_SQUARE,2)
                .pattern("NN")
                .pattern("NN")
                .pattern(" P")
                .input('N', Items.IRON_NUGGET)
                .input('P', ModBlocks.SIGN_POST)
                .criterion(hasItem(ModBlocks.SIGN_POST), conditionsFromItem(ModBlocks.SIGN_POST))
                .criterion(hasItem(Items.IRON_NUGGET), conditionsFromItem(Items.IRON_NUGGET))
                .offerTo(exporter);



        // ------------------------- <construction> -------------------------

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModBlocks.TRAFFIC_CONE,9)
                .pattern(" W ")
                .pattern("OOO")
                .pattern("GGG")
                .input('O', Items.ORANGE_CONCRETE)
                .input('W', Items.WHITE_CONCRETE)
                .input('G', Items.GRAY_CONCRETE)
                .criterion(hasItem(Items.ORANGE_CONCRETE), conditionsFromItem(Items.ORANGE_CONCRETE))
                .criterion(hasItem(Items.WHITE_CONCRETE), conditionsFromItem(Items.WHITE_CONCRETE))
                .criterion(hasItem(Items.GRAY_CONCRETE), conditionsFromItem(Items.GRAY_CONCRETE))
                .offerTo(exporter);


        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModBlocks.BOLLARD_CONSTRUCTION,4)
                .pattern(" W ")
                .pattern(" R ")
                .pattern("CWC")
                .input('R', Items.RED_CONCRETE)
                .input('W', Items.WHITE_CONCRETE)
                .input('C', Items.CYAN_TERRACOTTA)
                .criterion(hasItem(Items.RED_CONCRETE), conditionsFromItem(Items.RED_CONCRETE))
                .criterion(hasItem(Items.WHITE_CONCRETE), conditionsFromItem(Items.WHITE_CONCRETE))
                .criterion(hasItem(Items.CYAN_TERRACOTTA), conditionsFromItem(Items.CYAN_TERRACOTTA))
                .offerTo(exporter);


        ShapelessRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModBlocks.BOLLARD_CONSTRUCTION_LAMP, 1)
                .input(ModBlocks.BOLLARD_CONSTRUCTION)
                .input(Blocks.LANTERN)
                .criterion(hasItem(ModBlocks.BOLLARD_CONSTRUCTION), conditionsFromItem(ModBlocks.BOLLARD_CONSTRUCTION))
                .criterion(hasItem(Blocks.LANTERN), conditionsFromItem(Blocks.LANTERN))
                .offerTo(exporter);


        // ------------------------- <items> -------------------------
        // sign brush
        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.SIGN_BRUSH,1)
                .pattern("WB ")
                .pattern("RC ")
                .pattern("  S")
                .input('R', Items.RED_DYE)
                .input('W', Items.WHITE_DYE)
                .input('B', Items.BLUE_DYE)
                .input('C', Items.WHITE_WOOL)
                .input('S', Items.STICK)
                .criterion(hasItem(Items.RED_DYE), conditionsFromItem(Items.RED_DYE))
                .criterion(hasItem(Items.WHITE_DYE), conditionsFromItem(Items.WHITE_DYE))
                .criterion(hasItem(Items.BLUE_DYE), conditionsFromItem(Items.BLUE_DYE))
                .criterion(hasItem(Items.WHITE_WOOL), conditionsFromItem(Items.WHITE_WOOL))
                .criterion(hasItem(Items.STICK), conditionsFromItem(Items.STICK))
                .offerTo(exporter);
    }
}
