package de.cxlledjay.roadsideaddons.block.sign.variants;


import com.mojang.serialization.MapCodec;
import de.cxlledjay.roadsideaddons.block.sign.generic.AbstractSign;
import de.cxlledjay.roadsideaddons.block.sign.generic.SignVariant;
import de.cxlledjay.roadsideaddons.gui.SignShape;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;


public class SignRegulatory extends AbstractSign {

    // ---------------------------- <boilerplate> ----------------------------
    public SignRegulatory(Settings settings) {
        super(settings);
        // Set default rotation to 0 (South)
        this.setDefaultState(this.stateManager.getDefaultState().with(VARIANT, RegulatoryType.BLANK));
    }

    // ---------------------------- <CODEC> ----------------------------
    private static final MapCodec<SignRegulatory> CODEC = createCodec(SignRegulatory::new);
    @Override
    protected MapCodec<SignRegulatory> getCodec() {
        return CODEC;
    }

    // ---------------------------- <variants> ----------------------------

    // Define the specific Enum for this block
    public enum RegulatoryType implements SignVariant {

        // --- general ---
        DEFAULT("default", "general"),
        BAN_ACTUAL_AXLE_LOAD("ban_actual_axle_load", "general"),
        BAN_ACTUAL_HEIGHT("ban_actual_height", "general"),
        BAN_ACTUAL_LENGTH("ban_actual_length", "general"),
        BAN_ACTUAL_MASS("ban_actual_mass", "general"),
        BAN_ACTUAL_WIDTH("ban_actual_width", "general"),
        BAN_ACTUAL_WIDTH23("ban_actual_width23", "general"),
        BAN_BIKE("ban_bike", "general"),
        BAN_BUS("ban_bus", "general"),
        BAN_CAR("ban_car", "general"),
        BAN_CAR_TRAILER("ban_car_trailer", "general"),
        BAN_CATTLE("ban_cattle", "general"),
        BAN_CYCLE("ban_cycle", "general"),
        BAN_ESCOOTER("ban_escooter", "general"),
        BAN_HORSE("ban_horse", "general"),
        BAN_MOPED("ban_moped", "general"),
        BAN_MULTI_TRACK("ban_multi_track", "general"),
        BAN_PEDESTRIAN("ban_pedestrian", "general"),
        BAN_TRACTOR("ban_tractor", "general"),
        BAN_TRUCK("ban_truck", "general"),
        BAN_TRUCK_DANGEROUS("ban_truck_dangerous", "general"),
        BAN_TRUCK_DANGEROUS_WATER("ban_truck_dangerous_water", "general"),
        BAN_TRUCK_TRAILER("ban_truck_trailer", "general"),
        BAN_VEHICLE("ban_vehicle", "general"),
        MINIMUM_DISTANCE("minimum_distance", "general"),
        NO_TURNING("no_turning", "general"),

        // --- directional ---
        DIRECTION_HERE_LEFT("direction_here_left", "directional"),
        DIRECTION_HERE_RIGHT("direction_here_right", "directional"),
        DIRECTION_LEFT("direction_left", "directional"),
        DIRECTION_RIGHT("direction_right", "directional"),
        DIRECTION_RIGHT_LEFT("direction_right_left", "directional"),
        DIRECTION_STRAIGHT("direction_straight", "directional"),
        DIRECTION_STRAIGHT_LEFT("direction_straight_left", "directional"),
        DIRECTION_STRAIGHT_RIGHT("direction_straight_right", "directional"),
        PASS_LEFT("pass_left", "directional"),
        PASS_RIGHT("pass_right", "directional"),
        ROUNDABOUT("roundabout", "directional"),

        // --- special_routes ---
        SHARED_PEDESTRIAN_CYCLE("shared_pedestrian_cycle", "special_routes"), // Grouped here as it acts like the "special" signs
        SPECIAL_BIKE("special_bike", "special_routes"),
        SPECIAL_BUS("special_bus", "special_routes"),
        SPECIAL_HORSE("special_horse", "special_routes"),
        SPECIAL_PEDESTRIAN("special_pedestrian", "special_routes"),

        // --- no_stopping ---
        NO_STOPPING("no_stopping", "no_stopping"),
        NO_STOPPING_START_LEFT("no_stopping_start_left", "no_stopping"),
        NO_STOPPING_START_RIGHT("no_stopping_start_right", "no_stopping"),
        NO_STOPPING_END_MID_LEFT("no_stopping_end_mid_left", "no_stopping"),
        NO_STOPPING_END_MID_RIGHT("no_stopping_end_mid_right", "no_stopping"),
        NO_STOPPING_END_LEFT("no_stopping_end_left", "no_stopping"),
        NO_STOPPING_END_RIGHT("no_stopping_end_right", "no_stopping"),

