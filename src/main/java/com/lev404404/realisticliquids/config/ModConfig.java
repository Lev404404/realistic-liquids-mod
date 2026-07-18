package com.lev404404.realisticliquids.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ModConfig {
    public static class WaterConfig {
        public float transparency = 0.7f;
        public float waveHeight = 0.5f;
        public float waveSpeed = 1.0f;
        public int waterDropletSize = 2;
        public boolean enableWaves = true;
        public boolean enableParticles = true;
        public boolean enableShader = true;
    }

    public static class LavaConfig {
        public float transparency = 0.6f;
        public float emitHeight = 0.3f;
        public int emberParticleCount = 5;
        public boolean enableFlames = true;
        public boolean enableSmoke = true;
        public boolean enableShader = true;
    }

    public static WaterConfig water = new WaterConfig();
    public static LavaConfig lava = new LavaConfig();

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final File CONFIG_FILE = new File("config/realistic_liquids.json");

    public static void load() {
        try {
            if (CONFIG_FILE.exists()) {
                ConfigData data = GSON.fromJson(new FileReader(CONFIG_FILE), ConfigData.class);
                if (data.water != null) water = data.water;
                if (data.lava != null) lava = data.lava;
            } else {
                save();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void save() {
        try {
            CONFIG_FILE.getParentFile().mkdirs();
            ConfigData data = new ConfigData();
            data.water = water;
            data.lava = lava;
            FileWriter writer = new FileWriter(CONFIG_FILE);
            GSON.toJson(data, writer);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static class ConfigData {
        public WaterConfig water = new WaterConfig();
        public LavaConfig lava = new LavaConfig();
    }
}
