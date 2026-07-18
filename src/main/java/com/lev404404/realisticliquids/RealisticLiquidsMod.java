package com.lev404404.realisticliquids;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.item.FabricItemSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lev404404.realisticliquids.block.RealisticWaterBlock;
import com.lev404404.realisticliquids.block.RealisticLavaBlock;
import com.lev404404.realisticliquids.config.ModConfig;

public class RealisticLiquidsMod implements ModInitializer {
    public static final String MOD_ID = "realistic_liquids";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static final Block REALISTIC_WATER_BLOCK = new RealisticWaterBlock(FabricBlockSettings.copyOf(Blocks.WATER));
    public static final Block REALISTIC_LAVA_BLOCK = new RealisticLavaBlock(FabricBlockSettings.copyOf(Blocks.LAVA));
    public static final Item REALISTIC_WATER_BUCKET = new Item(new FabricItemSettings().maxCount(64));
    public static final Item REALISTIC_LAVA_BUCKET = new Item(new FabricItemSettings().maxCount(64));

    @Override
    public void onInitialize() {
        LOGGER.info("Initializing Realistic Liquids Mod...");
        ModConfig.load();
        
        Registry.register(Registries.BLOCK, new Identifier(MOD_ID, "realistic_water"), REALISTIC_WATER_BLOCK);
        Registry.register(Registries.BLOCK, new Identifier(MOD_ID, "realistic_lava"), REALISTIC_LAVA_BLOCK);
        Registry.register(Registries.ITEM, new Identifier(MOD_ID, "realistic_water_bucket"), REALISTIC_WATER_BUCKET);
        Registry.register(Registries.ITEM, new Identifier(MOD_ID, "realistic_lava_bucket"), REALISTIC_LAVA_BUCKET);
        
        LOGGER.info("Realistic Liquids Mod initialized successfully!");
    }
}
