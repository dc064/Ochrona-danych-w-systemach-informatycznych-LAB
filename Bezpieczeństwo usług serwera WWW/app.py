from flask import Flask
app = Flask(__name__)

@app.route('/')
def index():
    return "hello world"

#zeby apke we flasku uruchomil inny serwer- gunicorn
