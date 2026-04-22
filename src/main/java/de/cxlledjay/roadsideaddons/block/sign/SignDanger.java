package de.cxlledjay.roadsideaddons.block.sign;


import com.mojang.serialization.MapCodec;
import net.minecraft.block.HorizontalFacingBlock;


public class SignDanger extends AbstractSign {

    // boilerplate constructor
    public SignDanger(Settings settings) {
        super(settings);
    }

    // CODEC overwrite
    private static final MapCodec<SignDanger> CODEC = createCodec(SignDanger::new);
    @Override
    protected MapCodec<? extends HorizontalFacingBlock> getCodec() {
        return CODEC;
    }
}
