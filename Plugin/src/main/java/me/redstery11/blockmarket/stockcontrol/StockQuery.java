package me.redstery11.blockmarket.stockcontrol;

import io.socket.client.IO;
import io.socket.client.Socket;
import me.redstery11.blockmarket.marketgui.Gui;
import me.redstery11.blockmarket.marketgui.mainmenu.CryptoList;
import me.redstery11.blockmarket.marketgui.mainmenu.StockList;
import org.json.JSONObject;

import java.net.URI;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

public class StockQuery {
    private Gui gui = Gui.getInstance();
    private Socket socket;

    public StockQuery() {

        URI uri = URI.create("http://127.0.0.1:5000");
        IO.Options options = IO.Options.builder()
                // ...
                .build();

        this.socket = IO.socket(uri, options);

        stockConnection();
    }
    public void stockConnection() {

        this.socket.on("fetch", stocks -> {
            HashMap<String, Float> stockQuotes = new HashMap<>();
            HashMap<String, Float> cryptoQuotes = new HashMap<>();
            JSONObject obj = new JSONObject(stocks[0].toString());

            JSONObject stk = new JSONObject(obj.get("stocks").toString());
            JSONObject crypt = new JSONObject(obj.get("crypto").toString());

            Iterator<?> stockKeys = stk.keys();
            Iterator<?> cryptKeys = crypt.keys();

            while (stockKeys.hasNext()){
                String key = (String) stockKeys.next();
                float value = Float.parseFloat(stk.get(key).toString());

                stockQuotes.put(key, value);
            }

            while (cryptKeys.hasNext()){
                String key = (String) cryptKeys.next();
                float value = Float.parseFloat(crypt.get(key).toString());

                cryptoQuotes.put(key, value);
            }

            StockList.getInstance().updateQuotes(stockQuotes);

            CryptoList.getInstance().updateQuotes(cryptoQuotes);
        });

        socket.connect();

    }

    public void fetchStockQuotes(){
        this.socket.emit("yo");
    }
}
