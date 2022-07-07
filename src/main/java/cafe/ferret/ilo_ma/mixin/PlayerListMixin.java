package cafe.ferret.ilo_ma.mixin;

import net.minecraft.network.chat.ChatSender;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.PlayerChatMessage;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Function;

@Mixin(PlayerList.class)
public class PlayerListMixin {
	@Inject(method = "broadcastChatMessage(Lnet/minecraft/network/chat/PlayerChatMessage;Ljava/util/function/Function;Lnet/minecraft/network/chat/ChatSender;Lnet/minecraft/resources/ResourceKey;)V", at = @At("HEAD"), cancellable = true)
	public void onBroadcastChatMessage(PlayerChatMessage playerChatMessage, Function<ServerPlayer, PlayerChatMessage> function, ChatSender chatSender, ResourceKey<ChatType> resourceKey, CallbackInfo ci) {
		if (!playerChatMessage.serverContent().getString().startsWith("/") && chatSender != null) {
			StringBuilder sb = new StringBuilder(playerChatMessage.serverContent().getString());
			System.out.println(playerChatMessage.serverContent().getString());
		}
	}
}
