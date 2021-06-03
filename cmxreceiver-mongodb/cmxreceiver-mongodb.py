#!flask/bin/python

# Libraries
from pprint import pprint
from flask import Flask
from flask import json
from flask import request
import sys, getopt
from pymongo import MongoClient
import ssl

############## USER DEFINED SETTINGS ###############
# MERAKI SETTINGS
validator = ""
secret = ""
version = "2.0" # This code was written to support the CMX JSON version specified

# Save CMX Data
def save_data(data):
    print("---- SAVING CMX DATA ----")
    # CHANGE ME - send 'data' to a database or storage system
    pprint(data, indent=1)

    ## Adjust data before DB insert ##
    # Add GeoJSON structure for MongoDB Compass (or similar) mapping
    for i in data["data"]["observations"]:
        print("i",i)
        data["data"]["secret"] = "hidden"
        lat = i["location"]["lat"]
        lng = i["location"]["lng"]
        clientMac = i["clientMac"]
        data["data"]["geoJSON"] = {
          "type": "Feature",
          "geometry": {
            "type": "Point",
            "coordinates": [lat,lng]
          },
          "properties": {
            "name": clientMac
          }
        }

       
    # Commit to database
    client = MongoClient("mongodb://localhost:38028")    
    db = client.bpw1
    result = db.cmxdata.insert_one(data)
    print("MongoDB insert result: ",result )

####################################################
app = Flask(__name__)


# Respond to Meraki with validator
@app.route('/', methods=['GET'])
def get_validator():
    print("validator sent to: ",request.environ['REMOTE_ADDR'])
    return validator

# Accept CMX JSON POST
@app.route('/', methods=['POST'])
def get_cmxJSON():
    if not request.json or not 'data' in request.json:
        return("invalid data",400)
    cmxdata = request.json
    #pprint(cmxdata, indent=1)
    print("Received POST from ",request.environ['REMOTE_ADDR'])

    # Verify secret
    if cmxdata['secret'] != secret:
        print("secret invalid:", cmxdata['secret'])
        return("invalid secret",403)
    else:
        print("secret verified: ", cmxdata['secret'])

    # Verify version
    if cmxdata['version'] != version:
        print("invalid version")
        return("invalid version",400)
    else:
        print("version verified: ", cmxdata['version'])

    # Determine device type
    if cmxdata['type'] == "DevicesSeen":
        print("WiFi Devices Seen")
    elif cmxdata['type'] == "BluetoothDevicesSeen":
        print("Bluetooth Devices Seen")
    else:
        print("Unknown Device 'type'")
        return("invalid device type",403)

    # Do something with data (commit to database)
    save_data(cmxdata)

    # Return success message
    return "CMX POST Received"

# Launch application with supplied arguments
def main(argv):
    global validator
    global secret

    try:
       opts, args = getopt.getopt(argv,"hv:s:",["validator=","secret="])
    except getopt.GetoptError:
       print ('cmxreceiver.py -v <validator> -s <secret>')
       sys.exit(2)
    for opt, arg in opts:
       if opt == '-h':
           print ('cmxreceiver.py -v <validator> -s <secret>')
           sys.exit()
       elif opt in ("-v", "--validator"):
           validator = arg
       elif opt in ("-s", "--secret"):
           secret = arg

    print ('validator: '+ validator)
    print ('secret: '+ secret)


if __name__ == '__main__':
    main(sys.argv[1:])    
    ssl_context = ssl.SSLContext(ssl.PROTOCOL_TLS)
    ssl_context.load_cert_chain(certfile='cert.pem', keyfile='cert.key', password='')
    app.run(host='0.0.0.0', port=5000, ssl_context=ssl_context)
