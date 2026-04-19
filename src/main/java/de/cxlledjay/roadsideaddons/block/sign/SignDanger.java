package de.cxlledjay.roadsideaddons.block.sign;

import com.mojang.serialization.MapCodec;
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

public class SignDanger extends HorizontalFacingBlock {

    private static final MapCodec<SignDanger> CODEC = createCodec(SignDanger::new);


    public SignDanger(Settings settings) {
        super(settings);
    }

    @Override
    protected MapCodec<? extends HorizontalFacingBlock> getCodec() {
        return CODEC;
    }


    private static final VoxelShape SHAPE_SN = Block.createCuboidShape(0,0,6,16,16,10);
    private static final VoxelShape SHAPE_EW = Block.createCuboidShape(6,0,0,10,16,16);

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        VoxelShape res;
        switch (state.get(FACING)) {
            case NORTH:
            case SOUTH:
                res = SHAPE_SN;
                break;
            case EAST:
            case WEST:
            default:
                res = SHAPE_EW;
                break;
        }
        return res;
    }

    @Override
    public @Nullable BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing().getOpposite());
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }
}
