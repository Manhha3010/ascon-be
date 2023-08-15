package com.exsoft.momedumerchant.problem;

import org.zalando.problem.Status;

public class UnauthorizedProblem extends AbstractProblem {
    public UnauthorizedProblem(String title, String detail) {
        super(title, Status.UNAUTHORIZED, detail);
    }
}
