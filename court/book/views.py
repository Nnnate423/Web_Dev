from django.shortcuts import render
from django.http import HttpResponse, Http404, HttpResponseRedirect
#from .models import 
from django.urls import reverse
from django.utils import timezone

# Create your views here.
def index(request):
    return render(request,'book/index.html')