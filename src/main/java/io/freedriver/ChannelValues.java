package io.freedriver;

public class ChannelValues {
    public static final int MIN = 1100;
    public static final int MID = 1500;
    public static final int MAX = 1900;

    public static int left(boolean inverted) {
        return inverted ? MAX : MIN;
    }
    public static int left() {
        return left(false);
    }
    public static int right(boolean inverted) {
        return left(!inverted);
    }
    public static int right() {
        return right(false);
    }

    public static int down(boolean inverted) {
        return inverted ? MAX : MIN;
    }
    public static int down() {
        return down(false);
    }
    public static int up(boolean inverted) {
        return down(!inverted);
    }
    public static int up() {
        return up(false);
    }

}
