package me.redstery11.blockmarket.marketgui.assets.stock.sharecontrol;

import java.sql.SQLException;
import java.util.Arrays;

import me.redstery11.blockmarket.BlockMarket;
import me.redstery11.blockmarket.marketgui.Gui;
import me.redstery11.blockmarket.sql.PlayerData;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class SellShares extends StockInventory implements Listener {

    private Inventory inv;
    private double    earned = 0.0d;
    private int       shares;

    public SellShares(ItemStack item, int shares) {

        this.inv = super.buildSellInventory(item, item.getItemMeta().getDisplayName() + ": " + shares + ": $0");
    }

    public SellShares() {
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) throws SQLException {
        if (e.getInventory().getSize() != 54) {
            return;
        }

        String invTitle = e.getView().getTitle();

        try {
            if (!Arrays.asList("TLSA:", "BTC:", "AAPL:", "NFLX:", "GOOG:", "FB:", "AMZN:")
                       .contains(invTitle.substring(0, invTitle.indexOf(":") + 1))) {
                return;
            }
        } catch (Exception ex) {
            return;
        }

        if (!e.getInventory().getItem(31).getType().equals(Material.RED_WOOL)) {
            return;
        }

        e.setCancelled(true);

        Player p = (Player) e.getWhoClicked();
        String shareDeduction = e.getCurrentItem().getItemMeta().getDisplayName();
        ItemStack middleItem = e.getInventory().getContents()[22];
        double itemPrice =
            Double.parseDouble(e.getInventory().getContents()[22].getItemMeta().getLore().get(0).substring(1));
        Inventory inv;

        int shares = Integer.parseInt(invTitle.substring(invTitle.indexOf(":") + 2, invTitle.lastIndexOf(":")));

        switch (shareDeduction) {
            case "1M Shares":

                if (!canSell(1000000, shares)) {
                    p.sendMessage("You don't have enough shares to do this!");
                    break;
                }
                shares -= 1000000;

                this.earned += itemPrice * 1000000;

                inv = super.buildSellInventory(middleItem, middleItem.getItemMeta().getDisplayName() +
                    ": " +
                    shares +
                    ": +$" +
                    this.earned);

                p.openInventory(inv);


                break;
            case "100K Shares":

                if (!canSell(100000, shares)) {
                    p.sendMessage("You don't have enough shares to do this!");
                    break;
                }
                shares -= 100000;
                this.earned += itemPrice * 100000;

                inv = super.buildSellInventory(middleItem, middleItem.getItemMeta().getDisplayName() +
                    ": " +
                    shares +
                    ": +$" +
                    this.earned);

                p.openInventory(inv);
                break;
            case "10K Shares":

                if (!canSell(10000, shares)) {
                    p.sendMessage("You don't have enough shares to do this!");
                    break;
                }
                shares -= 10000;
                this.earned += itemPrice * 10000;

                inv = super.buildSellInventory(middleItem, middleItem.getItemMeta().getDisplayName() +
                    ": " +
                    shares +
                    ": +$" +
                    this.earned);

                p.openInventory(inv);
                break;
            case "1K Shares":

                if (!canSell(1000, shares)) {
                    p.sendMessage("You don't have enough shares to do this!");
                    break;
                }

                shares -= 1000;
                this.earned += itemPrice * 1000;

                inv = super.buildSellInventory(middleItem, middleItem.getItemMeta().getDisplayName() +
                    ": " +
                    shares +
                    ": +$" +
                    this.earned);

                p.openInventory(inv);
                break;
            case "100 Shares":

                if (!canSell(100, shares)) {
                    p.sendMessage("You don't have enough shares to do this!");
                    break;
                }

                shares -= 100;
                this.earned += itemPrice * 100;

                inv = super.buildSellInventory(middleItem, middleItem.getItemMeta().getDisplayName() +
                    ": " +
                    shares +
                    ": +$" +
                    this.earned);

                p.openInventory(inv);
                break;
            case "10 Shares":

                if (!canSell(10, shares)) {
                    p.sendMessage("You don't have enough shares to do this!");
                    break;
                }

                shares -= 10;
                this.earned += itemPrice * 10;

                inv = super.buildSellInventory(middleItem, middleItem.getItemMeta().getDisplayName() +
                    ": " +
                    shares +
                    ": +$" +
                    this.earned);

                p.openInventory(inv);
                break;
            case "1 Share":

                if (!canSell(1, shares)) {
                    p.sendMessage("You don't have enough shares to do this!");
                    break;
                }

                shares -= 1;
                this.earned += itemPrice;

                inv = super.buildSellInventory(middleItem, middleItem.getItemMeta().getDisplayName() +
                    ": " +
                    shares +
                    ": +$" +
                    this.earned);

                p.openInventory(inv);
                break;
        }

        if (e.getCurrentItem().getType().equals(Material.REDSTONE)) {
            this.earned = 0;
            this.shares = 0;
            Gui gui = Gui.getInstance();

            p.closeInventory();
            gui.openInv(p);
        }

        if (e.getCurrentItem().getType().equals(Material.EMERALD)) {
            PlayerData pd = new PlayerData();

            pd.updateBalance(p.getUniqueId().toString(), -this.earned);
            Economy econ = BlockMarket.getEconomy();

            econ.depositPlayer(p, this.earned);

            p.sendMessage(String.format("You have sold %1$s shares from %2$s for $%3$s", shares,
                                        middleItem.getItemMeta().getDisplayName(), this.earned
            ));

            this.earned = 0;

            Gui gui = Gui.getInstance();

            gui.openInv(p);


        }

    }

    public void openInv(Player ent) {
        ent.openInventory(this.inv);
    }

    private boolean canSell(int sharesToSell, int currentShareCount) {
        return currentShareCount >= sharesToSell;
    }
}
