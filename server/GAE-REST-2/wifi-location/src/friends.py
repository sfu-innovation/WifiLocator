import cgi
import os
import datetime
import urllib
import wsgiref.handlers
import csv
import rest
import main
import logging
from django.utils import simplejson as json

from src.models import *

from google.appengine.ext import db
from google.appengine.ext import webapp
from google.appengine.ext.webapp.util import run_wsgi_app

def getFriendList(self, json_obj):
	try:
		user = Users.get_by_id(int(json_obj["user_id"]))
		data = dict()
		#check if user is valid
		if not user: 
			data["status"] = 1
			self.response.headers['Content-Type'] = "application/json"
			self.response.out.write(json.dumps(data))
			return

		#thisUser = Users.get_by_id(int(user_id))
		name = user.short_name
		data["friend_list"] = []
		data["status"] = 2
		q = db.GqlQuery(("SELECT * FROM Friends " +
				"WHERE user = :1"), user.key())
		for item in q:
			friend = Users.get_by_id(item.friend_id)
			friend_first_name = friend.first_name
			friend_last_name = friend.last_name		
			map_name = "unknown"
			last_location = "unknown"
			if friend.last_location is not None:
				last_location = friend.last_location.zone_name
				
				zonemap = db.GqlQuery(("SELECT * FROM ZoneMaps " +
					"WHERE zones = :1"), friend.last_location.key())
				if zonemap.count() > 0:
					map_name = str(zonemap[0].map_name)
			
			data["status"] = 0
			data["friend_list"].append({
								'first_name' : friend_first_name,
								'last_name' : friend_last_name,
							   'friend_location' : last_location,
							   'last_update' : main.pretty_date(friend.last_update),
							   'map_name' : map_name}
								)
		self.response.headers['Content-Type'] = "application/json"
		self.response.out.write(json.dumps(data))
	except apiproxy_errors.OverQuotaError, message:
		logging.error(message)
		self.response.headers['Content-Type'] = "application/json"
		self.response.out.write(json.dumps({"status" : 10}))
		

class SetFriend(webapp.RequestHandler):
	def get(self):
		data = {'status' : 1, 'message' : 'requires post request'}
		self.response.out.write(json.dumps(data))
	def post(self):
		try:
			json_obj = json.loads(self.request.body)
			self.response.headers['Content-Type'] = "application/json"
			data = {}
			
			# get user
			userobj = db.GqlQuery(("SELECT * FROM Users WHERE short_name = :1"), urllib.unquote_plus(json_obj["user_name"]))
			if userobj.count() <= 0:
				data = {'status' : 1, 'message' : 'user not found'}
				self.response.out.write(json.dumps(data))
				return
			
			# get friend
			friendobj = db.GqlQuery(("SELECT * FROM Users WHERE short_name = :1"), urllib.unquote_plus(json_obj["friend"]))
			if userobj.count() <= 0:
				data = {'status' : 1, 'message' : 'friend not found'}
				self.response.out.write(json.dumps(data))
				return
				
			# check if friendship relationship already exists
			friendentry = db.GqlQuery(("SELECT * FROM Friends WHERE user = :1 and friend_id = :2"), 
				userobj[0].key(), friendobj[0].key().id())
			friendentry2 = db.GqlQuery(("SELECT * FROM Friends WHERE user = :1 and friend_id = :2"), 
				friendobj[0].key(), userobj[0].key().id())
			if friendentry.count() > 0 and json_obj["command"] == "addfriend":
				data = {'status' : 1, 'message' : 'entry already exists'}
				self.response.out.write(json.dumps(data))
				return
			elif (friendentry.count() <= 0 or friendentry2.count() <= 0) and json_obj["command"] == "deletefriend":
				data = {'status' : 1, 'message' : 'no entry found to delete'}
				self.response.out.write(json.dumps(data))
				return
				
			# preform operation
			if json_obj["command"] == "addfriend":
				Friends(user = userobj[0].key(), friend_id = friendobj[0].key().id()).put()
				Friends(user = friendobj[0].key(), friend_id = userobj[0].key().id()).put()
				data = {'status' : 0, 'message' : 'add friend successful'}
			elif json_obj["command"] == "deletefriend":
				friendentry[0].delete()
				friendentry2[0].delete()
				data = {'status' : 0, 'message' : 'delete friend successful'}
			else:
				data = {'status' : 1, 'message' : 'command invalid'}
				
			self.response.out.write(json.dumps(data))
			
		except apiproxy_errors.OverQuotaError, message:
			logging.error(message)
			self.response.headers['Content-Type'] = "application/json"
			self.response.out.write(json.dumps({"status" : 10}))
		