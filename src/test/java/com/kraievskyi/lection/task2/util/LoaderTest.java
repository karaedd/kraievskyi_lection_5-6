package com.kraievskyi.lection.task2.util;

import com.kraievskyi.lection.task2.model.Model;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.time.Instant;

class LoaderTest {

    private static final Path PATH = Path.of("src/main/resources/task2/config.properties");
    private static Model EXPECTED;
    private static Model ACTUAL;

    @BeforeAll
    static void setUp() {
        EXPECTED = new Model("Hello world", 50, Instant.parse("2022-11-29T18:30:00Z"));
        ACTUAL = Loader.loadFromProperties(Model.class, PATH);
    }

    @Test
    void expectedModel_Ok() {
        Assertions.assertEquals(ACTUAL, EXPECTED);
    }
}
