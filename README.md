# BBox_POC
Kafka blackbox proof of concept
The application is a Springboot application which uses an embedded Tomcat. The application listens for messages on a kafka endpoint, writes them to a Redis queue from where they are picked by a background thread, processed and written in Elasticsearch
