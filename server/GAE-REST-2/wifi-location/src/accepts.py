
import cgi
import os
import datetime
import urllib
import wsgiref.handlers
import csv
import rest
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
	Friends(user = this_user, friend_id = request.friend_id ).put()
	Friends(user = friend, friend_id = request.user_id ).put()
	#delete the request in database
	db.delete(request)
	data["status"] = 0
	self.response.headers['Content-Type'] = "application/json"
	self.response.out.write(json.dumps(data))