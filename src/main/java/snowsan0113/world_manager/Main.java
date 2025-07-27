package snowsan0113.world_manager;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import snowsan0113.world_manager.command.WorldCreateCommand;
import snowsan0113.world_manager.command.WorldInfoCommand;
import snowsan0113.world_manager.listener.WorldCreateGUIListener;
import snowsan0113.world_manager.listener.WorldListGUIListener;

public final class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        //command
        getCommand("world_list_gui").setExecutor(new WorldInfoCommand());
        getCommand("world_create_gui").setExecutor(new WorldCreateCommand());

        PluginManager plm = Bukkit.getServer().getPluginManager();
        plm.registerEvents(new WorldListGUIListener(), this);
        plm.registerEvents(new WorldCreateGUIListener(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }


}
