package me.redstery11.blockmarket.marketgui;

import me.redstery11.blockmarket.sql.PlayerData;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import java.sql.SQLException;
import java.util.Arrays;


public class MoneyManagement extends PurchaseGui implements Listener {
    private Inventory inventory;
    private double amount;


    public MoneyManagement(ItemStack item){
        this.inventory = super.buildInventory(item, item.getItemMeta().getDisplayName() + ": $0");
    }

    public MoneyManagement() {
    }

    public void openInv(Player ent){
        super.openInv(ent, this.inventory);
    }


    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) throws SQLException {

        if (e.getInventory().getLocation() != null) return;
        String invTitle = e.getView().getTitle();
        try {
            if (!Arrays.asList("Deposit", "Withdraw").contains(invTitle.substring(0, invTitle.indexOf(":")))) return;
        } catch (Exception ex) {
            return;
        }
        if (e.getCurrentItem() == null) return;
        if (e.getCurrentItem().equals(Material.AIR)) return;


        e.setCancelled(true);
        Player p = (Player) e.getWhoClicked();

        ItemStack middleItem = e.getInventory().getContents()[22];
        Inventory inventory;

        switch (e.getCurrentItem().getItemMeta().getDisplayName()) {

            case "$1M":
                this.amount += 1000000;

                inventory = super.buildInventory(middleItem, middleItem.getItemMeta().getDisplayName() + ": $" + this.amount);
                p.openInventory(inventory);
                break;

            case "$100K":

                this.amount += 100000;

                inventory = super.buildInventory(middleItem, middleItem.getItemMeta().getDisplayName() + ": $" + this.amount);
                p.openInventory(inventory);
                break;

            case "$10K":

                this.amount += 10000;

                inventory = super.buildInventory(middleItem, middleItem.getItemMeta().getDisplayName() + ": $" + this.amount);
                p.openInventory(inventory);
                break;

            case "$1K":

                this.amount += 1000;

                inventory = super.buildInventory(middleItem, middleItem.getItemMeta().getDisplayName() + ": $" + this.amount);
                p.openInventory(inventory);

                break;

            case "$100":

                this.amount += 100;

                inventory = super.buildInventory(middleItem, middleItem.getItemMeta().getDisplayName() + ": $" + this.amount);
                p.openInventory(inventory);

                break;

            case "$10":

                this.amount += 10;

                inventory = super.buildInventory(middleItem, middleItem.getItemMeta().getDisplayName() + ": $" + this.amount);
                p.openInventory(inventory);


                break;

            case "$1":

                this.amount += 1;

                inventory = buildInventory(middleItem, middleItem.getItemMeta().getDisplayName() + ": $" + this.amount);
                p.openInventory(inventory);

                break;

        }

        if (e.getCurrentItem().getType().equals(Material.REDSTONE)) {
            this.amount = 0;
            Gui gui = Gui.getInstance();

            p.closeInventory();
            gui.openInv(p);
        }

        if (e.getCurrentItem().getType().equals(Material.EMERALD)) {
            PlayerData pd = new PlayerData();
            if (e.getInventory().getContents()[22].getItemMeta().getDisplayName().equals("Withdraw")) {
                double currentBalance = pd.getBalance(e.getWhoClicked().getUniqueId().toString());

                if (currentBalance > this.amount) {
                    super.addInGameFunds(p, this.amount);

                    pd.withdraw(p.getUniqueId().toString(), this.amount);

                    p.sendMessage(this.amount + " Has been withdrawn from your Enderhood account");

                } else {
                    p.sendMessage("You do not have enough money in your Enderhood account!");
                    this.amount = 0;
                }
            } else {

                if (!super.hasInGameFunds(p, this.amount)) {
                    this.amount = 0;
                    p.sendMessage("You cannot afford that!");
                } else {

                    p.sendMessage(this.amount + " Has been deposited into your Enderhood Account");
                    super.removeInGameFunds(p, this.amount);

                    pd.deposit(e.getWhoClicked().getUniqueId().toString(), this.amount);

                    Gui gui = Gui.getInstance();
                    this.amount = 0;
                    p.closeInventory();
                    gui.openInv(p);

                }
            }
        }

    }

}
