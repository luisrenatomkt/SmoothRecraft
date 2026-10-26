package com.smoothrecraft;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

/** /smoothrecraft [on|off|toggle|status] : comando 100% client-side. */
public class SmoothRecraftCommand extends CommandBase {

    @Override
    public String getCommandName() {
        return "smoothrecraft";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/smoothrecraft [on|off|toggle|status]";
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
        switch (a) {
            case "on":     SRConfig.setEnabled(true);  break;
            case "off":    SRConfig.setEnabled(false); break;
            case "status": break;
            default:       SRConfig.setEnabled(!SRConfig.enabled); break;
        }
        boolean on = SRConfig.enabled;
        sender.addChatMessage(new ChatComponentText(
                EnumChatFormatting.AQUA + "[Smooth Recraft] " +
                (on ? EnumChatFormatting.GREEN + "ATIVADO" : EnumChatFormatting.RED + "DESATIVADO")));
    }
}
