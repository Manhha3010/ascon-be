package com.exsoft.momedumerchant.util;

public class HexToByteArray {
    public static byte[] calculate(String hexCiphertext) {


        // Convert hex string to byte array

        // Print the converted byte array

        return hexStringToByteArray(hexCiphertext);
    }

    // Function to convert a hexadecimal string to a byte array
    public static byte[] hexStringToByteArray(String hexString) {
        int length = hexString.length();
        byte[] byteArray = new byte[length / 2];
        for (int i = 0; i < length; i += 2) {
            byteArray[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4)
                    + Character.digit(hexString.charAt(i + 1), 16));
        }
        return byteArray;
    }

    public static String byteArrayToHexString(byte[] privateKey) {
        StringBuilder sb = new StringBuilder();
        for (byte b : privateKey) {
            sb.append(String.format("%02X", b));
        }
        return sb.toString();
    }
}
