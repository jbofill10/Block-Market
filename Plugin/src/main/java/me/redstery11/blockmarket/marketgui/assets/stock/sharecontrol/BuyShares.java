package me.redstery11.blockmarket.marketgui.assets.stock.sharecontrol;

import java.sql.SQLException;
import java.util.Arrays;

import me.redstery11.blockmarket.marketgui.Gui;
import me.redstery11.blockmarket.sql.PlayerData;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class BuyShares extends StockInventory implements Listener {
    private Inventory inv;
    private int       shares = 0;
    private double    cost   = 0.0d;

    public BuyShares(ItemStack item) {
        this.inv = super.buildBuyInventory(item, item.getItemMeta().getDisplayName() + ": 0: $0");
    }

    public BuyShares() {
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) throws SQLException {
        if (e.getInventory().getLocation() != null) {
            return;
        }
        String invTitle = e.getView().getTitle();

        try {
            if (!Arrays.asList("TLSA:", "AAPL:", "NFLX:", "GOOG:", "FB:", "AMZN:")
                       .contains(invTitle.substring(0, invTitle.indexOf(":") + 1))) {
                return;
            }
        } catch (Exception ex) {
            return;
        }

        System.out.println(invTitle.substring(0, invTitle.indexOf(":") + 1));

        if (!e.getInventory().getItem(31).getType().equals(Material.GREEN_WOOL)) {
            return;
        }

        if (e.getCurrentItem() == null) {
            return;
        }
        if (e.getCurrentItem().equals(Material.AIR)) {
            return;
        }

        e.setCancelled(true);

        String shareCount = e.getCurrentItem().getItemMeta().getDisplayName();

        double itemPrice =
            Double.parseDouble(e.getInventory().getContents()[22].getItemMeta().getLore().get(0).substring(1));

        ItemStack middleItem = e.getInventory().getContents()[22];
        Inventory inv;

        Player p = (Player) e.getWhoClicked();

        switch (shareCount) {
            case "1M Shares":
                this.shares += 1000000;
                this.cost += itemPrice * 1000000;

                inv = super.buildBuyInventory(middleItem, middleItem.getItemMeta().getDisplayName() +
                    ": " +
                    this.shares +
                    ": -$" +
                    this.cost);

                p.openInventory(inv);

                break;
            case "100K Shares":
                this.shares += 100000;
                this.cost += itemPrice * 100000;

                inv = super.buildBuyInventory(middleItem, middleItem.getItemMeta().getDisplayName() +
                    ": " +
                    this.shares +
                    ": -$" +
                    this.cost);

                p.openInventory(inv);
                break;
            case "10K Shares":
                this.shares += 10000;
                this.cost += itemPrice * 10000;

                inv = super.buildBuyInventory(middleItem, middleItem.getItemMeta().getDisplayName() +
                    ": " +
                    this.shares +
                    ": -$" +
                    this.cost);

                p.openInventory(inv);
                break;
            case "1K Shares":
                this.shares += 1000;
                this.cost += itemPrice * 1000;

                inv = super.buildBuyInventory(middleItem, middleItem.getItemMeta().getDisplayName() +
                    ": " +
                    this.shares +
                    ": -$" +
                    this.cost);

                p.openInventory(inv);
                break;
            case "100 Shares":
                this.shares += 100;
                this.cost += itemPrice * 100;

                inv = super.buildBuyInventory(middleItem, middleItem.getItemMeta().getDisplayName() +
                    ": " +
                    this.shares +
                    ": -$" +
                    this.cost);

                p.openInventory(inv);
                break;
            case "10 Shares":
                this.shares += 10;
                this.cost += itemPrice * 10;

                inv = super.buildBuyInventory(middleItem, middleItem.getItemMeta().getDisplayName() +
                    ": " +
                    this.shares +
                    ": -$" +
                    this.cost);

                p.openInventory(inv);
                break;
            case "1 Share":
                this.shares += 1;
                this.cost += itemPrice;

                inv = super.buildBuyInventory(middleItem, middleItem.getItemMeta().getDisplayName() +
                    ": " +
                    this.shares +
                    ": -$" +
                    this.cost);

                p.openInventory(inv);
                break;
        }

        if (e.getCurrentItem().getType().equals(Material.REDSTONE)) {
            this.cost = 0;
            this.shares = 0;
            Gui gui = Gui.getInstance();

            p.closeInventory();
            gui.openInv(p);
        }

        if (e.getCurrentItem().getType().equals(Material.EMERALD)) {
            PlayerData pd = new PlayerData();
            String userID = p.getUniqueId().toString();
            if (pd.canAfford(userID, this.cost)) {
                double balance = pd.getBalance(userID);

                balance -= this.cost;

                pd.updateBalance(userID, balance);
                System.out.println(cost);
                updateStocks(p.getUniqueId().toString(), middleItem.getItemMeta().getDisplayName(), this.shares,
                             this.cost
                );

                p.sendMessage(String.format("You have bought %1$s shares from %2$s for $%3$s", this.shares,
                                            middleItem.getItemMeta().getDisplayName(), this.cost
                ));

                Gui gui = Gui.getInstance();

                gui.openInv(p);

                this.cost = 0;
                this.shares = 0;

            } else {
                p.sendMessage("You cannot afford this!");
                this.cost = 0;
                this.shares = 0;

                inv = super.buildBuyInventory(middleItem, middleItem.getItemMeta().getDisplayName() + ": 0: 0");

                p.openInventory(inv);
            }
        }
    }

    public void openInv(Player ent) {
        ent.openInventory(this.inv);
    }

    private void updateStocks(String uuid, String stock, int amount, double cost) throws SQLException {
        PlayerData pd = new PlayerData();
        pd.updateStocks(uuid, stock, amount, cost);
    }
}
