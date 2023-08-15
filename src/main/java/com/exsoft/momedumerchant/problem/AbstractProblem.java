package com.exsoft.momedumerchant.problem;

import lombok.Getter;
import org.zalando.problem.StatusType;
import org.zalando.problem.ThrowableProblem;

import java.util.Collections;
import java.util.Map;

@Getter
public class AbstractProblem extends ThrowableProblem {
    private final String title;
    private final StatusType status;
    private final String detail;
    private final Map<String, Object> parameters;

    public AbstractProblem(String title, StatusType status) {
        this(title, status, null);
    }

    public AbstractProblem(String title, StatusType status, String detail) {
        this(title, status, detail, Collections.emptyMap());
    }

    public AbstractProblem(String title, StatusType status, String detail, Map<String, Object> parameters) {
        this(title, status, detail, parameters, null);
    }

    public AbstractProblem(String title, StatusType status, String detail, Map<String, Object> parameters, ThrowableProblem cause) {
        super(cause);
        this.title = title;
        this.status = status;
        this.detail = detail;
        this.parameters = parameters;
    }
}

