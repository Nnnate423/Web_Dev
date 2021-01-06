# Web_Dev

## 1. Django
learning note of coursera course: Django for everyone
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

### 1.3 View and URLs
django looks at urls.py to select view for every incoming requests
* the view form views.py
    * handle incoming data -> copy to database using ORM
    * retrieve data form db and render page
    * produce HTML -> return it to browser
* reading the URL
www.xxx.com/views/rest/24\
        django app; view within the app; url path param

* taking data from user:\
the escape function will prevent cross-site scripting -> generate safe html entities.
```
from django.utils.html import escape
def a_view(request):
    response="""<html>""" + escape(request.GET['guess']) + """</html>"""
```
* redirct
```
from django.http import HttpResponse
from django.http import HttpResponseRedirct
def bounce(request):
    return HttpResponse('some url')
```
OR
```
<a href= "{% url 'game:aview' x %}"></a>
```
* Templates\
Django Template language (DTL) - Jinja2\
Eg.
```
Data: {'x': 'abc'}
tpl: <h1></h1><pre> {{ x }} </pre>
outcome: <h1></h1><pre> abc </pre>
```
Code eg.
```
#url
#tmpl/game/200

#urls.py
path('game/<slug:guess>',views.GameView.as_view())

#gameview
from django.shortcut import render
from django.views import View
class GameView(View):
    def get(self,request,guess):
        x= {'guess',int(guess)};
        return render(request, 'tmpl/cond.html',x) 
```

* DTL
1. substitute\
{{ zap }} -> {{zap|safe}} means do not run escape filter on zap
2. calling code\
{% url 'view_name' aname.id%}\
{% author.get_url%}
3. logic\
    * if \
    {% if x>100 %}\
    {% end if %}
    * for 
        ```
        <ul>
        {% for x  in names %}
        <li>{{ x }}<li>
        {% endfor %}
        <ul>
        ```
        can walk through levels of dictionaries\
        like: a.b.c.d 
4. blocks\
    tmpl/base.html:
    ```
    <body>
    {% block content %}
    {% endblock %}
    </body>
    ```
    To fillin the tmpl's block: \
    con.html:
    ```
    {% extends 'tmpl/base.html' %}
    {% block content %}
    ...
    {% endblock %}
    ```
    Then inside gameview.py:\
    return render(request,'tmpl/con.html',x)
