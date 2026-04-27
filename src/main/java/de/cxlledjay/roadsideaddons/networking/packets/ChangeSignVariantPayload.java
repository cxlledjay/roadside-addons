package de.cxlledjay.roadsideaddons.networking.packets;

import de.cxlledjay.roadsideaddons.RoadsideAddons;
import de.cxlledjay.roadsideaddons.block.sign.generic.SignVariant;
import de.cxlledjay.roadsideaddons.registry.ModItems;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.Property;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;


public record ChangeSignVariantPayload(BlockPos pos, String variantId) implements CustomPayload {

    // unique id for this packet
    public static final CustomPayload.Id<ChangeSignVariantPayload> ID =
            new CustomPayload.Id<>(Identifier.of(RoadsideAddons.MOD_ID, "change_sign_variant"));

    // The Codec tells the game HOW to send this over the network.
    // PacketCodec.tuple() automatically matches your variables to codecs.
    public static final PacketCodec<RegistryByteBuf, ChangeSignVariantPayload> CODEC = PacketCodec.tuple(
            BlockPos.PACKET_CODEC, ChangeSignVariantPayload::pos,
            PacketCodecs.STRING, ChangeSignVariantPayload::variantId,
            ChangeSignVariantPayload::new
    );

    @Override
    public CustomPayload.Id<? extends CustomPayload> getId() {
        return ID;
    }

    // receive method (called on the server)
    public static void receive(ChangeSignVariantPayload payload, ServerPlayNetworking.Context context) {

        //execute on server
        context.server().execute(() -> {

            // retrieve context information
            ServerPlayerEntity player = context.player();
            ServerWorld world = player.getServerWorld();

            BlockPos targetPos = payload.pos();
            String targetVariant = payload.variantId();

            // weird too far away shenanigans
            if (player.squaredDistanceTo(targetPos.toCenterPos()) > 64.0) {
                return;
            }

            // get current block state
            BlockState currentState = world.getBlockState(targetPos);

            // identify SignVariant Property
            Property<?> targetProperty = null;
            for (Property<?> prop : currentState.getProperties()) {
                // Check if this property has values, and if the first value implements SignVariant
                if (!prop.getValues().isEmpty() && prop.getValues().iterator().next() instanceof SignVariant) {
                    targetProperty = prop;
                    break; // We found it!
                }
            }

            // if we found the property, apply the string using helper method
            if (targetProperty != null) {
                BlockState newState = applyVariantFromString(currentState, targetProperty, targetVariant);

                // 1. update the world!
                // The '3' is a flag that tells the server to update the block and send the change to clients.
                world.setBlockState(targetPos, newState, 3);

                // 2. also play a sound!
                // The first parameter is the player to EXCLUDE. By passing 'null',
                // we tell the server to play the sound for EVERYONE nearby, including the clicker!
                world.playSound(
                        null,
                        targetPos,
                        SoundEvents.BLOCK_METAL_PLACE, // The satisfying brush sweep sound
                        SoundCategory.BLOCKS,
                        1.0f, // Volume
                        1.0f  // Pitch (1.0 is normal speed)
                );

                // 3. and damage the item!
                // First, we need to figure out which hand the player is holding the brush in.
                ItemStack heldItem = player.getMainHandStack();
                EquipmentSlot slot = EquipmentSlot.MAINHAND;

                // If it's not in their main hand, check the off-hand
                if (!heldItem.isOf(ModItems.SIGN_BRUSH)) {
                    heldItem = player.getOffHandStack();
                    slot = EquipmentSlot.OFFHAND;
                }

                // If we successfully found a brush in either hand, damage it!
                if (heldItem.isOf(ModItems.SIGN_BRUSH)) {
                    // Damages the item by 1 point.
                    // We pass the player and the slot so that if the brush breaks,
                    // the game automatically plays the "tool break" particle/sound effect!
                    heldItem.damage(1, player, slot);
                }

            }
        });
    }




    // The <T extends Comparable<T>> forces Java to link the Property's type to the Value's type!
    private static <T extends Comparable<T>> BlockState applyVariantFromString(BlockState state, Property<T> property, String variantName) {
        // Minecraft Properties have a built-in .parse() method!
        // It safely attempts to convert the string back into the specific Enum.
        return property.parse(variantName)
                .map(value -> state.with(property, value))
                .orElse(state); // If the string is invalid, do nothing
    }

}