package de.cxlledjay.roadsideaddons.block.sign.variants;

import com.mojang.serialization.MapCodec;
import de.cxlledjay.roadsideaddons.block.sign.generic.AbstractSign;
import de.cxlledjay.roadsideaddons.block.sign.generic.SignVariant;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;


public class SignStop extends AbstractSign {

    // boilerplate constructor
    public SignStop(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(VARIANT, StopType.BLANK));
    }

    // CODEC overwrite
    private static final MapCodec<SignStop> CODEC = createCodec(SignStop::new);
    @Override
    protected MapCodec<SignStop> getCodec() {
        return CODEC;
    }

    // ---------------------------- <variants> ----------------------------

    // Define the specific Enum for this block
    public enum StopType implements SignVariant {

        // --- priority ---
        DEFAULT("default", "priority"),
        // --- legacy ---
        LEGACY("legacy", "legacy"),
        // --- blank ---
        BLANK("blank", "blank");

        private final String name;
        private final String category;

        StopType(String name, String category) {
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

    public static final EnumProperty<StopType> VARIANT = EnumProperty.of("variant", StopType.class);

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
