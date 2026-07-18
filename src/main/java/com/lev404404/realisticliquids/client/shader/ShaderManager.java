package com.lev404404.realisticliquids.client.shader;

import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL11;
import com.lev404404.realisticliquids.RealisticLiquidsMod;

public class ShaderManager {
    private static int waterShaderProgram = -1;
    private static int lavaShaderProgram = -1;
    private static boolean initialized = false;

    public static void init() {
        if (initialized) return;
        initialized = true;
        
        try {
            waterShaderProgram = createShaderProgram(
                getWaterVertexShader(),
                getWaterFragmentShader()
            );
            
            lavaShaderProgram = createShaderProgram(
                getLavaVertexShader(),
                getLavaFragmentShader()
            );
            
            RealisticLiquidsMod.LOGGER.info("Shaders initialized successfully!");
        } catch (Exception e) {
            RealisticLiquidsMod.LOGGER.error("Failed to initialize shaders", e);
        }
    }

    private static String getWaterVertexShader() {
        return "#version 120\n" +
               "varying vec2 texCoord;\n" +
               "varying float depth;\n" +
               "uniform float time;\n" +
               "uniform float waveHeight;\n" +
               "\n" +
               "void main() {\n" +
               "    texCoord = gl_MultiTexCoord0.st;\n" +
               "    depth = gl_Vertex.y;\n" +
               "    vec4 pos = gl_Vertex;\n" +
               "    pos.y += sin(texCoord.x * 10.0 + time * 0.5) * waveHeight * 0.1;\n" +
               "    pos.y += cos(texCoord.y * 10.0 + time * 0.3) * waveHeight * 0.08;\n" +
               "    gl_Position = gl_ModelViewProjectionMatrix * pos;\n" +
               "}";
    }

    private static String getWaterFragmentShader() {
        return "#version 120\n" +
               "uniform sampler2D texture;\n" +
               "uniform float time;\n" +
               "uniform float transparency;\n" +
               "varying vec2 texCoord;\n" +
               "varying float depth;\n" +
               "\n" +
               "void main() {\n" +
               "    vec2 wave = texCoord + vec2(sin(texCoord.y * 10.0 + time) * 0.05, cos(texCoord.x * 10.0 + time) * 0.05);\n" +
               "    vec4 texColor = texture2D(texture, mod(wave, 1.0));\n" +
               "    vec3 waterColor = vec3(0.3, 0.6, 1.0);\n" +
               "    gl_FragColor = vec4(waterColor, transparency) * texColor;\n" +
               "}";
    }

    private static String getLavaVertexShader() {
        return "#version 120\n" +
               "varying vec2 texCoord;\n" +
               "varying float intensity;\n" +
               "uniform float time;\n" +
               "uniform float emitHeight;\n" +
               "\n" +
               "void main() {\n" +
               "    texCoord = gl_MultiTexCoord0.st;\n" +
               "    intensity = gl_Vertex.y;\n" +
               "    vec4 pos = gl_Vertex;\n" +
               "    pos.y += sin(texCoord.x * 8.0 + time * 1.0) * emitHeight * 0.15;\n" +
               "    gl_Position = gl_ModelViewProjectionMatrix * pos;\n" +
               "}";
    }

    private static String getLavaFragmentShader() {
        return "#version 120\n" +
               "uniform sampler2D texture;\n" +
               "uniform float time;\n" +
               "uniform float transparency;\n" +
               "varying vec2 texCoord;\n" +
               "varying float intensity;\n" +
               "\n" +
               "void main() {\n" +
               "    vec2 flow = texCoord + vec2(sin(texCoord.y * 8.0 + time * 2.0) * 0.08, time * 0.2);\n" +
               "    vec4 texColor = texture2D(texture, mod(flow, 1.0));\n" +
               "    vec3 lavaColor = vec3(1.0, 0.4, 0.0);\n" +
               "    float glow = sin(time) * 0.3 + 0.7;\n" +
               "    gl_FragColor = vec4(lavaColor * glow, transparency) * texColor;\n" +
               "}";
    }

    private static int createShaderProgram(String vertexSource, String fragmentSource) {
        int vertexShader = GL20.glCreateShader(GL20.GL_VERTEX_SHADER);
        GL20.glShaderSource(vertexShader, vertexSource);
        GL20.glCompileShader(vertexShader);
        
        if (GL20.glGetShaderi(vertexShader, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
            String error = GL20.glGetShaderInfoLog(vertexShader);
            throw new RuntimeException("Vertex shader compilation failed: " + error);
        }
        
        int fragmentShader = GL20.glCreateShader(GL20.GL_FRAGMENT_SHADER);
        GL20.glShaderSource(fragmentShader, fragmentSource);
        GL20.glCompileShader(fragmentShader);
        
        if (GL20.glGetShaderi(fragmentShader, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
            String error = GL20.glGetShaderInfoLog(fragmentShader);
            throw new RuntimeException("Fragment shader compilation failed: " + error);
        }
        
        int program = GL20.glCreateProgram();
        GL20.glAttachShader(program, vertexShader);
        GL20.glAttachShader(program, fragmentShader);
        GL20.glLinkProgram(program);
        
        if (GL20.glGetProgrami(program, GL20.GL_LINK_STATUS) == GL11.GL_FALSE) {
            String error = GL20.glGetProgramInfoLog(program);
            throw new RuntimeException("Shader program linking failed: " + error);
        }
        
        GL20.glDeleteShader(vertexShader);
        GL20.glDeleteShader(fragmentShader);
        
        return program;
    }

    public static int getWaterShaderProgram() {
        return waterShaderProgram;
    }

    public static int getLavaShaderProgram() {
        return lavaShaderProgram;
    }

    public static void useWaterShader() {
        if (waterShaderProgram != -1) {
            GL20.glUseProgram(waterShaderProgram);
        }
    }

    public static void useLavaShader() {
        if (lavaShaderProgram != -1) {
            GL20.glUseProgram(lavaShaderProgram);
        }
    }

    public static void stopShader() {
        GL20.glUseProgram(0);
    }
}
