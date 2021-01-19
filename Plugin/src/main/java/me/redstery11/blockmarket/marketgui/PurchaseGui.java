package me.redstery11.blockmarket.marketgui;

import me.redstery11.blockmarket.BlockMarket;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public abstract class PurchaseGui {

    public Inventory buildInventory(ItemStack item, String displayName){
        Inventory inventory = Bukkit.createInventory(null, 54, displayName);

        inventory.setItem(22, item);

        inventory.setItem(28, createItem(Material.GREEN_WOOL, "$1M", Collections.singletonList("")));
        inventory.setItem(29, createItem(Material.GREEN_WOOL, "$100K", Collections.singletonList("")));
        inventory.setItem(30, createItem(Material.GREEN_WOOL, "$10K", Collections.singletonList("")));
        inventory.setItem(31, createItem(Material.GREEN_WOOL, "$1K", Collections.singletonList("")));
        inventory.setItem(32, createItem(Material.GREEN_WOOL, "$100", Collections.singletonList("")));
        inventory.setItem(33, createItem(Material.GREEN_WOOL, "$10", Collections.singletonList("")));
        inventory.setItem(34, createItem(Material.GREEN_WOOL, "$1", Collections.singletonList("")));
        inventory.setItem(48, createItem(Material.EMERALD, "Confirm", Collections.singletonList("")));
        inventory.setItem(50, createItem(Material.REDSTONE, "Cancel", Collections.singletonList("")));

        return inventory;

    }

    private ItemStack createItem(Material material, String displayName, List<String> lore){
        ItemStack item = new ItemStack(material);

        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(displayName);

        meta.setLore(lore);

        item.setItemMeta(meta);

        return item;
    }

    public void openInv(Player ent, Inventory inventory){ent.openInventory(inventory);}

    @EventHandler
    public abstract void onInventoryClick(InventoryClickEvent e) throws SQLException;

    public boolean hasInGameFunds(Player p,  double amount){
        Economy econ = BlockMarket.getEconomy();

        double playerBal = econ.getBalance(p);

        return playerBal > amount;
    }

    public void addInGameFunds(Player p, double amount){
        Economy econ = BlockMarket.getEconomy();
        econ.depositPlayer(p, amount);
    }

    public void removeInGameFunds(Player p, double amount){

        Economy econ = BlockMarket.getEconomy();
        EconomyResponse economyResponse = econ.withdrawPlayer(p, amount);
    }

}
