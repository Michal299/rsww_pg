from django.shortcuts import render
from django.http.request import HttpRequest
from django.http.response import JsonResponse
from django.contrib.auth import authenticate, login
from django.views.decorators.csrf import csrf_exempt

from rest_framework_simplejwt.serializers import TokenObtainPairSerializer
from rest_framework_simplejwt.views import TokenObtainPairView


@csrf_exempt
def user_login(request: HttpRequest):
    params = request.POST
    username = params.get('username', None)
    password = params.get('password', None)
    print(username, password)
    user = authenticate(request, username=username, password=password)
    if user is not None:
        login(request, user)
        response = {
            "user_id": str(user.id),
            "userFirstname": str(user.firstname),
            "userLastname": str(user.lastname)
        }
        return JsonResponse(response, status=202)
    else:
        return JsonResponse({'detail': 'Invalid login. Please try again.'}, status=401)


class CustomTokenObtainPairSerializer(TokenObtainPairSerializer):
    @classmethod
    def get_token(cls, user):
        token = super().get_token(user)

        token['username'] = user.username
        token['firstname'] = user.firstname
        token['lastname'] = user.lastname

        return token


class CustomTokenObtainPairView(TokenObtainPairView):
    serializer_class = CustomTokenObtainPairSerializer

