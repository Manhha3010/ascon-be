package com.exsoft.momedumerchant.problem;

import org.zalando.problem.Status;

public class AccessDeniedProblem extends AbstractProblem {
    public AccessDeniedProblem(String title, String detail) {
        super(title, Status.FORBIDDEN, detail);
    }
}