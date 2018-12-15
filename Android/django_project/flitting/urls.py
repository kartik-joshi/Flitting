# from django.contrib import admin
from django.conf import settings
from django.conf.urls.static import static
from django.urls import path
from django.contrib.auth import views as auth_views
from . import views
from rest_framework import routers

# router= routers.DefaultRouter(views.PostViewSet)
# router.register(r'',views.PostViewSet)

urlpatterns = [
    path('', views.home, name = 'flitting-home'),
    path('register/', views.register, name = 'flitting-register'),
    path('login/',auth_views.LoginView.as_view(template_name='flitting/login.html'), name='login'),
    path('logout/',auth_views.LogoutView.as_view(template_name='flitting/logout.html'), name='logout'),
    path('profile/',views.profile, name = "flitting-profile"),
    # api paths
    path('api/requests/<int:pk>', views.getRequest),
    path('api/listings/<int:pk>', views.getListing),
    path('api/listings/search', views.searchListing),
    path('api/users/<int:pk>', views.getUser),
    path('api/users/<int:userId>/listings', views.getListingsOf),
    path('api/users/<int:userId>/reviews', views.getReviewsOf),
    path('api/users/<int:userId>/requests', views.getRequestsOf),
    # path('api/requests/create', views.createRequest) #create new request via post
    # path('api/listings/create', views.createListing)
]


if settings.DEBUG:
    urlpatterns += static(settings.MEDIA_URL, document_root=settings.MEDIA_ROOT)

