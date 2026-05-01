package de.cxlledjay.roadsideaddons.gui;

public enum SignShape {

    NORMAL(64, 64, 128, 56, 56),
    SUPPLEMENTARY_WIDE(96, 48, 128, 56, 28),
    SUPPLEMENTARY_SQUARE(80, 80, 256, 56, 56),
    ONE_WAY(88, 32, 128, 55, 20);

    // --- Texture File Coordinates (The "Cropping" Box) ---
    public final int regionWidth;    // How many pixels wide to read from the PNG
    public final int regionHeight;   // How many pixels tall to read from the PNG
    public final int textureSize;    // The total size of the PNG (128 or 256)

    // --- GUI Display Size (How big it renders on screen) ---
    public final int renderWidth;
    public final int renderHeight;

    SignShape(int regionWidth, int regionHeight, int textureSize, int renderWidth, int renderHeight) {
        this.regionWidth = regionWidth;
        this.regionHeight = regionHeight;
        this.textureSize = textureSize;
        this.renderWidth = renderWidth;
        this.renderHeight = renderHeight;
    }
}