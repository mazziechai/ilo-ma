package cafe.ferret.ilo_ma.mixin;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
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
			at = @At(value = "INVOKE",
					target = "Lnet/minecraft/client/render/entity/EntityRenderer;renderLabelIfPresent(Lnet/minecraft/entity/Entity;Lnet/minecraft/text/Text;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V"))
	public void onNameTagRender(EntityRenderer<T> instance, T entity, Text text, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int light) {
		if (entity instanceof PlayerEntity) {
			((EntityRendererInvoker<T>) instance).invokeRenderNameTag(entity, Text.literal("jan 123").formatted(Formatting.YELLOW), matrixStack, vertexConsumerProvider, light);
		} else {
			((EntityRendererInvoker<T>) instance).invokeRenderNameTag(entity, entity.getDisplayName(), matrixStack, vertexConsumerProvider, light);
		}
	}
}
