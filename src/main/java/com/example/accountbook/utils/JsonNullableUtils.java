package com.example.accountbook.utils;

import org.openapitools.jackson.nullable.JsonNullable;

import java.util.function.Consumer;

public final class JsonNullableUtils {

    private JsonNullableUtils() {
    }

    public static <T> void changeIfPresent(JsonNullable<T> jsonNullable, Consumer<T> consumer) {
        if (jsonNullable.isPresent()) {
            consumer.accept(jsonNullable.get());
        }
    }
}
