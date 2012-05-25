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
		
		user_obj = Users.get_by_id(json_obj["user_id"])
		friend_obj = Users.get_by_id(json_obj["friend_id"])
		#check if user or friend is valid
		if not user_obj :
			self.response.out.write("user_not_found")
			return
		elif not friend_obj:
			self.response.out.write("friend_not_found")
			return
		
		#check if request already exist
		q = db.GqlQuery(("SELECT * FROM FriendRequests " + "WHERE user_id = :1 and friend_id = :2" ), int(json_obj["user_id"]),int(json_obj["friend_id"]))
		#print q.count()
		#print q[0]
		if q.count() > 0:
			self.response.out.write("request_existed")
			return

		#sends request	
		request = FriendRequests(user_id = json_obj["user_id"], friend_id = json_obj["friend_id"])
		request.put()
		#self.response.out.write("request_sent")
		self.response.headers['Content-Type'] = "application/json"
		self.response.out.write(json.dumps({"request_id" : request.key().id()}))
		
class GetRequests(webapp.RequestHandler):
	def get(self, user_id):
		user_obj = Users.get_by_id(int(user_id))
		if not user_obj :
			self.response.out.write("user_not_found")
			return
		
		q = db.GqlQuery(("SELECT * FROM FriendRequests " + "WHERE user_id = :1"), int(user_id))
		data = dict()
		data["Requests"] = []
		for requests in q:
			friend = Users.get_by_id(requests.friend_id)
			friendname = friend.short_name			
			data["Requests"].append({'friend_name' : friendname,
								'request_id' : str(requests.key().id())})
								
		

		self.response.headers['Content-Type'] = "application/json"
		self.response.out.write(json.dumps(data))
		
		
class acceptRequests(webapp.RequestHandler):
	def post(self, request_id):
		
		#check if request exist
		
		request = FriendRequests.get_by_id(int(request_id))
		if not request:
			self.response.out.write("request_not_found")
			return
		this_user = Users.get_by_id(request.user_id)
		friend =  Users.get_by_id(request.friend_id)
		#creates the two way relationship
		Friends(user = this_user, friend_id = request.friend_id ).put()
		Friends(user = friend, friend_id = request.user_id ).put()
		#delete the request in database
		db.delete(request)
		self.response.out.write("%s and %s are now friends."  % (this_user.short_name , friend.short_name))
		