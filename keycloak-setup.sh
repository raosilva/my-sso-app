#!/bin/bash

KEYCLOAK_URL="http://localhost:8080/auth"
REALM="myrealm"
CLIENT_ID="my-client"
CLIENT_SECRET="my-client-secret"

./kcadm.sh config credentials --server $KEYCLOAK_URL --realm master --user admin --password admin
./kcadm.sh create realms -s realm=$REALM -s enabled=true
./kcadm.sh create clients -r $REALM -s clientId=$CLIENT_ID -s enabled=true -s publicClient=false -s secret=$CLIENT_SECRET -s 'redirectUris=["http://localhost:8080/*"]' -s baseUrl="http://localhost:8080/"
./kcadm.sh create users -r $REALM -s username=user -s enabled=true
./kcadm.sh set-password -r $REALM --username user --new-password password
