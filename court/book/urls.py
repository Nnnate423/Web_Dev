from django.urls import path
from . import views

#app_name='book'
urlpatterns = [
    path('', views.index,name='index'),
    path('courts/', views.index, name='courts'),
    #path('book/')
]