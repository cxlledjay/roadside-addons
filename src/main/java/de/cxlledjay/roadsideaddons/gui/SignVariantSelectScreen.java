package de.cxlledjay.roadsideaddons.gui;

import de.cxlledjay.roadsideaddons.RoadsideAddons;
import de.cxlledjay.roadsideaddons.block.sign.generic.SignVariant;
import de.cxlledjay.roadsideaddons.networking.packets.ChangeSignVariantPayload;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.block.Block;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.registry.Registries;
import net.minecraft.state.property.Property;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

import java.util.*;

public class SignVariantSelectScreen extends Screen {

    // ---------------------------- <attributes> ----------------------------
    /// which block was clicked
    private final Block clickedBlock;
    private final BlockPos blockPos;
    /// derived from the clicked block: the variant Enum
    private final Property<?> variantProperty;
    private final SignShape shapeInfo;


    // ---------------------------- <interfaces> ----------------------------
    public SignVariantSelectScreen(BlockPos blockPos, Block clickedBlock, Property<?> variantProperty, SignShape shapeInfo) {
        super(Text.literal("Sign Variant Selection"));
        this.blockPos = blockPos;
        this.variantProperty = variantProperty;
        this.clickedBlock = clickedBlock;
        this.shapeInfo = shapeInfo;
    }

    @Override
    protected void init() {
        initGui();
    }


    @Override
    public void renderBackground(DrawContext context, int mouseX, int mouseY, float delta) {

        super.renderBackground(context, mouseX, mouseY, delta);

        // define the boundary
        int listBottomY = this.height - 40; // 30 (top) + (this.height - 60) (height)

        // color codes
        int darkTint = 0xD4000000;
        int lightTint = 0x55000000; // 20% Black for the middle so the screen isn't too dark

        // 1. Draw Top Header Bar (Dark)
        // From Y=0 down to the top of the list
        context.fill(0, 0, this.width, 10, darkTint);

        // 2. Draw Middle Content Area (Light)
        // Sits exactly behind the scrolling list
        context.fill(0, 10, this.width, listBottomY, lightTint);

        // 3. Draw Bottom Footer Bar (Dark)
        // From the bottom of the list down to the bottom of the screen
        context.fill(0, listBottomY, this.width, this.height, darkTint);
    }

    // ---------------------------- <gui> ----------------------------

    private static final int buttonSpacing = VariantButtonWidget.BUTTON_SIZE + 4;


    private void initGui() {

        // create custom list widget
        VariantListWidget listWidget = new VariantListWidget(this.client, this.width, this.height - 50, 10);

        // calculate how many buttons can fit in one row
        int maxButtonsPerRow = (listWidget.getRowWidth() - 120) / buttonSpacing;

        // get categorized variants from this.variantProperty
        Map<String, List<SignVariant>> groupedVariants = getGroupedVariants();

        // build the list widget
        // for each category, which is a pair of string (translate key of category) and list of concrete SignVariant Enum entries
        for (Map.Entry<String, List<SignVariant>> entry : groupedVariants.entrySet()) {

            // create title widget with translatable key
            String translatableCategory = "sign_category." + RoadsideAddons.MOD_ID + "." +entry.getKey();
            listWidget.addCategoryEntry(new VariantListWidget.HeaderEntry(translatableCategory));

            // get all enum entries for this category
            List<SignVariant> categoryVariants = entry.getValue();

            // as there might be too many variants to be rendered in one row:
            // create a "flex-wrap"
            for (int i = 0; i < categoryVariants.size(); i += maxButtonsPerRow) {

                // get a sub-list of variants for this specific row
                int end = Math.min(i + maxButtonsPerRow, categoryVariants.size());
                List<SignVariant> rowVariants = categoryVariants.subList(i, end);

                // create list of renderable buttons for sign variants
                List<VariantButtonWidget> rowButtons = new ArrayList<>();

                // center the row
                int currentX = (this.width / 2) - ((rowVariants.size() * buttonSpacing) / 2);

                // create buttons
                // for each variant in this row
                for (SignVariant variant : rowVariants) {

                    // get identifier of texture
                    Identifier tex = Identifier.of(
                            RoadsideAddons.MOD_ID,
                            "textures/block/" + Registries.BLOCK.getId(clickedBlock).getPath() + "/" + variant.asString() + ".png");

                    // add button to row
                    rowButtons.add(new VariantButtonWidget(currentX, 0, tex, shapeInfo,  btn -> {
                        // onClick handler (executed on client!)

                        // send variant change request packet to server
                        ChangeSignVariantPayload payload = new ChangeSignVariantPayload(this.blockPos, variant.asString());
                        ClientPlayNetworking.send(payload);

                        // and close the menu
                        this.close();
                    }));

                    // space buttons
                    currentX += buttonSpacing;
                }

                // add the row to the list
                listWidget.addCategoryEntry(new VariantListWidget.ButtonRowEntry(rowButtons));
            }
        }

        // finally, add the entire scrollable list to the screen
        this.addDrawableChild(listWidget);
    }



    /// helper method to generate sub lists of all categories within one variant type
    private Map<String, List<SignVariant>> getGroupedVariants() {
        // keep order as defined in the enum via LinkedHasMap
        Map<String, List<SignVariant>> sortedMap = new LinkedHashMap<>();

        // loop through all the values in this specific block's property
        for (Object obj : this.variantProperty.getValues()) {

            // We know it implements our interface, so we safely cast it
            SignVariant variant = (SignVariant) obj;

            // grab the category
            String category = variant.getCategory();

            // This is a fancy Java 8 one-liner:
            // "If the category folder doesn't exist in the map yet, create it.
            // Then, put this variant into that folder."
            sortedMap.computeIfAbsent(category, k -> new ArrayList<>()).add(variant);
        }
        return sortedMap;
    }
}
