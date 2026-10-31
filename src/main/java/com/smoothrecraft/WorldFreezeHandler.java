package com.smoothrecraft;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

/**
 * Instala o FreezeEntityRenderer uma unica vez, quando o jogador esta em um mundo.
 * Para evitar conflito, so substitui o renderer se ele for exatamente a classe
 * EntityRenderer (vanilla/OptiFine). Se outro mod ja trocou o renderer, nao mexe.
 */
public class WorldFreezeHandler {

    private boolean installed = false;
    private boolean failed = false;

    @SubscribeEvent
    public void onRenderTick(TickEvent.RenderTickEvent event) {
        if (event.phase != TickEvent.Phase.START || installed || failed || !SRConfig.freezeWorld) {
            return;
        }
        Minecraft mc = Minecraft.getMinecraft();
        if (mc.theWorld == null || mc.entityRenderer == null) {
            return;
        }
        if (mc.entityRenderer.getClass() != EntityRenderer.class) {
            failed = true;
            FMLLog.info("[Smooth Recraft] EntityRenderer ja foi substituido por outro mod (%s); "
                    + "congelamento do mundo nao sera instalado.", mc.entityRenderer.getClass().getName());
            return;
        }
        try {
            mc.entityRenderer = new FreezeEntityRenderer(mc, mc.getResourceManager());
            installed = true;
            FMLLog.info("[Smooth Recraft] Congelamento do mundo em containers instalado.");
        } catch (Throwable t) {
            failed = true;
            FMLLog.warning("[Smooth Recraft] Nao foi possivel instalar o congelamento: %s", t);
        }
    }
}
