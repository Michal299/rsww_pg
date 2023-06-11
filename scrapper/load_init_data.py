import pika
import json

address = 'localhost'
hotelQueue = 'AddHotelQueue'
transportQueue = 'AddFlightQueue'

connection = pika.BlockingConnection(pika.ConnectionParameters(address))
channel = connection.channel()

with open('data/hotels.json', 'r') as outfile:
    for line in outfile:
        message = json.dumps(json.loads(line))
        channel.basic_publish(exchange='', routing_key=hotelQueue, body=message)

with open('data/flights.json', 'r') as outfile:
    for line in outfile:
        message = json.dumps(json.loads(line))
        channel.basic_publish(exchange='', routing_key=transportQueue, body=message)

connection.close()
