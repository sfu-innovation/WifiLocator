import cgi
import os
import datetime
import urllib
import wsgiref.handlers
import csv
import rest
import logging
from google.appengine.runtime import apiproxy_errors
from django.utils import simplejson as json

from src.models import *
from google.appengine.ext import db
from google.appengine.ext import webapp
from google.appengine.ext.webapp.util import run_wsgi_app

def getEvents(self, json_obj):
	
	user_obj = Users.get_by_id(int(json_obj["user_id"]))
	bssid = urllib.unquote_plus(json_obj["mac_address"])
	data = dict()
	data["event_list"] = []
	item = db.GqlQuery(("SELECT * FROM BSSIDZones " +
		"WHERE mac_address = :1"), bssid)	
	if item.count() = 0:
		data["status"] = 1
		logging.debug("BSSID unknown, zone not found")
		self.response.headers['Content-Type'] = "application/json"
		self.response.out.write(json.dumps(data))
		logging.debug("BSSID unknown, zone not found")
		return
	
	
	curr_zone = item[0]
	#updates user location
	
	user_obj.last_location = curr_zone.key()
	user_obj.put()
	logging.debug("user information updated")
	
	eventlist = db.GqlQuery(("SELECT * FROM Events " +
		"WHERE zone = :1"), curr_zone)
	#no event found in that zone
	if eventlist.count() = 0:
		data["status"] = 2
		logging.debug("no event found")
		self.response.headers['Content-Type'] = "application/json"
		self.response.out.write(json.dumps(data))
		return
		
		
	for item in eventlist:
		event_name = item.name
		event_date = item.date
		event_time = item.time
		event_org = item.organizer
			
		data["status"] = 0
		data["event_list"].append({'event_name' : event_name,
							'event_org' : event_org,
						   'event_date' : event_date,
						   'event_time' : event_time}
							)
	self.response.headers['Content-Type'] = "application/json"
	self.response.out.write(json.dumps(data))

	
	
		
		

