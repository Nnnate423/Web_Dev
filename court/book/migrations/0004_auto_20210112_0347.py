# Generated by Django 3.1.5 on 2021-01-12 03:47

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('book', '0003_auto_20210112_0323'),
    ]

    operations = [
        migrations.AlterField(
            model_name='slot',
            name='day',
            field=models.DateField(verbose_name='court_date'),
        ),
    ]
