from app import socketio, fhub
from stock_list import stocks

@socketio.on('connect')
def on_connect():
    socketio.emit('Connected')

@socketio.on('yo')
def fetch():
    stock_quotes = {}
    for stock in stocks:
        data = fhub.quote(stock)['c']

        stock = 'BTC' if stock == 'BINANCE:BTCUSDT' else stock

        stock_quotes[stock] = data
    print("Got your message, bro")
    print(stock_quotes)
    socketio.emit('fetch', stock_quotes)
