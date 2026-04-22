package de.cxlledjay.roadsideaddons.block.sign;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.HorizontalFacingBlock;


public class SignStop extends AbstractSign {

    // boilerplate constructor
    public SignStop(Settings settings) {
        super(settings);
    }

    // CODEC overwrite
    private static final MapCodec<SignStop> CODEC = createCodec(SignStop::new);
    @Override
    protected MapCodec<? extends HorizontalFacingBlock> getCodec() {
        return CODEC;
    }
}
