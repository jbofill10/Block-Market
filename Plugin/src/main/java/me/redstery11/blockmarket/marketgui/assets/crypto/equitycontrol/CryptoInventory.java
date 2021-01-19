package me.redstery11.blockmarket.marketgui.assets.crypto.equitycontrol;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

abstract class CryptoInventory implements Listener {

    private final int millCount = 1;
    private final int HundredKCount = 1;
    private final int tenKCount = 1;
    private final int oneKCount = 1;
    private final int hundredCount = 1;
    private final int tenCount = 1;
    private final int oneCount = 1;

    public Inventory buildBuyInventory(ItemStack item, String displayName) {
        String cryptoName = item.getItemMeta().getDisplayName();

        Inventory inv = Bukkit.createInventory(null, 54, displayName);

        inv.setItem(22, item);

        inv.setItem(28,
                    createItem(Material.GREEN_WOOL, "1M " + cryptoName, Collections.singletonList(""), this.millCount)
        );

        inv.setItem(29, createItem(Material.GREEN_WOOL, "100K " + cryptoName, Collections.singletonList(""),
                                   this.HundredKCount
        ));

        inv.setItem(30,
                    createItem(Material.GREEN_WOOL, "10K " + cryptoName, Collections.singletonList(""), this.tenKCount)
        );

        inv.setItem(31,
                    createItem(Material.GREEN_WOOL, "1K " + cryptoName, Collections.singletonList(""), this.oneKCount)
        );

        inv.setItem(32, createItem(Material.GREEN_WOOL, "100 " + cryptoName, Collections.singletonList(""),
                                   this.hundredCount
        ));

        inv.setItem(33,
                    createItem(Material.GREEN_WOOL, "10 " + cryptoName, Collections.singletonList(""), this.tenCount)
        );

        inv.setItem(34,
                    createItem(Material.GREEN_WOOL, "1 " + cryptoName, Collections.singletonList(""), this.oneCount)
        );

        inv.setItem(48, createItem(Material.EMERALD, "Confirm", Collections.singletonList(""), 1));

        inv.setItem(50, createItem(Material.REDSTONE, "Cancel", Collections.singletonList(""), 1));

        return inv;
    }

    public Inventory buildSellInventory(ItemStack item, String displayName) {
        String cryptoName = item.getItemMeta().getDisplayName();

        Inventory inv = Bukkit.createInventory(null, 54, displayName);

        inv.setItem(22, item);

        inv.setItem(28,
                    createItem(Material.RED_WOOL, "1M " + cryptoName, Collections.singletonList(""), this.millCount)
        );

        inv.setItem(29, createItem(Material.RED_WOOL, "100K " + cryptoName, Collections.singletonList(""),
                                   this.HundredKCount
        ));

        inv.setItem(30,
                    createItem(Material.RED_WOOL, "10K " + cryptoName, Collections.singletonList(""), this.tenKCount)
        );

        inv.setItem(31,
                    createItem(Material.RED_WOOL, "1K " + cryptoName, Collections.singletonList(""), this.oneKCount)
        );

        inv.setItem(32,
                    createItem(Material.RED_WOOL, "100 " + cryptoName, Collections.singletonList(""), this.hundredCount)
        );

        inv.setItem(33,
                    createItem(Material.RED_WOOL, "10 " + cryptoName, Collections.singletonList(""), this.tenCount)
        );

        inv.setItem(34, createItem(Material.RED_WOOL, "1 " + cryptoName, Collections.singletonList(""), this.oneCount));

        inv.setItem(48, createItem(Material.EMERALD, "Confirm", Collections.singletonList(""), 1));

        inv.setItem(50, createItem(Material.REDSTONE, "Cancel", Collections.singletonList(""), 1));

        return inv;
    }

    private ItemStack createItem(Material material, String displayName, List<String> lore, int count) {

        ItemStack item = new ItemStack(material, count);

        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(displayName);

        meta.setLore(lore);

        item.setItemMeta(meta);

        return item;
    }

    @EventHandler
    public abstract void onInventoryClick(InventoryClickEvent e) throws SQLException;

    public double calculateCoin(double currentPrice, double moneyPaid) {
        return moneyPaid / currentPrice;
    }
}
