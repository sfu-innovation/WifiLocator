import cgi
import os
import datetime
import urllib
import wsgiref.handlers
import csv
import rest
from django.utils import simplejson as json

from src.models import Zones, ZoneNames, Friends, Areas, BSSIDZones, ZoneMaps, Users
from google.appengine.ext.webapp import template
from google.appengine.ext import db
from google.appengine.api import users
from google.appengine.ext import webapp
from google.appengine.ext.webapp.util import run_wsgi_app


class MainPage(webapp.RequestHandler):
	def get(self):
		'''
		areaReader = csv.reader(open(('surrey_data.csv'),'rU'), delimiter=',')
		
		for row in areaReader:
			Areas(zone_id=int(row[0]),zone_name=row[1]).put()
	
		
		macReader = csv.reader(open(('surrey_res.csv'),'rU'), delimiter=',')
		

    		for row in macReader:
			curr_area = Areas.all()
			temp = curr_area.filter("zone_id =", (int(row[1])+20))
			#print row[1]
			#print "\n"
			for area in temp:
				#print "[" + str(area.zone_id) + "]"
				BSSIDZones(zones = area, mac_address = row[0]).put()
			
		curr_area = Areas.all()
		temp = curr_area.filter("zone_id =", 10)
		for area in temp:
			ZoneMaps(zones = area, map_name = "twitter_icon.png").put()
		
		username = "Alex"
		user = db.GqlQuery(("SELECT * FROM Users " +
				"WHERE short_name = :1"), username)
		for this in user:
			Friends(user = this, friend_id = 10).put()
			Friends(user = this, friend_id = 13).put()
			Friends(user = this, friend_id = 14).put()
			Friends(user = this, friend_id = 12).put()
			break
		
		catherine = Users.get_by_id(28001)
		Friends(user = catherine, friend_id = 27001).put()
		friend = Users.get_by_id(27001)
		Friends(user = friend, friend_id = 28001).put()
		
		Friends(user = catherine, friend_id = 30001).put()
		friend = Users.get_by_id(30001)
		Friends(user = friend, friend_id = 28001).put()
		'''	
		
		bssid_query = db.GqlQuery("SELECT * "
				"FROM BSSIDZones ")
		area_query = db.GqlQuery("SELECT * "
						"FROM Areas "
						"ORDER BY zone_id")
		user_query = db.GqlQuery("SELECT * "
					"FROM Users ")
		friend_query = db.GqlQuery("SELECT * "
					"FROM Friends ")
		template_values = {
    		'bssid': bssid_query,
			'areas': area_query,
			'friends' : friend_query,
			'users' : user_query
		}
		path = os.path.join(os.path.dirname(__file__), 'index.html')
		self.response.out.write(template.render(path,template_values))
		

class BSSIDHandler(webapp.RequestHandler):
		
	def get(self, user_short_name, mac_address):
		data = {}
		#query users
		p = db.GqlQuery(("SELECT * FROM Users " +
				"WHERE short_name = :1"), urllib.unquote_plus(user_short_name))
		#get user id
		if p is not None:
			for user in p:
				user_id = user.key().id()
					
				q = db.GqlQuery(("SELECT * FROM BSSIDZones " +
				"WHERE mac_address = :1"), urllib.unquote_plus(mac_address))	
				if q is not None:
					for item in q:
						curr_zone = item.zones
						zone_id = curr_zone.zone_id
						
						#update user location
						user.last_location = curr_zone.key()
						user.put()
						
						#generating JSON data
						data = {'zone_id' : zone_id, 
								'zone_name' : curr_zone.zone_name, 
								'mac_address' : item.mac_address, 
								'user' : user.short_name, 
								'user_location' : curr_zone.zone_id,
								'last_update' : user.last_update.ctime(),
								}
					break							
											
				else: 	
					data = {'zone_id' : "INVALID",
							'zone_name' : "INVALID", 
							'mac_address' : item.mac_address, 
							'user' : user.short_name, 
							'user_location' : curr_zone.zone_id,
							'last_update' : user.last_update.ctime(),
							}
				break
		
		else: 	
			data = {'zone_id' : "N/A" ,
					'zone_name' : "N/A", 
					'mac_address' : mac_address, 
					'user' : "INVALID", 
					'user_location' : "N/A",
					'last_update' : "N/A",
					}
			
		
		self.response.headers['Content-Type'] = "application/json"
		self.response.out.write(json.dumps(data))	


