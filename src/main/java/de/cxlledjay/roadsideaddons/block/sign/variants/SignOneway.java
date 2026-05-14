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


public class SignOneway extends AbstractSign {

    // ---------------------------- <boilerplate> ----------------------------
    public SignOneway(Settings settings) {
        super(settings);
        // Set default rotation to 0 (South)
        this.setDefaultState(this.stateManager.getDefaultState().with(VARIANT, OnewayType.BLANK));
    }

    // ---------------------------- <CODEC> ----------------------------
    private static final MapCodec<SignOneway> CODEC = createCodec(SignOneway::new);
    @Override
    protected MapCodec<SignOneway> getCodec() {
        return CODEC;
    }

    // ---------------------------- <variants> ----------------------------

    // Define the specific Enum for this block
    public enum OnewayType implements SignVariant {

        // --- general ---
        LEFT("left", "general"),
        RIGHT("right", "general"),

        // --- detour ---
        DETOUR("detour", "detour"),
        DETOUR_END("detour_end", "detour"),

        // --- blank ---
        BLANK("blank", "blank");

        private final String name;
        private final String category;

        OnewayType(String name, String category) {
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

    public static final EnumProperty<OnewayType> VARIANT = EnumProperty.of("variant", OnewayType.class);

    @Override
    public EnumProperty<? extends SignVariant> getVariantProperty() {
        return VARIANT;
    }

    @Override
    public SignShape getSignShape() {
        return SignShape.ONE_WAY;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        // You MUST add both the new variant and the inherited rotation
        builder.add(VARIANT, ROTATION);
    }

    // ---------------------------- <hitbox> ----------------------------
    private static final VoxelShape POLE = Block.createCuboidShape(6, 0, 6, 10, 8, 10);

    private static final VoxelShape SHAPE_DEFAULT_SN = Block.createCuboidShape(-3,8,6,19,16,10);
    private static final VoxelShape SHAPE_DEFAULT_EW = Block.createCuboidShape(6,8,-3,10,16,19);

    private static final VoxelShape SHAPE_DEFAULT_SN_N22 = Block.createCuboidShape(-2,8,4,18,16,12);
    private static final VoxelShape SHAPE_DEFAULT_EW_N22 = Block.createCuboidShape(4,8,-2,12,16,18);

    private static final VoxelShape SHAPE_DEFAULT_SN_22 = Block.createCuboidShape(-2,8,4,18,16,12);
    private static final VoxelShape SHAPE_DEFAULT_EW_22 = Block.createCuboidShape(4,8,-2,12,16,18);

    private static final VoxelShape SHAPE_DEFAULT_DIAG = Block.createCuboidShape(0,8,0,16,16,16);



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
