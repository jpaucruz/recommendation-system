package com.jpaudelacruz.recommendation_system_ms.infrastructure.messaging.stream.extractors;

import com.jpaudelacruz.recommendation_system_ms.infrastructure.messaging.producer.model.UserActionMessagingEvent;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.streams.processor.TimestampExtractor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

public class UserActionMessagingEventTimestampExtractor implements TimestampExtractor {

    @Override
    public long extract(ConsumerRecord<Object, Object> consumerRecord, long l) {
        UserActionMessagingEvent action = (UserActionMessagingEvent) consumerRecord.value();
        return iso8601ToEpoch(action.getTimestamp());
    }

    private long iso8601ToEpoch(String createdAt) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {
            return formatter.parse(createdAt).getTime();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

}