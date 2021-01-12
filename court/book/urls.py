from django.urls import path
from . import views

app_name='book'
urlpatterns = [
    path('', views.Index,name='index'),
    path('courts/', views.CourtsView, name='courts'),
    path('book_courts/', views.BookView.as_view(), name='book_courts'),
    path('book_courts/transact', views.DoBook, name='do_book'),
    path('my_booking/', views.MyBookingView, name='my_booking'),
    path('aboutus/', views.AboutView,name = 'about'),
    #path('book/')
    path('register/',views.RegisterPage, name = 'register'),
    path('login/',views.LoginPage, name = 'login'),
    path('logout/',views.Logout, name = 'logout'),
]