package de.cxlledjay.roadsideaddons.block.sign;

import com.mojang.serialization.MapCodec;
import de.cxlledjay.roadsideaddons.block.sign.generic.AbstractSign;
import de.cxlledjay.roadsideaddons.block.sign.generic.SignVariant;
import net.minecraft.state.property.EnumProperty;


public class SignYield extends AbstractSign {

    // boilerplate constructor
    public SignYield(Settings settings) {
        super(settings);
    }


    // CODEC overwrite
    private static final MapCodec<SignYield> CODEC = createCodec(SignYield::new);
    @Override
    protected MapCodec<SignYield> getCodec() {
        return CODEC;
    }



    // ---------------------------- <variants> ----------------------------
    @Override
    public EnumProperty<? extends SignVariant> getVariantProperty() {
        return null;
    }
}
