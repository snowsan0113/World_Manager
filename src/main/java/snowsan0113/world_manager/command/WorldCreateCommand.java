package snowsan0113.world_manager.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import snowsan0113.world_manager.manager.gui.WorldCreateGUI;

public class WorldCreateCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender send, Command cmd, String s, String[] args) {
        if (cmd.getName().equalsIgnoreCase("world_create_gui")) {
            WorldCreateGUI.getInstance((Player) send).openInventory();
        }
        return false;
    }

}
