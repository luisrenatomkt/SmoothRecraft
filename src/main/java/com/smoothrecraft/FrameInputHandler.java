package com.smoothrecraft;

import java.io.IOException;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

/**
 * Esvazia a fila de eventos de entrada da GUI no inicio de cada frame.
 *
 * Vanilla 1.8.9 so chama GuiScreen.handleInput() dentro de Minecraft.runTick()
 * (20x/s). Aqui chamamos o MESMO metodo vanilla com mais frequencia. Como ele
 * consome a fila (Mouse.next / Keyboard.next), o tick seguinte encontra a fila
 * vazia: nenhum evento e processado duas vezes, criado, atrasado, agrupado ou
 * alterado. Cada clique mantem posicao e botao originais.
 */
public class FrameInputHandler {

    private boolean failed = false;

    @SubscribeEvent
    public void onRenderTick(TickEvent.RenderTickEvent event) {
        if (event.phase != TickEvent.Phase.START || failed || !SRConfig.enabled) {
            return;
        }

        Minecraft mc = Minecraft.getMinecraft();
        GuiScreen screen = mc.currentScreen;
        if (screen == null) {
            return;
        }
        if (!SRConfig.affectAllScreens && !(screen instanceof GuiContainer)) {
            return;
        }
        if (!Mouse.isCreated() || !Keyboard.isCreated()) {
            return;
        }

        try {
            screen.handleInput();
        } catch (IOException e) {
            failed = true;
            FMLLog.warning("[Smooth Recraft] IOException em handleInput; "
                    + "desativado nesta sessao: %s", e);
        }
    }
}
