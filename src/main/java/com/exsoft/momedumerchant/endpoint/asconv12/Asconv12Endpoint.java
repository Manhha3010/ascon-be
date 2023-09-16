package com.exsoft.momedumerchant.endpoint.asconv12;

import com.exsoft.momedumerchant.endpoint.ascon.AcsonPlaintextRequest;
import com.exsoft.momedumerchant.endpoint.ascon.Ascon128v1;
import com.exsoft.momedumerchant.util.HexToByteArray;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/asconv12")
@RestController
public class Asconv12Endpoint {

    //    byte[] ciphertext = ...; // Dữ liệu đã mã hóa
//    byte[] ad = ...; // Dữ liệu dữ kiện (optional)
//    byte[] nonce = ...; // Giá trị nonce (16 bytes)
//    byte[] key = ...; // Khóa (16 bytes)
//
//    Ascon128v1 ascon = new Ascon128v1();
//    byte[] decrypted = new byte[ciphertext.length - Ascon128v1.CRYPTO_ABYTES];
//    int decryptedLength = ascon.crypto_aead_decrypt(
//            decrypted, decrypted.length,
//            null, ciphertext, ciphertext.length,
//            ad, ad.length,
//            null, key
//    );

//    byte[] plaintext = ...; // Dữ liệu bạn muốn mã hóa
//    byte[] ad = ...; // Dữ liệu dữ kiện (optional)
//    byte[] nonce = ...; // Giá trị nonce (16 bytes)
//    byte[] key = ...; // Khóa (16 bytes)
//
//    Ascon128v1 ascon = new Ascon128v1();
//    byte[] ciphertext = new byte[plaintext.length + Ascon128v1.CRYPTO_ABYTES];
//    int ciphertextLength = ascon.crypto_aead_encrypt(
//            ciphertext, ciphertext.length,
//            plaintext, plaintext.length,
//            ad, ad.length,
//            null, nonce, key
//    );

    //    byte[] key = {
//            0x01, 0x23, 0x45, 0x67, (byte) 0x89, (byte) 0xAB, (byte) 0xCD, (byte) 0xEF,
//            (byte) 0xFE, (byte) 0xDC, (byte) 0xBA, (byte) 0x98, 0x76, 0x54, 0x32, 0x10
//    };
//
//    byte[] nonce = {
//            0x12, 0x34, 0x56, 0x78, (byte) 0x9A, (byte) 0xBC, (byte) 0xDE, (byte) 0xF0,
//            (byte) 0xF0, (byte) 0xDE, (byte) 0xBC, (byte) 0x9A, 0x78, 0x56, 0x34, 0x12
//    };
    char[] key2 = {
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00
    };
    char[] nonce2 = {
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00
    };

    byte[] key2Byte = {
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00
    };
    byte[] nonce2Byte = {
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00
    };
//     char[] nonce2= {
//        0x12, 0x34, 0x56, 0x78, 0x9A, 0xBC, 0xDE, 0xF0,
//                0xF0, 0xDE, 0xBC, 0x9A, 0x78, 0x56, 0x34, 0x12
//    };


    @PostMapping
    public ResponseEntity<String> ascon(@RequestBody AcsonPlaintextRequest request) {

        byte[] key = {
                0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
                0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00
        };
        byte[] nonce = {
                0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
                0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00
        };

        // Define the plaintext message
        String plaintext = "Hello, ASCON!";
        byte[] plaintextBytes = plaintext.getBytes();
        int plaintextLength = plaintextBytes.length;
        System.out.println("Plaintext Length: " + plaintextLength);

        // Additional data (associated data)
        byte[] ad = { /* Add your associated data here if needed */ };

        // Ciphertext and decrypted message
        byte[] ciphertext = new byte[plaintextLength + Ascon128av12.CRYPTO_ABYTES];
        byte[] decrypted = new byte[plaintextLength];

        // Encrypt
        int ciphertextLength = Ascon128av12.crypto_aead_encrypt(
                ciphertext, ciphertext.length, plaintextBytes, plaintextLength, null,0, null, nonce, key
        );



        byte [] ciphertextByte = HexToByteArray.hexStringToByteArray(request.getCiphertext());
        // Decrypt decrypted.length not use and nsec not use
        int decryptedLength = Ascon128av12.crypto_aead_decrypt(
                decrypted, decrypted.length, null, ciphertextByte, ciphertextLength, null, 0, nonce, key
        );

        if (decryptedLength >= 0) {
            String decryptedMessage = new String(decrypted, 0, decryptedLength);
            System.out.println("Decrypted Message: " + decryptedMessage);
            return new ResponseEntity<>(decryptedMessage, HttpStatus.OK);
        } else {
            System.out.println("Decryption failed!");
        }
        return new ResponseEntity<>("", HttpStatus.OK);
    }
}
