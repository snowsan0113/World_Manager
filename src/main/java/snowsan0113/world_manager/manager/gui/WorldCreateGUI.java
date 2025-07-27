package snowsan0113.world_manager.manager.gui;

import net.minecraft.server.level.EntityPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.craftbukkit.v1_20_R1.entity.CraftPlayer;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import snowsan0113.world_manager.Main;
import snowsan0113.world_manager.util.ItemUtil;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WorldCreateGUI implements Listener {

    private static final Map<Player, WorldCreateGUI> instance_map = new HashMap<>();

    //inv
    private Player player;
    private Inventory world_type_inv;
    private Inventory world_name_inv;

    //taskdata
    public World.Environment select_environment;
    public int now_task_type = 0;

    //util
    public static final Map<Material, World.Environment> world_env_map = Map.of(
            Material.GRASS_BLOCK, World.Environment.NORMAL,
            Material.NETHERRACK, World.Environment.NETHER,
            Material.END_STONE, World.Environment.THE_END,
            Material.BARRIER, World.Environment.CUSTOM
    );

    private WorldCreateGUI(Player player) {
        this.player = player;
        buildInventory();

        PluginManager plm = Bukkit.getServer().getPluginManager();
        plm.registerEvents(this, Main.getPlugin(Main.class));
    }

    public static WorldCreateGUI getInstance(Player player) {
        if (!instance_map.containsKey(player)) {
            instance_map.put(player, new WorldCreateGUI(player));
        }

        return instance_map.get(player);
    }

    private void buildInventory() {
        world_type_inv = Bukkit.createInventory(null, 9*3, "ワールドタイプを選択してください");
        world_type_inv.setItem(2, new ItemUtil(Material.GRASS_BLOCK).setDisplayLore("ノーマル", null).build());
        world_type_inv.setItem(4, new ItemUtil(Material.NETHER_BRICK).setDisplayLore("ネザー", null).build());
        world_type_inv.setItem(6, new ItemUtil(Material.END_STONE).setDisplayLore("エンド", null).build());
    }

    public void openInventory() {
        player.closeInventory();

        new BukkitRunnable() {
            @Override
            public void run() {
                if (now_task_type == 0) {
                    player.openInventory(world_type_inv);
                }
            }
        }.runTaskLater(Main.getPlugin(Main.class), 10L);
    }

    public void closeInstance() {
        instance_map.remove(player);
    }

}
