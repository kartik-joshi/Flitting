from django.shortcuts import render, redirect
from bootstrap_datepicker_plus import DatePickerInput
from django.http import HttpResponse
from django.contrib import messages
from .forms import UserRegisterForm, UserUpdateForm, ProfileUpdateForm
from django.contrib.auth.decorators import login_required
from django.contrib.auth.mixins import LoginRequiredMixin, UserPassesTestMixin
from rest_framework import viewsets
from .models import Listing, Request
from django.db.models import Q
from django.views.generic import (
        ListView,
        DetailView,
        CreateView,
        UpdateView,
        DeleteView
        )

# Create your views here.

def home(request):
    return render(request,'flitting/home.html')

def team(request):
    return render(request,'flitting/team.html')

def about(request):
    return render(request,'flitting/about.html')

# def listing(request):
#     return render(request,'flitting/listing.html')

def user(request):
    current_user = request.user
    print(current_user.id)
    return render(request,'flitting/user.html')

def register(request):
    if request.method == 'POST' :
        form = UserRegisterForm(request.POST)
        if form.is_valid():
            form.save()
            username = form.cleaned_data.get('username')
            messages.success(request,f'Your account has been created')
            return redirect('login')
    else:
        form = UserRegisterForm()
    return render(request,'flitting/register.html',{'form':form})

# define profile for flitting app
@login_required
def profile(request):
    if request.method == 'POST' :
        u_form = UserUpdateForm(request.POST,instance = request.user)
        p_form = ProfileUpdateForm(request.POST,
                                    request.FILES,
                                    instance = request.user.profile)
        if u_form.is_valid() and p_form.is_valid():
            u_form.save()
            p_form.save()
            messages.success(request,f'Your account has been udpated')
            return redirect('flitting-profile')
    else :
        u_form = UserUpdateForm(instance = request.user)
        p_form = ProfileUpdateForm(instance = request.user.profile)
    context = {
        'u_form' : u_form,
        'p_form' : p_form
    }
    return render(request,'flitting/profile.html',context)


## Listing  data pages
# List view to render posts
class ListingListView(LoginRequiredMixin,ListView):
    model = Listing
    template_name = 'flitting/listing_home.html'
    context_object_name = 'Listing'
    ## sort based on date posted
    ordering = ['-date_posted']
    def get_queryset(self):
        return self.model.objects.filter(user=self.request.user)

class ListingDetailView(LoginRequiredMixin,DetailView):
    model = Listing

#login required for creating post login required mixin
class ListingCreateView(LoginRequiredMixin,CreateView):
    model = Listing
    fields = ['city','country','category','description']
    def form_valid(self,form):
        form.instance.user = self.request.user
        return super().form_valid(form)

#User login required for Updateing only own post Update required mixin
class ListingUpdateView(LoginRequiredMixin,UserPassesTestMixin,UpdateView):
    model = Listing
    fields = ['city','country','category','description']
    def form_valid(self,form):
        form.instance.user = self.request.user
        return super().form_valid(form)

    def test_func(self):
        List = self.get_object()
        print(self.request.user,List.user)
        if self.request.user == List.user:
            return True
        return False

#User login required for Updateing only own post Update required mixin
class ListingDeleteView(LoginRequiredMixin,UserPassesTestMixin,DeleteView):
    model = Listing
    success_url = '/flitting/listing'
    def test_func(self):
        List = self.get_object()
        if self.request.user == List.user:
            return True
        return False




## Request data pages
# List view to render Requests
class RequestListView(LoginRequiredMixin,ListView):
    model = Request
    template_name = 'flitting/request_home.html'
    context_object_name = 'Requests'
    ## sort based on date posted
    ordering = ['-date_posted']
    def get_queryset(self):
        return self.model.objects.filter(Q(helper=self.request.user)|Q(newbie=self.request.user))

class RequestDetailView(LoginRequiredMixin,DetailView):
    model = Request

#login required for creating post login required mixin
class RequestCreateView(LoginRequiredMixin,CreateView):
    model = Request
    fields = ['helper','startDate','endDate','listing','description','isAccepted']
    def get_form(self):
        form = super().get_form()
        form.fields['startDate'].widget = DatePickerInput(format='%Y-%m-%d')
        form.fields['endDate'].widget = DatePickerInput(format='%Y-%m-%d')
        return form
    def form_valid(self,form):
        form.instance.newbie = self.request.user
        return super().form_valid(form)

#User login required for Updateing only own post Update required mixin
class RequestUpdateView(LoginRequiredMixin,UserPassesTestMixin,UpdateView):
    model = Request
    fields = ['startDate','endDate','listing','description','isAccepted']
    def get_form(self):
        form = super().get_form()
        form.fields['startDate'].widget = DatePickerInput(format='%Y-%m-%d')
        form.fields['endDate'].widget = DatePickerInput(format='%Y-%m-%d')
        return form
    def form_valid(self,form):
        return super().form_valid(form)
    def test_func(self):
        Request = self.get_object()
        if self.request.user == Request.helper or self.request.user == Request.newbie:
            return True
        return False

#User login required for Updateing only own post Update required mixin
class RequestDeleteView(LoginRequiredMixin,UserPassesTestMixin,DeleteView):
    model = Request
    success_url = '/flitting/request'
    def test_func(self):
        Request = self.get_object()
        if self.request.user == Request.helper or self.request.user == Request.newbie:
            return True
        return False


class RequestSearchListView(LoginRequiredMixin,ListView):
    model = Request
    template_name = 'flitting/request_home.html'
    context_object_name = 'Requests'
    ## sort based on date posted
    ordering = ['-date_posted']
    def get_queryset(self):
        return self.model.objects.filter(Q(helper=self.request.user)|Q(newbie=self.request.user))


def RequestSearchList(request):
    query = (request.GET.get('q', None))
    current_user = request.user
    try:
        # ps = [u.id for u in Profile.objects.all()
        #       .filter(Q(user__first_name__icontains=query) | Q(user__last_name__icontains=query))]

        ls = Listing.objects.all().filter(Q(city__icontains=query)
                                          | Q(country__icontains=query)
                                          | Q(category__icontains=query))
                                          # | Q(user__in=ps))
    # except Profile.DoesNotExist:
    #     print("no users!")
    except Listing.DoesNotExist:
        return Response(status=status.HTTP_404_NOT_FOUND)
    context = {
        'Listing' : ls
    }
    # print(context)
    # return render(request,'flitting/home.html')
    return render(request,'flitting/listing_home.html',context)
