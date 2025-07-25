package snowsan0113.world_manager.manager.gui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import snowsan0113.world_manager.util.ItemUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class WorldListGUI {

    //インスタンス
    private static final WorldListGUI instance = new WorldListGUI();

    //gui
    private final int SLOT_SIZE;
    private final List<Inventory> inventoryList;

    private WorldListGUI() {
        this.SLOT_SIZE = 9*6;
        this.inventoryList = new ArrayList<>();

        buildInventory();
    }

    public static WorldListGUI getInstance() {
        return instance;
    }

    private void buildInventory() {
        int page = 1; //現在のページ
        Inventory inv = Bukkit.createInventory(null, SLOT_SIZE, "ワールド（1ページ）");
        inventoryList.add(inv);
        List<World> world_list = Bukkit.getWorlds(); //リスト
        int now_inv_slot = 0; //現在アイテムを置いてるスロット

        int CAN_PUT_SLOT = SLOT_SIZE - 9; //置くことができるスロット数
        int MAX_PAGE = (int) Math.ceil((double) world_list.size() / CAN_PUT_SLOT); //最大ページ数
        for (int n = 0; n < world_list.size(); n++) {
            if (now_inv_slot >= CAN_PUT_SLOT) { //現在のスロットが置くことができるスロット数が超えた場合
                now_inv_slot=0;
                page+=1;
                inv = Bukkit.createInventory(null, SLOT_SIZE, "ワールド（" + page + "ページ）");
                inventoryList.add(inv);
            }

            World world = world_list.get(n);
            ItemStack inv_item = new ItemUtil(Material.GREEN_WOOL)
                    .setDisplayLore(world.getName(), Arrays.asList(world.getEnvironment().name()))
                    .build();
            inv.setItem(now_inv_slot, inv_item);

            if (page >= 2) inv.setItem(45, new ItemUtil("MHF_ArrowLeft").setDisplayLore("前のページ", null).build());
            if (page < MAX_PAGE) inv.setItem(53, new ItemUtil("MHF_ArrowRight").setDisplayLore("次のページ", null).build());
            now_inv_slot++;
        }
    }

    public void openInventory(Player player, int page) {
        Inventory inv = inventoryList.get(page);
        player.openInventory(inv);
    }
}
