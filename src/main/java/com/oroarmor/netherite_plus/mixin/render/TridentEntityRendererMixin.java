package com.oroarmor.netherite_plus.mixin.render;

import static com.oroarmor.netherite_plus.NetheritePlusMod.id;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import com.oroarmor.netherite_plus.item.NetheritePlusItems;

import net.minecraft.client.render.entity.TridentEntityRenderer;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.util.Identifier;

@Mixin(TridentEntityRenderer.class)
public class TridentEntityRendererMixin {

	@Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/TridentEntityRenderer;getTexture(Lnet/minecraft/entity/projectile/TridentEntity;)Lnet/minecraft/util/Identifier;"))
	public Identifier getTextureMixin(TridentEntityRenderer renderer, TridentEntity entity) {
		return entity.tridentStack.getItem() == NetheritePlusItems.NETHERITE_TRIDENT ? id("textures/entity/netherite_trident.png") : TridentEntityRenderer.TEXTURE;
	}
}
