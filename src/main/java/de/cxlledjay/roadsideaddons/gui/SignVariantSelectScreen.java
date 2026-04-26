package de.cxlledjay.roadsideaddons.gui;

import de.cxlledjay.roadsideaddons.RoadsideAddons;
import net.minecraft.block.Block;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.state.property.Property;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

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
        createVariantSelectScreen();
    }


    // ---------------------------- <gui> ----------------------------
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

        VariantButtonWidget testButton = new VariantButtonWidget(x, y, testTexture, button -> {
            RoadsideAddons.LOGGER.info("sign_regulatory/speed_limit_50 selected!");
            this.close();
        });


        VariantButtonWidget testButton2 = new VariantButtonWidget(x + VariantButtonWidget.BUTTON_SIZE + 4, y2, testTexture2, button -> {
            RoadsideAddons.LOGGER.info("sign_danger/default selected!");
            this.close();
        });

        this.addDrawableChild(testButton);
        this.addDrawableChild(testButton2);
    }
}
