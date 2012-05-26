import cgi
import os
import datetime
import urllib
import wsgiref.handlers
import csv
import rest
import main
from django.utils import simplejson as json

from src.models import *

from google.appengine.ext import db
from google.appengine.ext import webapp
from google.appengine.ext.webapp.util import run_wsgi_app


class FriendHandler(webapp.RequestHandler):
	def get(self, user_short_name):
		data = dict()
		p = db.GqlQuery(("SELECT * FROM Users " +
				"WHERE short_name = :1"), urllib.unquote_plus(user_short_name))
		if p is not None:
			for user in p:
				#thisUser = Users.get_by_id(int(user_id))
				name = user.short_name
				data = dict()
				data[name] = []
				q = db.GqlQuery(("SELECT * FROM Friends " +
						"WHERE user = :1"), user.key())
				for item in q:
					friend = Users.get_by_id(item.friend_id)
					friendname = friend.short_name
					if friend.last_location is not None:
						last_location = friend.last_location.zone_name
					else:
						last_location = "Unknown"
					
					data[name].append({'friend_name' : friendname,
									   'friend_location' : last_location,
									   'last_update' : main.pretty_date(friend.last_update)}
										)
		self.response.headers['Content-Type'] = "application/json"
		self.response.out.write(json.dumps(data))

class SetFriend(webapp.RequestHandler):
	def get(self):
		self.response.out.write("requires post or delete request")
	def post(self):
		json_obj = json.loads(self.request.body)
		
		userobj = db.GqlQuery(("SELECT * FROM Users " + "WHERE short_name = :1"), urllib.unquote_plus(json_obj["user_name"]))
		if userobj.count() <= 0:
			self.response.out.write("user_not_found")
			return
		friendobj = db.GqlQuery(("SELECT * FROM Users " + "WHERE short_name = :1"), urllib.unquote_plus(json_obj["friend"]))
		if userobj.count() <= 0:
			self.response.out.write("friend_not_found")
			return
			
		if json_obj["command"] == "addfriend":
			Friends(user = userobj[0].key(), friend_id = friendobj[0].key().id()).put()
			self.response.out.write("add_Success")
		elif json_obj["command"] == "deletefriend":
			friendentry = db.GqlQuery(("SELECT * FROM Friends " + "WHERE user = :1 AND friend_id = :2"), userobj[0].key(), friendobj[0].key().id())
			if friendentry.count() <= 0:
				self.response.out.write("entry_not_found")
			friendentry[0].delete()
			self.response.out.write("delete_success")
		else:
			self.response.out.write("invalid_command")

