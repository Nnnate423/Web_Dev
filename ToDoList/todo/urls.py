from django.urls import path
from . import views

app_name="todo"
urlpatterns = [
    path('', views.IndexView.as_view(), name="index"),
    path('<int:id>/', views.update, name="update"),
    path('<int:id>/delete', views.delete, name="delete"),
]