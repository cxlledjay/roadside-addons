package de.cxlledjay.roadsideaddons.datagen;

import com.google.gson.*;
import de.cxlledjay.roadsideaddons.RoadsideAddons;
import de.cxlledjay.roadsideaddons.block.RotatableBlock;
import de.cxlledjay.roadsideaddons.block.sign.generic.AbstractSign;
import de.cxlledjay.roadsideaddons.block.sign.generic.SignVariant;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.block.Block;
import net.minecraft.data.client.*;
import net.minecraft.registry.Registries;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.util.Identifier;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class SignModelHelper {


    private static final double[] angles = {0, -22.5, -45, 22.5}; //< all four rotation angles (DO NOT EDIT!!)
    private static final String[] fileEndings = {"0", "n22", "n45", "22"}; //< matching names for rotation angles (DO NOT EDIT!!)



    // ---------------------------- <interfaces / wrapper> ----------------------------


    // register a "normal" 16 way rotatable block with only one variant (e.g. cone, pole, bollard, ...)
    public static void generateRotatableBlockData(FabricDataOutput output, BlockStateModelGenerator generator, Block modblock) {
        // generate block and item model for this block
        generateRotatedBlockModelsFromTemplate(modblock, output, generator);

        // generate blockstates for rotation
        generateNormalBlockstates(modblock, generator);
    }



    // register signs with variable amount of variants
    public static void generateSignData(FabricDataOutput output, BlockStateModelGenerator generator, Block modblock) {
        // generate block and item model for this block
        generateSignBlockModels(modblock, output, generator);

        // generate blockstates for rotation and variant
        generateSignBlockstates(modblock, generator);
    }


    // ---------------------------- <rotation generator> ----------------------------

    private static void generateRotatedBlockModelsFromTemplate(Block modblock, FabricDataOutput output, BlockStateModelGenerator generator) {

        // get filesystem path
        Path templateModelDir = output.getPath().getParent()
                .resolve("resources/assets")
                .resolve(RoadsideAddons.MOD_ID)
                .resolve("models/block");

        //RoadsideAddons.LOGGER.info("template models directory located: " + templateModelDir.toString());


        // get identifier of handled block
        String blockId = Registries.BLOCK.getId(modblock).getPath();
        RoadsideAddons.LOGGER.info("generating 16 way rotatable block models for: " + blockId);
        Path templatePath = templateModelDir.resolve(blockId + ".json");



        // read in template JSON
        String jsonContent = null;
        try {
            jsonContent = Files.readString(templatePath);
        } catch (IOException e) {
            RoadsideAddons.LOGGER.error("could not resolve templatePath: " + templatePath.toString());
            return;
        }

        JsonObject templateJson = JsonParser.parseString(jsonContent).getAsJsonObject();
        //RoadsideAddons.LOGGER.info("template json object" + templateJson.toString());



        // create 4 different rotated models

        for(int i = 0; i < angles.length; ++i) {


            // create copy!!
            JsonObject modifiedJson = templateJson.deepCopy();

            // iterate trough json object and apply rotation
            applyRotation(modifiedJson, angles[i]);

            // use generator to save new json
            Identifier modifiedJsonId = Identifier.of(RoadsideAddons.MOD_ID, "block/" + blockId + "/"+ blockId + "_" + fileEndings[i]);
            generator.modelCollector.accept(modifiedJsonId, () -> modifiedJson);
        }

        // register item model via parent (refers to template file, which shall be rendered in inv)
        generator.registerParentedItemModel(modblock, Identifier.of(RoadsideAddons.MOD_ID + ":" + "block/" + blockId));
    }

    private static void generateSignBlockModels(Block modblock, FabricDataOutput output, BlockStateModelGenerator generator) {
        if (!(modblock instanceof AbstractSign signBlock)) return;

        // get the property
        EnumProperty<? extends SignVariant> property = signBlock.getVariantProperty();

        // check if property is actually implemented
        if(property == null) {
            // fall back to normal rotatable block
            generateRotatedBlockModelsFromTemplate(modblock, output, generator);
            return; // and exit!!
        }

        // get filesystem path
        Path templateModelDir = output.getPath().getParent()
                .resolve("resources/assets")
                .resolve(RoadsideAddons.MOD_ID)
                .resolve("models/block");

        //RoadsideAddons.LOGGER.info("template models directory located: " + templateModelDir.toString());

        // get identifier of handled block
        String blockId = Registries.BLOCK.getId(modblock).getPath();
        RoadsideAddons.LOGGER.info("generating sign block models for: " + blockId);
        Path templatePath = templateModelDir.resolve(blockId + ".json");

        // read in template JSON
        String jsonContent = null;
        try {
            jsonContent = Files.readString(templatePath);
        } catch (IOException e) {
            RoadsideAddons.LOGGER.error("could not resolve templatePath: " + templatePath.toString());
            return;
        }

        JsonObject templateJson = JsonParser.parseString(jsonContent).getAsJsonObject();
        //RoadsideAddons.LOGGER.info("template json object" + templateJson.toString());


        for(SignVariant variant : property.getValues()) {
            RoadsideAddons.LOGGER.info("calculating variant " + variant.toString() + " for " + blockId);

            // create all 4 different rotated models

            for (int i = 0; i < angles.length; ++i) {
                // create copy!!
                JsonObject modifiedJson = templateJson.deepCopy();

                // iterate trough json object and apply rotation
                applyRotation(modifiedJson, angles[i]);

                // Apply Texture Surgery (Swap the 'overlay' key)
                JsonObject textures = modifiedJson.getAsJsonObject("textures");
                textures.addProperty("overlay", RoadsideAddons.MOD_ID + ":block/" + blockId + "/" + variant.asString());

                // use generator to save new json
                Identifier modifiedJsonId = Identifier.of(RoadsideAddons.MOD_ID, "block/" + blockId + "/" + blockId + "_" + fileEndings[i] + "_" + variant.asString());
                generator.modelCollector.accept(modifiedJsonId, () -> modifiedJson);
            }
        }
        // register item model via parent (refers to template file, which shall be rendered in inv)
        generator.registerParentedItemModel(modblock, Identifier.of(RoadsideAddons.MOD_ID + ":" + "block/" + blockId));
    }


    private static void applyRotation(JsonObject modelJson, double newAngle) {
        // 1. Get the 'elements' array from your template
        if (modelJson.has("elements") && modelJson.get("elements").isJsonArray()) {
            JsonArray elements = modelJson.getAsJsonArray("elements");

            // 2. Loop through every element (the cubes in Blockbench)
            for (JsonElement element : elements) {
                if (element.isJsonObject()) {
                    JsonObject elementObj = element.getAsJsonObject();

                    // 3. Verify the element has a "name" property, and it equals "rotatable"
                    if (elementObj.has("name") && "rotatable".equals(elementObj.get("name").getAsString())) {

                        // 4. Check if this targeted element has a 'rotation' object
                        if (elementObj.has("rotation") && elementObj.get("rotation").isJsonObject()) {
                            JsonObject rotationObj = elementObj.getAsJsonObject("rotation");

                            // 5. Overwrite the 'angle' property
                            // .addProperty automatically replaces the old value if the key exists
                            if (newAngle == Math.floor(newAngle)) {
                                // Pass it as an integer to force Gson to omit the ".0"
                                rotationObj.addProperty("angle", (int) newAngle);
                            } else {
                                // Keep it as a double for values like 22.5
                                rotationObj.addProperty("angle", newAngle);
                            }
                        }
                    }
                }
            }
        }
    }






    // ---------------------------- <blockstates generator> ----------------------------


    private static void generateNormalBlockstates(Block modblock, BlockStateModelGenerator generator) {

        // get path+name (e.g. "block/sign_post/sign_post")
        String blockName = "block/" + Registries.BLOCK.getId(modblock).getPath() + "/" + Registries.BLOCK.getId(modblock).getPath();

        // get identifiers based on naming convention
        List<Identifier> modelIds = new ArrayList<>();

        for (String fileEnding : fileEndings) {
            Identifier id = Identifier.of(RoadsideAddons.MOD_ID, blockName + "_" + fileEnding);
            modelIds.add(id);
        }

        generator.blockStateCollector.accept(VariantsBlockStateSupplier.create(modblock)
                .coordinate(BlockStateVariantMap.create(RotatableBlock.ROTATION)
                        .register(rotation -> {

                            Identifier modelId = modelIds.get(rotation % 4);

                            // Calculates 0, 90, 180, 270 based on the pair
                            int yStep = ((rotation + 1) / 4) * 90;
                            VariantSettings.Rotation yRot = getRotationFromDegrees(yStep);

                            return BlockStateVariant.create()
                                    .put(VariantSettings.MODEL, modelId)
                                    .put(VariantSettings.Y, yRot);
                        })
                )
        );
    }

    private static void generateSignBlockstates(Block modblock, BlockStateModelGenerator generator) {
        // 1. Cast to access our variant property contract
        if (!(modblock instanceof AbstractSign signBlock)) return;

        // 2. Get the property and the block's base path
        EnumProperty<? extends SignVariant> variantProperty = signBlock.getVariantProperty();

        // check if property is actually implemented
        if(variantProperty == null) {
            // fall back to normal rotatable block
            generateNormalBlockstates(modblock, generator);
            return; //and exit!
        }

        // and the block path (e.g. sign_danger)
        String blockPath = Registries.BLOCK.getId(modblock).getPath();

        // 3. Create the Cartesian Product (Variant x Rotation)
        generator.blockStateCollector.accept(VariantsBlockStateSupplier.create(modblock)
                .coordinate(BlockStateVariantMap.create(variantProperty, RotatableBlock.ROTATION)
                        .register((variant, rotation) -> {

                            // Get the variant name (e.g., "deer", "crossing", "default")
                            String variantName = variant.asString();

                            // Match your naming convention: "block/sign_danger/deer_22"
                            // rotation % 4 picks "0", "n22", "n45", or "22" from fileEndings array
                            String modelPath = "block/" + blockPath + "/" + blockPath + "_" + fileEndings[rotation % 4] + "_" + variantName;
                            Identifier modelId = Identifier.of(RoadsideAddons.MOD_ID, modelPath);

                            // Use your Quadrant Math to calculate the engine Y rotation
                            int yStep = ((rotation + 1) / 4) * 90;
                            VariantSettings.Rotation yRot = getRotationFromDegrees(yStep);

                            return BlockStateVariant.create()
                                    .put(VariantSettings.MODEL, modelId)
                                    .put(VariantSettings.Y, yRot);
                        })
                )
        );

    }



    // Helper to map the integer degrees to the Enum required by the API
    private static VariantSettings.Rotation getRotationFromDegrees(int degrees) {
        return switch (degrees % 360) {
            case 90 -> VariantSettings.Rotation.R90;
            case 180 -> VariantSettings.Rotation.R180;
            case 270 -> VariantSettings.Rotation.R270;
            default -> VariantSettings.Rotation.R0;
        };
    }




}
