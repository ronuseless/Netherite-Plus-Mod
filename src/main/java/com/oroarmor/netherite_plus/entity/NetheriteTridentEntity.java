package com.oroarmor.netherite_plus.entity;

import com.oroarmor.netherite_plus.config.NetheritePlusConfig;
import com.oroarmor.netherite_plus.item.NetheritePlusItems;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class NetheriteTridentEntity extends TridentEntity {

	public NetheriteTridentEntity(EntityType<? extends TridentEntity> entityType, World world) {
		super(entityType, world);
		tridentStack = new ItemStack(NetheritePlusItems.NETHERITE_TRIDENT);
	}

	@Environment(EnvType.CLIENT)
	public NetheriteTridentEntity(World world, double x, double y, double z) {
		super(world, x, y, z);
		tridentStack = new ItemStack(NetheritePlusItems.NETHERITE_TRIDENT);
	}

	public NetheriteTridentEntity(World world, LivingEntity owner, ItemStack stack) {
		super(world, owner, stack);
		tridentStack = stack;
	}

	@Override
	protected void onEntityHit(EntityHitResult entityHitResult) {
		Entity entity = entityHitResult.getEntity();
		float f = 8.0F;
		if (entity instanceof LivingEntity) {
			LivingEntity livingEntity = (LivingEntity) entity;
			f += EnchantmentHelper.getAttackDamage(tridentStack, livingEntity.getGroup());
		}

		f = (float) (f * NetheritePlusConfig.DAMAGE.TRIDENT_DAMAGE_MULTIPLIER.getValue() + NetheritePlusConfig.DAMAGE.TRIDENT_DAMAGE_ADDITION.getValue());

		Entity entity2 = getOwner();
		DamageSource damageSource = DamageSource.trident(this, entity2 == null ? this : entity2);
		dealtDamage = true;
		SoundEvent soundEvent = SoundEvents.ITEM_TRIDENT_HIT;
		if (entity.damage(damageSource, f)) {
			if (entity.getType() == EntityType.ENDERMAN) {
				return;
			}

			if (entity instanceof LivingEntity) {
				LivingEntity livingEntity2 = (LivingEntity) entity;
				if (entity2 instanceof LivingEntity) {
					EnchantmentHelper.onUserDamaged(livingEntity2, entity2);
					EnchantmentHelper.onTargetDamaged((LivingEntity) entity2, livingEntity2);
				}

				onHit(livingEntity2);
			}
		}

		this.setVelocity(getVelocity().multiply(-0.01D, -0.1D, -0.01D));
		float g = 1.0F;
		if (world instanceof ServerWorld && world.isThundering() && EnchantmentHelper.hasChanneling(tridentStack)) {
			BlockPos blockPos = entity.getBlockPos();
			if (world.isSkyVisible(blockPos)) {
				LightningEntity lightningEntity = EntityType.LIGHTNING_BOLT.create(world);
				lightningEntity.refreshPositionAfterTeleport(Vec3d.ofBottomCenter(blockPos));
				lightningEntity.setChanneler(entity2 instanceof ServerPlayerEntity ? (ServerPlayerEntity) entity2 : null);
				world.spawnEntity(lightningEntity);
				soundEvent = SoundEvents.ITEM_TRIDENT_THUNDER;
				g = 5.0F;
			}
		}

		playSound(soundEvent, g, 1.0F);
	}

}
