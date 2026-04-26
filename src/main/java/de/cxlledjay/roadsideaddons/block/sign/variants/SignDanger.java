package de.cxlledjay.roadsideaddons.block.sign.variants;


import com.mojang.serialization.MapCodec;
import de.cxlledjay.roadsideaddons.block.sign.generic.AbstractSign;
import de.cxlledjay.roadsideaddons.block.sign.generic.SignVariant;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;


public class SignDanger extends AbstractSign {

    // ---------------------------- <boilerplate> ----------------------------
    public SignDanger(Settings settings) {
        super(settings);
        // Set default rotation to 0 (South)
        this.setDefaultState(this.stateManager.getDefaultState().with(VARIANT, DangerType.DEFAULT));
    }

    // ---------------------------- <CODEC> ----------------------------
    private static final MapCodec<SignDanger> CODEC = createCodec(SignDanger::new);
    @Override
    protected MapCodec<SignDanger> getCodec() {
        return CODEC;
    }

    // ---------------------------- <variants> ----------------------------

    // Define the specific Enum for this block
    public enum DangerType implements SignVariant {
        DEFAULT("default", "general"),
        CATTLE("cattle", "general"),
        CONSTRUCTION("construction", "general"),
        CROSSING("crossing", "general"),
        CURVE_LEFT("curve_left", "general"),
        CURVE_RIGHT("curve_right", "general"),
        DANGERZONE("dangerzone", "general"),
        DOUBLECURVE_LEFT("doublecurve_left", "general"),
        DOUBLECURVE_RIGHT("doublecurve_right", "general"),
        DRIFT("drift", "general"),
        FROG("frog", "general"),
        GRADIENT("gradient", "general"),
        HORSE("horse", "general"),
        ICE("ice", "general"),
        NARROWED("narrowed", "general"),
        NARROWED_LEFT("narrowed_left", "general"),
        NARROWED_RIGHT("narrowed_right", "general"),
        ONCOMING("oncoming", "general"),
        PEDESTRIAN("pedestrian", "general"),
        PEDESTRIAN_CROSSING("pedestrian_crossing", "general"),
        PLANE("plane", "general"),
        RIGHT_OF_WAY("right_of_way", "general"),
        ROCKFALL("rockfall", "general"),
        SHORE("shore", "general"),
        SLOPE("slope", "general"),
        TRAFFIC_LIGHT("traffic_light", "general"),
        TRAIN("train", "general"),
        UNEVEN("uneven", "general"),
        TREE("tree", "general"),
        WILDLIFE("wildlife", "general");

        private final String name;
        private final String category;

        DangerType(String name, String category) {
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
