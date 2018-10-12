#!/bin/bash

# We need to move settings.xml inside the Docker Build image, do avoid the hardcoding path below (Docker HOME and Jnlp HOME differ)
export MVN_BUILD_COMMAND=${MVN_BUILD_COMMAND:-"mvn -e clean install"}
export ARTIFACT_LOCATION='${APP_HOME}/target/*.jar'