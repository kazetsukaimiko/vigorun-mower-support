package io.freedriver;

public class VigorunDemixer implements MixerAlgorithm {
    @Override
    public ThrottleAndSteering apply(SkidSteerLeftRight skidSteerLeftRight) {
        int leftOffset = skidSteerLeftRight.leftTread() - ChannelValues.MID;
        int rightOffset = skidSteerLeftRight.rightTread() - ChannelValues.MID;
        int leftMinusRight = leftOffset - rightOffset;
        int leftPlusRight = leftOffset + rightOffset;
        int throttlePosition = ChannelValues.MID + (leftPlusRight / 2);
        int steeringPosition = ChannelValues.MID + (leftMinusRight / 2);
        return new ThrottleAndSteering(throttlePosition, steeringPosition);
    }
}
