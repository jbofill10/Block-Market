package me.redstery11.blockmarket.marketgui.mainmenu;

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

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class CryptoList extends BasicInventory implements Listener {
    private static CryptoList instance;
    private Inventory inventory;

    public CryptoList(){
        this.inventory = buildInventory();
    }

    public void updateQuotes(HashMap<String, Float> stockQuotes){

        HashSet<Player> userCache = Gui.getInstance().getCache();

        for (int i = 0; i < this.inventory.getSize(); i++){

            ItemStack item = this.inventory.getContents()[i];

            if (item == null) continue;

            String displayName = item.getItemMeta().getDisplayName();
            String quota = String.valueOf(stockQuotes.get(displayName));
            ItemMeta changedItemMeta = super.editItem(item, displayName, Arrays.asList("$" + quota));

            item.setItemMeta(changedItemMeta);

            this.inventory.setItem(i, item);
        }
        System.out.println(userCache.toString());
        for (Player p : userCache){
            System.out.println(p.getDisplayName());
            p.updateInventory();
        }


    }

    @Override
    public Inventory buildInventory() {
        HashMap<String, Material> cryptoList = MarketConfig.getCryptoMap();

        Inventory inventory = Bukkit.createInventory(null, 54, "Cryptocurrencies");

        int invIndex = 0;

        for (Map.Entry<String, Material> entry : cryptoList.entrySet()){
            inventory.setItem(invIndex++, super.createAssetItem(entry.getValue(), entry.getKey(), 0.0d));
        }

        inventory.setItem(45, createItem(Material.COMPASS,"BACK", Arrays.asList("")));

        return inventory;
    }

    @EventHandler
    @Override
    public void onInventoryClick(InventoryClickEvent e) {

        if (!e.getView().getTitle().equals("Cryptocurrencies")) return;

        e.setCancelled(true);
        Player p = (Player) e.getWhoClicked();

        if (Arrays.asList("ETH", "BTC").contains(e.getCurrentItem().getItemMeta().getDisplayName())){
            AssetAction assetAction = new AssetAction(e.getCurrentItem());

            assetAction.openInventory(p);

        }
    }

    public void openInventory(Player ent){
        ent.openInventory(this.inventory);
    }

    public static CryptoList getInstance(){
        if (instance == null){
            instance = new CryptoList();
        }

        return instance;
    }

}
