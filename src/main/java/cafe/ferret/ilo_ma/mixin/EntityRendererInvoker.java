package cafe.ferret.ilo_ma.mixin;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(EntityRenderer.class)
public interface EntityRendererInvoker<T extends Entity> {
	@Invoker("renderLabelIfPresent")
	void invokeRenderNameTag(T entity, Text text, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light);
}
