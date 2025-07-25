package snowsan0113.world_manager;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import snowsan0113.world_manager.command.WorldInfoCommand;
import snowsan0113.world_manager.listener.InventoryClickListener;
import snowsan0113.world_manager.manager.gui.WorldListGUI;

public final class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        //command
        getCommand("world_list_gui").setExecutor(new WorldInfoCommand());

        //listener
        PluginManager plm = getServer().getPluginManager();
        plm.registerEvents(new InventoryClickListener(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }


}
