package com.epam.jdbc.validation;

public interface Validator {

    void validate(String sourceFilePath, String validationSchemaPath)
            throws Exception;
}
