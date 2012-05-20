import cgi
import os
import datetime
import urllib
import wsgiref.handlers
import csv
import rest

from google.appengine.ext.webapp import template
from google.appengine.ext import db
from google.appengine.api import users
from google.appengine.ext import webapp
from google.appengine.ext.webapp.util import run_wsgi_app




class MainPage(webapp.RequestHandler):
    def get(self):
		#csvReader = csv.reader(open('data.csv'), delimiter=",")
    	
    	test_query = db.GqlQuery("SELECT * "
                            "FROM Zones ")

    	test = test_query.fetch(2)
    	template_values = {
    		'foo': "Catherine",
    		'test': test,
    	}
        path = os.path.join(os.path.dirname(__file__), 'index.html')
        self.response.out.write(template.render(path,template_values))

class Zones(db.Model):
	zone_id = db.IntegerProperty(required=True, choices=set([1,2,3,4,5,6,7,8]))
	mac_address = db.StringProperty(required=True,)
	date_added = db.DateProperty()
	


def render_to_response(tmpl, data):
    t = loader.get_template(tmpl)
    c = Context(data)
    return HttpResponse(t.render(c))

application = webapp.WSGIApplication([('/', MainPage), ('/rest/.*', rest.Dispatcher)], debug=True)

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