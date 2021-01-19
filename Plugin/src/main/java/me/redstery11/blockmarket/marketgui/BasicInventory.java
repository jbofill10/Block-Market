package me.redstery11.blockmarket.marketgui;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

public abstract class BasicInventory {

  public abstract Inventory buildInventory();

  public ItemStack createItem(Material material, String displayName, List<String> lore) {
    ItemStack item = new ItemStack(material, 1);

    ItemMeta meta = item.getItemMeta();

    meta.setDisplayName(displayName);

    meta.setLore(lore);

    item.setItemMeta(meta);

    return item;
  }

  public ItemStack createAssetItem(Material material, String displayName, double quota) {
    ItemStack item = new ItemStack(material, 1);

    ItemMeta meta = editItem(item, displayName, Arrays.asList("$" + (quota)));

    item.setItemMeta(meta);

    return item;
  }

  protected ItemMeta editItem(ItemStack item, String displayName, List<String> lore) {

    ItemMeta meta = item.getItemMeta();
    meta.setDisplayName(displayName);
    meta.setLore(lore);

    return meta;
  }

  @EventHandler
  public abstract void onInventoryClick(InventoryClickEvent e);

  public abstract void openInventory(Player ent);
}
