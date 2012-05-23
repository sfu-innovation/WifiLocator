from google.appengine.ext import db


class ZoneNames(db.Model):
	zone_id = db.IntegerProperty(required=True)
	zone_name = db.StringProperty(required=True)

class Zones(db.Model):
	#zone = db.ReferenceProperty(Zones, collection_name='addresses')
	zone_id = db.IntegerProperty(required=True)
	mac_address = db.StringProperty(required=True)
	#date_added = db.DateProperty()
	
class Friends(db.Model):
	user_name = db.StringProperty(required=True)
	friend_name = db.StringProperty(required=False)
	
class Areas(db.Model):
	zone_id = db.IntegerProperty(required=True)
	zone_name = db.StringProperty(required=True)

class BSSIDZones(db.Model):
	zones = db.ReferenceProperty(Areas, collection_name='bssid')
	mac_address = db.StringProperty(required=True)

