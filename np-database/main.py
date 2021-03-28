import os
import requests
from bson.json_util import dumps
from bson.objectid import ObjectId
from flask import jsonify, flash, request, Flask
from flask_cors import CORS
from pymongo import MongoClient
from pymongo.errors import ServerSelectionTimeoutError
from datetime import datetime


app = Flask(__name__)
CORS(app)


client = MongoClient('mongodb://' + os.environ['MONGODB_USERNAME'] + ':' + os.environ['MONGODB_PASSWORD'] + '@' + os.environ['MONGODB_HOSTNAME'] + ':27017/' + os.environ['MONGODB_DATABASE'], authSource="admin")
db = client[os.environ['MONGODB_DATABASE']]


@app.route('/vehicles', methods=['POST'])
def add_vehicle():
	_json = request.json
	_plate = _json['plate']
    _brand = _json['brand']
    _model = _json['model']
	_owner = _json['owner']
	_address = _json['address']
    _phone = _json['phone']
    _allowed = _json['allowed']

	if db.vehicle.find_one({'plate': _plate}) != None:
		return bad_request('Vehicle already exists')
	
	# validate the received values
	if _plate and _phone and request.method == 'POST':
		# save details
		id = db.vehicle.insert({
            'plate': _plate, 
            'brand': _brand, 
            'model': _model, 
            'owner': _owner, 
            'address': _address, 
            'phone': _phone,
            'allowed': _allowed
        })
		log(_email, 'Vehicle CREATED')
		resp = jsonify({
            '_id': str(id), 
            'plate': _plate, 
            'brand': _brand, 
            'model': _model, 
            'owner': _owner, 
            'address': _address, 
            'phone': _phone,
            'allowed': _allowed
        })
		resp.status_code = 201
		return resp
	else:
		return not_found()

		
		

@app.route('/vehicles', methods=['GET'])
def vehicles():
	vehicles = db.vehicle.find().sort("plate", 1)
	resp = dumps(vehicles)

	return resp


@app.route('/vehicles/<id>', methods=['GET'])
def vehicle(id):
	vehicle = db.vehicle.find_one({'_id': ObjectId(id)})
	resp = dumps(vehicle)

	return resp


@app.route('/vehicles/<id>', methods=['PUT'])
def update_vehicle(id):
	_json = request.json
	_id = id
	_plate = _json['plate']
    _brand = _json['brand']
    _model = _json['model']
	_owner = _json['owner']
	_address = _json['address']
    _phone = _json['phone']
    _allowed = _json['allowed']	

	# validate the received values
	if _plate and _phone and _id and request.method == 'PUT':
		# save edits
		db.vehicle.update_one(
            {'_id': ObjectId(_id['$oid']) if '$oid' in _id else ObjectId(_id)}, 
            {'$set': {
                'plate': _plate, 
                'brand': _brand, 
                'model': _model, 
                'owner': _owner, 
                'address': _address, 
                'phone': _phone,
                'allowed': _allowed
                }
            })
		resp = jsonify('Vehicle updated successfully!')
		resp.status_code = 200
		return resp
	else:
		return not_found()


@app.route('/vehicles/<id>', methods=['DELETE'])
def delete_vehicle(id):
	vehicle = db.vehicle.find_one({'_id': ObjectId(id)})

	db.vehicle.delete_one({'_id': ObjectId(id)})
	resp = jsonify('Vehicle deleted successfully!')
	resp.status_code = 200

	log(vehicle.email, 'Vehicle DELETED')

	return resp


@app.errorhandler(404)
def not_found(error=None):
    message = {
        'status': 404,
        'message': 'Not Found: ' + request.url,
    }
    resp = jsonify(message)
    resp.status_code = 404

    return resp


@app.errorhandler(400)
def bad_request(error=None):
    message = {
        'status': 400,
        'message': error,
		'detail': 'Bad Request'
    }
    resp = jsonify(message)
    resp.status_code = 400

    return resp


@app.errorhandler(500)
def nternal_server_error(error=None):
    message = {
        'status': 500,
        'message': 'Internal Server Error' + request.url,
    }
    resp = jsonify(message)
    resp.status_code = 500

    return resp


if __name__ == "__main__":
    app.run()