package com.exsoft.momedumerchant.endpoint.asconv12;

import com.exsoft.momedumerchant.endpoint.ascon.AcsonPlaintextRequest;
import com.exsoft.momedumerchant.endpoint.ascon.Ascon128v1;
import com.exsoft.momedumerchant.endpoint.ascon.DiffieHellManRequest;
import com.exsoft.momedumerchant.util.HexToByteArray;
import com.google.crypto.tink.subtle.X25519;
import io.netty.util.internal.StringUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.CryptoPrimitive;
import java.security.InvalidKeyException;
import java.security.SecureRandom;
import java.util.Arrays;

@RequestMapping("/asconv12")
@RestController
@RequiredArgsConstructor
public class Asconv12Endpoint {

    byte[] key = {
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00
    };
    byte[] nonce = {
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00
    };
    byte[] privateKey = new byte[Ascon128av12.CRYPTO_ABYTES];

    byte[] publicKey = new byte[Ascon128av12.CRYPTO_ABYTES];

    byte[] sharedKey = new byte[Ascon128av12.CRYPTO_ABYTES];


    @PostMapping("/diffie-hellman")
    public ResponseEntity<String> diffieHellMan(@RequestBody DiffieHellManRequest request) throws InvalidKeyException {
        byte[] publicKeyPartner = StringUtil.decodeHexDump(request.getPublicKey());
        // generate private key
        byte[] ephemeralPrivateKeyBytes = X25519.generatePrivateKey();
        byte[] ephemeralPublicKeyBytes = X25519.publicFromPrivate(ephemeralPrivateKeyBytes);
        // generate public key
        sharedKey =  X25519.computeSharedSecret(ephemeralPrivateKeyBytes, publicKeyPartner);

        // generate shared key


        return new ResponseEntity<>(StringUtil.toHexString(ephemeralPublicKeyBytes), HttpStatus.OK);

    }

    @PostMapping
    public ResponseEntity<String> ascon(@RequestBody AcsonPlaintextRequest request) {


        // Define the plaintext message
        String plaintext = "Hello, ASCON!";
        byte[] plaintextBytes = plaintext.getBytes();
        int plaintextLength = plaintextBytes.length;

        // Additional data (associated data)
        byte[] ad = { /* Add your associated data here if needed */ };

        // Ciphertext and decrypted message
        byte[] ciphertext = new byte[plaintextLength + Ascon128av12.CRYPTO_ABYTES];
        byte[] decrypted = new byte[plaintextLength];
        // get 16 byte from shared key
        byte[] sharedKey16 = Arrays.copyOfRange(sharedKey, 0, 16);

        // Encrypt
        int ciphertextLength = Ascon128av12.crypto_aead_encrypt(
                ciphertext, ciphertext.length, plaintextBytes, plaintextLength, null,0, null, nonce, sharedKey16
        );
        System.err.println("in decty"+StringUtil.toHexString(sharedKey));



        byte [] ciphertextByte = StringUtil.decodeHexDump(request.getCiphertext());
        // Decrypt decrypted.length not use and nsec not use
        int decryptedLength = Ascon128av12.crypto_aead_decrypt(
                decrypted, decrypted.length, null, ciphertextByte, ciphertextLength, null, 0, nonce, sharedKey16
        );

        if (decryptedLength >= 0) {
            String decryptedMessage = new String(decrypted, 0, decryptedLength);
            System.out.println("Decrypted Message: " + decryptedMessage);
            return new ResponseEntity<>(decryptedMessage, HttpStatus.OK);
        } else {
            System.out.println("Decryption failed!");
        }
        return new ResponseEntity<>("Decryption failed!", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
