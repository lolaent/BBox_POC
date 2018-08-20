package com.lolatech.springkafka.dto;

import org.codehaus.jackson.annotate.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName="timelinedto", type="timeline")
public class TimelineDto {

	@Id
	private String id;

    @JsonProperty("_messagetimems")
	private String messageTimestamp;

    @JsonProperty("_messagetime")
	private String messageDateTime;

    @JsonProperty("_raw")
	private String content;

    @JsonProperty("_collector")
	private String collector;

    @JsonProperty("dest_host")
	private String destinationHost;

    @JsonProperty("fac")
	private String fac;

    @JsonProperty("pri")
	private String priority;

    @JsonProperty("sev")
	private String severity;

    @JsonProperty("severity")
	private String severityName;

    @JsonProperty("_size")
	private String size;

    @JsonProperty("_source")
	private String source;

    @JsonProperty("_sourcecategory")
	private String sourceCategory;

    @JsonProperty("_sourcehost")
	private String sourceHost;

    @JsonProperty("_sourcename")
	private String sourceName;

    public TimelineDto(String id, String messageTimestamp, String messageDateTime, String content, String collector, String destinationHost, String fac,
            String priority, String severity, String severityName, String size, String source, String sourceCategory, String sourceHost, String sourceName) {
        this.id = id;
        this.messageTimestamp = messageTimestamp;
        this.messageDateTime = messageDateTime;
        this.content = content;
        this.collector = collector;
        this.destinationHost = destinationHost;
        this.fac = fac;
        this.priority = priority;
        this.severity = severity;
        this.severityName = severityName;
        this.size = size;
        this.source = source;
        this.sourceCategory = sourceCategory;
        this.sourceHost = sourceHost;
        this.sourceName = sourceName;
    }

    public TimelineDto() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessageTimestamp() {
        return messageTimestamp;
    }

    public void setMessageTimestamp(String messageTimestamp) {
        this.messageTimestamp = messageTimestamp;
    }

    public String getMessageDateTime() {
        return messageDateTime;
    }

    public void setMessageDateTime(String messageDateTime) {
        this.messageDateTime = messageDateTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCollector() {
        return collector;
    }

    public void setCollector(String collector) {
        this.collector = collector;
    }

    public String getDestinationHost() {
        return destinationHost;
    }

    public void setDestinationHost(String destinationHost) {
        this.destinationHost = destinationHost;
    }

    public String getFac() {
        return fac;
    }

    public void setFac(String fac) {
        this.fac = fac;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public String getSeverityName() {
        return severityName;
    }

    public void setSeverityName(String severityName) {
        this.severityName = severityName;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSourceCategory() {
        return sourceCategory;
    }

    public void setSourceCategory(String sourceCategory) {
        this.sourceCategory = sourceCategory;
    }

    public String getSourceHost() {
        return sourceHost;
    }

    public void setSourceHost(String sourceHost) {
        this.sourceHost = sourceHost;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }
}
