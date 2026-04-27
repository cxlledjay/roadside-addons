package de.cxlledjay.roadsideaddons.item;

import de.cxlledjay.roadsideaddons.block.sign.generic.AbstractSign;
import de.cxlledjay.roadsideaddons.gui.SignVariantSelectScreen;
import net.minecraft.block.Block;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.Property;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SignBrush extends Item {


    public SignBrush(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        return useOnBlockScreen(context);
    }

    public ActionResult useOnBlockScreen(ItemUsageContext context) {

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
                    MinecraftClient.getInstance().setScreen(new SignVariantSelectScreen(pos, clickedBlock, variantProperty));
                }

                // set result to success for player hand swing animation
                res = ActionResult.SUCCESS;
            }
        }
        return res;
    }


    // testing
    private ActionResult useOnBlockTest(ItemUsageContext context) {

        ActionResult res = ActionResult.FAIL;

        World world = context.getWorld();
        BlockPos pos = context.getBlockPos();
        Block clickedBlock = world.getBlockState(pos).getBlock();

        // executing server sided code
        if(!world.isClient()){

            // check if sign block was clicked
            if(clickedBlock instanceof AbstractSign signBlock) {

                // only execute brush logic when it has multiple variants
                if(signBlock.getVariantProperty() != null) {

                    Property<?> variantProperty = signBlock.getVariantProperty();

                    // for now: cycle variant
                    // TODO: menu and custom screen
                    world.setBlockState(pos, world.getBlockState(pos).cycle(variantProperty));

                    // also damage the brush
                    context.getStack().damage(1,((ServerWorld) world),
                            ((ServerPlayerEntity) context.getPlayer()), item -> {
                                assert context.getPlayer() != null;
                                context.getPlayer().sendEquipmentBreakStatus(item, EquipmentSlot.MAINHAND);
                            });

                    // and play a sound
                    world.playSound(null, pos, SoundEvents.BLOCK_METAL_HIT, SoundCategory.BLOCKS);

                    // set result to success for player hand swing animation
                    res = ActionResult.SUCCESS;
                }
            }
        }

        return res;
    }



}
