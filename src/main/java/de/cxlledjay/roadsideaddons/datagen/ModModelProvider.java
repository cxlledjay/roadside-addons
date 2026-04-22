package de.cxlledjay.roadsideaddons.datagen;

import com.google.gson.JsonPrimitive;
import de.cxlledjay.roadsideaddons.RoadsideAddons;
import de.cxlledjay.roadsideaddons.block.RotatableBlock;
import de.cxlledjay.roadsideaddons.registry.ModBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.block.Block;
import net.minecraft.data.client.*;
import net.minecraft.registry.Registries;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;

public class ModModelProvider extends FabricModelProvider {

    // constructor
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    // ---------------------------- <generate block models> ----------------------------
    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        // already generates item model for block here!!!
        register8WaySign(blockStateModelGenerator, ModBlocks.SIGN_POST_BASE);
        register8WaySign(blockStateModelGenerator, ModBlocks.SIGN_POST);
        register8WaySign(blockStateModelGenerator, ModBlocks.SIGN_DANGER);
        register8WaySign(blockStateModelGenerator, ModBlocks.SIGN_STOP);
        register8WaySign(blockStateModelGenerator, ModBlocks.SIGN_YIELD);
    }

    // ---------------------------- <generate item models> ----------------------------
    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        // ...
    }






    // ---------------------------- <helper methods> ----------------------------
    private void register8WaySign(BlockStateModelGenerator generator, Block block) {
        // Get the base name (e.g., "sign_post_base")
        String baseName = Registries.BLOCK.getId(block).getPath();

        // 1. Define the two model locations
        Identifier straightModel = Identifier.of(RoadsideAddons.MOD_ID, "block/" + baseName);
        Identifier diagonalModel = Identifier.of(RoadsideAddons.MOD_ID, "block/" + baseName + "45");

        // 2. Build the variant map
        generator.blockStateCollector.accept(VariantsBlockStateSupplier.create(block)
                .coordinate(BlockStateVariantMap.create(RotatableBlock.ROTATION)
                        .register(rotation -> {
                            // Alternates: 0, 2, 4, 6 are straight | 1, 3, 5, 7 are diagonal
                            Identifier model = (rotation % 2 == 0) ? straightModel : diagonalModel;

                            // Calculates 0, 90, 180, 270 based on the pair
                            int yStep = (rotation / 2) * 90;
                            VariantSettings.Rotation yRot = getRotationFromDegrees(yStep);

                            return BlockStateVariant.create()
                                    .put(VariantSettings.MODEL, model)
                                    .put(VariantSettings.Y, yRot);
                        })
                )
        );
    }

    // Helper to map the integer degrees to the Enum required by the API
    private VariantSettings.Rotation getRotationFromDegrees(int degrees) {
        return switch (degrees % 360) {
            case 90 -> VariantSettings.Rotation.R90;
            case 180 -> VariantSettings.Rotation.R180;
            case 270 -> VariantSettings.Rotation.R270;
            default -> VariantSettings.Rotation.R0;
        };
    }
}
