package de.cxlledjay.roadsideaddons.block.sign.variants;

import com.mojang.serialization.MapCodec;
import de.cxlledjay.roadsideaddons.block.sign.generic.AbstractSign;
import de.cxlledjay.roadsideaddons.block.sign.generic.SignVariant;
import net.minecraft.state.property.EnumProperty;


public class SignStop extends AbstractSign {

    // boilerplate constructor
    public SignStop(Settings settings) {
        super(settings);
    }

    // CODEC overwrite
    private static final MapCodec<SignStop> CODEC = createCodec(SignStop::new);
    @Override
    protected MapCodec<SignStop> getCodec() {
        return CODEC;
    }



    // ---------------------------- <variants> ----------------------------
    @Override
    public EnumProperty<? extends SignVariant> getVariantProperty() {
        return null;
    }
}
