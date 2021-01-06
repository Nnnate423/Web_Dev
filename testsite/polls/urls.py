from django.urls import path

from . import views
#means from polls import views.

urlpatterns = [
    path('', views.index, name='index'),
]