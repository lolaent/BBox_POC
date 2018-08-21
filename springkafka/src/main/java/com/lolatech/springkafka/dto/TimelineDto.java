package com.lolatech.springkafka.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.elasticsearch.annotations.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Document(indexName="timelinedto", type="timeline")
@JsonIgnoreProperties(ignoreUnknown = true)
public class TimelineDto {

    public TimelineDto(String messageDateTime, String content) {
        this.messageDateTime = messageDateTime;
        this.content = content;
        this.setTransactionId(extractTransactionId(content));
    }

    @JsonIgnore
    private String id;

    @JsonAlias({"_messagetime"})
	private String messageDateTime;

	@JsonAlias({"_raw"})
	private String content;

	@JsonIgnoreProperties(allowGetters = true)
	private String transactionId;

    public void setContent(String content) {
        this.content = content;
        this.setTransactionId(extractTransactionId(content));
    }

    private String extractTransactionId(String content) {
        String transactionId = null;
        if (content != null) {
            String message[] = content.split(": ", 2);
            transactionId = message[1].split(" ")[4];
        }
        return transactionId;
    }
}
