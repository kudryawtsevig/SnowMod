package com.snowmod;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.snowmod.init.ModBlocks;
import com.snowmod.init.ModItems;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(SnowMod.MOD_ID)
public class SnowMod {
    public static final String MOD_ID = "snowmod";
    public static final Logger LOGGER = LoggerFactory.getLogger(SnowMod.class);

    public SnowMod(FMLJavaModLoadingContext context) {
        // Register items and blocks
        ModItems.ITEMS.register(context.getModBusGroup());
        ModBlocks.BLOCKS.register(context.getModBusGroup());

        LOGGER.info("SnowMod initialized!");
    }
}
