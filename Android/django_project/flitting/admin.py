from django.contrib import admin
from .models import Profile
from .models import Profile , Listing, Request, Review
# Register your models here.


admin.site.register(Profile)
admin.site.register(Listing)
admin.site.register(Request)
admin.site.register(Review)
