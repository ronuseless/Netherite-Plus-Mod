package com.oroarmor.netherite_plus.config;

import java.io.File;
import java.util.List;

import com.google.common.collect.ImmutableList;
import com.oroarmor.netherite_plus.NetheritePlusMod;
import com.oroarmor.util.config.Config;
import com.oroarmor.util.config.ConfigItem;
import com.oroarmor.util.config.ConfigItemGroup;

import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;

public final class NetheritePlusConfig extends Config {
	public static class ANVIL extends ConfigItemGroup {
		public static final ConfigItem<Double> XP_REDUCTION = new ConfigItem<>("xp_reduction", 0.5, "config.netherite_plus.anvil.xp_redcution");

		public static final List<ConfigItem<?>> OPTIONS = ImmutableList.of(XP_REDUCTION);

		public ANVIL() {
			super(OPTIONS, "anvil");
		}

	}

	public static class DAMAGE extends ConfigItemGroup {
		public static final ConfigItem<Double> BOW_DAMAGE_ADDITION = new ConfigItem<>("bow_damage_addition", 0d, "config.netherite_plus.damage.bow_damage_addition");
		public static final ConfigItem<Double> BOW_DAMAGE_MULTIPLIER = new ConfigItem<>("bow_damage_multiplier", 1d, "config.netherite_plus.damage.bow_damage_multiplier");

		public static final ConfigItem<Double> CROSSBOW_DAMAGE_ADDITION = new ConfigItem<>("crossbow_damage_addition", 0d, "config.netherite_plus.damage.crossbow_damage_addition");
		public static final ConfigItem<Double> CROSSBOW_DAMAGE_MULTIPLIER = new ConfigItem<>("crossbow_damage_multiplier", 1d, "config.netherite_plus.damage.crossbow_damage_multiplier");

		public static final ConfigItem<Double> TRIDENT_DAMAGE_ADDITION = new ConfigItem<>("trident_damage_addition", 0d, "config.netherite_plus.damage.trident_damage_addition");
		public static final ConfigItem<Double> TRIDENT_DAMAGE_MULTIPLIER = new ConfigItem<>("trident_damage_multiplier", 1d, "config.netherite_plus.damage.trident_damage_multiplier");

		public static final List<ConfigItem<?>> OPTIONS = ImmutableList.of(BOW_DAMAGE_ADDITION, BOW_DAMAGE_MULTIPLIER, CROSSBOW_DAMAGE_ADDITION, CROSSBOW_DAMAGE_MULTIPLIER, TRIDENT_DAMAGE_ADDITION, TRIDENT_DAMAGE_MULTIPLIER);

		public DAMAGE() {
			super(OPTIONS, "damage");
		}

	}

	public static class DURABILITIES extends ConfigItemGroup {
		public static final ConfigItem<Integer> BOW_DURABILITY = new ConfigItem<>("bow", 768, "config.netherite_plus.durabilities.bow");

		public static final ConfigItem<Integer> CROSSBOW_DURABILITY = new ConfigItem<>("crossbow", 652, "config.netherite_plus.durabilities.crossbow");

		public static final ConfigItem<Integer> ELYTRA_DURABILITY = new ConfigItem<>("elytra", 864, "config.netherite_plus.durabilities.elytra");

		public static final ConfigItem<Integer> FISHING_ROD_DURABILITY = new ConfigItem<>("fishing_rod", 128, "config.netherite_plus.durabilities.fishing_rod");

		public static final ConfigItem<Integer> SHIELD_DURABILITY = new ConfigItem<>("shield", 672, "config.netherite_plus.durabilities.shield");

		public static final ConfigItem<Integer> TRIDENT_DURABILITY = new ConfigItem<>("trident", 500, "config.netherite_plus.durabilities.trident");

		public static final List<ConfigItem<?>> OPTIONS = ImmutableList.of(ELYTRA_DURABILITY, FISHING_ROD_DURABILITY, SHIELD_DURABILITY, BOW_DURABILITY, CROSSBOW_DURABILITY, TRIDENT_DURABILITY);

		public DURABILITIES() {
			super(OPTIONS, "durabilities");
		}

	}

	public static class ENABLED extends ConfigItemGroup {
		public static final ConfigItem<Boolean> ENABLED_CONFIG_PRINT = new ConfigItem<>("config_debug_print", false, "config.netherite_plus.enabled.config_debug_print");

