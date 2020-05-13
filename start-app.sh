#!/bin/bash

mvn clean install && docker-compose build --no-cache && docker-compose up -d
