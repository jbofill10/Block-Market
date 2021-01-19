package me.redstery11.blockmarket.marketgui.assets.crypto.equitycontrol;

import java.sql.SQLException;
import java.util.Arrays;

import me.redstery11.blockmarket.marketgui.Gui;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class BuyCrypto extends CryptoInventory implements Listener {
    private Inventory inv;
    private double    coin;
    private double    cost;
    private String    itemName;

    public BuyCrypto(ItemStack item) {

        this.inv = super.buildBuyInventory(item, item.getItemMeta().getDisplayName() + ": 0: $0");

        this.itemName = item.getItemMeta().getDisplayName();
    }

    public BuyCrypto() {
    }

    public void openInv(Player ent) {
        ent.openInventory(this.inv);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) throws SQLException {
        String invTitle = e.getView().getTitle();

        try {
            if (!Arrays.asList("BTC:", "ETH:").contains(invTitle.substring(0, invTitle.indexOf(":") + 1))) {
                return;
            }

        } catch (Exception ex) {
            return;
        }

        if (e.getCurrentItem() == null) {
            return;
        }
        if (e.getCurrentItem().equals(Material.AIR)) {
            return;
        }

        e.setCancelled(true);

        String coinCount = e.getCurrentItem().getItemMeta().getDisplayName();

        double itemPrice =
            Double.parseDouble(e.getInventory().getContents()[22].getItemMeta().getLore().get(0).substring(1));

        ItemStack middleItem = e.getInventory().getContents()[22];

        Inventory inv;

        Player p = (Player) e.getWhoClicked();

        if (("1M " + this.itemName).equals(coinCount)) {
            this.coin += super.calculateCoin(itemPrice, 1000000);

            this.cost += 1000000;

            inv = super.buildBuyInventory(middleItem,
                                 middleItem.getItemMeta().getDisplayName() + ": " + this.coin + ": -$" + this.cost
            );

            p.openInventory(inv);

        } else if (("100K " + this.itemName).equals(coinCount)) {
            this.coin += super.calculateCoin(itemPrice, 100000);
            this.cost += 100000;

            inv = super.buildBuyInventory(middleItem,
                                 middleItem.getItemMeta().getDisplayName() + ": " + this.coin + ": -$" + this.cost
            );

            p.openInventory(inv);

        } else if (("10K " + this.itemName).equals(coinCount)) {
            this.coin += super.calculateCoin(itemPrice, 10000);
            this.cost += 10000;

            inv = super.buildBuyInventory(middleItem,
                                 middleItem.getItemMeta().getDisplayName() + ": " + this.coin + ": -$" + this.cost
            );

            p.openInventory(inv);

        } else if (("1K " + this.itemName).equals(coinCount)) {
            this.coin += super.calculateCoin(itemPrice, 1000);
            this.cost += 1000;

            inv = super.buildBuyInventory(middleItem,
                                 middleItem.getItemMeta().getDisplayName() + ": " + this.coin + ": -$" + this.cost
            );

            p.openInventory(inv);

        } else if (("100 " + this.itemName).equals(coinCount)) {
            this.coin += super.calculateCoin(itemPrice, 100);
            this.cost += 100;

            inv = super.buildBuyInventory(middleItem,
                                 middleItem.getItemMeta().getDisplayName() + ": " + this.coin + ": -$" + this.cost
            );

            p.openInventory(inv);

        } else if (("10 " + this.itemName).equals(coinCount)) {
            this.coin += super.calculateCoin(itemPrice, 10);
            this.cost += 10;

            inv = super.buildBuyInventory(middleItem,
                                 middleItem.getItemMeta().getDisplayName() + ": " + this.coin + ": -$" + this.cost
            );

            p.openInventory(inv);

        } else if (("1 " + this.itemName).equals(coinCount)) {
            this.coin += super.calculateCoin(itemPrice, 1);
            this.cost += 1;

            inv = super.buildBuyInventory(middleItem,
                                 middleItem.getItemMeta().getDisplayName() + ": " + this.coin + ": -$" + this.cost
            );

            p.openInventory(inv);
        }

        if (e.getCurrentItem().getType().equals(Material.REDSTONE)) {
            this.cost = 0;
            this.coin = 0;
            Gui gui = Gui.getInstance();

            p.closeInventory();
            gui.openInv(p);
        }
    }

}
