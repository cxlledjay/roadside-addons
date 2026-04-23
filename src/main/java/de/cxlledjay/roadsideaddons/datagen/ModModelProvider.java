package de.cxlledjay.roadsideaddons.datagen;

import com.google.gson.JsonPrimitive;
import de.cxlledjay.roadsideaddons.RoadsideAddons;
import de.cxlledjay.roadsideaddons.block.RotatableBlock;
import de.cxlledjay.roadsideaddons.registry.ModBlocks;
import de.cxlledjay.roadsideaddons.registry.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.block.Block;
import net.minecraft.data.client.*;
import net.minecraft.registry.Registries;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;

public class ModModelProvider extends FabricModelProvider {

    private static FabricDataOutput out;

    // constructor
    public ModModelProvider(FabricDataOutput output) {
        super(output);
        out = output;
    }

    // ---------------------------- <generate block models> ----------------------------
    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        // already generates item model for block here!!!

        // generate rotating block models for all 16 way rotating blocks
        SignModelHelper.generateRotatableBlockData(out, blockStateModelGenerator, ModBlocks.SIGN_POST_BASE);
        SignModelHelper.generateRotatableBlockData(out, blockStateModelGenerator, ModBlocks.SIGN_POST);
        SignModelHelper.generateSignData(out, blockStateModelGenerator, ModBlocks.SIGN_DANGER);
        SignModelHelper.generateSignData(out, blockStateModelGenerator, ModBlocks.SIGN_YIELD);
        SignModelHelper.generateSignData(out, blockStateModelGenerator, ModBlocks.SIGN_STOP);
    }

    // ---------------------------- <generate item models> ----------------------------
    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(ModItems.SIGN_BRUSH, Models.GENERATED);
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
