package de.cxlledjay.roadsideaddons.block.sign.variants;


import com.mojang.serialization.MapCodec;
import de.cxlledjay.roadsideaddons.block.sign.generic.AbstractSign;
import de.cxlledjay.roadsideaddons.block.sign.generic.SignVariant;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;


public class SignInformation extends AbstractSign {

    // ---------------------------- <boilerplate> ----------------------------
    public SignInformation(Settings settings) {
        super(settings);
        // Set default rotation to 0 (South)
        this.setDefaultState(this.stateManager.getDefaultState().with(VARIANT, InformationType.BLANK));
    }

    // ---------------------------- <CODEC> ----------------------------
    private static final MapCodec<SignInformation> CODEC = createCodec(SignInformation::new);
    @Override
    protected MapCodec<SignInformation> getCodec() {
        return CODEC;
    }

    // ---------------------------- <variants> ----------------------------

    // Define the specific Enum for this block
    public enum InformationType implements SignVariant {


        // --- Zones ---
        BIKE_ZONE("bike_zone", "zone"),
        BIKE_ZONE_END("bike_zone_end", "zone"),
        NO_STOPPING_ZONE("no_stopping_zone", "zone"),
        NO_STOPPING_ZONE_END("no_stopping_zone_end", "zone"),
        PARKING_ZONE("parking_zone", "zone"),
        PARKING_ZONE_END("parking_zone_end", "zone"),
        PEDESTRIAN_ZONE("pedestrian_zone", "zone"),
        PEDESTRIAN_ZONE_END("pedestrian_zone_end", "zone"),
        SPEED_LIMIT_20_ZONE("speed_limit_20_zone", "zone"),
        SPEED_LIMIT_20_ZONE_END("speed_limit_20_zone_end", "zone"),
        SPEED_LIMIT_30_ZONE("speed_limit_30_zone", "zone"),
        SPEED_LIMIT_30_ZONE_END("speed_limit_30_zone_end", "zone"),

        // --- Parking ---
        PARKING("parking", "parking"),
        PARKING_LEFT("parking_left", "parking"),
        PARKING_RIGHT("parking_right", "parking"),
        PARKING_MIDDLE_LEFT("parking_middle_left", "parking"),
        PARKING_MIDDLE_RIGHT("parking_middle_right", "parking"),
        PARKING_GARAGE("parking_garage", "parking"),
        PARK_AND_RIDE("park_and_ride", "parking"),

        // --- Highway & Motorway ---
        AUTOBAHN("autobahn", "highway"),
        AUTOBAHN_END("autobahn_end", "highway"),
        MOTOR_ROAD("motor_road", "highway"),
        MOTOR_ROAD_END("motor_road_end", "highway"),
        EMERGENCY_BAY("emergency_bay", "highway"),

        // --- general ---
        DEAD_END("dead_end", "general"),
        PRIORITY("priority", "general"),
        TUNNEL("tunnel", "general"),

        // --- pedestrian_crossing ---
        PEDESTRIAN_CROSSING_LEFT("pedestrian_crossing_left", "pedestrian_crossing"),
        PEDESTRIAN_CROSSING_RIGHT("pedestrian_crossing_right", "pedestrian_crossing"),

        // --- Facilities & Services ---
        CAMPER("camper", "facilities"),
        CAMPING("camping", "facilities"),
        CAMPING_GROUND("camping_ground", "facilities"),
        CHURCH("church", "facilities"),
        FIRST_AID("first_aid", "facilities"),
        GAS_STATION("gas_station", "facilities"),
        GAS_STATION_CHARGING("gas_station_charging", "facilities"),
        GAS_STATION_CNG("gas_station_cng", "facilities"),
        GAS_STATION_H2("gas_station_h2", "facilities"),
        GAS_STATION_LPG("gas_station_lpg", "facilities"),
        HOTEL("hotel", "facilities"),
        INFORMATION("information", "facilities"),
        INN("inn", "facilities"),
        KIOSK("kiosk", "facilities"),
        PHONE("phone", "facilities"),
        PHONE_SOS("phone_sos", "facilities"),
        POLICE("police", "facilities"),
        ROADSIDE_ASSISTANCE("roadside_assistance", "facilities"),
        STAIRS_DOWN("stairs_down", "facilities"),
        STAIRS_UP("stairs_up", "facilities"),
        WC("wc", "facilities"),

        // --- blank ---
        BLANK("blank", "blank");

        private final String name;
        private final String category;

        InformationType(String name, String category) {
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

    public static final EnumProperty<InformationType> VARIANT = EnumProperty.of("variant", InformationType.class);

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
