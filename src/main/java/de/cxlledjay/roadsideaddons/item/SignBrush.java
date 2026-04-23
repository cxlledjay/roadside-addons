package de.cxlledjay.roadsideaddons.item;

import de.cxlledjay.roadsideaddons.block.sign.generic.AbstractSign;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import static de.cxlledjay.roadsideaddons.block.sign.SignDanger.VARIANT;

public class SignBrush extends Item {


    public SignBrush(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {

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

                    // for now: cycle variant
                    // TODO: menu and custom screen
                    world.setBlockState(pos, world.getBlockState(pos).cycle(VARIANT));

                    // also damage the brush
                    context.getStack().damage(1,((ServerWorld) world),
                            ((ServerPlayerEntity) context.getPlayer()), item -> {
                                assert context.getPlayer() != null;
                                context.getPlayer().sendEquipmentBreakStatus(item, EquipmentSlot.MAINHAND);
                            });

                    // and play a sound
                    world.playSound(null, pos, SoundEvents.BLOCK_NETHER_ORE_FALL, SoundCategory.BLOCKS);

                    // set result to success for player hand swing animation
                    res = ActionResult.SUCCESS;
                }
            }
        }

        return res;
    }



}
