package com.projecturanus.brokenworkbench.common;

public final class BitwiseUtils {
    private BitwiseUtils() {}

    /**
     * Get 1 bit boolean on specified index
     * For example, 0b111101111 & (1 << 4) == 0
     * @param index bit index
     * @param num binary number
     * @return if bit index on num is 1
     */
    public static boolean isTrueOnIndex(int index, int num) {
        return (num & (1 << index)) != 0;
    }

    public static int setTrueOnIndex(int index, int num) {
        return ((num) | 1 << index);
    }

    public static int booleanAsBinary(boolean... booleans) {
        int x = 0;
        for (boolean b : booleans) {
            x <<= 1;
            if (b) x += 1;
        }
        return x;
    }
}
