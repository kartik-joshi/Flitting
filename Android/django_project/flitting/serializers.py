from rest_framework import serializers
from .models import Profile, Listing, Request, Review

from django.contrib.auth.models import User

class UserSerializer(serializers.ModelSerializer):

    class Meta:
        model = User
        fields = ('first_name', 'last_name', 'email')


class ProfileSerializer(serializers.ModelSerializer):
    details = UserSerializer(source='user')

    class Meta:
        model = Profile
        fields = ('image', 'phone', 'details')


class ListingSerializer(serializers.ModelSerializer):
    userProfile = serializers.SerializerMethodField()

    def get_userProfile(self, obj):
        return ProfileSerializer(Profile.objects.get(user=obj.user.id)).data

    class Meta:
        model = Listing
        fields = '__all__'


class RequestSerializer(serializers.ModelSerializer):
    userProfile = serializers.SerializerMethodField()

    def get_userProfile(self, obj):
        return ProfileSerializer(Profile.objects.get(user=obj.newbie.id)).data

    class Meta:
        model = Request
        fields = '__all__'


class ReviewSerializer(serializers.ModelSerializer):
    newbie = serializers.SerializerMethodField()

    def get_newbie(self, obj):
        return ProfileSerializer(Profile.objects.get(user=obj.newbie.id)).data

    class Meta:
        model = Review
        fields = '__all__'
