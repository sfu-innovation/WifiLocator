from django.conf.urls.defaults import *

urlpatterns = patterns('',
    (r'^$', 'djangocat.main.views.main'),
)
