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
        return this.width - 40; // Leave room for the scrollbar
    }


    // ---------------------------- <rows aka. "entries"> ----------------------------

    /// abstract Entry class for category information headers or the actual texturized buttons
    public static abstract class Entry extends ElementListWidget.Entry<Entry> {

    }

    // --- THE HEADER ENTRY ---
    public static class HeaderEntry extends VariantListWidget.Entry {
        private final Text title;

        public HeaderEntry(String key) {
            this.title = Text.translatable(key).formatted(Formatting.YELLOW, Formatting.BOLD);
        }

        @Override
        public void render(DrawContext context, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
            // Draw the text perfectly centered in the row
            int textWidth = MinecraftClient.getInstance().textRenderer.getWidth(this.title);
            int textY = y + entryHeight - 10;
            context.drawTextWithShadow(MinecraftClient.getInstance().textRenderer, this.title, x + (entryWidth / 2) - (textWidth / 2), textY, 0xFFFFFF);
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
