package me.redstery11.blockmarket.marketgui;

import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import me.redstery11.blockmarket.marketgui.mainmenu.CryptoList;
import me.redstery11.blockmarket.marketgui.mainmenu.StockList;
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

/**
 * Singleton Class that shows current stock quotes
 */
public class Gui implements Listener {
    private static Gui                    instance = null;
    // Current stock quote prices
    private        HashMap<String, Float> quotes   = null;
    // Inventory for stock market
    private        Inventory              inv;
    // Caches player references that have used /market
    private        HashSet<Player>        userCache;

    private Gui() {

        this.inv = createInv();
        this.userCache = new HashSet<>();
    }

    public static Gui getInstance() {
        if (instance == null) {
            instance = new Gui();
        }
        return instance;
    }

    /**
     * Opens market inventory for player that executes /market Caches player in userCache
     *
     * @param ent
     */
    public void openInv(Player ent) throws SQLException {
        PlayerData pd = new PlayerData();

        if (!this.userCache.contains(ent)) {

            String uuid = ent.getUniqueId().toString();

            System.out.println("\n\n\n" + pd.playerExists(uuid) + "\n\n\n");

            if (!pd.playerExists(uuid)) {
                pd.addPlayer(uuid);
            }

            userCache.add(ent);
        }

        ent.openInventory(this.inv);

    }

    /**
     * Creates stock market inventory upon instantiation in onEnable
     *
     * @return stores in class inventory
     */
    public Inventory createInv() {
        Inventory inventory = Bukkit.createInventory(null, 9, "Block Market");

        inventory.setItem(0, createItem(Material.BOOK, "Stocks", Collections.singletonList("Buy and sell stocks")));

        inventory.setItem(1, createItem(Material.ENDER_PEARL, "Cryptocurrencies",
                                        Collections.singletonList("Buy and sell cryptocurrencies")
        ));

        inventory.setItem(2, createItem(Material.PAPER, "Your Financial Assets",
                                        Collections.singletonList("List of purchased stocks and crypto")
        ));

        inventory.setItem(8, createItem(Material.PLAYER_HEAD, "Account Settings",
                                        Collections.singletonList("Deposit and withdraw money from your account")
        ));


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
    public void onStockSelection(InventoryClickEvent e) throws SQLException {
        Player p = (Player) e.getWhoClicked();

        if (e.getInventory().getLocation() != null) {
            return;
        }

        if (!e.getView().getTitle().equals("Block Market")) {
            return;
        }
        if (e.getCurrentItem() == null) {
            return;
        }
        if (e.getCurrentItem().equals(Material.AIR)) {
            return;
        }

        e.setCancelled(true);

        ItemMeta selectedItemMeta = e.getCurrentItem().getItemMeta();
        if (selectedItemMeta.getDisplayName().equals("Stocks")) {
            StockList stockList = StockList.getInstance();

            stockList.openInventory(p);
        }

        if (selectedItemMeta.getDisplayName().equals("Cryptocurrencies")) {

            CryptoList cryptoList = CryptoList.getInstance();

            cryptoList.openInventory(p);

        }


    }

    public HashSet<Player> getCache() {
        return this.userCache;
    }


}
