package io.freedriver;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.util.Optional;
import java.util.stream.Stream;

import static io.freedriver.ChannelValues.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class VigorunMixerTest {

    MixerAlgorithm victim = new VigorunDemixer();

    @ParameterizedTest
    @EnumSource(TestCases.class)
    public void testExtremes(TestCases testCase) {
        // This is the skid steer input we want
        var result = victim.apply(testCase.input);

        // This is how we simulate it on the controller-
        var errors = Stream.concat(
                checkValues("Steering", testCase.expectedResult.steering(), result.steering()),
                checkValues("Throttle", testCase.expectedResult.throttle(), result.throttle())
        ).toList();

        assertTrue(errors.isEmpty(), String.join("\n", errors));
    }

    private static Stream<String> checkValues(String name, int expected, int actual) {
        if (expected == actual) {
            System.out.printf("%s was %d as expected.%n", name, actual);
        }
        return expected == actual
                ? Stream.empty()
                : Stream.of("%s should be %d, was %d".formatted(name, expected, actual));
    }

    @Getter
    @RequiredArgsConstructor
    public enum TestCases {
        PIVOT_LEFT(
                new SkidSteerLeftRight(MIN, MAX),
                new ThrottleAndSteering(MID, MIN)
        ),
        PIVOT_RIGHT(
                new SkidSteerLeftRight(MAX, MIN),
                new ThrottleAndSteering(MID, MAX)
        ),
        BOTH_MAX_FORWARD(
                new SkidSteerLeftRight(MAX, MAX),
                new ThrottleAndSteering(MAX, MID)
        ),
        BOTH_MAX_BACKWARD(
                new SkidSteerLeftRight(MIN, MIN),
                new ThrottleAndSteering(MIN, MID)
        ),
        RIGHT_TREAD_MAX_FORWARD(
                new SkidSteerLeftRight(MID, MAX),
                new ThrottleAndSteering(MAX, MIN)
        ),
        RIGHT_TREAD_MAX_BACKWARD(
                new SkidSteerLeftRight(MID, MIN),
                new ThrottleAndSteering(MIN, MIN)
        ),
        LEFT_TREAD_MAX_FORWARD(
                new SkidSteerLeftRight(MAX, MID),
                new ThrottleAndSteering(MAX, MAX)
        ),
        LEFT_TREAD_MAX_BACKWARD(
                new SkidSteerLeftRight(MIN, MID),
                new ThrottleAndSteering(MIN, MAX)
        ),
        NO_MOVEMENT(
                new SkidSteerLeftRight(MID, MID),
                new ThrottleAndSteering(MID, MID)
        ),
        ;

        private final SkidSteerLeftRight input;
        private final ThrottleAndSteering expectedResult;

    }
}
