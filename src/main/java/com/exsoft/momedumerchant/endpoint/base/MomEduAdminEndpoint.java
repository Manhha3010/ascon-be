package com.exsoft.momedumerchant.endpoint.base;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;

public abstract class MomEduAdminEndpoint {
    public static final String HEADER_PAGE_COUNT = "X-Page-Count";
    public static final String HEADER_PAGE_NUMBER = "X-Page-Number";
    public static final String HEADER_PAGE_SIZE = "X-Page-Size";
    public static final String HEADER_TOTAL_COUNT = "X-Total-Count";
    public static final ResponseEntity<?> RESPONSE_NO_CONTENT = ResponseEntity.noContent().build();
    public static final ResponseEntity<?> RESPONSE_ACCEPTED = ResponseEntity.accepted().build();

    private static HttpHeaders buildPagingHeaders(@NonNull Page<?> page) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HEADER_PAGE_COUNT, String.valueOf(page.getTotalPages()));
        headers.add(HEADER_PAGE_NUMBER, String.valueOf(page.getPageable().getPageNumber()));
        headers.add(HEADER_PAGE_SIZE, String.valueOf(page.getPageable().getPageSize()));
        headers.add(HEADER_TOTAL_COUNT, String.valueOf(page.getTotalElements()));
        return headers;
    }

    public ResponseEntity<?> buildPagingResponse(@NonNull Page<?> page) {
        return ResponseEntity.ok().headers(buildPagingHeaders(page)).body(page.getContent());
    }
}
