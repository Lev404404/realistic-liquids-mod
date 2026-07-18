package com.lev404404.realisticliquids.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.Environment;
import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;

import com.lev404404.realisticliquids.RealisticLiquidsMod;
import com.lev404404.realisticliquids.client.shader.ShaderManager;
import com.lev404404.realisticliquids.client.render.WaveSimulation;

@Environment(EnvType.CLIENT)
public class RealisticLiquidsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ShaderManager.init();
        WaveSimulation.init();
        
        ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> {
            return 0x3F76E4;
        }, RealisticLiquidsMod.REALISTIC_WATER_BLOCK);
        
        ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> {
            return 0xFF6D00;
        }, RealisticLiquidsMod.REALISTIC_LAVA_BLOCK);
    }
}
