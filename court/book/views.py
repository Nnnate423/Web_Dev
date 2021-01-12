from django.shortcuts import render,redirect
from django.http import HttpResponse, Http404, HttpResponseRedirect
from .models import *
from .forms import *
from django.urls import reverse
from django.utils import timezone
from django.views import View
from django.contrib.auth import authenticate, login, logout
from django.contrib import messages
from django.contrib.auth.mixins import LoginRequiredMixin


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
            return redirect('book:my_booking')
        else:
            messages.info(request, 'username or password not correct')
    return render(request,'accounts/login.html',context)

def Logout(request):
    context={}
    logout(request)
    return redirect('book:login')

def CourtsView(request):
    context={}
    all_court = court.objects.all()
    context["courts"] = all_court
    return render(request, "book/courts.html", context)

class BookView(LoginRequiredMixin,View):
    login_url = 'book:login'
    def get(self,request):
        context={}
        form= SearchForm()
        context["searchform"] = form
        return render(request, "book/book_courts.html",context)
    
    def post(self,request):
        context={}
        if request.POST['select1']=="1":
            if_indoor=[True,False]
        else: 
            if_indoor = [True] if request.POST['select1'] == "2" else [False]

        if request.POST['select2']=="1":
            if_std=[True,False]
        else: 
            if_std = [True] if request.POST['select2'] == "2" else [False]
        ids=court.objects.filter(indoor__in = if_indoor, std__in = if_std)
        slots = slot.objects.select_related().filter(courtid__in = ids)
        context['slots'] = slots[:]
        return render(request, "book/book_courts.html",context)

def DoBook(request):
    context={}
    if request.method == 'POST':
        slot_int=int(request.POST.get('slot_num','0'))
        if slot_int not in [1,2,3]:
            messages.info(request, 'you did not select any slots')
            return redirect('book:book_courts')
        slot_id=int(request.POST.get('slot_id',0))
        the_slot=slot.objects.get(pk=slot_id)
        #username=request.POST.get('username')
        update_slot = slot.objects.get(pk=slot_id)
        if slot_int == 1:
            update_slot.slot1=True
        elif slot_int ==2:
            update_slot.slot2=True
        else:
            update_slot.slot3=True
        update_slot.save()
        new_order = order(username= request.user,slotid=the_slot, slot_num=slot_int)
        new_order.save()
        print(update_slot,new_order)
        return redirect('book:my_booking')
    else:
        return redirect('book:book_courts')

def MyBookingView(request):
    context={}
    update_slot = slot.objects.get(pk=1)
    update_slot
    if request.user.is_authenticated:
        orders= order.objects.filter(username=request.user)
        context['orders']=orders[:]
        return render(request,"book/my_booking.html",context)
    else:
        return redirect('book:login')

def AboutView(request):
    return render(request,'book/about_us.html')