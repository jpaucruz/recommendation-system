package com.jpaudelacruz.recommendation_system_ms.shared;

import java.time.Instant;

public class CustomDateUtils {

    public static Long toEpochMillis(String date) {
        Instant instant = Instant.parse(date);
        return instant.toEpochMilli();
    }

}