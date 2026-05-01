package de.cxlledjay.roadsideaddons.block.sign.variants;

import com.mojang.serialization.MapCodec;
import de.cxlledjay.roadsideaddons.block.sign.generic.AbstractSign;
import de.cxlledjay.roadsideaddons.block.sign.generic.SignVariant;
import de.cxlledjay.roadsideaddons.gui.SignShape;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;


public class SignYield extends AbstractSign {

    // boilerplate constructor
    public SignYield(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(VARIANT, YieldType.BLANK));
    }


    // CODEC overwrite
    private static final MapCodec<SignYield> CODEC = createCodec(SignYield::new);
    @Override
    protected MapCodec<SignYield> getCodec() {
        return CODEC;
    }

    // ---------------------------- <variants> ----------------------------

    // Define the specific Enum for this block
    public enum YieldType implements SignVariant {

        // --- priority ---
        DEFAULT("default", "priority"),
        // --- blank ---
        BLANK("blank", "blank");

        private final String name;
        private final String category;

        YieldType(String name, String category) {
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

    public static final EnumProperty<YieldType> VARIANT = EnumProperty.of("variant", YieldType.class);

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
