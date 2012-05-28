
import cgi
import os
import datetime
import urllib
import wsgiref.handlers
import csv
import rest
import logging 
from django.utils import simplejson as json

from src.models import *
from google.appengine.ext import db
from google.appengine.ext import webapp
from google.appengine.ext.webapp.util import run_wsgi_app
from google.appengine.runtime import apiproxy_errors


def acceptFriendRequest(self, json_obj):
	request = FriendRequests.get_by_id(int(json_obj["request_id"]))
	data = dict()
	#check if request exist
	if not request:
		data["status"] = 1
		self.response.headers['Content-Type'] = "application/json"
		self.response.out.write(json.dumps(data))
		return
	this_user = Users.get_by_id(request.user_id)
	friend =  Users.get_by_id(request.friend_id)
	#creates the two way relationship
	try: 
		
		new1 = Friends(user = this_user, friend_id = request.friend_id )
		new1.put()

		new2 = Friends(user = friend, friend_id = request.user_id )
		new2.put()
		
		logging.debug(this_user.short_name + " and " + friend.short_name + "are now friends")
		logging.debug("Friends IDs: " + str(new1.key().id()) +  " & " + str(new2.key().id()) )
		
	except:
		logging.error("fail creating friends")
		
	#delete the request in database
	try:
		
		db.delete(request)
		data["status"] = 0
		logging.debug("Request: " + str(json_obj["request_id"]) + " is removed." )
		self.response.headers['Content-Type'] = "application/json"
		self.response.out.write(json.dumps(data))
	except: 
		logging.error("fail to remove friendship request")
		
	