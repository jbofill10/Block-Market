package me.redstery11.blockmarket;


import io.github.cdimascio.dotenv.Dotenv;
import me.redstery11.blockmarket.marketcontrol.CloseMarket;
import me.redstery11.blockmarket.marketcontrol.OpenMarket;
import me.redstery11.blockmarket.marketcontrol.Market;
import me.redstery11.blockmarket.marketgui.*;
import me.redstery11.blockmarket.marketgui.assets.AssetAction;
import me.redstery11.blockmarket.marketgui.assets.crypto.equitycontrol.BuyCrypto;
import me.redstery11.blockmarket.marketgui.assets.crypto.equitycontrol.SellCrypto;
import me.redstery11.blockmarket.marketgui.mainmenu.AccountSettings;
import me.redstery11.blockmarket.marketgui.mainmenu.CryptoList;
import me.redstery11.blockmarket.marketgui.mainmenu.StockList;
import me.redstery11.blockmarket.marketgui.assets.stock.sharecontrol.BuyShares;
import me.redstery11.blockmarket.marketgui.assets.stock.sharecontrol.SellShares;
import me.redstery11.blockmarket.sql.SqlConnection;
import me.redstery11.blockmarket.stockcontrol.StockQuery;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;


public class BlockMarket extends JavaPlugin {

    private static Plugin instance = null;
    public static Dotenv dotenv;
    public static StockQuery sq = null;
    private static Economy econ;

    @Override
    public void onEnable() {
        dotenv = Dotenv.load();

        sq = new StockQuery();

        // Start up SQL
        SqlConnection.getInstance();

        getLogger().info("[Block-Market]: Block-Market Loaded");
        registerCommands();
        registerListeners();
        setupEconomy();

        instance = this;

    }

    @Override
    public void onDisable() {

        instance = null;

    }

    private void registerCommands(){
        this.getCommand("market").setExecutor(new Market());
        this.getCommand("openmarket").setExecutor(new OpenMarket());
        this.getCommand("closemarket").setExecutor(new CloseMarket());
    }

    private void registerListeners(){
        this.getServer().getPluginManager().registerEvents(Gui.getInstance(), this);
        this.getServer().getPluginManager().registerEvents(new AssetAction(), this);
        this.getServer().getPluginManager().registerEvents(new AccountSettings(), this);
        this.getServer().getPluginManager().registerEvents(new MoneyManagement(), this);
        this.getServer().getPluginManager().registerEvents(new BuyShares(), this);
        this.getServer().getPluginManager().registerEvents(new SellShares(), this);
        this.getServer().getPluginManager().registerEvents(StockList.getInstance(), this);
        this.getServer().getPluginManager().registerEvents(CryptoList.getInstance(), this);
        this.getServer().getPluginManager().registerEvents(new BuyCrypto(), this);
        this.getServer().getPluginManager().registerEvents(new SellCrypto(), this);
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;

    }
    public static Plugin getInstance(){
        return instance;
    }

    public static Economy getEconomy() {
        return econ;
    }

}
