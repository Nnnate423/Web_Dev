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

    PS. rmb to implement __str__ method in model classes -> allow easier visualization

    #SELECT*
    User.objects.values()
    User.objects.all()
    #Select WHERE
    User.objects.filter(name="xx").values()
    User.objects.get(name="xx") //can only be used for select 1 return val
    #DELETE
    User.objects.filter(name="xx").delete()
    #UPDATE
    User.objects.filter(name="xx").update(email="yy")
    #ORDER BY
    User.objects.values().order_by("email")
    #COUNT
    ~.count()
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
* path() args\
The path() function is passed four arguments, two required: route and view, and two optional: kwargs, and name. \
the 'name' value as called by the {% url %} template tag like: {% url 'name' some_val%}\
some_val will fill in the ```'<int:xxx>/'``` slot of the path
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
<a href = "{% url 'login' %}"?next{{ request.path }}>login </a>
//goto login and then go back here
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
### 1.4 cookie, session, user login
<a href = "{% url 'login' %}"?next{{ request.path }}>login </a>
```
{% if next %}
{% if user.is_authenticated %}
{% else %}
{% endif %}
{% else %}
{% endif %}
```
In Python:
```
if request.user.is_authenticated():
    print(request.user.username)
```
* to require login
```
from django.contrib.auth.mixins import LoginRequiredMixin
class ProetctedView(LoginRequiredMixin,View):
    def get(self,request):
        return render(request, "xxx/xxx.html")
```

### 1.5 forms
Django handles three distinct parts of the work involved in forms:

* preparing and restructuring data to make it ready for rendering
* creating HTML forms for the data
* receiving and processing submitted forms and data from the client

Check documentation:  https://docs.djangoproject.com/en/3.1/topics/forms/



# 2. Spring boot
## 1. annotation & basics
* @SpringBootApplication
    @SpringBootApplication = @SpringBootConfiguration + @EnableAutoConfiguration + @ComponentScan
* @WebMvcTest
    for mock spring mvc test, create mockMvc and do rest calls to controller.
* @Automired
    can be used on:
    * constructor
    * property
    * setter
* validation
    * @NotNull: a constrained CharSequence, Collection, Map, or Array is valid as long as it's not null, but it can be empty
    * @NotEmpty: a constrained CharSequence, Collection, Map, or Array is valid as long as it's not null and its size/length is greater than zero
    * @NotBlank: a constrained String is valid as long as it's not null and the trimmed length is greater than zero  

* mapping
    * @RequestMapping General-purpose request handling 
    * @GetMapping Handles HTTP GET requests
    * @PostMapping Handles HTTP POST requests 
    * @PutMapping Handles HTTP PUT requests
    * @DeleteMapping Handles HTTP DELETE requests
    * @PatchMapping Handles HTTP PATCH requests
* Spring Web dependency
    it brings spring MVC and embeded tomcat.



## 2. JDBC
* JDBC template

* rowmapper
use class or method reference by like: this::method
```
@Override
public Ingredient findOne(String id) {
  return jdbc.queryForObject(
      "select id, name, type from Ingredient where id=?",
      new RowMapper<Ingredient>() {
}
  public Ingredient mapRow(ResultSet rs, int rowNum)
      throws SQLException {
    return new Ingredient(
        rs.getString("id"),
        rs.getString("name"),
        Ingredient.Type.valueOf(rs.getString("type")));
};
}, id);
```

* save data 
    1. Directly, using the update() method
    2. Using the SimpleJdbcInsert wrapper class