package de.cxlledjay.roadsideaddons.block.sign;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.HorizontalFacingBlock;


public class SignYield extends AbstractSign {

    // boilerplate constructor
    public SignYield(Settings settings) {
        super(settings);
    }

    // CODEC overwrite
    private static final MapCodec<SignYield> CODEC = createCodec(SignYield::new);
    @Override
    protected MapCodec<? extends HorizontalFacingBlock> getCodec() {
        return CODEC;
    }
}
