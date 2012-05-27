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


class SendRequest(webapp.RequestHandler):
	def post(self):
		json_obj = json.loads(self.request.body)
		try:
			user_obj = Users.get_by_id(int(json_obj["user_id"]))
			friend_obj = Users.get_by_id(int(json_obj["friend_id"]))
		
		#check if user or friend is valid
			if not user_obj :
				self.response.headers['Content-Type'] = "application/json"
				self.response.out.write(json.dumps({"request_id" : "unknown", "status" : 1}))
				return
			elif not friend_obj:
				self.response.headers['Content-Type'] = "application/json"
				self.response.out.write(json.dumps({"request_id" : "unknown", "status" : 2}))
				return
				
			#check if request already exist
			q = db.GqlQuery(("SELECT * FROM FriendRequests " + "WHERE user_id = :1 and friend_id = :2" ), int(json_obj["friend_id"]),int(json_obj["user_id"]))
			#print q.count()
			#print q[0]
			if q.count() > 0:
				self.response.headers['Content-Type'] = "application/json"
				self.response.out.write(json.dumps({"request_id" : q[0].key().id(), "status" : 3}))
				return
			
			
			p = db.GqlQuery(("SELECT * FROM FriendRequests " + "WHERE user_id = :1 and friend_id = :2" ), int(json_obj["user_id"]),int(json_obj["friend_id"]))
			if p.count() > 0:
				self.response.headers['Content-Type'] = "application/json"
				self.response.out.write(json.dumps({"request_id" : p[0].key().id(), "status" : 3}))
				return
				
			#sends request	   
			request = FriendRequests(user_id = json_obj["friend_id"], friend_id = json_obj["user_id"])
			request.put()
			#self.response.out.write("request_sent")
			self.response.headers['Content-Type'] = "application/json"
			self.response.out.write(json.dumps({"request_id" : request.key().id(), "status" : 0}))
			
		except apiproxy_errors.OverQuotaError, message:
			logging.error(message)
			self.response.headers['Content-Type'] = "application/json"
			self.response.out.write(json.dumps({"request_id" : "unknown", "status" : 10}))
		
class GetRequests(webapp.RequestHandler):
	def post(self):
		json_obj = json.loads(self.request.body)
		user_obj = Users.get_by_id(int(json_obj["user_id"]))
		data = dict()
		data["requests"] = []
		if not user_obj :
			#data["Requests"].append({"request_id" : "unknown"})
			data["status"] = 1
			self.response.headers['Content-Type'] = "application/json"
			self.response.out.write(json.dumps(data))
			return

		q = db.GqlQuery(("SELECT * FROM FriendRequests " + "WHERE user_id = :1"), int(json_obj["user_id"]))
		if q.count() == 0:
			data["status"] = 2
			self.response.headers['Content-Type'] = "application/json"
			self.response.out.write(json.dumps(data))
			return
		for requests in q:
			friend = Users.get_by_id(requests.friend_id)
			friendname = friend.short_name			
			data["requests"].append({'friend_name' : friendname,
								'request_id' : str(requests.key().id())})

			data["status"] = 0
		self.response.headers['Content-Type'] = "application/json"
		self.response.out.write(json.dumps(data))		
		
class acceptRequests(webapp.RequestHandler):
	def post(self):
		json_obj = json.loads(self.request.body)
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