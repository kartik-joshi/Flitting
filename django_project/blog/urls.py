# from django.contrib import admin
from django.urls import path, include
from .views import (PostListView,
                    PostDetailView,
                    PostCreateView,
                    PostUpdateView,
                    PostDeleteView )
from . import views
from rest_framework import routers


router= routers.DefaultRouter(views.PostViewSet)
router.register(r'',views.PostViewSet)

urlpatterns = [
    path('', PostListView.as_view(), name = 'blog-home'),
    path('api/',include(router.urls)),
    path('about/', views.about, name = 'blog-about'),
    path('post/<int:pk>/', PostDetailView.as_view(), name = 'post-detail'),
    path('post/new/', PostCreateView.as_view(), name = 'post-create'),
    path('post/<int:pk>/update/', PostUpdateView.as_view(), name = 'post-update'),
    path('post/<int:pk>/delete/', PostDeleteView.as_view(), name = 'post-delete'),
]