class MapHandler(webapp.RequestHandler):
	def get(self, zone_id):
		q = db.GqlQuery(("SELECT * FROM Areas " +
				"WHERE zone_id = :1"), int(zone_id))
		for item in q:
			for currmap in item.maps:
				#print currmap.key().id()
				data = {'map_name' : currmap.map_name, 'zone_name' : item.zone_name, 'zone_id' : zone_id}
		
		self.response.headers['Content-Type'] = "application/json"
		self.response.out.write(json.dumps(data))


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
					data[name].append({'friend_name' : friendname,
									   'friend_location' : friend.last_location.zone_name,
									   'last_update' : friend.last_update.ctime()}
										)
		self.response.headers['Content-Type'] = "application/json"
		self.response.out.write(json.dumps(data))

class SetFriend(webapp.RequestHandler):
	def get(self):
		self.response.out.write("requires post or delete request")
	def post(self):
		json_obj = json.loads(self.request.body)
		userobj = db.GqlQuery(("SELECT * FROM Users " + "WHERE short_name = :1"), urllib.unquote_plus(json_obj["user_name"]))
		friendobj = db.GqlQuery(("SELECT * FROM Users " + "WHERE short_name = :1"), urllib.unquote_plus(json_obj["friend"]))
		Friends(user = userobj[0].key(), friend_id = friendobj[0].key().id()).put()
		self.response.out.write("post_Success")
	# def delete(self):
		# json_obj = json.loads(self.request.body)
		# friend_userobj = db.GqlQuery(("SELECT * FROM Users " + "WHERE short_name = :1"), urllib.unquote_plus(json_obj["friend"]))
		# friend_table_entry = db.GqlQuery(("SELECT * FROM Friends " + "WHERE friend_id = :1"), urllib.unquote_plus(friend_userobj.key().id()))
		# friend_table_entry.delete()
		# self.response.out.write("delete_Success")

class SetUser(webapp.RequestHandler):
	def get(self):
		self.response.out.write("requires post or delete request")
	def post(self):
		json_obj = json.loads(self.request.body)
		Users(first_name = json_obj["first_name"], short_name = json_obj["short_name"], last_name = json_obj["last_name"]).put()
		self.response.out.write("post_Success")
	# def delete(self):
		# json_obj = json.loads(self.request.body)
		# userobj = db.GqlQuery(("SELECT * FROM Users " + "WHERE short_name = :1"), urllib.unquote_plus(json_obj["short_name"]))
		# userobj.delete()
		# self.response.out.write("delete_Success")
	
application = webapp.WSGIApplication([('/', MainPage), 
				      ('/getzone/(.*)/(.*)', BSSIDHandler),
				      ('/getmap/(.*)', MapHandler),
				      ('/getfriends/(.*)', FriendHandler),
				      ('/rest/.*', rest.Dispatcher), 
					  ('/setfriend/', SetFriend),
					  ('/setuser/', SetUser)],
					debug=True)

# configure the rest dispatcher to know what prefix to expect on request urls
rest.Dispatcher.base_url = "/rest"

# add all models from the current module, and/or...
rest.Dispatcher.add_models_from_module(__name__)

# add all models from some other module, and/or...
#rest.Dispatcher.add_models_from_module(my_model_module)
# add specific models
#rest.Dispatcher.add_models({"zones": Zones})
  
# add specific models (with given names) and restrict the supported methods
#rest.Dispatcher.add_models({'zones' : (Zones, rest.READ_ONLY_MODEL_METHODS)})

# use custom authentication/authorization
#rest.Dispatcher.authenticator = MyAuthenticator()
#rest.Dispatcher.authorizer = MyAuthorizer()
                                     
def main():
    run_wsgi_app(application)

if __name__ == "__main__":
    main()