from app import socketio, fhub
from stock_list import stocks

@socketio.on('connect')
def on_connect():
    socketio.emit('Connected')

@socketio.on('yo')
def fetch():
    quotes = {}
    stock_quotes = {}
    crypto_quotes = {}
    for stock in stocks:
        data = fhub.quote(stock)['c']

        stock = 'BTC' if stock == 'BINANCE:BTCUSDT' else stock
        stock = 'ETH' if stock == 'BINANCE:ETHUSDT' else stock
        
        if 'BTC' in stock or 'ETH' in stock:
            crypto_quotes[stock] = data
        else:
            stock_quotes[stock] = data

    quotes['stocks'] = stock_quotes
    quotes['crypto'] = crypto_quotes

    socketio.emit('fetch', quotes)
