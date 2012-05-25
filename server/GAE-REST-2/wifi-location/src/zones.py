import cgi
import os
import datetime
import urllib
import wsgiref.handlers
import csv
import rest
from django.utils import simplejson as json

from src.models import Zones, ZoneNames, Friends, Areas, BSSIDZones, ZoneMaps, Users

from google.appengine.ext import db
from google.appengine.ext import webapp
from google.appengine.ext.webapp.util import run_wsgi_app

class BSSIDHandler(webapp.RequestHandler):
	
	def get(self, user_short_name, mac_address):
		def pretty_date(time=False):
			"""
			Get a datetime object or a int() Epoch timestamp and return a
			pretty string like 'an hour ago', 'Yesterday', '3 months ago',
			'just now', etc
			"""
			from datetime import datetime
			now = datetime.now()
			if type(time) is int:
			    diff = now - datetime.fromtimestamp(time)
			elif isinstance(time,datetime):
			    diff = now - time 
			elif not time:
			    diff = now - now
			second_diff = diff.seconds
			day_diff = diff.days
			
			if day_diff < 0:
			    return ''
			
			if day_diff == 0:
			    if second_diff < 10:
			        return "just now"
			    if second_diff < 60:
			        return str(second_diff) + " seconds ago"
			    if second_diff < 120:
			        return  "a minute ago"
			    if second_diff < 3600:
			        return str( second_diff / 60 ) + " minutes ago"
			    if second_diff < 7200:
			        return "an hour ago"
			    if second_diff < 86400:
			        return str( second_diff / 3600 ) + " hours ago"
			if day_diff == 1:
			    return "Yesterday"
			if day_diff < 7:
			    return str(day_diff) + " days ago"
			if day_diff < 31:
			    return str(day_diff/7) + " weeks ago"
			if day_diff < 365:
			    return str(day_diff/30) + " months ago"
			return str(day_diff/365) + " years ago"
	
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
							'last_update' : pretty_date(user[0].last_update),
							}							
			else: 	
				data = {'zone_id' : -1,
						'zone_name' : "Unknown", 
						'mac_address' : "Unkown", 
						'user' : user[0].short_name, 
						'user_location' : -1,
						'last_update' : pretty_date(user[0].last_update),
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
		q = db.GqlQuery(("SELECT * FROM Areas " +
				"WHERE zone_id = :1"), int(zone_id))
		for item in q:
			for currmap in item.maps:
				#print currmap.key().id()
				data = {'map_name' : currmap.map_name, 'zone_name' : item.zone_name, 'zone_id' : zone_id}
		
		self.response.headers['Content-Type'] = "application/json"
		self.response.out.write(json.dumps(data))

