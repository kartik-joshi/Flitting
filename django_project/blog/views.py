from django.shortcuts import render
from .models import Post
from django.contrib.auth.mixins import LoginRequiredMixin, UserPassesTestMixin
from rest_framework import viewsets
from .serializers import PostSerializer
from django.views.generic import (
        ListView,
        DetailView,
        CreateView,
        UpdateView,
        DeleteView
        )

# Create your views here.

# Home functional rendering
def home(request):
    context = {
        'posts' : Post.objects.all()
    }
    return render(request,'blog/home.html',context)

# List view to render posts
class PostListView(ListView):
    model = Post
    template_name = 'blog/home.html'
    context_object_name = 'posts'
    ## sort based on date posted
    ordering = ['-date_posted']
#    <app>/<model>_<viewtype>.html


class PostDetailView(DetailView):
    model = Post

#login required for creating post login required mixin
class PostCreateView(LoginRequiredMixin,CreateView):
    model = Post
    fields = ['title','content']
    def form_valid(self,form):
        form.instance.author = self.request.user
        return super().form_valid(form)

#User login required for Updateing only own post Update required mixin
class PostUpdateView(LoginRequiredMixin,UserPassesTestMixin,UpdateView):
    model = Post
    fields = ['title','content']
    def form_valid(self,form):
        form.instance.author = self.request.user
        return super().form_valid(form)

    def test_func(self):
        post = self.get_object()
        if self.request.user == post.author:
            return True
        return False

#User login required for Updateing only own post Update required mixin
class PostDeleteView(LoginRequiredMixin,UserPassesTestMixin,DeleteView):
    model = Post
    success_url = '/blog'
    def test_func(self):
        post = self.get_object()
        if self.request.user == post.author:
            return True
        return False
def about(request):
    return render(request,'blog/about.html',{'title':'About'})



# ViewList looks for <app>/<model>_<viewtype>.html


## class view to send data for rest api using viewset for android

class PostViewSet(viewsets.ModelViewSet):
    queryset=Post.objects.all()
    serializer_class= PostSerializer
