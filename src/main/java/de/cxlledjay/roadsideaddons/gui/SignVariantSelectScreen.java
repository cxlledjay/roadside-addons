package de.cxlledjay.roadsideaddons.gui;

import de.cxlledjay.roadsideaddons.RoadsideAddons;
import de.cxlledjay.roadsideaddons.block.sign.generic.SignVariant;
import net.minecraft.block.Block;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.DirectionalLayoutWidget;
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


    // ---------------------------- <interfaces> ----------------------------
    public SignVariantSelectScreen(BlockPos blockPos, Block clickedBlock, Property<?> variantProperty) {
        super(Text.literal("Sign Variant Selection"));
        this.blockPos = blockPos;
        this.variantProperty = variantProperty;
        this.clickedBlock = clickedBlock;
    }

    @Override
    protected void init() {
        initGui();
    }

    @Override
    public void renderBackground(DrawContext context, int mouseX, int mouseY, float delta) {

        super.renderBackground(context, mouseX, mouseY, delta);

        // 1. Define the boundaries based on your list dimensions
        int listTopY = 10;
        int listBottomY = this.height - 40; // 30 (top) + (this.height - 60) (height)

        // We use 0xCC000000 (80% Black) for the dark bars so it perfectly
        // matches the starting color of your fillGradient inside the list!
        int darkTint = 0xD4000000;
        int lightTint = 0x55000000; // 20% Black for the middle so the screen isn't too dark

        // 2. Draw Top Header Bar (Dark)
        // From Y=0 down to the top of the list
        context.fill(0, 0, this.width, listTopY, darkTint);

        // 3. Draw Middle Content Area (Light)
        // Sits exactly behind the scrolling list
        context.fill(0, listTopY, this.width, listBottomY, lightTint);

        // 4. Draw Bottom Footer Bar (Dark)
        // From the bottom of the list down to the bottom of the screen
        context.fill(0, listBottomY, this.width, this.height, darkTint);
    }

    // ---------------------------- <gui> ----------------------------

    private static final int buttonSpacing = VariantButtonWidget.BUTTON_SIZE + 4;


    private void initGui() {

        // 1. Create the scrollable list (leaves 30px at top/bottom for title/close button)
        VariantListWidget listWidget = new VariantListWidget(this.client, this.width, this.height - 50, 10);

        // Calculate how many buttons can fit in one row
        int maxButtonsPerRow = (listWidget.getRowWidth() - 120) / buttonSpacing;

        // 2. Get your grouped variants (From our earlier Map code!)
        Map<String, List<SignVariant>> groupedVariants = getGroupedVariants();

        // 3. Build the List
        for (Map.Entry<String, List<SignVariant>> entry : groupedVariants.entrySet()) {

            // Add the Markdown-style Header!
            listWidget.addCategoryEntry(new VariantListWidget.HeaderEntry(entry.getKey()));

            List<SignVariant> categoryVariants = entry.getValue();

            // --- THE "FLEX-WRAP" CHUNKING LOGIC ---
            for (int i = 0; i < categoryVariants.size(); i += maxButtonsPerRow) {

                // Get a sub-list (chunk) of variants for this specific row
                int end = Math.min(i + maxButtonsPerRow, categoryVariants.size());
                List<SignVariant> rowVariants = categoryVariants.subList(i, end);

                List<VariantButtonWidget> rowButtons = new ArrayList<>();

                // Create the buttons for this row
                int currentX = (this.width / 2) - ((rowVariants.size() * buttonSpacing) / 2); // Centers the row

                for (SignVariant variant : rowVariants) {
                    Identifier tex = Identifier.of(RoadsideAddons.MOD_ID, "textures/block/" + Registries.BLOCK.getId(clickedBlock).getPath() + "/" + variant.asString() + ".png");

                    RoadsideAddons.LOGGER.info(tex.toString());

                    rowButtons.add(new VariantButtonWidget(currentX, 0, tex, btn -> {
                        // Send your packet here!
                        this.close();
                    }));

                    currentX += buttonSpacing;
                }

                // Add the row to the list!
                listWidget.addCategoryEntry(new VariantListWidget.ButtonRowEntry(rowButtons));
            }
        }

        // 4. Finally, add the entire scrollable list to the screen
        this.addDrawableChild(listWidget);
    }




    private Map<String, List<SignVariant>> getGroupedVariants() {
        // We use a LinkedHashMap because it preserves the order your
        // categories were defined in the Enum!
        Map<String, List<SignVariant>> sortedMap = new LinkedHashMap<>();

        // Loop through all the values in this specific block's property
        for (Object obj : this.variantProperty.getValues()) {

            // We know it implements our interface, so we safely cast it
            SignVariant variant = (SignVariant) obj;

            // Grab the category (e.g., "Speed Limits", "Traffic Bans")
            String category = variant.getCategory();

            // This is a fancy Java 8 one-liner:
            // "If the category folder doesn't exist in the map yet, create it.
            // Then, put this variant into that folder."
            sortedMap.computeIfAbsent(category, k -> new ArrayList<>()).add(variant);
        }
        return sortedMap;
    }





    private void createVariantSelectScreen() {

        // extract information




        // Let's make square buttons for the grid (e.g., 24x24)
        int buttonSize = 256;
        int x = (this.width / 2) - (buttonSize / 2);
        int y = (this.height / 2) - (buttonSize / 2);

        int buttonSize2 = 128;
        int x2 = (this.width / 2) - (buttonSize / 2);
        int y2 = (this.height / 2) - (buttonSize / 2);

        // Let's load the speed limit 50 sign just for this test!
        // Path looks for: assets/roadsideaddons/textures/block/signs/speed_limit_50.png
        Identifier testTexture = Identifier.of(RoadsideAddons.MOD_ID, "textures/block/sign_regulatory/speed_limit_50.png");
        Identifier testTexture2 = Identifier.of(RoadsideAddons.MOD_ID, "textures/block/sign_danger/default.png");

        List<VariantButtonWidget> buttons = new ArrayList<>();

        for(int i = 0; i < 10; ++i){
            VariantButtonWidget testButton = new VariantButtonWidget(x + ((VariantButtonWidget.BUTTON_SIZE + 4)*i), y, testTexture, button -> {
                RoadsideAddons.LOGGER.info("sign_regulatory/speed_limit_50 selected!");
                this.close();
            });
            buttons.add(testButton);
        }

        VariantButtonWidget testButton = new VariantButtonWidget(x, y, testTexture, button -> {
            RoadsideAddons.LOGGER.info("sign_regulatory/speed_limit_50 selected!");
            this.close();
        });


        VariantButtonWidget testButton2 = new VariantButtonWidget(x + VariantButtonWidget.BUTTON_SIZE + 4, y2, testTexture2, button -> {
            RoadsideAddons.LOGGER.info("sign_danger/default selected!");
            this.close();
        });


        DirectionalLayoutWidget testFlexBox = DirectionalLayoutWidget.horizontal().spacing(4);
        testFlexBox.getMainPositioner().alignVerticalCenter();
        for(VariantButtonWidget btn : buttons) {
            testFlexBox.add(btn);
        }

        testFlexBox.forEachChild(this::addDrawableChild);

        //this.addDrawableChild(testButton);
        //this.addDrawableChild(testButton2);
    }
}
