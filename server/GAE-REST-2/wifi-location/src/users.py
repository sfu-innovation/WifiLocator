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

class SetUser(webapp.RequestHandler):
	def get(self):
		data = {'status' : 1, 'message' : 'requires post request'}
		self.response.out.write(json.dumps(data))
		
	def post(self):
		try:
			json_obj = json.loads(self.request.body)
			self.response.headers['Content-Type'] = "application/json"
			data = {}
			
			# check if user exists
			userobj = db.GqlQuery(("SELECT * FROM Users " + "WHERE short_name = :1"), urllib.unquote_plus(json_obj["short_name"]))
			if json_obj["command"] == "adduser" and userobj.count() > 0:
				data = {'status' : 1, 'message' : 'user already exists'}
				self.response.out.write(json.dumps(data))
				return
			elif json_obj["command"] == "deleteuser" and userobj.count() <= 0:
				data = {'status' : 1, 'message' : 'user not found'}
				self.response.out.write(json.dumps(data))
				return
			
			# preform operation
			if json_obj["command"] == "adduser":
				Users(first_name = json_obj["first_name"], short_name = json_obj["short_name"], last_name = json_obj["last_name"]).put()
				data = {'status' : 0, 'message' : 'user added successfully'}
			elif json_obj["command"] == "deleteuser":
				# delete all friend relationships associated with the user
				# (Jordan: this case is WIP at the moment)
				#friend_relationships = db.GqlQuery(("SELECT * FROM Friends " + "WHERE user_id = :1"), userobj[0].key())
				#if friend_relationships.count() > 0:
				#	friend_relationships[0].delete()
				#for relationship in friend_relationships:
				#	relationship.delete()

				# delete user
				userobj[0].delete()
				data = {'status' : 0, 'message' : 'user deleted successfully'}
			else:
				data = {'status' : 1, 'message' : 'invalid command'}
			self.response.out.write(json.dumps(data))
		except apiproxy_errors.OverQuotaError, message:
			logging.error(message)
			self.response.headers['Content-Type'] = "application/json"
			self.response.out.write(json.dumps({"status" : 10}))

class GetUserId(webapp.RequestHandler):
	def get(self):
		data = {'status' : 1, 'message' : 'requires post request'}
		self.response.out.write(json.dumps(data))
	def post(self):
		try:
			json_obj = json.loads(self.request.body)
			self.response.headers['Content-Type'] = "application/json"
			data = {}
			
			userobj = db.GqlQuery(("SELECT * FROM Users " + "WHERE short_name = :1"), urllib.unquote_plus(json_obj["short_name"]))
			if userobj.count() <= 0:
				data = {'status' : 1, 'message' : 'user not found'}
			else:
				data = {'status' : 0, 'message' : 'user found', 'user_id': userobj[0].key().id()}
			self.response.out.write(json.dumps(data))
		except apiproxy_errors.OverQuotaError, message:
			logging.error(message)
			self.response.headers['Content-Type'] = "application/json"
			self.response.out.write(json.dumps({"status" : 10}))