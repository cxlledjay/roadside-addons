package de.cxlledjay.roadsideaddons.block.sign.variants;


import com.mojang.serialization.MapCodec;
import de.cxlledjay.roadsideaddons.block.sign.generic.AbstractSign;
import de.cxlledjay.roadsideaddons.block.sign.generic.SignVariant;
import de.cxlledjay.roadsideaddons.gui.SignShape;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;


public class SignDanger extends AbstractSign {

    // ---------------------------- <boilerplate> ----------------------------
    public SignDanger(Settings settings) {
        super(settings);
        // Set default rotation to 0 (South)
        this.setDefaultState(this.stateManager.getDefaultState().with(VARIANT, DangerType.BLANK));
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

        // --- general ---
        //DEFAULT("default", "general"),
        CATTLE("cattle", "general"),
        CONSTRUCTION("construction", "general"),
        DANGERZONE("dangerzone", "general"),
        DRIFT("drift", "general"),
        FROG("frog", "general"),
        GRADIENT("gradient", "general"),
        HORSE("horse", "general"),
        ICE("ice", "general"),
        ONCOMING("oncoming", "general"),
        PEDESTRIAN("pedestrian", "general"),
        PEDESTRIAN_CROSSING("pedestrian_crossing", "general"),
        PLANE("plane", "general"),
        ROCKFALL("rockfall", "general"),
        SHORE("shore", "general"),
        SLOPE("slope", "general"),
        TRAFFIC_LIGHT("traffic_light", "general"),
        UNEVEN("uneven", "general"),
        //TREE("tree", "general"),
        WILDLIFE("wildlife", "general"),
        TRAIN("train", "general"),

        // --- street_course ---
        CURVE_LEFT("curve_left", "street_course"),
        CURVE_RIGHT("curve_right", "street_course"),
        DOUBLECURVE_LEFT("doublecurve_left", "street_course"),
        DOUBLECURVE_RIGHT("doublecurve_right", "street_course"),
        NARROWED("narrowed", "street_course"),
        NARROWED_LEFT("narrowed_left", "street_course"),
        NARROWED_RIGHT("narrowed_right", "street_course"),

        // --- priority ---
        RIGHT_OF_WAY("right_of_way", "priority"),
        CROSSING("crossing", "priority"),


        // --- blank ---
        BLANK("blank", "blank");

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
    public SignShape getSignShape() {
        return SignShape.NORMAL;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        // You MUST add both the new variant and the inherited rotation
        builder.add(VARIANT, ROTATION);
    }

}
