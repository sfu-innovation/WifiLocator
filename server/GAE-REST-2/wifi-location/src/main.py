import cgi
import os
import datetime
import urllib
import wsgiref.handlers
import csv
import rest
from django.utils import simplejson as json

from src.models import *
from src.friends import *
from src.zones import *
from src.requests import *
from src.users import *

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


def pretty_date(time=False):
		"""
		Get a datetime object or a int() Epoch timestamp and return a
		pretty string like 'an hour ago', 'Yesterday', '3 months ago',
		'just now', etc
		"""
		from datetime import datetime
		now = datetime.now()
		if type(time) is int:
		    diff = now - datetime.fromtimestamp(time)
		elif isinstance(time,datetime):
		    diff = now - time 
		elif not time:
		    diff = now - now
		second_diff = diff.seconds
		day_diff = diff.days

		if day_diff < 0:
		    return ''

		if day_diff == 0:
		    if second_diff < 10:
		        return "just now"
		    if second_diff < 60:
		        return str(second_diff) + " seconds ago"
		    if second_diff < 120:
		        return  "a minute ago"
		    if second_diff < 3600:
		        return str( second_diff / 60 ) + " minutes ago"
		    if second_diff < 7200:
		        return "an hour ago"
		    if second_diff < 86400:
		        return str( second_diff / 3600 ) + " hours ago"
		if day_diff == 1:
		    return "Yesterday"
		if day_diff < 7:
		    return str(day_diff) + " days ago"
		if day_diff < 31:
		    return str(day_diff/7) + " weeks ago"
		if day_diff < 365:
		    return str(day_diff/30) + " months ago"
		return str(day_diff/365) + " years ago"
		

	
application = webapp.WSGIApplication([('/', MainPage), 
				      ('/getzone/(.*)/(.*)', BSSIDHandler),
				      ('/getmap/(.*)', MapHandler),
				      ('/getfriends/(.*)', FriendHandler),
				      ('/rest/.*', rest.Dispatcher), 
					  ('/setfriend/', SetFriend),
					  ('/setuser/', SetUser),
					  ('/sendrequest/', SendRequest),
					  ('/getrequests/', GetRequests),
					  ('/acceptrequest/(.*)',acceptRequests)],
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