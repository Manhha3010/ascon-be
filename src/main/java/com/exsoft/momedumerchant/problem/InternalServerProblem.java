package com.exsoft.momedumerchant.problem;

import org.zalando.problem.Status;
import org.zalando.problem.ThrowableProblem;

import java.util.Map;

public class InternalServerProblem extends AbstractProblem {
    public InternalServerProblem(String title, String detail) {
        super(title, Status.INTERNAL_SERVER_ERROR, detail);
    }

    public InternalServerProblem(String title, String detail, Map<String, Object> parameters, ThrowableProblem cause) {
        super(title, Status.INTERNAL_SERVER_ERROR, detail, parameters, cause);
    }
}
