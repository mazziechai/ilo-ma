package cafe.ferret.ilo_ma.mixin;

import cafe.ferret.ilo_ma.tpt.TokiPonaTasoValidator;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.ChatSender;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.PlayerChatMessage;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

@Mixin(PlayerList.class)
public class PlayerListMixin {
	/**
	 *
	 */
	@Inject(method = "broadcastChatMessage(Lnet/minecraft/network/chat/PlayerChatMessage;Ljava/util/function/Function;Lnet/minecraft/network/chat/ChatSender;Lnet/minecraft/resources/ResourceKey;)V", at = @At("HEAD"), cancellable = true)
	public void onBroadcastChatMessage(PlayerChatMessage playerChatMessage, Function<ServerPlayer, PlayerChatMessage> function, ChatSender chatSender, ResourceKey<ChatType> resourceKey, CallbackInfo ci) {
		String content = playerChatMessage.serverContent().getString();

		if (!content.startsWith("/") && chatSender != null) {
			if (!TokiPonaTasoValidator.validateMessage(content)) {
				ci.cancel();
			}
		}
	}
}
