package me.redstery11.blockmarket.marketgui.mainmenu;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import me.redstery11.blockmarket.marketgui.BasicInventory;
import me.redstery11.blockmarket.marketgui.Gui;
import me.redstery11.blockmarket.marketgui.MarketConfig;
import me.redstery11.blockmarket.marketgui.assets.AssetAction;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class StockList extends BasicInventory implements Listener {
    private static StockList instance;
    private        Inventory inventory;


    public StockList() {
        this.inventory = buildInventory();
    }

    public static StockList getInstance() {
        if (instance == null) {
            instance = new StockList();
        }

        return instance;
    }

    /**
     * Updates the current quotes of the stocks in the market
     *
     * @param stockQuotes
     */
    public void updateQuotes(HashMap<String, Float> stockQuotes) {

        HashSet<Player> userCache = Gui.getInstance().getCache();

        for (int i = 0; i < this.inventory.getSize(); i++) {

            ItemStack item = this.inventory.getContents()[i];

            if (item == null) {
                continue;
            }

            String displayName = item.getItemMeta().getDisplayName();
            String quota = String.valueOf(stockQuotes.get(displayName));
            ItemMeta changedItemMeta = super.editItem(item, displayName, Arrays.asList("$" + quota));

            item.setItemMeta(changedItemMeta);

            this.inventory.setItem(i, item);
        }

        for (Player p : userCache) {
            p.updateInventory();
        }

    }

    @Override
    public Inventory buildInventory() {

        Inventory inventory = Bukkit.createInventory(null, 54, "Stocks");

        HashMap<String, Material> iconMap = MarketConfig.getStockMap();

        int invIndex = 0;

        for (Map.Entry<String, Material> entry : iconMap.entrySet()) {
            inventory.setItem(invIndex++, super.createAssetItem(entry.getValue(), entry.getKey(), 0.0d));
        }

        inventory.setItem(45, super.createItem(Material.COMPASS, "BACK", Arrays.asList("")));


        return inventory;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();

        p.updateInventory();

        if (e.getInventory().getLocation() != null) {
            return;
        }

        if (!e.getView().getTitle().equals("Stocks")) {
            return;
        }
        if (e.getCurrentItem() == null) {
            return;
        }
        if (e.getCurrentItem().equals(Material.AIR)) {
            return;
        }

        e.setCancelled(true);

        if (Arrays.asList("TLSA", "AAPL", "NFLX", "GOOG", "FB", "AMZN", "BTC", "ETH")
                  .contains(e.getCurrentItem().getItemMeta().getDisplayName())) {
            p.closeInventory();
            AssetAction assetAction = new AssetAction(e.getCurrentItem());
            assetAction.openInventory(p);
        }

    }

    @Override
    public void openInventory(Player ent) {
        ent.openInventory(this.inventory);
    }

}