		public static final ConfigItem<Boolean> ENABLED_ANVIL = new ConfigItem<>("anvils", true, "config.netherite_plus.enabled.anvils");

		public static final ConfigItem<Boolean> ENABLED_BOWS_AND_CROSSBOWS = new ConfigItem<>("bows_and_crossbows", true, "config.netherite_plus.enabled.bows_crossbows");

		public static final ConfigItem<Boolean> ENABLED_ELYTRA = new ConfigItem<>("elytra", true, "config.netherite_plus.enabled.elytra");

		public static final ConfigItem<Boolean> ENABLED_FAKE_NETHERITE_BLOCKS = new ConfigItem<>("fake_netherite_blocks", true, "config.netherite_plus.enabled.fake_netherite_blocks");

		public static final ConfigItem<Boolean> ENABLED_FISHING_ROD = new ConfigItem<>("fishing_rod", true, "config.netherite_plus.enabled.fishing_rod");

		public static final ConfigItem<Boolean> ENABLED_HORSE_ARMOR = new ConfigItem<>("horse_armor", true, "config.netherite_plus.enabled.horse_armor");

		public static final ConfigItem<Boolean> ENABLED_SHIELDS = new ConfigItem<>("shields", true, "config.netherite_plus.enabled.shields");

		public static final ConfigItem<Boolean> ENABLED_SHULKER_BOXES = new ConfigItem<>("shulker_boxes", true, "config.netherite_plus.enabled.shulker_boxes");

		public static final ConfigItem<Boolean> ENABLED_TRIDENT = new ConfigItem<>("trident", true, "config.netherite_plus.enabled.trident");

		public static ConfigItem<Boolean> ENABLED_BEACON = new ConfigItem<>("beacon", true, "config.netherite_plus.enabled.beacon");

		public static final List<ConfigItem<?>> OPTIONS = ImmutableList.of(ENABLED_SHULKER_BOXES, ENABLED_ELYTRA, ENABLED_SHIELDS, ENABLED_BOWS_AND_CROSSBOWS, ENABLED_FISHING_ROD, ENABLED_HORSE_ARMOR, ENABLED_FAKE_NETHERITE_BLOCKS, ENABLED_ANVIL, ENABLED_CONFIG_PRINT, ENABLED_TRIDENT, ENABLED_BEACON);

		public ENABLED() {
			super(OPTIONS, "enabled");
		}
	}

	public static class GRAPHICS extends ConfigItemGroup {

		public static final ConfigItem<Double> LAVA_VISION_DISTANCE = new ConfigItem<>("lava_vision_distance", 0.25, "config.netherite_plus.graphics.lava_vision", NetheritePlusConfig::createLavaVisionUpdatePacket);
		public static final List<ConfigItem<?>> OPTIONS = ImmutableList.of(LAVA_VISION_DISTANCE);

		public GRAPHICS() {
			super(OPTIONS, "graphics");
		}

	}

	private static final String CONFIG_FILE_NAME = "netherite_plus.json";

	private static final List<ConfigItemGroup> CONFIGS = ImmutableList.of(new ENABLED(), new DURABILITIES(), new DAMAGE(), new ANVIL(), new GRAPHICS());

	public NetheritePlusConfig() {
		super(CONFIGS, new File(FabricLoader.getInstance().getConfigDir().toFile(), CONFIG_FILE_NAME));
	}

	public static void createLavaVisionUpdatePacket(ConfigItem<Double> configItem) {
		PacketByteBuf passedData = new PacketByteBuf(Unpooled.buffer());
		passedData.writeDouble(configItem.getValue());

		NetheritePlusMod.CONNECTED_CLIENTS.forEach(_player -> ServerSidePacketRegistry.INSTANCE.sendToPlayer(_player, NetheritePlusMod.id("lava_vision_packet"), passedData));

	}

	public static void createLavaVisionUpdatePacket(PlayerEntity player) {
		PacketByteBuf passedData = new PacketByteBuf(Unpooled.buffer());
		passedData.writeDouble(GRAPHICS.LAVA_VISION_DISTANCE.getValue());
		ServerSidePacketRegistry.INSTANCE.sendToPlayer(player, NetheritePlusMod.id("lava_vision_packet"), passedData);

	}

}
