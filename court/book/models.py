from django.db import models
from django.contrib.auth.models import User

# Create your models here.
class court(models.Model):
    def __str__(self):
        return self.courtname + " ID: " +str(self.id)
    courtname = models.CharField(max_length = 128)
    location = models.CharField(max_length = 128)
    indoor = models.BooleanField()
    std = models.BooleanField()

class slot(models.Model):
    def __str__(self):
        return str(self.id) + " courts ID: " + str(self.courtid) 
    courtid = models.ForeignKey(court, on_delete = models.CASCADE)
    day = models.DateField("court_date")
    slot1 = models.BooleanField(default=False)
    slot2 = models.BooleanField(default=False)
    slot3 = models.BooleanField(default=False)

class order(models.Model):
    def __str__(self):
        return str(self.id) + " uname: "+ str(self.username) + "slots: "+ str(self.slotid) + "  " + str(self.slot_num)
    username = models.ForeignKey(User, on_delete = models.CASCADE)
    slotid = models.ForeignKey(slot, on_delete = models.CASCADE)
    slot_num = models.IntegerField(default= 0)

class review(models.Model):
    def __str__(self):
        return "orderid: " + str(self.orderid) + " courtid: "+ str(self.courtid) + " rate: "+ str(self.rate)
    orderid = models.ForeignKey(order, on_delete = models.CASCADE)
    courtid = models.ForeignKey(court, on_delete = models.CASCADE)
    rate = models.FloatField(default=0)
