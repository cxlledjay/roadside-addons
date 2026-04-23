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
import net.minecraft.util.math.RotationPropertyHelper;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.Nullable;

abstract public class RotatableBlock extends Block {

    // 8 way rotation property
    public static final IntProperty ROTATION = Properties.ROTATION;


    // ---------------------------- <constructor> ----------------------------
    public RotatableBlock(Settings settings) {
        super(settings);

        // Set default rotation to 0 (South)
        this.setDefaultState(this.stateManager.getDefaultState().with(ROTATION, 0));
    }



    // ---------------------------- <unique codec> ----------------------------
    @Override
    protected abstract MapCodec<? extends Block> getCodec();



    // ---------------------------- <placement rotation> ----------------------------
    @Override
    public @Nullable BlockState getPlacementState(ItemPlacementContext ctx) {
        // calculate 16-way rotation based on player yaw
        return this.getDefaultState().with(ROTATION, MathHelper.floor((double)(ctx.getPlayerYaw() * 16.0f / 360.0f) + 0.5) & 0x0F);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(ROTATION);
    }
}
