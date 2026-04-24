package de.cxlledjay.roadsideaddons.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

public class TrafficCone extends RotatableBlock {

    public TrafficCone(Settings settings) {
        super(settings);
    }


    // ---------------------------- <unique codec> ----------------------------
    public static final MapCodec<TrafficCone> CODEC = createCodec(TrafficCone::new);

    @Override
    protected MapCodec<? extends RotatableBlock> getCodec() {
        return CODEC;
    }



    // ---------------------------- <hitbox> ----------------------------
    private static final VoxelShape SHAPE = Block.createCuboidShape(3, 0, 3, 13, 12, 13);

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }
}