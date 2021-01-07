from django.shortcuts import render,get_object_or_404, redirect
from django.views import generic
from django.http import HttpResponse, Http404, HttpResponseRedirect
#from .models import 
from django.urls import reverse
from .models import ToDo_Entry
from django.utils import timezone

# Create your views here.
class IndexView(generic.ListView):
    template_name='todo/index.html'
    model = ToDo_Entry
    def post(self, request, *args, **kwargs):
        if request.method == "POST":
            if "id" in request.POST and request.POST["id"] != "":
                q=ToDo_Entry.objects.get(pk=request.POST["id"])
                q.todo_text=request.POST["todo_text"]
                #print(q.status)
                q.status=True if "status" in request.POST else False
                q.save()
            else:
                q=ToDo_Entry(todo_text=request.POST["todo_text"],pub_date=timezone.now(),status=False)
                q.save()
        return HttpResponseRedirect(reverse('todo:index'))

def update(request, id):
    q=ToDo_Entry.objects.filter(pk=id)
    return render(request, "todo/update.html",{"entry" : q[0]})

def delete(request, id):
    q=ToDo_Entry.objects.get(pk=id)
    q.delete()
    return HttpResponseRedirect(reverse('todo:index'))