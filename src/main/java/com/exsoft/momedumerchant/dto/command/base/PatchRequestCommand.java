package com.exsoft.momedumerchant.dto.command.base;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public abstract class PatchRequestCommand implements Serializable {
    protected Map<String, Object> patchRequestData = new HashMap<>();

    public boolean contains(String key) {
        return patchRequestData.containsKey(key);
    }
}
