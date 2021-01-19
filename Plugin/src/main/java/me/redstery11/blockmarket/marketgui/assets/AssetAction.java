package me.redstery11.blockmarket.marketgui.assets;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import me.redstery11.blockmarket.marketgui.Gui;
import me.redstery11.blockmarket.marketgui.assets.crypto.equitycontrol.BuyCrypto;
import me.redstery11.blockmarket.marketgui.assets.stock.sharecontrol.BuyShares;
import me.redstery11.blockmarket.marketgui.assets.stock.sharecontrol.SellShares;
import me.redstery11.blockmarket.sql.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class AssetAction implements Listener {

    private Inventory inv;

    public AssetAction(ItemStack item) {
        this.inv = buildInventory(item);
    }

    public AssetAction() {
    }

    public Inventory buildInventory(ItemStack item) {
        Inventory inventory = Bukkit.createInventory(null, 54, item.getItemMeta().getDisplayName());

        inventory
            .setItem(22, createItem(item.getType(), item.getItemMeta().getDisplayName(), item.getItemMeta().getLore()));

        inventory.setItem(29, createItem(Material.GREEN_WOOL, "BUY", Arrays.asList("")));

        inventory.setItem(33, createItem(Material.RED_WOOL, "SELL", Arrays.asList("")));

        inventory.setItem(45, createItem(Material.COMPASS, "BACK", Arrays.asList("")));

        return inventory;
    }

    private ItemStack createItem(Material material, String displayName, List<String> lore) {
        ItemStack item = new ItemStack(material, 1);

        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(displayName);

        meta.setLore(lore);

        item.setItemMeta(meta);

        return item;
    }

    @EventHandler
    private void onAssetSelection(InventoryClickEvent e) throws SQLException {

        if (!Arrays.asList("TLSA", "AAPL", "NFLX", "GOOG", "FB", "AMZN", "BTC", "ETH")
                   .contains(e.getView().getTitle())) {
            return;
        }

        if (e.getCurrentItem() == null) {
            return;
        }
        if (e.getCurrentItem().getType().equals(Material.AIR)) {
            return;
        }

        e.setCancelled(true);

        Player p = (Player) e.getWhoClicked();

        if (e.getCurrentItem().getType().equals(Material.COMPASS)) {
            Gui gui = Gui.getInstance();
            gui.openInv(p);
        }

        if (Arrays.asList("TLSA", "AAPL", "NFLX", "GOOG", "FB", "AMZN")
                  .contains(e.getInventory().getItem(22).getItemMeta().getDisplayName())) {

            if (e.getCurrentItem().getType().equals(Material.GREEN_WOOL)) {
                BuyShares buyShares = new BuyShares(e.getInventory().getContents()[22]);

                p.closeInventory();

                buyShares.openInv(p);
            }

            if (e.getCurrentItem().getType().equals(Material.RED_WOOL)) {
                PlayerData pd = new PlayerData();

                int shares = pd.getShares(p.getUniqueId().toString(),
                                          e.getInventory().getItem(22).getItemMeta().getDisplayName()
                );
                SellShares sellShares = new SellShares(e.getInventory().getItem(22), shares);

                p.closeInventory();

                sellShares.openInv(p);
            }

        }

        if(Arrays.asList("BTC", "ETH").contains(e.getInventory().getItem(22).getItemMeta().getDisplayName())){
            if (e.getCurrentItem().getType().equals(Material.GREEN_WOOL)) {
                System.out.println("hello");
                BuyCrypto buyCrypto = new BuyCrypto(e.getInventory().getContents()[22]);

                p.closeInventory();

                buyCrypto.openInv(p);
            }

            if (e.getCurrentItem().getType().equals(Material.RED_WOOL)) {
//                PlayerData pd = new PlayerData();
//
//                int shares = pd.getShares(p.getUniqueId().toString(),
//                                          e.getInventory().getItem(22).getItemMeta().getDisplayName()
//                );
//                SellShares sellShares = new SellShares(e.getInventory().getItem(22), shares);
//
//                p.closeInventory();
//
//                sellShares.openInv(p);
            }
        }

    }

    public void openInventory(Player ent) {
        ent.openInventory(this.inv);
    }

}
