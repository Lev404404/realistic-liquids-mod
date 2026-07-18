package com.lev404404.realisticliquids.client.render;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;

public class WaveSimulation {
    private static Map<Long, Float> waveHeights = new HashMap<>();
    private static Map<Long, Float> waveVelocities = new HashMap<>();
    private static float damping = 0.99f;
    private static float tension = 0.25f;
    private static float spread = 0.25f;
    private static boolean initialized = false;

    public static void init() {
        initialized = true;
    }

    public static void simulateWaves(World world, BlockPos center, float strength) {
        if (!initialized) return;
        
        long key = center.asLong();
        waveHeights.put(key, strength);
        
        Map<Long, Float> newHeights = new HashMap<>(waveHeights);
        Map<Long, Float> newVelocities = new HashMap<>(waveVelocities);
        
        for (Map.Entry<Long, Float> entry : waveHeights.entrySet()) {
            long posKey = entry.getKey();
            float height = entry.getValue();
            float velocity = waveVelocities.getOrDefault(posKey, 0.0f);
            
            velocity += -tension * height;
            velocity *= damping;
            
            newVelocities.put(posKey, velocity);
            newHeights.put(posKey, height + velocity);
            
            BlockPos pos = BlockPos.fromLong(posKey);
            BlockPos[] neighbors = {
                pos.north(), pos.south(), pos.east(), pos.west()
            };
            
            for (BlockPos neighbor : neighbors) {
                long neighborKey = neighbor.asLong();
                float neighborHeight = waveHeights.getOrDefault(neighborKey, 0.0f);
                float diff = spread * (height - neighborHeight);
                newVelocities.put(neighborKey, waveVelocities.getOrDefault(neighborKey, 0.0f) + diff);
            }
        }
        
        waveHeights = newHeights;
        waveVelocities = newVelocities;
        
        Iterator<Map.Entry<Long, Float>> iterator = waveHeights.entrySet().iterator();
        while (iterator.hasNext()) {
            if (Math.abs(iterator.next().getValue()) < 0.01f) {
                iterator.remove();
            }
        }
    }

    public static float getWaveHeight(BlockPos pos) {
        return waveHeights.getOrDefault(pos.asLong(), 0.0f);
    }

    public static void reset() {
        waveHeights.clear();
        waveVelocities.clear();
    }
}
