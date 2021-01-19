package me.redstery11.blockmarket.marketgui.mainmenu;

import me.redstery11.blockmarket.marketgui.Gui;
import me.redstery11.blockmarket.marketgui.MoneyManagement;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class AccountSettings implements Listener {

    private Inventory inventory;

    public AccountSettings(){

        this.inventory = buildInventory();

    }

    private Inventory buildInventory(){
        Inventory inventory = Bukkit.createInventory(null, 9, "Account");

        inventory.setItem(0, createItem(Material.GREEN_DYE, "Deposit", Collections.singletonList("Deposit money into your Enderhood Account")));

        inventory.setItem(1, createItem(Material.RED_DYE, "Withdraw", Arrays.asList("Withdraw money from your Enderhood Account", "To your player balance")));

        inventory.setItem(2, createItem(Material.BOOK, "Show stocks", Arrays.asList("Display your currently owned stocks")));

        inventory.setItem(8, createItem(Material.COMPASS, "BACK", Collections.singletonList("")));

        return inventory;
    }

    private ItemStack createItem(Material material, String displayName, List<String> lore){
        ItemStack item = new ItemStack(material, 1);

        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(displayName);

        meta.setLore(lore);

        item.setItemMeta(meta);

        return item;

    }

    public void openInv(Player ent){
        ent.openInventory(this.inventory);
    }

    @EventHandler
    private void onInventoryClick(InventoryClickEvent e) throws SQLException {

        if (e.getInventory().getLocation() != null) return;

        if (!e.getView().getTitle().equals("Account")) return;
        if (e.getCurrentItem() == null) return;
        if (e.getCurrentItem().equals(Material.AIR)) return;

        e.setCancelled(true);

        Player p = (Player) e.getWhoClicked();
        Material itemClicked = e.getCurrentItem().getType();
        if (itemClicked.equals(Material.GREEN_DYE) || itemClicked.equals(Material.RED_DYE)){
            // stock buy shit
            MoneyManagement fa = new MoneyManagement(e.getCurrentItem());

            fa.openInv((Player) e.getWhoClicked());
        }

        if (itemClicked.equals(Material.COMPASS)){
            Gui g = Gui.getInstance();

            p.closeInventory();

            g.openInv(p);
        }
    }

}
