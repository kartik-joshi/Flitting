from django.db import models
from django.utils import timezone
from django.contrib.auth.models import User
from PIL import Image

# Create your models here for flitting app


class Profile(models.Model):
    user = models.OneToOneField(User, on_delete=models.CASCADE)
    image = models.ImageField(default='default.jpg', upload_to='profile_pics')
    phone = models.TextField()

    def __str__(self):
        return f'{self.user.username} Profile'

    def save(self, **kwargs):
        super().save()
        img = Image.open(self.image.path)
        if img.height > 300 or img.width > 300:
            output_size = (300, 300)
            img.thumbnail(output_size)
            img.save(self.image.path)


class Listing(models.Model):
    user = models.ForeignKey(User, on_delete=models.CASCADE)
    city = models.TextField()
    country = models.TextField()
    description = models.TextField()
    category = models.TextField()
    date_posted = models.DateTimeField(default=timezone.now)


class Request(models.Model):
    helper = models.ForeignKey(
        User, on_delete=models.CASCADE, related_name='Request_helper')
    newbie = models.ForeignKey(
        User, on_delete=models.CASCADE, related_name='Request_newbie')
    text = models.TextField(max_length=500)
    listing = models.ForeignKey(Listing, on_delete=models.PROTECT)
    description = models.TextField()
    isAccepted = models.BooleanField()
    startDate = models.DateTimeField()
    endDate = models.DateTimeField()
    date_posted = models.DateTimeField(default=timezone.now)


class Review(models.Model):
    helper = models.ForeignKey(
        User, on_delete=models.CASCADE, related_name='Review_helper')
    newbie = models.ForeignKey(
        User, on_delete=models.CASCADE, related_name='Review_newbie')
    text = models.TextField(max_length=500)
    rating = models.IntegerField()
    date_posted = models.DateTimeField(default=timezone.now)
