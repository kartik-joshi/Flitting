from django.shortcuts import render, redirect
from django.http import HttpResponse
from django.contrib import messages
from .forms import UserRegisterForm, UserUpdateForm, ProfileUpdateForm
from django.contrib.auth.decorators import login_required
from rest_framework import viewsets

from rest_framework import status
from rest_framework.decorators import api_view
from rest_framework.response import Response
from .models import Request, Listing, Profile, Review
from django.contrib.auth.models import User
from django.db.models import Q
from .serializers import *

# Create your views here.


def home(request):
    return render(request, 'flitting/home.html')


def listing(request):
    return render(request, 'flitting/listing.html')


def register(request):
    if request.method == 'POST':
        form = UserRegisterForm(request.POST)
        if form.is_valid():
            form.save()
            username = form.cleaned_data.get('username')
            messages.success(request, f'Your account has been created')
            return redirect('login')
    else:
        form = UserRegisterForm()
    return render(request, 'flitting/register.html', {'form': form})

# define profile for flitting app


@login_required
def profile(request):
    if request.method == 'POST':
        u_form = UserUpdateForm(request.POST, instance=request.user)
        p_form = ProfileUpdateForm(request.POST,
                                   request.FILES,
                                   instance=request.user.profile)
        if u_form.is_valid() and p_form.is_valid():
            u_form.save()
            p_form.save()
            messages.success(request, f'Your account has been udpated')
            return redirect('flitting-profile')
    else:
        u_form = UserUpdateForm(instance=request.user)
        p_form = ProfileUpdateForm(instance=request.user.profile)
    context = {
        'u_form': u_form,
        'p_form': p_form
    }
    return render(request, 'flitting/profile.html', context)


# Viewset for profile android call
@api_view(['GET'])
def getRequest(request, pk):
    try:
        r = Request.objects.get(pk=pk)
    except Request.DoesNotExist:
        return Response(status=status.HTTP_404_NOT_FOUND)

    serializer = RequestSerializer(r)
    return Response(serializer.data)


@api_view(['GET'])
def getListing(request, pk):
    try:
        r = Listing.objects.get(pk=pk)
    except Listing.DoesNotExist:
        return Response(status=status.HTTP_404_NOT_FOUND)

    serializer = ListingSerializer(r)
    return Response(serializer.data)


@api_view(['GET'])
def getUser(request, pk):
    try:
        r = Profile.objects.get(user=pk)
    except Profile.DoesNotExist:
        return Response(status=status.HTTP_404_NOT_FOUND)

    serializer = ProfileSerializer(r)
    return Response(serializer.data)


@api_view(['GET'])
def getListingsOf(request, userId):
    try:
        r = Listing.objects.all().filter(user=userId)
    except Listing.DoesNotExist:
        return Response(status=status.HTTP_404_NOT_FOUND)

    serializer = ListingSerializer(r, many=True)
    return Response(serializer.data)


@api_view(['GET'])
def getReviewsOf(request, userId):
    try:
        r = Review.objects.all().filter(helper=userId)
    except Review.DoesNotExist:
        return Response(status=status.HTTP_404_NOT_FOUND)

    serializer = ReviewSerializer(r, many=True)
    return Response(serializer.data)


@api_view(['GET'])
def getRequestsOf(request, userId):
    try:
        r = Request.objects.all().filter(helper=userId)
    except Request.DoesNotExist:
        return Response(status=status.HTTP_404_NOT_FOUND)

    serializer = RequestSerializer(r, many=True)
    return Response(serializer.data)


@api_view(['GET'])
def searchListing(request):
    query = request.GET.get('q', None)
    try:
        ps = [u.user.id for u in Profile.objects.all()
              .filter(Q(user__first_name__icontains=query) | Q(user__last_name__icontains=query))]
        print(len(ps))
        ls = Listing.objects.all().filter(Q(city__icontains=query)
                                          | Q(country__icontains=query)
                                          | Q(category__icontains=query)
                                          | Q(user__in=ps))
        print(len(ls))
    except Profile.DoesNotExist:
        print("no users!")
    except Listing.DoesNotExist:
        return Response(status=status.HTTP_404_NOT_FOUND)

    serializer = ListingSerializer(ls, many=True)
    return Response(serializer.data)

@api_view(['GET'])
def getReviewsOf(request, userId):
    try:
        r = Review.objects.all().filter(helper=userId)
    except Review.DoesNotExist:
        return Response(status=status.HTTP_404_NOT_FOUND)

    serializer = ReviewSerializer(r, many=True)
    return Response(serializer.data)

# class ProfileViewSet(viewsets.ModelViewSet):
#     quertset=
