#!/bin/bash

# Make models migrations
echo "Make models migrations"
python manage.py makemigrations

# Apply database migrations
echo "Apply database migrations"
python manage.py migrate

# Create super user
echo "Create superuser"
python manage.py createadmin

# Create 20 custom users
echo "Create 20 custom users"
python manage.py generateusers -c 20

# Launch server
echo "Launch server"
python manage.py runserver 0.0.0.0:8000