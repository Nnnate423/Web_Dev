from django.contrib.auth.forms import UserCreationForm
from django.contrib.auth.models import User
from django import forms


class CreateUserForm(UserCreationForm):
    class Meta:
        model = User
        fields = ['username', 'email', 'password1', 'password2']

class SearchForm(forms.Form):
    CHOICE1 = (('1','both'),('2','indoor'),('3','outdoor'),)
    select1 = forms.ChoiceField(label= 'Environment', widget=forms.Select, choices=CHOICE1)
    CHOICE2 = (('1','both'),('2','standard'),('3','kids'),)
    select2 = forms.ChoiceField(label= 'Type',widget=forms.Select, choices=CHOICE2)

