package net.mengie32.hexbubbles.blocks;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

import org.jetbrains.annotations.Nullable;

import com.mojang.datafixers.util.Pair;

import at.petrak.hexcasting.api.HexAPI;
import at.petrak.hexcasting.api.block.circle.BlockAbstractImpetus;
import at.petrak.hexcasting.common.lib.HexCreativeTabs;
import at.petrak.hexcasting.common.lib.HexItems;
import net.mengie32.hexbubbles.blocks.circles.impeti.BlockScanningImpetus;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;

public class HexbubbleBlocks {    
    private static final Map<Identifier, Block> BLOCKS = new LinkedHashMap<>();
    private static final Map<Identifier, Pair<Block, Item.Settings>> BLOCK_ITEMS = new LinkedHashMap<>();
    private static final Map<ItemGroup, List<Block>> BLOCK_TABS = new LinkedHashMap<>();

    public static void registerBlocks(BiConsumer<Block, Identifier> registry) {
        for (var entry : BLOCKS.entrySet()) {
            registry.accept(entry.getValue(), entry.getKey());
        }
    }

    public static void registerBlockItems(BiConsumer<Item, Identifier> registry) {
        for (var entry : BLOCK_ITEMS.entrySet()) {
            registry.accept(new BlockItem(entry.getValue().getFirst(), entry.getValue().getSecond()), entry.getKey());
        }
    }

     private static <T extends Block> T blockNoItem(String name, T block) {
        var old = BLOCKS.put(HexAPI.modLoc(name), block);
        if (old != null) {
            throw new IllegalArgumentException("Typo? Duplicate id " + name);
        }
        return block;
    }
    private static <T extends Block> T blockItem(String name, T block) {
        return blockItem(name, block, HexItems.props(), HexCreativeTabs.HEX);
    }

    private static <T extends Block> T blockItem(String name, T block, Item.Settings props, @Nullable ItemGroup tab) {
        blockNoItem(name, block);
        var old = BLOCK_ITEMS.put(HexAPI.modLoc(name), new Pair<>(block, props));
        if (old != null) {
            throw new IllegalArgumentException("Typo? Duplicate id " + name);
        }
        if (tab != null) {
            BLOCK_TABS.computeIfAbsent(tab, t -> new ArrayList<>()).add(block);
        }
        return block;
    }


    public static final BlockScanningImpetus BLOCK_SCANNING_IMPETUS = blockItem(
        "impetus/scanning",
        new BlockScanningImpetus(
            AbstractBlock.Settings
            .copy(Blocks.DEEPSLATE_TILES)
            .strength(4f, 4f)
            .pistonBehavior(PistonBehavior.BLOCK)
            .luminance(bs -> bs.get(BlockAbstractImpetus.ENERGIZED) ? 15 : 0))
        );
}
