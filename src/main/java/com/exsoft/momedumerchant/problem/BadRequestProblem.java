package com.exsoft.momedumerchant.problem;

import org.zalando.problem.Status;

public class BadRequestProblem extends AbstractProblem {
    public BadRequestProblem(String title, String detail) {
        super(title, Status.BAD_REQUEST, detail);
    }
}
