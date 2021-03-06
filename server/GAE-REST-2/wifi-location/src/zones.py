import cgi
import os
import datetime
import urllib
import wsgiref.handlers
import csv
import rest
import logging
from django.utils import simplejson as json
import main
from src.models import Zones, ZoneNames, Friends, Areas, BSSIDZones, ZoneMaps, Users

from google.appengine.ext import db
from google.appengine.ext import webapp
from google.appengine.ext.webapp.util import run_wsgi_app

def updateZone(self, json_obj):
	try:
		user_obj = Users.get_by_id(int(json_obj["user_id"]))
		data = {}
		#if user not valid
		if not user_obj :
			
			data = {'status' : 1,
					'zone_name' : "unknown", 
					'map_name' : "unknown", 
					'last_update' : "unknown"
					}
			
			self.response.headers['Content-Type'] = "application/json"
			self.response.out.write(json.dumps(data))
			return
		
		#get zone from mac address
		item = db.GqlQuery(("SELECT * FROM BSSIDZones " +
			"WHERE mac_address = :1"), urllib.unquote_plus(json_obj["mac_address"]))	
		if item.count() > 0:
			
			curr_zone = item[0].zones
			zone_id = curr_zone.zone_id

			#update user location
			user_obj.last_location = curr_zone.key()
			user_obj.put()
			
			
			if curr_zone.maps.count() > 0:
				map_name = curr_zone.maps[0].map_name
				

			#generating JSON data
				data = {'status' : 0,
						'zone_name' : curr_zone.zone_name, 
						'map_name' : map_name,
						'last_update' : main.pretty_date(user_obj.last_update),
						}	
				self.response.headers['Content-Type'] = "application/json"
				self.response.out.write(json.dumps(data))	
				return
			
			#map not found
			else: 
				data = {'status' : 3,
						'zone_name' : curr_zone.zone_name, 
						'map_name' : "unknown",
						'last_update' : main.pretty_date(user_obj.last_update),
						}	
				self.response.headers['Content-Type'] = "application/json"
				self.response.out.write(json.dumps(data))	
				return
		#zone unknown						
		else: 	
			data = {'status' : 2,
					'zone_name' : "unknown", 
					'last_update' : main.pretty_date(user_obj.last_update),
					'map_name' : "unknown"
					}
			self.response.headers['Content-Type'] = "application/json"
			self.response.out.write(json.dumps(data))	
			return
	
	except apiproxy_errors.OverQuotaError, message:
		logging.error(message)
		self.response.headers['Content-Type'] = "application/json"
		self.response.out.write(json.dumps({"status" : 10}))

class MapHandler(webapp.RequestHandler):
	def get(self, zone_id):
		try:
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
			
		except apiproxy_errors.OverQuotaError, message:
			logging.error(message)
			self.response.headers['Content-Type'] = "application/json"
			self.response.out.write(json.dumps({"status" : 10}))