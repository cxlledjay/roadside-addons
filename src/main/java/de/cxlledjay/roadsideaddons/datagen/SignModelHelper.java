package de.cxlledjay.roadsideaddons.datagen;

import com.google.gson.*;
import de.cxlledjay.roadsideaddons.RoadsideAddons;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.block.Block;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class SignModelHelper {

    // Define a Pretty Printing GSON instance at the top of your class
    private static final Gson GSON = new GsonBuilder().create();

    public static void generateRotatedBlockModelsFromTemplate(Block modblock, FabricDataOutput output, BlockStateModelGenerator generator) {

        // get filesystem paths
        Path gendataModelDir = output.getPath()
                .resolve("assets/")
                .resolve(RoadsideAddons.MOD_ID)
                .resolve("models/blocks");

        Path templateModelDir = output.getPath().getParent()
                .resolve("resources/assets")
                .resolve(RoadsideAddons.MOD_ID)
                .resolve("models/block");

        //RoadsideAddons.LOGGER.info("gendata directory located: " + gendataModelDir.toString());
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
        RoadsideAddons.LOGGER.info("template json object" + templateJson.toString());



        // create 4 different rotated models
        double[] angles = {0, 22.5, 45, -22.5}; //< all four rotation angles (DO NOT EDIT!!)
        String[] fileEndings = {"0", "22", "45", "n22"}; //< matching names for rotation angles (DO NOT EDIT!!)

        for(int i = 0; i < angles.length; ++i) {


            // create copy!!
            JsonObject modifiedJson = templateJson.deepCopy();

            // iterate trough json object and apply rotation
            applyRotation(modifiedJson, angles[i]);

            // use generator to save new json
            Identifier modifiedJsonId = Identifier.of(RoadsideAddons.MOD_ID, "block/" + blockId + "_" + fileEndings[i]);
            generator.modelCollector.accept(modifiedJsonId, () -> modifiedJson);
        }
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

                    // 3. Check if this element has a 'rotation' object
                    if (elementObj.has("rotation") && elementObj.get("rotation").isJsonObject()) {
                        JsonObject rotationObj = elementObj.getAsJsonObject("rotation");

                        // 4. Overwrite the 'angle' property
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
