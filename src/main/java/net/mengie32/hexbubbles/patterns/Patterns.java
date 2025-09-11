package net.mengie32.hexbubbles.patterns;

import at.petrak.hexcasting.api.casting.math.HexDir;
import at.petrak.hexcasting.api.casting.castables.Action;
import at.petrak.hexcasting.api.casting.math.HexPattern;
import at.petrak.hexcasting.api.casting.ActionRegistryEntry;
import at.petrak.hexcasting.common.lib.hex.HexActions;
import net.mengie32.hexbubbles.Hexbubbles;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class Patterns {
    // Thank you to my lord and saviour Luxof for implementing hexes in a way I can comprehend

    public static void registerPatterns() {
        Hexbubbles.LOGGER.info("Registering Patterns!");

        register("write_slate", "deeeeewaqa", HexDir.EAST, new WriteSlate());
        register("bubble_spawn_empty", "qqqqqddqd", HexDir.WEST, new BubbleSpawnEmpty());
    }

    private static void register(
        String name,
        String signature,
        HexDir startDir,
        Action action
    ) {
        Registry.register(HexActions.REGISTRY, new Identifier(Hexbubbles.MOD_ID, name), new ActionRegistryEntry(HexPattern.fromAngles(signature, startDir), action));
    }
}
