# from django.contrib import admin
from django.conf import settings
from django.conf.urls.static import static
from django.urls import path
from django.contrib.auth import views as auth_views
from . import views
from .views import (ListingListView,
                    ListingDetailView,
                    ListingCreateView,
                    ListingUpdateView,
                    ListingDeleteView,
                    RequestListView,
                    RequestDetailView,
                    RequestCreateView,
                    RequestUpdateView,
                    RequestDeleteView,
                    )

urlpatterns = [
    path('', views.home, name = 'flitting-home'),
    path('register/', views.register, name = 'flitting-register'),
    path('login/',auth_views.LoginView.as_view(template_name='flitting/login.html'), name='login'),
    path('logout/',auth_views.LogoutView.as_view(template_name='flitting/logout.html'), name='logout'),
    path('profile/',views.profile, name = "flitting-profile"),
    path('team/', views.team, name = 'flitting-team'),
    path('user/', views.user, name = 'flitting-user'),
    path('about/', views.about, name = 'flitting-about'),
    path('listing/', ListingListView.as_view(), name = 'listing-home'),
    path('listing/<int:pk>/', ListingDetailView.as_view(), name = 'listing-detail'),
    path('listing/new/', ListingCreateView.as_view(), name = 'listing-create'),
    path('listing/<int:pk>/update/', ListingUpdateView.as_view(), name = 'listing-update'),
    path('listing/<int:pk>/delete/', ListingDeleteView.as_view(), name = 'listing-delete'),
    path('request/', RequestListView.as_view(), name = 'request-home'),
    path('request/<int:pk>/', RequestDetailView.as_view(), name = 'request-detail'),
    path('request/new/', RequestCreateView.as_view(success_url="/flitting/request/"), name = 'request-create'),
    path('request/<int:pk>/update/', RequestUpdateView.as_view(success_url="/flitting/request/"), name = 'request-update'),
    path('request/<int:pk>/delete/', RequestDeleteView.as_view(success_url="/flitting/request/"), name = 'request-delete'),
    path('request/search', views.RequestSearchList, name = 'request-search'),
]


if settings.DEBUG:
    urlpatterns += static(settings.MEDIA_URL, document_root=settings.MEDIA_ROOT)
