package de.cxlledjay.roadsideaddons.block.sign.variants;


import com.mojang.serialization.MapCodec;
import de.cxlledjay.roadsideaddons.block.sign.generic.AbstractSign;
import de.cxlledjay.roadsideaddons.block.sign.generic.SignVariant;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;


public class SignRegulatory extends AbstractSign {

    // ---------------------------- <boilerplate> ----------------------------
    public SignRegulatory(Settings settings) {
        super(settings);
        // Set default rotation to 0 (South)
        this.setDefaultState(this.stateManager.getDefaultState().with(VARIANT, DangerType.DEFAULT));
    }

    // ---------------------------- <CODEC> ----------------------------
    private static final MapCodec<SignRegulatory> CODEC = createCodec(SignRegulatory::new);
    @Override
    protected MapCodec<SignRegulatory> getCodec() {
        return CODEC;
    }

    // ---------------------------- <variants> ----------------------------

    // Define the specific Enum for this block
    public enum DangerType implements SignVariant {
        DEFAULT("default"),
        SPEED_LIMIT_30("speed_limit_30");

        private final String name;

        DangerType(String name) {
            this.name = name;
        }

        @Override
        public String asString() {
            return this.name;
        }
    }

    public static final EnumProperty<DangerType> VARIANT = EnumProperty.of("variant", DangerType.class);

    @Override
    public EnumProperty<? extends SignVariant> getVariantProperty() {
        return VARIANT;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        // You MUST add both the new variant and the inherited rotation
        builder.add(VARIANT, ROTATION);
    }

}
