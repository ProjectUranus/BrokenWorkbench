package com.projecturanus.brokenworkbench.common.util;

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

    public static boolean[] binaryAsBooleans(int n, int size) {
        boolean[] booleans = new boolean[size];
        for (int i = 0; i < size; i++) {
            booleans[i] = isTrueOnIndex(i, n);
        }
        return booleans;
    }

    public static String disabledSlotDescription(int disabledSlots) {
        StringBuilder stringBuilder = new StringBuilder();
        boolean[] disabled = binaryAsBooleans(disabledSlots, 9);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; i < 3; i++) {
                if (disabled[i * 3 + j]) {
                    stringBuilder.append("(").append(i + 1).append(", ").append(j + 1).append(")").append(", ");
                }
            }
        }
        return stringBuilder.toString();
    }
}
