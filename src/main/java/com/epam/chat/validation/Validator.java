package com.epam.chat.validation;

public interface Validator {

    void validate(String sourceFilePath, String validationSchemaPath)
            throws Exception;
}
