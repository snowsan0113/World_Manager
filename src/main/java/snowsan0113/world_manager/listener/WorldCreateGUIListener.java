package snowsan0113.world_manager.listener;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import snowsan0113.world_manager.Main;
import snowsan0113.world_manager.manager.gui.WorldCreateGUI;

import java.util.List;

public class WorldCreateGUIListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        HumanEntity player = event.getWhoClicked();
        InventoryView inv = event.getView();
        ItemStack current = event.getCurrentItem();
        WorldCreateGUI instance = WorldCreateGUI.getInstance((Player) player);
        int slot = event.getSlot();
        if (inv.getTitle().equalsIgnoreCase("ワールドタイプを選択してください")) {
            if (slot == 2 || slot == 4 || slot == 6) {
                instance.now_task_type=1;
                instance.select_environment = WorldCreateGUI.world_env_map.get(current.getType());

                player.sendMessage("ワールド名を30秒以内に入力してください：");
            }
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onChat(PlayerChatEvent event) {
        Player player = event.getPlayer();
        WorldCreateGUI instance = WorldCreateGUI.getInstance(player);
        List<World> world_list = Bukkit.getWorlds();
        String message = event.getMessage();
        if (instance != null) {
            if (instance.now_task_type == 1) {
                BukkitTask wait_task = new BukkitRunnable() {
                    @Override
                    public void run() {
                        player.sendMessage("30秒経過したためワールド作成が中止されました。");
                        event.setCancelled(false);
                        instance.closeInstance();
                    }
                }.runTaskLater(Main.getPlugin(Main.class), 20 * 30L);

                event.setCancelled(true);
                if (world_list.stream().noneMatch(world -> world.getName().equalsIgnoreCase(message))) {
                    player.sendMessage("ワールドを作成しています。");
                    WorldCreator world_creator = new WorldCreator(message);
                    world_creator.environment(instance.select_environment);
                    Bukkit.createWorld(world_creator);
                    player.sendMessage("ワールドを作成することができました。");
                    wait_task.cancel();
                }
                else {
                    player.sendMessage("そのワールドは存在します");
                }
            }
        }
    }

}
