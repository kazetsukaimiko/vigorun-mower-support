package io.freedriver;

import java.util.function.Function;

public interface DemixerAlgorithm extends Function<ThrottleAndSteering, SkidSteerLeftRight> {
}
