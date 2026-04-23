package de.cxlledjay.roadsideaddons.block.sign.generic;

import com.mojang.serialization.MapCodec;
import de.cxlledjay.roadsideaddons.block.RotatableBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Property;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

public abstract class AbstractSign extends RotatableBlock{

    // ---------------------------- <constructor> ----------------------------
    public AbstractSign(Settings settings) {
        super(settings);
    }


    // ---------------------------- <hitbox> ----------------------------
    private static final VoxelShape SHAPE_DEFAULT_SN = Block.createCuboidShape(0,0,6,16,16,10);
    private static final VoxelShape SHAPE_DEFAULT_EW = Block.createCuboidShape(6,0,0,10,16,16);

    private static final VoxelShape SHAPE_DEFAULT_SN_N22 = Block.createCuboidShape(0,0,4,16,16,12);
    private static final VoxelShape SHAPE_DEFAULT_EW_N22 = Block.createCuboidShape(4,0,0,12,16,16);

    private static final VoxelShape SHAPE_DEFAULT_SN_22 = Block.createCuboidShape(0,0,4,16,16,12);
    private static final VoxelShape SHAPE_DEFAULT_EW_22 = Block.createCuboidShape(4,0,0,12,16,16);

    private static final VoxelShape SHAPE_DEFAULT_DIAG = Block.createCuboidShape(2,0,2,14,16,14);



    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        VoxelShape res;
        switch (state.get(ROTATION)) {
            case 0:
            case 8:
                res = SHAPE_DEFAULT_SN;
                break;
            case 4:
            case 12:
                res = SHAPE_DEFAULT_EW;
                break;
            case 1:
            case 9:
                res = SHAPE_DEFAULT_SN_N22;
                break;
            case 3:
            case 11:
                res = SHAPE_DEFAULT_SN_22;
                break;
            case 5:
            case 13:
                res = SHAPE_DEFAULT_EW_N22;
                break;
            case 7:
            case 15:
                res = SHAPE_DEFAULT_EW_22;
                break;
            case 2:
            case 6:
            case 10:
            case 14:
            default:
                res = SHAPE_DEFAULT_DIAG;
                break;
        }
        return res;
    }

    // ---------------------------- <variants interface> ----------------------------
    public abstract EnumProperty<? extends SignVariant> getVariantProperty();
}