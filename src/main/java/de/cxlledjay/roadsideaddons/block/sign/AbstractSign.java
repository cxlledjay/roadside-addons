package de.cxlledjay.roadsideaddons.block.sign;

import com.mojang.serialization.MapCodec;
import de.cxlledjay.roadsideaddons.block.RotatableBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.Nullable;

abstract class AbstractSign extends RotatableBlock {

    // ---------------------------- <constructor> ----------------------------
    public AbstractSign(Settings settings) {
        super(settings);
    }


    // ---------------------------- <hitbox> ----------------------------
    private static final VoxelShape SHAPE_DEFAULT_SN = Block.createCuboidShape(0,0,6,16,16,10);
    private static final VoxelShape SHAPE_DEFAULT_EW = Block.createCuboidShape(6,0,0,10,16,16);
    private static final VoxelShape SHAPE_DEFAULT_DIAG = Block.createCuboidShape(0,0,0,16,16,16);

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        VoxelShape res;
        switch (state.get(ROTATION)) {
            case 0:
            case 4:
                res = SHAPE_DEFAULT_SN;
                break;
            case 2:
            case 6:
                res = SHAPE_DEFAULT_EW;
                break;
            case 1:
            case 3:
            case 5:
            case 7:
            default:
                res = SHAPE_DEFAULT_DIAG;
                break;
        }
        return res;
    }
}