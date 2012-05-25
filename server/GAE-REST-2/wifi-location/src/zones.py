import cgi
import os
import datetime
import urllib
import wsgiref.handlers
import csv
import rest
from django.utils import simplejson as json
import main
from src.models import Zones, ZoneNames, Friends, Areas, BSSIDZones, ZoneMaps, Users

from google.appengine.ext import db
from google.appengine.ext import webapp
from google.appengine.ext.webapp.util import run_wsgi_app

class BSSIDHandler(webapp.RequestHandler):
	
	def get(self, user_short_name, mac_address):
			
		data = {}
		#query users
		user = db.GqlQuery(("SELECT * FROM Users " +
				"WHERE short_name = :1"), urllib.unquote_plus(user_short_name))
		#get user id
		if user.count() > 0:
			user_id = user[0].key().id()
				
			item = db.GqlQuery(("SELECT * FROM BSSIDZones " +
			"WHERE mac_address = :1"), urllib.unquote_plus(mac_address))	
			if item.count() > 0:
					curr_zone = item[0].zones
					zone_id = curr_zone.zone_id
					
					#update user location
					user[0].last_location = curr_zone.key()
					user[0].put()
					
					#generating JSON data
					data = {'zone_id' : zone_id, 
							'zone_name' : curr_zone.zone_name, 
							'mac_address' : item[0].mac_address, 
							'user' : user[0].short_name, 
							'user_location' : curr_zone.zone_id,
							'last_update' : main.pretty_date(user[0].last_update),
							}							
			else: 	
				data = {'zone_id' : -1,
						'zone_name' : "Unknown", 
						'mac_address' : "Unkown", 
						'user' : user[0].short_name, 
						'user_location' : -1,
						'last_update' : main.pretty_date(user[0].last_update),
						}
		else: 	
			data = {'zone_id' : -1,
					'zone_name' : "Unknown", 
					'mac_address' : "Unknown", 
					'user' : "Unknown", 
					'user_location' : -1,
					'last_update' : "Unknown",
					}
		
		self.response.headers['Content-Type'] = "application/json"
		self.response.out.write(json.dumps(data))	
		
	

class MapHandler(webapp.RequestHandler):
	def get(self, zone_id):
		item = db.GqlQuery(("SELECT * FROM Areas " +
				"WHERE zone_id = :1"), int(zone_id))
		data = dict()
		if item.count() > 0 and item[0].maps.count() > 0:
			currmap = item[0].maps[0]
			data = {'map_name' : currmap.map_name, 'zone_name' : item[0].zone_name, 'zone_id' : zone_id}
		else:
			data = {'map_name' : 'Unknown', 'zone_name' : 'Unknown', 'zone_id' : zone_id}
		self.response.headers['Content-Type'] = "application/json"
		self.response.out.write(json.dumps(data))

