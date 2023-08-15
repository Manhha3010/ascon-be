package com.exsoft.momedumerchant.problem;

import org.zalando.problem.Status;
import org.zalando.problem.ThrowableProblem;

import java.util.Map;

public class NotFoundProblem extends AbstractProblem {
    public NotFoundProblem(String title, String detail) {
        super(title, Status.NOT_FOUND, detail);
    }

    public NotFoundProblem(String title, String detail, Map<String, Object> parameters, ThrowableProblem cause) {
        super(title, Status.NOT_FOUND, detail, parameters, cause);
    }
}
