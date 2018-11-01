#!/bin/bash

# We need to move settings.xml inside the Docker Build image, do avoid the hardcoding path below (Docker HOME and Jnlp HOME differ)
export MVN_BUILD_COMMAND=${MVN_BUILD_COMMAND:-"mvn -e clean install"}
export ARTIFACT_LOCATION="target/*.jar .s2i*"

export BOOTSTRAP_SERVERS=${BOOTSTRAP_SERVERS:-"apache-kafka:9092"}
export ELASTICSEARCH_CLUSTERNAME=${ELASTICSEARCH_CLUSTERNAME:-"docker-cluster"}
export ELASTICSEARCH_HOST=${ELASTICSEARCH_HOST:-"test-es"}
export ELASTICSEARCH_PORT=${ELASTICSEARCH_PORT:-"9300"}
export ENVIRONMENT=${ENVIRONMENT:-"remote"}
export REDIS_ADDRESS=${REDIS_ADDRESS:-"${NAME}-rdsdb.${DEPLOY_NAMESPACE}.svc.cluster.local:6379"}
export REDIS_TIMEOUT=${REDIS_TIMEOUT:-"3000"}
export REDIS_PASSWORD=${REDIS_PASSWORD:-"thisisapass"}