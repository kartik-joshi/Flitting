# Generated by Django 2.1 on 2018-12-01 23:53

from django.db import migrations


class Migration(migrations.Migration):

    dependencies = [
        ('flitting', '0008_review'),
    ]

    operations = [
        migrations.RemoveField(
            model_name='listing',
            name='user',
        ),
        migrations.RemoveField(
            model_name='profile',
            name='user',
        ),
        migrations.RemoveField(
            model_name='request',
            name='listing',
        ),
        migrations.RemoveField(
            model_name='request',
            name='user',
        ),
        migrations.RemoveField(
            model_name='review',
            name='helper',
        ),
        migrations.RemoveField(
            model_name='review',
            name='newbie',
        ),
        migrations.DeleteModel(
            name='Listing',
        ),
        migrations.DeleteModel(
            name='Profile',
        ),
        migrations.DeleteModel(
            name='Request',
        ),
        migrations.DeleteModel(
            name='Review',
        ),
    ]