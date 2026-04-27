package de.cxlledjay.roadsideaddons.networking;

import de.cxlledjay.roadsideaddons.networking.packets.ChangeSignVariantPayload;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

public class ModMessages {

    public static void registerC2SPackets() {
        // 1. Register the payload type so the game knows it exists
        PayloadTypeRegistry.playC2S().register(ChangeSignVariantPayload.ID, ChangeSignVariantPayload.CODEC);

        // 2. Define what happens when the server receives it
        ServerPlayNetworking.registerGlobalReceiver(ChangeSignVariantPayload.ID, ChangeSignVariantPayload::receive);
    }

}
