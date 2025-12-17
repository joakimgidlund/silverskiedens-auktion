#!/bin/sh

cd ../auctionapi
mvn clean package
cd ../auth
mvn clean package
cd ../bidbroker
mvn clean package
cd ../auction-frontend
npm install
npm run build
cd ../docker
docker-compose build
echo Done!