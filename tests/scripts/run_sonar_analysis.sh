#!/bin/sh

mvn clean verify sonar:sonar \
    -Dsonar.projectKey="${SONARQUBE_PKEY}" \
    -Dsonar.host.url="${SONARQUBE_HOST}" \
    -Dsonar.login="${SONARQUBE_USER}" \
    -Dsonar.password="${SONARQUBE_PASS}"
