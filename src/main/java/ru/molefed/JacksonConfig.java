package ru.molefed;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Configuration
public class JacksonConfig {

    @Bean
    public ObjectMapper objectMapper(Jackson2ObjectMapperBuilder builder) {
        ObjectMapper objectMapper = builder.createXmlMapper(false).build();
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.registerModule(jsonMapperJava8DateTimeModule());
        return objectMapper;
    }

    private Module jsonMapperJava8DateTimeModule() {
        SimpleModule bean = new SimpleModule();

        bean.addSerializer(ZonedDateTime.class, new JsonSerializer<>() {
            @Override
            public void serialize(
                    ZonedDateTime zonedDateTime, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
                    throws IOException {
                jsonGenerator.writeString(DateTimeFormatter.ISO_ZONED_DATE_TIME.format(zonedDateTime));
            }
        });

        bean.addDeserializer(ZonedDateTime.class, new JsonDeserializer<>() {
            @Override
            public ZonedDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                return ZonedDateTime.parse(jsonParser.getValueAsString(), DateTimeFormatter.ISO_ZONED_DATE_TIME);
            }
        });

        bean.addSerializer(LocalDateTime.class, new JsonSerializer<>() {
            @Override
            public void serialize(
                    LocalDateTime localDateTime, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
                    throws IOException {
                jsonGenerator.writeString(DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(localDateTime));
            }
        });

        bean.addDeserializer(LocalDateTime.class, new JsonDeserializer<>() {
            @Override
            public LocalDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                return LocalDateTime.parse(jsonParser.getValueAsString(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            }
        });

        bean.addSerializer(LocalDate.class, new JsonSerializer<>() {
            @Override
            public void serialize(
                    LocalDate localDate, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
                    throws IOException {
                jsonGenerator.writeString(DateTimeFormatter.ISO_LOCAL_DATE.format(localDate));
            }
        });

        bean.addDeserializer(LocalDate.class, new JsonDeserializer<>() {
            @Override
            public LocalDate deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                return LocalDate.parse(jsonParser.getValueAsString(), DateTimeFormatter.ISO_LOCAL_DATE);
            }
        });

        return bean;
    }

}
