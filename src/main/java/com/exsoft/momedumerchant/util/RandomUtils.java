package com.exsoft.momedumerchant.util;

import java.security.SecureRandom;
import java.util.UUID;

public abstract class RandomUtils {

    private static final SecureRandom random = new SecureRandom(randomUUID().toString().getBytes());

    public static UUID randomUUID() {
        return UUID.randomUUID();
    }

    public static int randomInt() {
        return random.nextInt();
    }

    public static int randomInt(int bound) {
        return random.nextInt(bound);
    }

}
