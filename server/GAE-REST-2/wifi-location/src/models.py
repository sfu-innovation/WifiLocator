import cgi

from google.appengine.api import users
from google.appengine.ext import db
from google.appengine.ext import webapp
from google.appengine.ext.webapp import template
from google.appengine.ext.webapp.util import run_wsgi_app

from google.appengine.ext.db import djangoforms


class SuperZones(db.Model):
	super_zone_name =  db.StringProperty(required=True)


class Areas(db.Model):
	zone_id = db.IntegerProperty(required=False)
	zone_name = db.StringProperty(required=True)
	super_zone = db.ReferenceProperty(SuperZones, required = False)

class ZoneNames(db.Model):
	zone_id = db.IntegerProperty(required=True)
	zone_name = db.StringProperty(required=True)



class Zones(db.Model):
	#zone = db.ReferenceProperty(Zones, collection_name='addresses')
	zone_id = db.IntegerProperty(required=True)
	mac_address = db.StringProperty(required=True)
	#date_added = db.DateProperty()

class Users(db.Model):
	short_name = db.StringProperty(required=True)
	first_name = db.StringProperty(required=False)
	last_name = db.StringProperty(required=False)
	last_location = db.ReferenceProperty(Areas, collection_name='userlocation', required=False)
	last_update = db.DateTimeProperty(auto_now = True, required=False)
	signal_strength = db.IntegerProperty(required=False)
	
class Friends(db.Model):
	user = db.ReferenceProperty(Users, collection_name='friends')
	friend_id = db.IntegerProperty(required=True)
	
class BSSIDZones(db.Model):
	zones = db.ReferenceProperty(Areas, collection_name='bssid')
	mac_address = db.StringProperty(required=True)

class ZoneMaps(db.Model):
	zones = db.ReferenceProperty(Areas, collection_name='maps')
	map_name = db.StringProperty(required=True)
	
class FriendRequests(db.Model):
	user_id = db.IntegerProperty(required=True)
	friend_id = db.IntegerProperty (required=True)
	
class Events(db.Model):
	name =  db.StringProperty(required=True)
	organizer = db.StringProperty(required=True)
	start_time = db.DateTimeProperty(required=False, auto_now = False)
	end_time = db.DateTimeProperty(required=False, auto_now = False)
	location = db.StringProperty(required=False)
	category = db.StringProperty(required=True)
	contact_name =  db.StringProperty(required=False)
	contact_email = db.EmailProperty(required =True)
	description = db.TextProperty(required=False)
	super_zone = db.ReferenceProperty(SuperZones, collection_name='event_super_zone', required=False)
	
	
class EventForm(djangoforms.ModelForm):
	class Meta:
		model = Events