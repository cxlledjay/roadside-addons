package de.cxlledjay.roadsideaddons.block.bollards;

import com.mojang.serialization.MapCodec;
import de.cxlledjay.roadsideaddons.block.RotatableBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

public class GermanBollard extends RotatableBlock {

    public GermanBollard(Settings settings) {
        super(settings);
    }


    // ---------------------------- <unique codec> ----------------------------
    public static final MapCodec<GermanBollard> CODEC = createCodec(GermanBollard::new);

    @Override
    protected MapCodec<? extends RotatableBlock> getCodec() {
        return CODEC;
    }



    // ---------------------------- <hitbox> ----------------------------
    private static final VoxelShape HITBOX = Block.createCuboidShape(7, 0, 7, 9, 14, 9);


    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return HITBOX;
    }
}
