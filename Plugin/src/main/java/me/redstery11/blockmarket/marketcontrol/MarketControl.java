package me.redstery11.blockmarket.marketcontrol;


import static me.redstery11.blockmarket.BlockMarket.sq;

public class MarketControl implements Runnable {

    private volatile boolean isOpen = false;
    private static MarketControl instance = null;
    @Override
    public void run() {

        if (!isOpen)
            return;
        // Message server to get stock quotes
        sq.fetchStockQuotes();
    }

    public void openMarket(){
        this.isOpen = true; }

    public void closeMarket(){

        this.isOpen = false;
    }

    public synchronized static MarketControl getInstance(){
        if (instance == null) {
            instance = new MarketControl();
        }

        return instance;
    }
}
