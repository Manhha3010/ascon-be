package com.exsoft.momedumerchant.dto.criteria;

import com.exsoft.momedumerchant.util.StringUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Data
public abstract class DefaultQueryCriteria implements Serializable {
    public static final String SORT_DELIMITER = ":";
    public static final String SORT_DESC = "desc";

    private int page = 0;
    private int size = 25;
    private String sort;

    @JsonIgnore
    public Pageable getPageable() {
        Sort s = Sort.unsorted();
        if (StringUtils.hasText(sort)) {
            Set<String> sortParams = StringUtils.commaDelimitedListToSet(sort);
            Set<Sort.Order> orders = new HashSet<>();
            sortParams.forEach(keyValuePair -> {
                String[] params = StringUtils.split(keyValuePair, SORT_DELIMITER);
                if (params != null && params.length > 0) {
                    if (SORT_DESC.equalsIgnoreCase(params[1]))
                        orders.add(Sort.Order.desc(params[0]));
                    else
                        orders.add(Sort.Order.asc(params[0]));
                } else
                    orders.add(Sort.Order.asc(keyValuePair));
            });
            if (!orders.isEmpty())
                s = Sort.by(new ArrayList<>(orders));
        }
        return PageRequest.of(page, size, s);
    }
}
