package com.snowmod.init;

import com.snowmod.SnowMod;
import com.snowmod.block.SnowBlockCustom;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS,
            SnowMod.MOD_ID);

    // Snow Block
    public static final RegistryObject<Block> SNOW_BLOCK = BLOCKS.register("snow_block",
            () -> new SnowBlockCustom(BlockBehaviour.Properties.of()
                    .strength(0.2f)
                    .sound(SoundType.SNOW)
                    .requiresCorrectToolForDrops()));

    // Register block items
    public static void registerBlockItems() {
        ModItems.ITEMS.register("snow_block", () -> new BlockItem(SNOW_BLOCK.get(), new Item.Properties()));
    }

    static {
        registerBlockItems();
    }
}
