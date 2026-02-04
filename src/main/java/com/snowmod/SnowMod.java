package com.snowmod;

import com.snowmod.init.ModBlocks;
import com.snowmod.init.ModItems;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(SnowMod.MOD_ID)
public class SnowMod {
    public static final String MOD_ID = "snowmod";
    public static final Logger LOGGER = LoggerFactory.getLogger(SnowMod.class);

    public SnowMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register items and blocks
        ModItems.ITEMS.register(modEventBus);
        ModBlocks.BLOCKS.register(modEventBus);

        LOGGER.info("SnowMod initialized!");
    }
}
