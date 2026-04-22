package de.cxlledjay.roadsideaddons.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.Nullable;

public class SignPostBase extends RotatableBlock {

    public SignPostBase(Settings settings) {
        super(settings);
    }


    // ---------------------------- <unique codec> ----------------------------
    public static final MapCodec<SignPostBase> CODEC = createCodec(SignPostBase::new);

    @Override
    protected MapCodec<? extends RotatableBlock> getCodec() {
        return CODEC;
    }



    // ---------------------------- <hitbox> ----------------------------
    private static final VoxelShape SHAPE = Block.createCuboidShape(6, 0, 6, 10, 16, 10);

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }
}
