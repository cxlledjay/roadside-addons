package de.cxlledjay.roadsideaddons.block.sign.variants;


import com.mojang.serialization.MapCodec;
import de.cxlledjay.roadsideaddons.block.sign.generic.AbstractSign;
import de.cxlledjay.roadsideaddons.block.sign.generic.SignVariant;
import de.cxlledjay.roadsideaddons.gui.SignShape;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;


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

    // ---------------------------- <hitbox> ----------------------------
    private static final VoxelShape POLE = Block.createCuboidShape(6, 0, 6, 10, 4, 10);

    private static final VoxelShape SHAPE_DEFAULT_SN = Block.createCuboidShape(2,4,6,14,16,10);
    private static final VoxelShape SHAPE_DEFAULT_EW = Block.createCuboidShape(6,4,2,10,16,14);

    private static final VoxelShape SHAPE_DEFAULT_SN_N22 = Block.createCuboidShape(2,4,4,14,16,12);
    private static final VoxelShape SHAPE_DEFAULT_EW_N22 = Block.createCuboidShape(4,4,2,12,16,14);

    private static final VoxelShape SHAPE_DEFAULT_SN_22 = Block.createCuboidShape(2,4,4,14,16,12);
    private static final VoxelShape SHAPE_DEFAULT_EW_22 = Block.createCuboidShape(4,4,2,12,16,14);

    private static final VoxelShape SHAPE_DEFAULT_DIAG = Block.createCuboidShape(4,4,4,12,16,12);



    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        VoxelShape res;
        switch (state.get(ROTATION)) {
            case 0:
            case 8:
                res = VoxelShapes.union(POLE, SHAPE_DEFAULT_SN);
                break;
            case 4:
            case 12:
                res = VoxelShapes.union(POLE, SHAPE_DEFAULT_EW);
                break;
            case 1:
            case 9:
                res = VoxelShapes.union(POLE, SHAPE_DEFAULT_SN_N22);
                break;
            case 3:
            case 11:
                res = VoxelShapes.union(POLE, SHAPE_DEFAULT_EW_22);
                break;
            case 5:
            case 13:
                res = VoxelShapes.union(POLE, SHAPE_DEFAULT_EW_N22);
                break;
            case 7:
            case 15:
                res = VoxelShapes.union(POLE, SHAPE_DEFAULT_SN_22);
                break;
            case 2:
            case 6:
            case 10:
            case 14:
            default:
                res = VoxelShapes.union(POLE, SHAPE_DEFAULT_DIAG);
                break;
        }
        return res;
    }

}
