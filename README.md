# Web_Dev

## 1. Django

### 1.1 Model View Controller
3 functions of a web app
* Model\
    persistent data kept in data store
* View\
    html, css etc. -> look and feel of an app
* Controller\
    code that does thinking and decision making.

### 1.2 Django data model 
* object relational mapping\
    allow us to map tables to objects and columns\
    we use those objects to store and retrieve data from database\
    improve portability across database\
    can only write python code for database
    ```
    #model.py: inside application folder
    from django.db import models

    class User(models.Model):
        name = models.CharField(max_length=128)
        email = models.CharField(max_length=128)
    ```
    equivalent to  SQL:
    ```
    CREATE TABLE Users(
        name VARCHAR(128),
        email VARCHAR(128)
    );
    ```
    * migration\
    "python manage.py makemigrations" -> make migration file using model.py\
    migration is guided by settings.py\
    "python manage.py migrate" -> apply these change\
    reads migrations folders in app folder and creates/evolves tables in db

    * insert a record
    ```
    > python manage.py shell
    from usermodel.models import User
    u=User(name="xx",email="xx")
    u.save()
    ```

    * CRUD 
    ```
    from usermodel.models import User

    #SELECT*
    User.objects.values()
    #Select WHERE
    User.objects.filter(name="xx").values()
    #DELETE
    User.objects.filter(name="xx").delete()
    #UPDATE
    User.objects.filter(name="xx").update(email="yy")
    #ORDER BY
    User.objects.values().order_by("email")
    ```
