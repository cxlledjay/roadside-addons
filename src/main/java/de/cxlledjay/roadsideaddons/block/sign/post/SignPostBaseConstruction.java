package de.cxlledjay.roadsideaddons.block.sign.post;

import com.mojang.serialization.MapCodec;
import de.cxlledjay.roadsideaddons.block.RotatableBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public class SignPostBaseConstruction extends RotatableBlock {

    public SignPostBaseConstruction(Settings settings) {
        super(settings);
    }


    // ---------------------------- <unique codec> ----------------------------
    public static final MapCodec<SignPostBaseConstruction> CODEC = createCodec(SignPostBaseConstruction::new);

    @Override
    protected MapCodec<? extends RotatableBlock> getCodec() {
        return CODEC;
    }



    // ---------------------------- <hitbox> ----------------------------
    private static final VoxelShape POLE = Block.createCuboidShape(6, 0, 6, 10, 16, 10);
    // 2. A wider base at the bottom
    private static final VoxelShape BASE_SN = Block.createCuboidShape(3, 0, 0, 13, 3, 16);
    private static final VoxelShape BASE_EW = Block.createCuboidShape(0, 0, 3, 16, 3, 13);


    // Merge the two parts together
    private static final VoxelShape HITBOX_SN = VoxelShapes.union(POLE, BASE_SN);
    private static final VoxelShape HITBOX_EW = VoxelShapes.union(POLE, BASE_EW);

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return switch (state.get(ROTATION)) {
            case 0, 1, 2, 15, 7, 8, 9, 10 -> HITBOX_SN;
            default -> HITBOX_EW;
        };
    }
}
