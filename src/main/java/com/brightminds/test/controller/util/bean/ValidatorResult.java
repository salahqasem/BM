package com.brightminds.test.controller.util.bean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ValidatorResult {

    private final List<String> errors = new ArrayList();

    public ValidatorResult(List<String> errors) {
        if(errors != null && !errors.isEmpty()) {
            this.errors.addAll(errors);
        }
    }

    public boolean isValid() {
        return errors.isEmpty();
    }

    public List<String> getErrors() {
        return Collections.unmodifiableList(errors);
    }
}
