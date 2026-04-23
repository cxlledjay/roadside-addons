package de.cxlledjay.roadsideaddons.block.sign;


import com.mojang.serialization.MapCodec;
import de.cxlledjay.roadsideaddons.block.sign.generic.AbstractSign;
import de.cxlledjay.roadsideaddons.block.sign.generic.SignVariant;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;


public class SignDanger extends AbstractSign {

    // ---------------------------- <boilerplate> ----------------------------
    public SignDanger(Settings settings) {
        super(settings);
        // Set default rotation to 0 (South)
        this.setDefaultState(this.stateManager.getDefaultState().with(VARIANT, DangerType.DEFAULT));
    }

    // ---------------------------- <CODEC> ----------------------------
    private static final MapCodec<SignDanger> CODEC = createCodec(SignDanger::new);
    @Override
    protected MapCodec<SignDanger> getCodec() {
        return CODEC;
    }

    // ---------------------------- <variants> ----------------------------

    // Define the specific Enum for this block
    public enum DangerType implements SignVariant {
        DEFAULT("default"),
        CROSSING("crossing");

        private final String name;

        DangerType(String name) {
            this.name = name;
        }

        @Override
        public String asString() {
            return this.name;
        }
    }

    public static final EnumProperty<DangerType> VARIANT = EnumProperty.of("variant", DangerType.class);

    @Override
    public EnumProperty<? extends SignVariant> getVariantProperty() {
        return VARIANT;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        // You MUST add both the new variant and the inherited rotation
        builder.add(VARIANT, ROTATION);
    }



    // ---------------------------- <change sign method> ----------------------------

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {

        // server sided event
        if(!world.isClient()) {
            world.setBlockState(pos, state.cycle(VARIANT));
        }

        return ActionResult.SUCCESS;
    }
}
