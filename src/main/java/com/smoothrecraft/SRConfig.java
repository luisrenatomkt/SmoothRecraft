package com.smoothrecraft;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

public final class SRConfig {

    /** Liga/desliga o processamento de entrada por frame. Padrao: true. */
    public static volatile boolean enabled = true;

    /** Se true, aplica a qualquer GuiScreen; se false, apenas a telas de container. */
    public static volatile boolean affectAllScreens = false;

    /** Congela visualmente o mundo enquanto um container esta aberto (FPS alto e estavel). */
    public static volatile boolean freezeWorld = true;

    private static Configuration cfg;

    private SRConfig() {}

    public static void load(File file) {
        cfg = new Configuration(file);
        sync();
    }

    public static void sync() {
        enabled = cfg.getBoolean("enabled", Configuration.CATEGORY_GENERAL, true,
                "Processa cliques/movimentos da GUI a cada frame em vez de a cada tick (50 ms). "
              + "Nao automatiza nada e nao adiciona atrasos.");
        affectAllScreens = cfg.getBoolean("affectAllScreens", Configuration.CATEGORY_GENERAL, false,
                "Se false, so age em telas de container (inventario, bancada, baus...). "
              + "Se true, age em qualquer tela.");
        freezeWorld = cfg.getBoolean("freezeWorld", Configuration.CATEGORY_GENERAL, true,
                "Enquanto um container esta aberto, o cenario ao redor fica congelado (so visual) "
              + "e o FPS fica alto e estavel. O jogo continua rodando normalmente por tras. "
              + "Coloque false se tiver conflito com outro mod/cliente.");
        if (cfg.hasChanged()) cfg.save();
    }

    public static void setEnabled(boolean value) {
        enabled = value;
        if (cfg != null) {
            cfg.get(Configuration.CATEGORY_GENERAL, "enabled", true).set(value);
            cfg.save();
        }
    }

    public static void setFreezeWorld(boolean value) {
        freezeWorld = value;
        if (cfg != null) {
            cfg.get(Configuration.CATEGORY_GENERAL, "freezeWorld", true).set(value);
            cfg.save();
        }
    }
}
