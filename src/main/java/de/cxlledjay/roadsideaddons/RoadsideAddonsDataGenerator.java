package de.cxlledjay.roadsideaddons;

import de.cxlledjay.roadsideaddons.datagen.ModBlockTagProvider;
import de.cxlledjay.roadsideaddons.datagen.ModLootTableProvider;
import de.cxlledjay.roadsideaddons.datagen.ModModelProvider;
import de.cxlledjay.roadsideaddons.datagen.ModRecipeProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class RoadsideAddonsDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

		pack.addProvider(ModBlockTagProvider::new);
		pack.addProvider(ModLootTableProvider::new);
		pack.addProvider(ModModelProvider::new);
		pack.addProvider(ModRecipeProvider::new);
	}
}
