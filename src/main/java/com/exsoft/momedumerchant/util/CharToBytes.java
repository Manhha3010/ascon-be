package com.exsoft.momedumerchant.util;

import org.bouncycastle.crypto.CharToByteConverter;

public class CharToBytes implements CharToByteConverter {
    /**
     * @return
     */
    @Override
    public String getType() {
        return null;
    }

    /**
     * @param chars
     * @return
     */
    @Override
    public byte[] convert(char[] chars) {
        return new byte[0];
    }
}
