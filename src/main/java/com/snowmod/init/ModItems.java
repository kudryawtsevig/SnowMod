package com.snowmod.init;

import com.snowmod.SnowMod;
import com.snowmod.item.SnowflakeItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, SnowMod.MOD_ID);

    // Snowflake Item
    public static final RegistryObject<Item> SNOWFLAKE = ITEMS.register("snowflake",
            () -> new SnowflakeItem(new Item.Properties().stacksTo(16)));
}
