package de.cxlledjay.roadsideaddons.gui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.widget.ElementListWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.List;

/// Scrollable List Widget containing Header or VariantButtonWidget Entries
public class VariantListWidget extends ElementListWidget<VariantListWidget.Entry> {

    public VariantListWidget(MinecraftClient client, int width, int height, int y) {
        // args: client, width, height, y (start), itemHeight (how tall each row is)
        super(client, width, height, y, VariantButtonWidget.BUTTON_SIZE + 4);
    }

    // wrapper to expose addEntry
    public void addCategoryEntry(Entry entry) {
        this.addEntry(entry);
    }

    @Override
    public int getRowWidth() {
        return this.width - 60; // leave room
    }

    @Override
    public void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
        // 1. SCISSOR TEST
        // This tells the rendering engine: "Do not draw anything outside of this box!"
        // This prevents your scrolling icons from rendering over your title and buttons.
        context.enableScissor(this.getX(), this.getY(), this.getX() + this.width, this.getY() + this.height);

        // 2. RENDER ONLY THE ITEMS
        // By calling this directly, we completely skip the vanilla dirt background and shadows!
        this.renderList(context, mouseX, mouseY, delta);

        context.disableScissor();

        // --- NEW: THE FADE OUT SHADOWS ---
        // How tall you want the gradient fade to be (in pixels)
        int shadowHeight = 15;

        // Top Fade: Starts at 80% Black (0xCC000000) and fades down to 0% Black (0x00000000)
        context.fillGradient(
                this.getX(), this.getY(),
                this.getX() + this.width, this.getY() + shadowHeight,
                0xCC000000, 0x00000000
        );

        // Bottom Fade: Starts at 0% Black (0x00000000) and fades down to 80% Black (0xCC000000)
        context.fillGradient(
                this.getX(), this.getY() + this.height - shadowHeight,
                this.getX() + this.width, this.getY() + this.height,
                0x00000000, 0xCC000000
        );

        // 3. DRAW CUSTOM "DISCORD STYLE" SCROLLBAR
        int maxScroll = this.getMaxScroll();
        if (maxScroll > 0) { // Only draw the scrollbar if there are enough items to scroll

            int scrollbarX = this.getScrollbarX();
            int trackHeight = this.height;

            // Calculate the height of the scrollbar "thumb" (the part you drag)
            int totalContentHeight = this.getMaxPosition();
            int thumbHeight = Math.max(20, (int) ((float) (trackHeight * trackHeight) / totalContentHeight));

            // Calculate the Y position of the thumb based on current scroll amount
            int thumbY = this.getY() + (int) ((this.getScrollAmount() / (float) maxScroll) * (trackHeight - thumbHeight));

            // Draw the track background (Optional: a very faint, dark line)
            // context.fill(scrollbarX, this.getY(), scrollbarX + 4, this.getY() + this.height, 0x40000000);

            // Draw the sleek, thin scrollbar thumb (Discord uses a light grey color: #B9BBBE)
            // Note: 0xFF is the alpha (100% visible), followed by the hex color
            context.fill(scrollbarX, thumbY, scrollbarX + 4, thumbY + thumbHeight, 0xFFB9BBBE);
        }
    }

    // 4. FIX MOUSE DRAGGING
    // We must tell the vanilla logic where our new thin scrollbar is located
    // so the player can still click and drag it with their mouse!
    @Override
    public int getScrollbarX() {
        // Places the scrollbar hitbox 50 pixels inward from the right edge of the list
        return this.getX() + this.width - 50;
    }



    // ---------------------------- <rows aka. "entries"> ----------------------------

    /// abstract Entry class for category information headers or the actual texturized buttons
    public static abstract class Entry extends ElementListWidget.Entry<Entry> {

    }

    // --- THE HEADER ENTRY ---
    public static class HeaderEntry extends VariantListWidget.Entry {
        private final Text title;

        public HeaderEntry(String key) {
            this.title = Text.translatable(key).formatted(Formatting.WHITE, Formatting.BOLD);
        }

        @Override
        public void render(DrawContext context, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {

            // 1. Calculate our starting positions
            float scale = 1.75f; // 75% bigger
            int textWidth = MinecraftClient.getInstance().textRenderer.getWidth(this.title);

            // Calculate the centered X coordinate normally
            int centerX = x + (entryWidth / 2) - (int)((textWidth * scale) / 2);
            int textY = y + entryHeight - 35; // Push to bottom of the row

            // --- DRAWING BIG TEXT ---
            context.getMatrices().push(); // Grab the magnifying glass

            // Move the matrix origin to exactly where we want to draw
            context.getMatrices().translate(centerX, textY, 0);
            // Zoom in!
            context.getMatrices().scale(scale, scale, scale);

            // Draw the text at (0, 0) because we already moved the matrix to the right spot
            context.drawTextWithShadow(MinecraftClient.getInstance().textRenderer, this.title, 0, 0, 0xFFFFFF);

            context.getMatrices().pop(); // Put the magnifying glass away

            // --- DRAWING THE UNDERLINE ---
            // context.fill(startX, startY, endX, endY, ARGB_COLOR)
            // We draw a 1-pixel tall white line across the entire list width, slightly below the text
            int lineY = textY + 20;
            context.fill(x+85, lineY, x + entryWidth-90, lineY + 2, 0xFFFFFFFF);
        }

        // Headers don't have clickable elements
        @Override public List<? extends Element> children() { return List.of(); }
        @Override public List<? extends Selectable> selectableChildren() { return List.of(); }
    }

    // --- THE BUTTON ROW ENTRY ---
    public static class ButtonRowEntry extends VariantListWidget.Entry {
        private final List<VariantButtonWidget> buttons;

        public ButtonRowEntry(List<VariantButtonWidget> buttons) {
            this.buttons = buttons;
        }

        @Override
        public void render(DrawContext context, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
            // Tell each button in this row to draw itself, updating its Y coordinate so it scrolls!
            for (VariantButtonWidget button : buttons) {
                button.setY(y); // Keep the button attached to the scroll position
                button.render(context, mouseX, mouseY, tickDelta);
            }
        }

        // Return the buttons so the screen knows they can be clicked
        @Override public List<? extends Element> children() { return this.buttons; }
        @Override public List<? extends Selectable> selectableChildren() { return this.buttons; }
    }
}
