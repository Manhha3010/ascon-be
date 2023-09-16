package com.exsoft.momedumerchant.endpoint.ascon;

import lombok.Data;

@Data
public class AcsonPlaintextRequest {

    String ciphertext;
    String key;
    String nonce;
}
