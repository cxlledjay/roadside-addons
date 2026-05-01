package de.cxlledjay.roadsideaddons.block.sign.variants;


import com.mojang.serialization.MapCodec;
import de.cxlledjay.roadsideaddons.block.sign.generic.AbstractSign;
import de.cxlledjay.roadsideaddons.block.sign.generic.SignVariant;
import de.cxlledjay.roadsideaddons.gui.SignShape;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;


public class SignPriorityRoad extends AbstractSign {

    // ---------------------------- <boilerplate> ----------------------------
    public SignPriorityRoad(Settings settings) {
        super(settings);
        // Set default rotation to 0 (South)
        this.setDefaultState(this.stateManager.getDefaultState().with(VARIANT, PriorityType.BLANK));
    }

    // ---------------------------- <CODEC> ----------------------------
    private static final MapCodec<SignPriorityRoad> CODEC = createCodec(SignPriorityRoad::new);
    @Override
    protected MapCodec<SignPriorityRoad> getCodec() {
        return CODEC;
    }

    // ---------------------------- <variants> ----------------------------

    // Define the specific Enum for this block
    public enum PriorityType implements SignVariant {
        PRIORITY_ROAD("priority_road", "general"),
        PRIORITY_ROAD_END("priority_road_end", "general"),

        // --- blank ---
        BLANK("blank", "blank");

        private final String name;
        private final String category;

        PriorityType(String name, String category) {
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

    public static final EnumProperty<PriorityType> VARIANT = EnumProperty.of("variant", PriorityType.class);

    @Override
    public EnumProperty<? extends SignVariant> getVariantProperty() {
        return VARIANT;
    }

    @Override
    public SignShape getSignShape() {
        return SignShape.NORMAL;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        // You MUST add both the new variant and the inherited rotation
        builder.add(VARIANT, ROTATION);
    }

}
