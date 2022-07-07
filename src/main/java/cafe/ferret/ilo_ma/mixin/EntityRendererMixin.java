package cafe.ferret.ilo_ma.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/* IN THEORY, I need to do this:
 * - Mixin to EntityRenderer to add a condition for it to check if:
 *     1) the entity is a player, and
 *     2) if that player has a nickname,
 *   In which case it will display the player's nickname as the "entity.getDisplayName()".
 *   This DOES NOT break interactions elsewhere because we aren't changing the player's actual name,
 *   JUST how it displays on the name tag (Styled Nicknames handles the player list and chat well enough)
 */

@SuppressWarnings("unchecked")
@Mixin(EntityRenderer.class)
public class EntityRendererMixin<T extends Entity> {
	@Redirect(method = "render",
			at     = @At(value  = "INVOKE",
					target = "Lnet/minecraft/client/renderer/entity/EntityRenderer;renderNameTag(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/network/chat/Component;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V"))
	public void onNameTagRender(EntityRenderer<T> instance, T entity, Component text, PoseStack matrices, MultiBufferSource vertexConsumers, int light) {
		if (entity instanceof Player) {
			((EntityRendererInvoker<T>) instance).invokeRenderNameTag(entity, Component.literal("jan 123").withStyle(ChatFormatting.YELLOW), matrices, vertexConsumers, light);
		} else {
			((EntityRendererInvoker<T>) instance).invokeRenderNameTag(entity, entity.getDisplayName(), matrices, vertexConsumers, light);
		}
	}
}
