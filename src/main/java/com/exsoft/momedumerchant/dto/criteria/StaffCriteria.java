package com.exsoft.momedumerchant.dto.criteria;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class StaffCriteria extends BaseCriteria {

    private Long status;

}
