package com.exsoft.momedumerchant.endpoint.ascon;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ascon")
@RequiredArgsConstructor
@Slf4j
public class AsconEndPoint {

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

    private final byte[] key = {
            0x01, 0x23, 0x45, 0x67, (byte) 0x89, (byte) 0xAB, (byte) 0xCD, (byte) 0xEF,
            (byte) 0xFE, (byte) 0xDC, (byte) 0xBA, (byte) 0x98, (byte) 0x76, (byte) 0x54, (byte) 0x32, 0x10
    };

    private final byte[] nonce = {
            0x12, 0x34, 0x56, 0x78, (byte) 0x9A, (byte) 0xBC, (byte) 0xDE, (byte) 0xF0,
            (byte) 0xF0, (byte) 0xDE, (byte) 0xBC, (byte) 0x9A, 0x78, 0x56, 0x34, 0x12
    };


    @PostMapping
    public String ascon(@RequestBody AcsonPlaintextRequest request) {
        System.err.println(request.getPlainText());

        return request.getPlainText();
    }
}
