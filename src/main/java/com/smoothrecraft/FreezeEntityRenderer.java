package com.smoothrecraft;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.shader.Framebuffer;
import net.minecraftforge.fml.common.FMLLog;

/**
 * Enquanto um container (inventario, bancada, bau...) esta aberto, o mundo
 * deixa de ser renderizado a cada frame. No primeiro frame com o container
 * aberto o mundo e renderizado normalmente e uma copia (snapshot) da imagem e
 * guardada; nos frames seguintes essa copia e apenas desenhada como fundo.
 *
 * Isto e so visual: o jogo (ticks, rede, servidor) continua rodando normal.
 * Nenhum clique ou item e tocado.
 */
public class FreezeEntityRenderer extends EntityRenderer {

    private final Minecraft minecraft;
    private Framebuffer snapshot;
    private boolean hasSnapshot = false;
    private boolean broken = false;

    public FreezeEntityRenderer(Minecraft mc, IResourceManager resourceManager) {
        super(mc, resourceManager);
        this.minecraft = mc;
    }

    private boolean shouldFreeze() {
        return !broken
                && SRConfig.freezeWorld
                && minecraft.theWorld != null
                && minecraft.currentScreen instanceof GuiContainer
                && !minecraft.gameSettings.anaglyph
                && OpenGlHelper.isFramebufferEnabled();
    }

    private boolean sizeChanged() {
        return snapshot == null
                || snapshot.framebufferWidth != minecraft.displayWidth
                || snapshot.framebufferHeight != minecraft.displayHeight;
    }

    @Override
    public void renderWorld(float partialTicks, long finishTimeNano) {
        if (!shouldFreeze()) {
            hasSnapshot = false;
            super.renderWorld(partialTicks, finishTimeNano);
            return;
        }

        if (!hasSnapshot || sizeChanged()) {
            // Frame "real": renderiza o mundo e guarda a imagem.
            super.renderWorld(partialTicks, finishTimeNano);
            try {
                capture();
                hasSnapshot = true;
            } catch (Throwable t) {
                fail(t);
            }
            return;
        }

        // Frames congelados: so desenha a copia.
        try {
            drawSnapshot();
        } catch (Throwable t) {
            fail(t);
            super.renderWorld(partialTicks, finishTimeNano);
        }
    }

    private void fail(Throwable t) {
        broken = true;
        hasSnapshot = false;
        FMLLog.warning("[Smooth Recraft] Falha no congelamento do mundo; "
                + "desativado nesta sessao: %s", t);
        try {
            minecraft.getFramebuffer().bindFramebuffer(true);
        } catch (Throwable ignored) {
        }
    }

    private void capture() {
        int w = minecraft.displayWidth;
        int h = minecraft.displayHeight;
        if (snapshot == null) {
            snapshot = new Framebuffer(w, h, false);
            snapshot.setFramebufferFilter(9728); // GL_NEAREST
        } else if (sizeChanged()) {
            snapshot.createBindFramebuffer(w, h);
        }
        blit(minecraft.getFramebuffer(), snapshot, w, h);
    }

    private void drawSnapshot() {
        int w = minecraft.displayWidth;
        int h = minecraft.displayHeight;
        blit(snapshot, minecraft.getFramebuffer(), w, h);
        GlStateManager.clear(256); // limpa so o depth buffer
    }

    /** Copia "from" para "to" desenhando um quad de tela cheia (mesma tecnica do vanilla). */
    private void blit(Framebuffer from, Framebuffer to, int w, int h) {
        GlStateManager.matrixMode(5889);
        GlStateManager.pushMatrix();
        GlStateManager.matrixMode(5888);
        GlStateManager.pushMatrix();

        to.bindFramebuffer(true);
        from.framebufferRender(w, h);
        minecraft.getFramebuffer().bindFramebuffer(true);

        GlStateManager.matrixMode(5889);
        GlStateManager.popMatrix();
        GlStateManager.matrixMode(5888);
        GlStateManager.popMatrix();

        GlStateManager.enableAlpha();
        GlStateManager.alphaFunc(516, 0.1F);
        GlStateManager.enableDepth();
        GlStateManager.depthMask(true);
        GlStateManager.disableBlend();
        GlStateManager.enableCull();
        GlStateManager.enableTexture2D();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    }
}
