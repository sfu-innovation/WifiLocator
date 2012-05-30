import cgi
import os
import datetime
import urllib
import wsgiref.handlers
import csv
import rest
from google.appengine.runtime import apiproxy_errors
from django.utils import simplejson as json
from datetime import datetime

from src.models import *
from google.appengine.ext import db
from google.appengine.ext import webapp
from google.appengine.ext.webapp.util import run_wsgi_app

class CSVImporter(webapp.RequestHandler):
	def get(self):
		'''
		try:
			surrey_zones = [3007,4006,7007,13004,14005,15005]
			
			for i in surrey_zones:
			#areaReader = csv.reader(open(('surrey_data.csv'),'rU'), delimiter=',')
				curr_zone = Areas.get_by_id(i)
				#print curr_zone.key().id()
				removelist = db.GqlQuery("SELECT * FROM BSSIDZones " + "WHERE zones = :1" , curr_zone)
				#print removelist.count()
				if removelist.count() > 0:
				
					for k in removelist:
						db.delete(k)
					print "zone: " + str(i) + " deleted"	
				else: 
					print "zone not found"
					return
			#for row in areaReader:
			#	Areas(zone_id=int(row[0]),zone_name=row[1]).put()
			#	print "areas imported"
		except:
			print "delete fail"	
		
		#path = os.path.join(os.path.dirname(__file__) + '/../csv/', 'events.csv')
		time1 = "30 May 2012 11:00"	
		time2 = "1 Jun 2012 20:00"
		print "hellw?"
		print time2
		newtime = datetime.strptime(time2, "%d %b %Y %H:%M")
		print datetime.ctime(newtime)
		'''
		
		path = os.path.join(os.path.dirname(__file__) + '/../csv/', 'events.csv')
		csvReader = csv.reader(open(path,'rU'), delimiter=',')
		#read the first row of header
		header = csvReader.next()
		#print header[5]
		
		for row in csvReader:
			#time =  (row[5][5:22]).rstrip()
			#print "original: " + time
			#print len(time)
			#newtime = datetime.strptime(time, "%d %b %Y %H:%M")
			#print "formatted: " + datetime.ctime(newtime)


			time =  (row[5][5:22]).rstrip()
			formatedtime = datetime.strptime(time, "%d %b %Y %H:%M")

			area = Areas.get_by_id(int(row[7]))
		
			
			temp = Events(name = row[0],
					organizer = row[1],
					 category = row[2],
					 contact_name = row[3],
					 contact_email = row[4],
					 datetime = formatedtime,
					 description = row[6],
					 zone = area)
			temp.put()
			print "Event: " + row[0] + " is added."
		#print "event imported"

	
