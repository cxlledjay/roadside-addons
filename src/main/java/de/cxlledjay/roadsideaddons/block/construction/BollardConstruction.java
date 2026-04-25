package de.cxlledjay.roadsideaddons.block.construction;

import com.mojang.serialization.MapCodec;
import de.cxlledjay.roadsideaddons.block.RotatableBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public class BollardConstruction extends RotatableBlock {

    public BollardConstruction(Settings settings) {
        super(settings);
    }


    // ---------------------------- <unique codec> ----------------------------
    public static final MapCodec<BollardConstruction> CODEC = createCodec(BollardConstruction::new);

    @Override
    protected MapCodec<? extends RotatableBlock> getCodec() {
        return CODEC;
    }



    // ---------------------------- <hitbox> ----------------------------
    private static final VoxelShape BASE_SN = Block.createCuboidShape(3, 0, 0, 13, 3, 16);
    private static final VoxelShape BASE_EW = Block.createCuboidShape(0, 0, 3, 16, 3, 13);


    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return switch (state.get(ROTATION)) {
            case 0, 1, 2, 15, 7, 8, 9, 10 -> BASE_SN;
            default -> BASE_EW;
        };
    }
}
