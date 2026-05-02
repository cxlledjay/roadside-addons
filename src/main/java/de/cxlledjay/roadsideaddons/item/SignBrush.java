package de.cxlledjay.roadsideaddons.item;

import de.cxlledjay.roadsideaddons.block.sign.generic.AbstractSign;
import de.cxlledjay.roadsideaddons.gui.SignVariantSelectScreen;
import net.minecraft.block.Block;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.Property;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class SignBrush extends Item {


    public SignBrush(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        // default action
        ActionResult res = ActionResult.FAIL;

        // retrieve info from context
        World world = context.getWorld();
        BlockPos pos = context.getBlockPos();
        Block clickedBlock = world.getBlockState(pos).getBlock();

        // check if sign block was clicked
        if(clickedBlock instanceof AbstractSign signBlock) {

            // check if there are multiple variants
            if(signBlock.getVariantProperty() != null) {

                // get the variant enum
                Property<?> variantProperty = signBlock.getVariantProperty();

                if(world.isClient()) {
                    // open gui on client side
                    MinecraftClient.getInstance().setScreen(new SignVariantSelectScreen(pos, clickedBlock, variantProperty, signBlock.getSignShape()));
                }

                // set result to success for player hand swing animation
                res = ActionResult.SUCCESS;
            }
        }
        return res;
    }


    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.translatable("tooltip.roadside-addons.brush"));
        super.appendTooltip(stack, context, tooltip, type);
    }
}
