package snowsan0113.world_manager.listener;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import snowsan0113.world_manager.manager.gui.WorldListGUI;

public class InventoryClickListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        InventoryView view = event.getView();
        HumanEntity player = event.getWhoClicked();
        ItemStack current_item = event.getCurrentItem();
        if (view.getTitle().contains("ワールド")) {
            String page = view.getTitle().replace("ワールド（", "").replace("ページ）", "");

            if (current_item != null) {
                if (current_item.getType() == Material.PLAYER_HEAD) {
                    if (event.getSlot() == 45 && !page.equalsIgnoreCase("1")) {
                        WorldListGUI.getInstance().openInventory((Player) player, Integer.parseInt(page) - 1);
                    }
                    if (event.getSlot() == 53) {
                        WorldListGUI.getInstance().openInventory((Player) player, Integer.parseInt(page));
                    }
                }
            }

        }
    }
}
