from google.appengine.ext import db

class Areas(db.Model):
	zone_id = db.IntegerProperty(required=True)
	zone_name = db.StringProperty(required=True)

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