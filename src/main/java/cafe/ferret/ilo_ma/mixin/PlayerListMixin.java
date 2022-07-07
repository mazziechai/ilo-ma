package cafe.ferret.ilo_ma.mixin;

import cafe.ferret.ilo_ma.tpt.TokiPonaTasoValidator;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.ChatSender;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.PlayerChatMessage;
import net.minecraft.network.chat.contents.LiteralContents;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.FilteredText;
import net.minecraft.server.players.PlayerList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Function;

@Mixin(PlayerList.class)
public abstract class PlayerListMixin {
	/**
	 *
	 */
	@Inject(method = "broadcastChatMessage(Lnet/minecraft/server/network/FilteredText;Lnet/minecraft/server/level/ServerPlayer;Lnet/minecraft/resources/ResourceKey;)V", at = @At("HEAD"), cancellable = true)
	public void onBroadcastChatMessage(FilteredText<PlayerChatMessage> filteredText, ServerPlayer serverPlayer, ResourceKey<ChatType> resourceKey, CallbackInfo ci) {
		String content = filteredText.raw().serverContent().getString();

		if (!content.startsWith("/") && serverPlayer != null) {
			if (!TokiPonaTasoValidator.validateMessage(content)) {
				serverPlayer.displayClientMessage(Component.literal("o toki Inli ala lon ma ni a!").withStyle(ChatFormatting.RED), false);
				ci.cancel();
			}
		}
	}
}
