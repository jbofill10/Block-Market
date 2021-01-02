from flask import Flask
from flask_socketio import SocketIO
from dotenv import load_dotenv
import finnhub, os
import concurrent.futures

load_dotenv()

app = Flask(__name__)

socketio = SocketIO(app=app, cors_allowed_origins='*')

fhub = finnhub.Client(os.getenv('FINNHUB'))

from sockets import *

if __name__ == "__main__":   
    socketio.run(app=app, host='127.0.0.1', port=5000, debug=True)