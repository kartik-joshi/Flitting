from django.db import models
from django.utils import timezone
from django.contrib.auth.models import User
from django.urls import reverse

# Create your models here.

class Post(models.Model):
    title = models.CharField(max_length = 100)
    content = models.TextField()
    # Only update date when object is created
    #auto_now =  True will update date all the time good for last modified attributes
    #default = value we want to assing as default user
    date_posted = models.DateTimeField(default=timezone.now)
    # On delete of user delete alle the Blog
    # Add user as foreignkey to link given blog to user
    author = models.ForeignKey(User, on_delete= models.CASCADE)

    def __str__(self):
        return self.title
    def get_absolute_url(self):
        return reverse('post-detail',kwargs={'pk':self.pk})
