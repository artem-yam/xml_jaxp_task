package com.epam.chat.validation;

import org.xml.sax.SAXException;

import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;

public class ValidatorSample {
    
    public void validate() throws SAXException, IOException {
        SchemaFactory factory =
            SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
        File validXml = new File("src/main/resources/chat.xml");
        Schema schema =
            factory.newSchema(new File("src/main/resources/chat.xsd"));
        
        Validator validator = schema.newValidator();
        
        validator.validate(new StreamSource(validXml));
    }
    
}