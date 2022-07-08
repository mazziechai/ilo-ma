package cafe.ferret.ilo_ma.mixin;

import cafe.ferret.ilo_ma.util.TokiPonaTasoValidator;
import net.minecraft.network.message.MessageType;
import net.minecraft.network.message.SignedMessage;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.filter.FilteredMessage;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.registry.RegistryKey;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerManager.class)
public abstract class PlayerListMixin {
	/**
	 *
	 */
	@Inject(method = "broadcast(Lnet/minecraft/server/filter/FilteredMessage;Lnet/minecraft/server/network/ServerPlayerEntity;Lnet/minecraft/util/registry/RegistryKey;)V", at = @At("HEAD"), cancellable = true)
	public void onBroadcastChatMessage(FilteredMessage<SignedMessage> filteredMessage, ServerPlayerEntity serverPlayerEntity, RegistryKey<MessageType> registryKey, CallbackInfo ci) {
		String content = filteredMessage.raw().getContent().getString();

		if (!content.startsWith("/") && serverPlayerEntity != null) {
			if (!TokiPonaTasoValidator.validateMessage(content)) {
				serverPlayerEntity.sendMessage(Text.literal("o toki Inli ala lon ma ni a!").formatted(Formatting.RED), false);
				ci.cancel();
			}
		}
	}
}
