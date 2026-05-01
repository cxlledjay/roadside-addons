package de.cxlledjay.roadsideaddons.block.sign.variants;


import com.mojang.serialization.MapCodec;
import de.cxlledjay.roadsideaddons.block.sign.generic.AbstractSign;
import de.cxlledjay.roadsideaddons.block.sign.generic.SignVariant;
import de.cxlledjay.roadsideaddons.gui.SignShape;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;


public class SignSupplementarySquare extends AbstractSign {

    // ---------------------------- <boilerplate> ----------------------------
    public SignSupplementarySquare(Settings settings) {
        super(settings);
        // Set default rotation to 0 (South)
        this.setDefaultState(this.stateManager.getDefaultState().with(VARIANT, SupplementarySquareType.BLANK));
    }

    // ---------------------------- <CODEC> ----------------------------
    private static final MapCodec<SignSupplementarySquare> CODEC = createCodec(SignSupplementarySquare::new);
    @Override
    protected MapCodec<SignSupplementarySquare> getCodec() {
        return CODEC;
    }

    // ---------------------------- <variants> ----------------------------

    // Define the specific Enum for this block
    public enum SupplementarySquareType implements SignVariant {

        // --- Priority Routing ---
        PRIORITY_BOTTOM_LEFT_2("priority_bottom_left_2", "priority"),
        PRIORITY_TOP_LEFT_2("priority_top_left_2", "priority"),
        PRIORITY_BOTTOM_RIGHT_2("priority_bottom_right_2", "priority"),
        PRIORITY_TOP_RIGHT_2("priority_top_right_2", "priority"),
        PRIORITY_BOTTOM_LEFT_1("priority_bottom_left_1", "priority"),
        PRIORITY_BOTTOM_LEFT_3("priority_bottom_left_3", "priority"),
        PRIORITY_TOP_LEFT_1("priority_top_left_1", "priority"),
        PRIORITY_BOTTOM_RIGHT_1("priority_bottom_right_1", "priority"),
        PRIORITY_BOTTOM_RIGHT_3("priority_bottom_right_3", "priority"),
        PRIORITY_TOP_RIGHT_1("priority_top_right_1", "priority"),

        // --- General ---
        BIKE_VEHICLE("bike_vehicle", "general"),
        BUS_TRAILER_TOO("bus_trailer_too", "general"),
        CYCLE_MOPED_FREE("cycle_moped_free", "general"),
        ROUNDABOUT_LEFT("roundabout_left", "general"),
        ROUNDABOUT_STRAIGHT("roundabout_straight", "general"),
        TRUCK_BUS_TRAILER("truck_bus_trailer", "general"),
        WET("wet", "general"),

        // --- traffic_light ---
        GREEN_ARROW("green_arrow", "traffic_light"),

        // --- blank ---
        BLANK("blank", "blank");

        private final String name;
        private final String category;

        SupplementarySquareType(String name, String category) {
            this.name = name;
            this.category = category;
        }

        @Override
        public String asString() {
            return this.name;
        }

        @Override
        public String getCategory() { return this.category; }
    }

    public static final EnumProperty<SupplementarySquareType> VARIANT = EnumProperty.of("variant", SupplementarySquareType.class);

    @Override
    public EnumProperty<? extends SignVariant> getVariantProperty() {
        return VARIANT;
    }

    @Override
    public SignShape getSignShape() {
        return SignShape.SUPPLEMENTARY_SQUARE;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        // You MUST add both the new variant and the inherited rotation
        builder.add(VARIANT, ROTATION);
    }

}
