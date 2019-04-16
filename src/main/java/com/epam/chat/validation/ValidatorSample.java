package com.epam.chat.validation;

import org.xml.sax.SAXException;

import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;

public class ValidatorSample {

    public static void main(String[] args) throws SAXException, IOException {
        ValidatorSample validator = new ValidatorSample();
        validator.validate();
    }

    public void validate() throws SAXException, IOException {
        SchemaFactory factory =
                SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
        File validXml = new File("src/main/resources/chat.xml");
        Schema schema =
                factory.newSchema(new File("src/main/resources/chat.xsd"));

        Validator validator = schema.newValidator();

        try {
            validator.validate(new StreamSource(validXml));
        } catch (SAXException e) {
            System.err.println("Valid XML isn't valid??");
        }
        System.out.println("OK");
    }

}
