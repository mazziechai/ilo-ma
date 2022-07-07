package cafe.ferret.ilo_ma.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(EntityRenderer.class)
public interface EntityRendererInvoker<T extends Entity> {
	@Invoker("renderNameTag")
	void invokeRenderNameTag(T entity, Component text, PoseStack matrices, MultiBufferSource vertexConsumers, int light);
}
