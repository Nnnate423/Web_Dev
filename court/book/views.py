from django.shortcuts import render,redirect
from django.http import HttpResponse, Http404, HttpResponseRedirect
#from .models import 
from django.urls import reverse
from django.utils import timezone
from django.contrib.auth.forms import UserCreationForm
from django.contrib.auth import authenticate, login, logout
from .forms import CreateUserForm
from django.contrib import messages

# Create your views here.
def Index(request):
    return render(request,'book/index.html')

def RegisterPage(request):
    form = CreateUserForm()
    if request.method == 'POST':
        form = CreateUserForm(request.POST)
        if form.is_valid():
            form.save()
            user=form.cleaned_data.get('username')
            messages.success(request, 'account created successfully: '+ user)
            return redirect('book:login')
    context={'form': form}
    return render(request,'accounts/register.html',context)

def LoginPage(request):
    context={}
    if request.method == 'POST':
        username= request.POST.get('username')
        pw = request.POST.get('password')
        user = authenticate(request,username=username,password=pw)
        if user is not None:
            login(request,user)
            return redirect('book:index')
        else:
            messages.info(request, 'username or password not correct')
    return render(request,'accounts/login.html',context)

def Logout(request):
    context={}
    logout(request)
    return redirect('book:login')