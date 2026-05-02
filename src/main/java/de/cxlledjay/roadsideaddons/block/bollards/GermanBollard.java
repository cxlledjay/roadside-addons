package de.cxlledjay.roadsideaddons.block.bollards;

import com.mojang.serialization.MapCodec;
import de.cxlledjay.roadsideaddons.block.RotatableBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class GermanBollard extends RotatableBlock {

    public GermanBollard(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(IS_INTERSECTION, false));
    }


    // ---------------------------- <unique codec> ----------------------------
    public static final MapCodec<GermanBollard> CODEC = createCodec(GermanBollard::new);

    @Override
    protected MapCodec<? extends RotatableBlock> getCodec() {
        return CODEC;
    }



    // ---------------------------- <hitbox> ----------------------------
    private static final VoxelShape HITBOX = Block.createCuboidShape(6, 0, 6, 10, 20, 10);


    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return HITBOX;
    }



    // ---------------------------- <variants> ----------------------------
    public static final BooleanProperty IS_INTERSECTION = BooleanProperty.of("is_intersection");

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        // You MUST add both the new variant and the inherited rotation
        builder.add(IS_INTERSECTION, ROTATION);
    }




    // ---------------------------- <variant switching> ----------------------------


    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {

        // only interact with states on server
        if(!world.isClient()) {
            world.setBlockState(pos, state.cycle(IS_INTERSECTION), 3);
        }

        // make hand swing
        return ActionResult.SUCCESS;
    }
}
