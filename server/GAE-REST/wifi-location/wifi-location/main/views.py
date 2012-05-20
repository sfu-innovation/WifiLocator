import datetime

from django.http import HttpResponse

from djangocat.main.models import Visitor

def main(request):
    visitor = Visitor()
    visitor.ip = request.META['REMOTE_ADDR']
    visitor.put()
    
    result = ''
    visitors = Visitor.all()
    results = visitors.order('-added_on')

    for visitor in results.fetch(limit=40):
        result += visitor.ip + u' visited on ' + unicode(visitor.added_on) + '<br/>'
    return HttpResponse(result)

