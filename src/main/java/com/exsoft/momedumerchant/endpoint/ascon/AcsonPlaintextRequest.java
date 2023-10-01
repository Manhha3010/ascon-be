package com.exsoft.momedumerchant.endpoint.ascon;

import lombok.Data;

@Data
public class AcsonPlaintextRequest {

    String ciphertext;
    String publicKey;
    String nonce;
    Integer cipherTextLength;
    Integer messageLength;
}
