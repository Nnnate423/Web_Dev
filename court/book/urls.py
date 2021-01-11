from django.urls import path
from . import views

app_name='book'
urlpatterns = [
    path('', views.Index,name='index'),
    path('courts/', views.Index, name='courts'),
    #path('book/')
    path('register/',views.RegisterPage, name = 'register'),
    path('login/',views.LoginPage, name = 'login'),
    path('logout/',views.Logout, name = 'logout'),
]