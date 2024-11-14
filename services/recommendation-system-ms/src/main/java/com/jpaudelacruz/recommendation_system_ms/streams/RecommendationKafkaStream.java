package com.jpaudelacruz.recommendation_system_ms.streams;

import com.jpaudelacruz.recommendation_system_ms.models.avro.UserAction;
import com.jpaudelacruz.recommendation_system_ms.models.avro.UserRecommendation;
import com.jpaudelacruz.recommendation_system_ms.streams.extractors.UserActionTimestampExtractor;
import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde;
import jakarta.annotation.PostConstruct;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class RecommendationKafkaStream {

    private static final Logger logger = LoggerFactory.getLogger(RecommendationKafkaStream.class);

    // decay factor
    final double DECAY_FACTOR = 0.95;
    final long DECAY_INTERVAL_MS = 24 * 60 * 60 * 1000;

    private final StreamsBuilder streamsBuilder;
    private final Map<String, String> avroSerdeConfig;

    public RecommendationKafkaStream(StreamsBuilder streamsBuilder) {
        this.streamsBuilder = streamsBuilder;
        this.avroSerdeConfig = new HashMap<>();
        // TODO: improve
        this.avroSerdeConfig.put("schema.registry.url", "http://schema-registry:8081");
    }

    @PostConstruct
    public void init() {
        buildRecommendationStream(streamsBuilder);
    }

    public void buildRecommendationStream(StreamsBuilder builder) {

        final SpecificAvroSerde<UserAction> userActionSpecificAvroSerde = new SpecificAvroSerde<>();
        userActionSpecificAvroSerde.configure(avroSerdeConfig, false);
        final SpecificAvroSerde<UserRecommendation> userRecommendationSpecificAvroSerde = new SpecificAvroSerde<>();
        userRecommendationSpecificAvroSerde.configure(avroSerdeConfig, false);

        KStream<String, UserAction> actions = builder
                .stream("user_actions", Consumed.with(Serdes.String(), userActionSpecificAvroSerde).withTimestampExtractor(new UserActionTimestampExtractor()))
                .peek((key, value) -> logger.debug("UserAction: {}", value));

        Map<String, Integer> actionWeights = new HashMap<>();
        actionWeights.put("view", 1);
        actionWeights.put("add-to-cart", 3);
        actionWeights.put("purchase", 5);

        KTable<String, UserRecommendation> scoreTable = actions
                .groupBy((key, event) -> event.getUserId() + "|" + event.getProductId(), Grouped.with(Serdes.String(), userActionSpecificAvroSerde))
                .aggregate(
                        () -> new UserRecommendation("", "", "", "", "", 0.0, System.currentTimeMillis()),
                        (key, action, userRecommendation) -> {
                            logger.debug("UserActionToAnalyze: {}", key);
                            logger.debug("Agg: {}", userRecommendation);
                            double currentScore = userRecommendation.getScore();
                            long currentTime = System.currentTimeMillis();
                            long timeSinceLastEvent = currentTime - userRecommendation.getTimestamp();
                            if (timeSinceLastEvent > DECAY_INTERVAL_MS) {
                                double decayFactor = Math.pow(DECAY_FACTOR, (double) timeSinceLastEvent / DECAY_INTERVAL_MS);
                                currentScore *= decayFactor;
                            }
                            int weight = actionWeights.getOrDefault(action.getAction(), 1);
                            double newScore = currentScore + weight;
                            return new UserRecommendation(key, action.getUserId(), action.getProductId(), "0", "0", newScore, currentTime);
                        },
                        Materialized.with(Serdes.String(), userRecommendationSpecificAvroSerde)
                );

        scoreTable.toStream().foreach((key, value) -> {
            logger.info("KEY: {} - VALUE {}", key, value);
        });

        scoreTable
                .toStream()
                .to("user_recommendations", Produced.with(Serdes.String(), userRecommendationSpecificAvroSerde));

    }
}
