package me.redstery11.blockmarket.marketgui;

import org.bukkit.Material;

import java.util.HashMap;

public class MarketConfig {

    private static final HashMap<String, Material> stocks = new HashMap<String, Material>(){{
       put("TSLA", Material.DARK_OAK_BOAT);
       put("AAPL", Material.APPLE);
       put("FB", Material.BOOK);
       put("GOOG", Material.DIAMOND);
       put("AMZN", Material.ORANGE_DYE);
       put("NFLX", Material.RED_DYE);
    }};

    private static final HashMap<String, Material> cryptos = new HashMap<String, Material>(){{
        put("ETH", Material.BLACK_DYE);
        put("BTC", Material.GOLD_NUGGET);
    }};

    public static HashMap<String, Material> getStockMap(){
        return stocks;
    }

    public static HashMap<String, Material> getCryptoMap(){
        return cryptos;
    }

}
