package de.cxlledjay.roadsideaddons.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class VariantButtonWidget extends ButtonWidget {

    // ---------------------------- <attributes> ----------------------------
    // size settings
    public static final int BUTTON_SIZE = 64; // size of button
    private static final int ICON_SIZE = 56;  // size of texture rendering
    private static final int HIGHLIGHTED_SIZE = BUTTON_SIZE;  // how big the texture grows when hovering over it
    private static final int HIGHLIGHTED_DIFF = (HIGHLIGHTED_SIZE-ICON_SIZE) / 2;

    // saved texture
    private final Identifier texture; // path to texture file

    // ---------------------------- <create variant button> ----------------------------
    /// x,y: position; texture: path to texture file
    public VariantButtonWidget(int x, int y, Identifier texture, PressAction onPress) {
        super(x, y, BUTTON_SIZE, BUTTON_SIZE, Text.empty(), onPress, ButtonWidget.DEFAULT_NARRATION_SUPPLIER);
        this.texture = texture;
    }

    // ---------------------------- <custom button rendering> ----------------------------
    @Override
    protected void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {

        // position calculation
        int texX = this.getX() + ((BUTTON_SIZE - ICON_SIZE) / 2);
        int texY = this.getY() + ((BUTTON_SIZE - ICON_SIZE) / 2);

        // hover effect
        if (this.isHovered() || this.isSelected()) {

            // tint next rendering object black --> for shadow effect
            RenderSystem.setShaderColor(0.0F, 0.0F, 0.0F, 0.25f);
            RenderSystem.enableBlend(); // Required for transparency to work

            // draw shadow
            context.drawTexture(this.texture, texX - HIGHLIGHTED_DIFF + 2, texY - HIGHLIGHTED_DIFF + 2, 0, 0, HIGHLIGHTED_SIZE, HIGHLIGHTED_SIZE, HIGHLIGHTED_SIZE * 2, HIGHLIGHTED_SIZE * 2);

            // reset tinting!!
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

            // draw grown texture
            context.drawTexture(this.texture, texX - HIGHLIGHTED_DIFF, texY - HIGHLIGHTED_DIFF, 0, 0, HIGHLIGHTED_SIZE, HIGHLIGHTED_SIZE, HIGHLIGHTED_SIZE * 2, HIGHLIGHTED_SIZE * 2);

        } else {
            // draw normal texture
            context.drawTexture(this.texture, texX, texY, 0, 0, ICON_SIZE, ICON_SIZE, ICON_SIZE * 2, ICON_SIZE * 2);
        }
    }
}