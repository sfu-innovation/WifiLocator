from google.appengine.ext import db

class Visitor(db.Model):
    ip = db.StringProperty()
    added_on = db.DateTimeProperty(auto_now_add=True)

