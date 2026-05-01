package de.cxlledjay.roadsideaddons.block.sign.variants;


import com.mojang.serialization.MapCodec;
import de.cxlledjay.roadsideaddons.block.sign.generic.AbstractSign;
import de.cxlledjay.roadsideaddons.block.sign.generic.SignVariant;
import de.cxlledjay.roadsideaddons.gui.SignShape;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;


public class SignSupplementaryWide extends AbstractSign {

    // ---------------------------- <boilerplate> ----------------------------
    public SignSupplementaryWide(Settings settings) {
        super(settings);
        // Set default rotation to 0 (South)
        this.setDefaultState(this.stateManager.getDefaultState().with(VARIANT, SupplementaryWideType.BLANK));
    }

    // ---------------------------- <CODEC> ----------------------------
    private static final MapCodec<SignSupplementaryWide> CODEC = createCodec(SignSupplementaryWide::new);
    @Override
    protected MapCodec<SignSupplementaryWide> getCodec() {
        return CODEC;
    }

    // ---------------------------- <variants> ----------------------------

    // Define the specific Enum for this block
    public enum SupplementaryWideType implements SignVariant {


        // --- Distance (in X) ---
        IN_50M("in_50m", "distance"),
        IN_100M("in_100m", "distance"),
        IN_200M("in_200m", "distance"),
        IN_300M("in_300m", "distance"),
        IN_400M("in_400m", "distance"),
        IN_500M("in_500m", "distance"),
        IN_750M("in_750m", "distance"),
        IN_1KM("in_1km", "distance"),
        IN_2KM("in_2km", "distance"),
        IN_5KM("in_5km", "distance"),

        // --- Length / Duration (for X) ---
        FOR_50M("for_50m", "length"),
        FOR_100M("for_100m", "length"),
        FOR_200M("for_200m", "length"),
        FOR_300M("for_300m", "length"),
        FOR_400M("for_400m", "length"),
        FOR_500M("for_500m", "length"),
        FOR_750M("for_750m", "length"),
        FOR_1KM("for_1km", "length"),
        FOR_2KM("for_2km", "length"),
        FOR_5KM("for_5km", "length"),

        // --- directional ---
        TO_LEFT("to_left", "directional"),
        TO_RIGHT("to_right", "directional"),
        TO_LEFT2("to_left2", "directional"),
        TO_RIGHT2("to_right2", "directional"),
        TO_LEFT_PEDESTRIAN("to_left_pedestrian", "directional"),
        TO_RIGHT_PEDESTRIAN("to_right_pedestrian", "directional"),
        TO_ROUNDABOUT_FIRST_EXIT("to_roundabout_first_exit", "directional"),
        TO_BOTH_HORIZONTAL_ARROWS("to_both_horizontal_arrows", "directional"),
        TO_BOTH_VERTICAL_ARROWS("to_both_vertical_arrows", "directional"),

        // --- Time ---
        TIME_PARKING_DISC("time_parking_disc", "time"),
        TIME_16_18("time_16_18", "time"),
        TIME_WORKING_DAYS("time_working_days", "time"),
        TIME_WORKING_DAYS_18_19("time_working_days_18_19", "time"),
        TIME_MO_FR_16_18("time_mo_fr_16_18", "time"),
        TIME_SA_SO("time_sa_so", "time"),
        TIME_NOISE_PROTECTION("time_noise_protection", "time"),
        TIME_SCHOOL_WAY("time_school_way", "time"),

        // --- Weight ---
        WEIGHT_2_8_T("weight_2_8_t", "weight"),
        WEIGHT_7_5_T("weight_7_5_t", "weight"),
        WEIGHT_12_T("weight_12_t", "weight"),




        // --- general ---
        BIKE("bike", "general"),
        BUS("bus", "general"),
        CAMPER("camper", "general"),
        CAR("car", "general"),
        CAR_TRAILER("car_trailer", "general"),
        CYCLE("cycle", "general"),
        MOPED("moped", "general"),
        TRACTOR("tractor", "general"),
        TRAILER("trailer", "general"),
        TRAIN("train", "general"),
        TRUCK("truck", "general"),
        TRUCK_BIG("truck_big", "general"),
        TRUCK_TRAILER("truck_trailer", "general"),
        WHEELCHAIR("wheelchair", "general"),
        MILITARY("military", "general"),
        STREET_TRAIN("street_train", "general"),
        CATTLE("cattle", "general"),
        HORSE("horse", "general"),
        PEDESTRIAN("pedestrian", "general"),
        SHOULDER_LEFT("shoulder_left", "general"),
        SHOULDER_RIGHT("shoulder_right", "general"),
        CRASH("crash", "general"),
        CHILDREN("children", "general"),


        // --- text ---
        ACCIDENT("accident", "text"),
        CONSTRUCTION_EXIT("construction_exit", "text"),
        DIRTY_ROAD("dirty_road", "text"),
        EXPLOSION("explosion", "text"),
        HUMP("hump", "text"),
        LEFT_TURNING("left_turning", "text"),
        OIL("oil", "text"),
        POLICE_CONTROL("police_control", "text"),
        STREET_DAMAGE("street_damage", "text"),
        WATER("water", "text"),
        EXIT("exit", "text"),
        ACCESS("access", "text"),
        E_VEHICLES("e_vehicles", "text"),
        TAXI("taxi", "text"),
        TEXT_END("text_end", "text"),
        TEXT_HOSPITAL("text_hospital", "text"),
        TEXT_KINDERGARDEN("text_kindergarden", "text"),
        TEXT_LOADINGAREA("text_loadingarea", "text"),
        TEXT_NOISE_PROTECTION("text_noise_protection", "text"),
        TEXT_NURSING_HOME("text_nursing_home", "text"),
        TEXT_RESIDENTS_ONLY("text_residents_only", "text"),
        TEXT_SCHOOL("text_school", "text"),
        TEXT_STOP_HERE_RED("text_stop_here_red", "text"),
        TEXT_DISMOUT_CYCLISTS("text_dismout_cyclists", "text"),
        CONSTRUCTION_TRAFFIC("construction_traffic", "text"),

        // --- blank ---
        BLANK("blank", "blank");

        private final String name;
        private final String category;

        SupplementaryWideType(String name, String category) {
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

    public static final EnumProperty<SupplementaryWideType> VARIANT = EnumProperty.of("variant", SupplementaryWideType.class);

    @Override
    public EnumProperty<? extends SignVariant> getVariantProperty() {
        return VARIANT;
    }

    @Override
    public SignShape getSignShape() {
        return SignShape.SUPPLEMENTARY_WIDE;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        // You MUST add both the new variant and the inherited rotation
        builder.add(VARIANT, ROTATION);
    }

}
