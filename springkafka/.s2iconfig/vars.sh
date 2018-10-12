#!/bin/bash

# We need to move settings.xml inside the Docker Build image, do avoid the hardcoding path below (Docker HOME and Jnlp HOME differ)
export MVN_BUILD_COMMAND=${MVN_BUILD_COMMAND:-"mvn -e clean install"}
export ARTIFACT_LOCATION="${APP_HOME}/target/*.jar ${APP_HOME}/.s2i*"

export ELASTIC_CLUSTERNAME=${ELASTIC_CLUSTERNAME:-"docker-cluster"}
export ELASTIC_HOST=${ELASTIC_HOST:-"${NAME}-es"}
export ELASTIC_PORT=${ELASTIC_PORT:-"9200"}

export ENVIRONMENT=${ENVIRONMENT:-"rackspace"} ## TBD Why?

export REDIS_ADDRESS=${REDIS_ADDRESS:-"${NAME}-rdsdb.${DEPLOY_NAMESPACE}.svc.cluster.local:6379"}
export REDIS_TIMEOUT=${REDIS_TIMEOUT:-"3000"}
export REDIS_PASSWORD=${REDIS_PASSWORD:-"thisisapass"}

