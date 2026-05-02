package de.cxlledjay.roadsideaddons.block.construction;

import com.mojang.serialization.MapCodec;
import de.cxlledjay.roadsideaddons.block.RotatableBlock;
import de.cxlledjay.roadsideaddons.block.sign.variants.SignDanger;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class BollardConstructionLamp extends RotatableBlock {

    public BollardConstructionLamp(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(LAMP_ON, false));
    }


    // ---------------------------- <unique codec> ----------------------------
    public static final MapCodec<BollardConstructionLamp> CODEC = createCodec(BollardConstructionLamp::new);

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


    // ---------------------------- <variants> ----------------------------
    public static final BooleanProperty LAMP_ON = BooleanProperty.of("lamp_on");

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        // You MUST add both the new variant and the inherited rotation
        builder.add(LAMP_ON, ROTATION);
    }



    // ---------------------------- <variant switching> ----------------------------

    // --- 1. CHECK POWER WHEN PLACED ---
    @Override
    public @Nullable BlockState getPlacementState(ItemPlacementContext ctx) {
        // Ask the world if the position we are clicking is receiving power
        boolean isPowered = ctx.getWorld().isReceivingRedstonePower(ctx.getBlockPos());

        // Return the default state, but with the LIT property matching the power state
        return this.getDefaultState()
                .with(LAMP_ON, isPowered)
                .with(ROTATION, MathHelper.floor((double)(ctx.getPlayerYaw() * 16.0f / 360.0f) + 0.5) & 0x0F);
    }

    // --- 2. LISTEN FOR REDSTONE CHANGES ---
    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify) {
        // Always ensure we are only changing block states on the Server side!
        if (!world.isClient) {

            // 1. Is the block currently receiving power?
            boolean isPowered = world.isReceivingRedstonePower(pos);

            // 2. Is our current block state different from the power state?
            if (state.get(LAMP_ON) != isPowered) {
                // 3. If they don't match, update the block state!
                // The '3' (Block.NOTIFY_ALL) flag tells the server to update the block visually for clients.
                world.setBlockState(pos, state.with(LAMP_ON, isPowered), 3);
            }
        }
    }
}
