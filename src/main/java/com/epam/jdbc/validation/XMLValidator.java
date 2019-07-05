package com.epam.jdbc.validation;

import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;

public class XMLValidator implements Validator {

    private static final String SCHEMA_LANGUAGE =
            "http://www.w3.org/2001/XMLSchema";

    public void validate(String sourceFilePath, String validationSchemaPath)
            throws Exception {
        SchemaFactory factory = SchemaFactory.newInstance(SCHEMA_LANGUAGE);
        Schema schema = factory.newSchema(new File(validationSchemaPath));
        javax.xml.validation.Validator validator = schema.newValidator();
        File xml = new File(sourceFilePath);
        validator.validate(new StreamSource(xml));
    }

}