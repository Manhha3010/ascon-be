package com.exsoft.momedumerchant.endpoint.ascon;

import lombok.Data;

@Data
public class DiffieHellManRequest {
        String publicKey;
        String privateKey;
        String nonce;
}
