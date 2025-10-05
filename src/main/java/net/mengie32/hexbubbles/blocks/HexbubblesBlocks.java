package net.mengie32.hexbubbles.blocks;

import at.petrak.hexcasting.common.lib.HexBlocks;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.mengie32.hexbubbles.Hexbubbles;
import net.mengie32.hexbubbles.blocks.circles.impeti.BlockScanningImpetus;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class HexbubblesBlocks {
    public static final BlockScanningImpetus IMPETUS_SCANNING = registerBlock("impetus/scanning", new BlockScanningImpetus(FabricBlockSettings.copy(HexBlocks.IMPETUS_EMPTY)));

    private static <T extends Block> T registerBlock(String name, T block){
        registerBlockItem(name,block);
        return Registry.register(
            Registries.BLOCK,
            new Identifier(Hexbubbles.MOD_ID, name),
            block
        );
    }

    private static Item registerBlockItem(String name, Block block){
        return Registry.register(  
            Registries.ITEM,
            new Identifier(Hexbubbles.MOD_ID, name),
            new BlockItem(block, new FabricItemSettings())
        );
    }

    public static void registerHexbubbleBlocks(){
        Hexbubbles.LOGGER.info("Registering blocks for " + Hexbubbles.MOD_ID);
    }
}
