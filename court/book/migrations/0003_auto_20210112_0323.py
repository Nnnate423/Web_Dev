# Generated by Django 3.1.5 on 2021-01-12 03:23

from django.conf import settings
from django.db import migrations


class Migration(migrations.Migration):
    atomic = False
    dependencies = [
        migrations.swappable_dependency(settings.AUTH_USER_MODEL),
        ('book', '0002_auto_20210112_0312'),
    ]

    operations = [
        migrations.RenameModel(
            old_name='courts',
            new_name='court',
        ),
        migrations.RenameModel(
            old_name='orders',
            new_name='order',
        ),
        migrations.RenameModel(
            old_name='reviews',
            new_name='review',
        ),
        migrations.RenameModel(
            old_name='slots',
            new_name='slot',
        ),
    ]
