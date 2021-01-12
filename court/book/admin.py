from django.contrib import admin
from .models import *
# Register your models here.
admin.site.register(court)
admin.site.register(order)
admin.site.register(slot)
admin.site.register(review)