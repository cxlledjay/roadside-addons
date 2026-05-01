package de.cxlledjay.roadsideaddons.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class VariantButtonWidget extends ButtonWidget {

    // ---------------------------- <attributes> ----------------------------
    // size settings
    public static final int BUTTON_SIZE = 64;

    // saved texture
    private final Identifier texture; // path to texture file
    private final SignShape shapeInfo; // path to texture file

    // ---------------------------- <create variant button> ----------------------------
    /// x,y: position; texture: path to texture file
    public VariantButtonWidget(int x, int y, Identifier texture, SignShape shapeInfo, PressAction onPress) {
        super(x, y, BUTTON_SIZE, BUTTON_SIZE, Text.empty(), onPress, ButtonWidget.DEFAULT_NARRATION_SUPPLIER);
        this.texture = texture;
        this.shapeInfo = shapeInfo;
    }

    // ---------------------------- <custom button rendering> ----------------------------
    @Override
    protected void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
        // 1. GUI Display Sizes
        final int baseW = this.shapeInfo.renderWidth;
        final int baseH = this.shapeInfo.renderHeight;

        // Center it in the button box (e.g., 40x40)
        final int texX = this.getX() + ((this.width - baseW) / 2);
        final int texY = this.getY() + ((this.height - baseH) / 2);

        // 2. Texture Cropping Variables
        final int regionW = this.shapeInfo.regionWidth;
        final int regionH = this.shapeInfo.regionHeight;
        final int texSize = this.shapeInfo.textureSize;

        if (this.isHovered() || this.isSelected()) {
            final int grow = 4;
            final int hoverW = baseW + (grow * 2);
            final int hoverH = baseH + (grow * 2);

            final int hoverX = texX - grow;
            final int hoverY = texY - grow;

            RenderSystem.enableBlend();

            // --- DRAW SHADOW ---
            RenderSystem.setShaderColor(0.0F, 0.0F, 0.0F, 0.25f);
            context.drawTexture(this.texture,
                    hoverX + 2, hoverY + 2, // Screen X, Y
                    hoverW, hoverH,         // Screen Width, Height (Scaled up)
                    0, 0,                   // Crop Start X, Y
                    regionW, regionH,       // Crop Width, Height
                    texSize, texSize        // Total PNG Size
            );

            // --- DRAW HIGHLIGHTED TEXTURE ---
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            context.drawTexture(this.texture,
                    hoverX, hoverY,
                    hoverW, hoverH,
                    0, 0,
                    regionW, regionH,
                    texSize, texSize
            );

        } else {
            // --- DRAW NORMAL TEXTURE ---
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            context.drawTexture(this.texture,
                    texX, texY,             // Screen X, Y
                    baseW, baseH,           // Screen Width, Height
                    0, 0,                   // Crop Start X, Y
                    regionW, regionH,       // Crop Width, Height
                    texSize, texSize        // Total PNG Size
            );
        }
    }
}