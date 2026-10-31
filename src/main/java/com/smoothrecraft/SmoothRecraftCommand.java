package com.smoothrecraft;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

/**
 * /smoothrecraft [on|off|toggle|status]
 * /smoothrecraft freeze [on|off|toggle]
 * Comando 100% client-side.
 */
public class SmoothRecraftCommand extends CommandBase {

    @Override
    public String getCommandName() {
        return "smoothrecraft";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/smoothrecraft [on|off|toggle|status] | /smoothrecraft freeze [on|off|toggle]";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        String a = args.length > 0 ? args[0].toLowerCase() : "toggle";

        if (a.equals("freeze")) {
            String b = args.length > 1 ? args[1].toLowerCase() : "toggle";
            switch (b) {
                case "on":  SRConfig.setFreezeWorld(true);  break;
                case "off": SRConfig.setFreezeWorld(false); break;
                default:    SRConfig.setFreezeWorld(!SRConfig.freezeWorld); break;
            }
        } else {
            switch (a) {
                case "on":     SRConfig.setEnabled(true);  break;
                case "off":    SRConfig.setEnabled(false); break;
                case "status": break;
                default:       SRConfig.setEnabled(!SRConfig.enabled); break;
            }
        }

        sender.addChatMessage(new ChatComponentText(
                EnumChatFormatting.AQUA + "[Smooth Recraft] "
                + EnumChatFormatting.WHITE + "Entrada por frame: " + state(SRConfig.enabled)
                + EnumChatFormatting.WHITE + " | Congelar mundo: " + state(SRConfig.freezeWorld)));
    }

    private static String state(boolean on) {
        return on ? EnumChatFormatting.GREEN + "ATIVADO" : EnumChatFormatting.RED + "DESATIVADO";
    }
}
