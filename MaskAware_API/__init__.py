from flask import Flask,jsonify, make_response,request
from functions import databaseentry, databaseread

app = Flask(__name__)

@app.route('/', methods=['GET'])
def home():
    return ":) The data entry point is /entrypoint and exit point is /exitpoint"

@app.route('/entrypoint', methods=['GET'])
def entrypoint():
    username=request.args.get("username")
    air=request.args.get("air")
    ir=request.args.get("ir")
    output=databaseentry(username,air,ir)
    return make_response(jsonify(output), 200)

@app.route('/exitpoint', methods=['GET'])
def exitpoint():
    username=request.args.get("username")
    output=databaseread(username)
    return make_response(jsonify(output), 200)