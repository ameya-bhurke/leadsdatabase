package util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import model.ClassificationEnum;

import java.io.IOException;

public class CustomClassificationEnumSerializer extends JsonSerializer<ClassificationEnum> {

    public void serialize(ClassificationEnum classificationEnum, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        jsonGenerator.writeString(classificationEnum.getValue());
    }
}
