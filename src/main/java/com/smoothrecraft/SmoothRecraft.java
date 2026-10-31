package com.smoothrecraft;

import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(
    modid = SmoothRecraft.MODID,
    name = SmoothRecraft.NAME,
    version = SmoothRecraft.VERSION,
    acceptedMinecraftVersions = "[1.8.9]",
    clientSideOnly = true,
    acceptableRemoteVersions = "*"
)
public class SmoothRecraft {

    public static final String MODID = "smoothrecraft";
    public static final String NAME = "Smooth Recraft";
    public static final String VERSION = "1.0.0";

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        SRConfig.load(event.getSuggestedConfigurationFile());
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        FMLCommonHandler.instance().bus().register(new FrameInputHandler());
        FMLCommonHandler.instance().bus().register(new WorldFreezeHandler());
        ClientCommandHandler.instance.registerCommand(new SmoothRecraftCommand());
        FMLLog.info("[%s] Carregado. Entrada por frame: %s | Congelar mundo: %s",
                NAME, SRConfig.enabled ? "ATIVADO" : "desativado",
                SRConfig.freezeWorld ? "ATIVADO" : "desativado");
    }
}
