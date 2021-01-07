from django.db import models

# Create your models here.
class ToDo_Entry(models.Model):
    def __str__(self):
        return str(self.pub_date)+" : "+self.todo_text
    todo_text = models.CharField(max_length=128)
    pub_date = models.DateTimeField('date_published')
    status = models.BooleanField('Done',default=False)