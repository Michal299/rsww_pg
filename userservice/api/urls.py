from django.urls import path
from api.views import CustomTokenObtainPairView, CustomUsersView

from rest_framework_simplejwt.views import TokenRefreshView


urlpatterns = [
    path('token/', CustomTokenObtainPairView.as_view(), name='token_obtain_pair'),
    path('token/refresh/', TokenRefreshView.as_view(), name='token_refresh'),

    path('users/', CustomUsersView.as_view(), name='users'),
]
