package com.exsoft.momedumerchant.dto.criteria;


import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class BaseCriteria extends DefaultQueryCriteria {

    private String query;

}