        // --- no_stopping_restricted ---
        NO_STOPPING_RESTRICTED("no_stopping_restricted", "no_stopping_restricted"),
        NO_STOPPING_RESTRICTED_START_LEFT("no_stopping_restricted_start_left", "no_stopping_restricted"),
        NO_STOPPING_RESTRICTED_START_RIGHT("no_stopping_restricted_start_right", "no_stopping_restricted"),
        NO_STOPPING_RESTRICTED_MID_LEFT("no_stopping_restricted_mid_left", "no_stopping_restricted"),
        NO_STOPPING_RESTRICTED_MID_RIGHT("no_stopping_restricted_mid_right", "no_stopping_restricted"),
        NO_STOPPING_RESTRICTED_END_LEFT("no_stopping_restricted_end_left", "no_stopping_restricted"),
        NO_STOPPING_RESTRICTED_END_RIGHT("no_stopping_restricted_end_right", "no_stopping_restricted"),

        // --- speed_limit ---
        SPEED_LIMIT_5("speed_limit_5", "speed_limit"),
        SPEED_LIMIT_10("speed_limit_10", "speed_limit"),
        SPEED_LIMIT_20("speed_limit_20", "speed_limit"),
        SPEED_LIMIT_30("speed_limit_30", "speed_limit"),
        SPEED_LIMIT_40("speed_limit_40", "speed_limit"),
        SPEED_LIMIT_50("speed_limit_50", "speed_limit"),
        SPEED_LIMIT_60("speed_limit_60", "speed_limit"),
        SPEED_LIMIT_70("speed_limit_70", "speed_limit"),
        SPEED_LIMIT_80("speed_limit_80", "speed_limit"),
        SPEED_LIMIT_90("speed_limit_90", "speed_limit"),
        SPEED_LIMIT_100("speed_limit_100", "speed_limit"),
        SPEED_LIMIT_110("speed_limit_110", "speed_limit"),
        SPEED_LIMIT_120("speed_limit_120", "speed_limit"),
        SPEED_LIMIT_130("speed_limit_130", "speed_limit"),


        // --- speed_limit_end ---
        SPEED_LIMIT_END_5("speed_limit_end_5", "speed_limit_end"),
        SPEED_LIMIT_END_10("speed_limit_end_10", "speed_limit_end"),
        SPEED_LIMIT_END_20("speed_limit_end_20", "speed_limit_end"),
        SPEED_LIMIT_END_30("speed_limit_end_30", "speed_limit_end"),
        SPEED_LIMIT_END_40("speed_limit_end_40", "speed_limit_end"),
        SPEED_LIMIT_END_50("speed_limit_end_50", "speed_limit_end"),
        SPEED_LIMIT_END_60("speed_limit_end_60", "speed_limit_end"),
        SPEED_LIMIT_END_70("speed_limit_end_70", "speed_limit_end"),
        SPEED_LIMIT_END_80("speed_limit_end_80", "speed_limit_end"),
        SPEED_LIMIT_END_90("speed_limit_end_90", "speed_limit_end"),
        SPEED_LIMIT_END_100("speed_limit_end_100", "speed_limit_end"),
        SPEED_LIMIT_END_110("speed_limit_end_110", "speed_limit_end"),
        SPEED_LIMIT_END_120("speed_limit_end_120", "speed_limit_end"),
        SPEED_LIMIT_END_130("speed_limit_end_130", "speed_limit_end"),
        SPEED_LIMIT_END("speed_limit_end", "speed_limit_end"),

        // --- passing ---
        NO_PASSING("no_passing", "passing"),
        NO_PASSING_END("no_passing_end", "passing"),
        NO_PASSING_TRUCKS("no_passing_trucks", "passing"),
        NO_PASSING_TRUCKS_END("no_passing_trucks_end", "passing"),

        // --- other ---
        NO_ENTRY("no_entry", "other"),
        BUS_STOP("bus_stop", "other"),
        SPEED_MINIMUM_30("speed_minimum_30", "other"),
        SPEED_MINIMUM_30_OVER("speed_minimum_30_over", "other"),

        // --- priority ---
        ONCOMING_PRIORITY("oncoming_priority", "priority"),

        // --- blank ---
        BLANK("blank", "blank");

        private final String name;
        private final String category;

        RegulatoryType(String name, String category) {
            this.name = name;
            this.category = category;
        }

        @Override
        public String asString() {
            return this.name;
        }

        @Override
        public String getCategory() {
            return this.category;
        }
    }

    public static final EnumProperty<RegulatoryType> VARIANT = EnumProperty.of("variant", RegulatoryType.class);

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
