package com.exsoft.momedumerchant.problem;

import org.zalando.problem.Status;

public class InsufficientPermissionProblem extends AbstractProblem {

    public InsufficientPermissionProblem(String title, String detail) {
        super(title, Status.FORBIDDEN, detail);
    }

}
