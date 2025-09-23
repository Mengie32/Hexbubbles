package net.mengie32.hexbubbles;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.fabricmc.api.ModInitializer;
import net.mengie32.hexbubbles.blocks.HexbubbleBlocks;
import net.mengie32.hexbubbles.entity.ModEntities;
import net.mengie32.hexbubbles.patterns.Patterns;

public class Hexbubbles implements ModInitializer {
	public static final String MOD_ID = "hexbubbles";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		LOGGER.info("Hello Fabric world from Hexbubbles!");
		Patterns.registerPatterns();
		ModEntities.registerModEntities();
	}
}