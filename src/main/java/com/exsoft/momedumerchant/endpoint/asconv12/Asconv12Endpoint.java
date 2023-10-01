package com.exsoft.momedumerchant.endpoint.asconv12;

import com.exsoft.momedumerchant.dto.DeviceControl;
import com.exsoft.momedumerchant.dto.HomeStatus;
import com.exsoft.momedumerchant.endpoint.ascon.AcsonPlaintextRequest;
import com.exsoft.momedumerchant.endpoint.ascon.Ascon128v1;
import com.exsoft.momedumerchant.endpoint.ascon.DiffieHellManRequest;
import com.exsoft.momedumerchant.util.HexToByteArray;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.crypto.tink.subtle.X25519;
import com.google.gson.Gson;
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
        byte[] ad = { /* Add your associated data here if needed */};
        Gson gson = new Gson();
        HomeStatus homeStatus ;
        DeviceControl deviceControl = new DeviceControl();
        int plaintextLength = request.getMessageLength();
        byte[] ciphertextByte = StringUtil.decodeHexDump(request.getCiphertext());
        byte[] sharedKey16 = Arrays.copyOfRange(sharedKey, 0, 16);
        byte[] decrypted = new byte[plaintextLength];
        int decryptedLength = Ascon128av12.crypto_aead_decrypt(
                decrypted, decrypted.length, null, ciphertextByte, request.getCipherTextLength(), null, 0, nonce, sharedKey16
        );
        if (decryptedLength >= 0) {
            String decryptedMessage = new String(decrypted, 0, decryptedLength);
            homeStatus = gson.fromJson(decryptedMessage, HomeStatus.class);
            System.out.println("Decrypted Message: " + decryptedMessage);
        } else {
            System.out.println("Decryption failed!");
            return new ResponseEntity<>("Decryption failed!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
//        deviceControl.setFan(homeStatus.getHumidity() > 30);
//        deviceControl.setLight(homeStatus.getTemperature() > 30);
//        deviceControl.setPump(homeStatus.getTemperature() > 30);
        deviceControl.setFan(Math.random()<0.5);
        deviceControl.setLight(Math.random()<0.5);
        deviceControl.setPump(Math.random()<0.5);
        String plaintext = gson.toJson(deviceControl);
        byte[] plaintextBytes = plaintext.getBytes();
        byte[] ciphertext = new byte[plaintext.getBytes().length + Ascon128av12.CRYPTO_ABYTES];
        System.err.println("plaintext: " + plaintext);
        int ciphertextLength = Ascon128av12.crypto_aead_encrypt(
                ciphertext, ciphertext.length, plaintextBytes, plaintext.getBytes().length, null, 0, null, nonce, sharedKey16
        );
        if (ciphertextLength >= 0) {
            String ciphertextHex = StringUtil.toHexString(ciphertext);
            System.out.println("Ciphertext: " + ciphertextHex);
            return new ResponseEntity<>(ciphertextHex, HttpStatus.OK);
        } else {
            System.out.println("Encryption failed!");
            return new ResponseEntity<>("Encryption failed!", HttpStatus.INTERNAL_SERVER_ERROR);
        }


        // Encrypt


        // Decrypt decrypted.length not use and nsec not use

    }
}
