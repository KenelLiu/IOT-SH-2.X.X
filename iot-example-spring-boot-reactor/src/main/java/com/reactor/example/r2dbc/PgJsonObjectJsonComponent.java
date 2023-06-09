package com.reactor.example.r2dbc;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.*;
import io.r2dbc.postgresql.codec.Json;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;

@Log4j2
@JsonComponent
class PgJsonObjectJsonComponent {

    static class Deserializer extends JsonDeserializer<Json> {

        @Override
        public Json deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
            JsonNode value = ctxt.readTree(p);
            log.info("read json value :{}", value);
            return Json.of(value.toString());
        }
    }

    static class Serializer extends JsonSerializer<Json> {

        @Override
        public void serialize(Json value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            String text = value.asString();
            log.info("The raw json value from PostgresSQL JSON type:{}", text);
            JsonFactory factory = new JsonFactory();
            JsonParser parser = factory.createParser(text);
            TreeNode node = gen.getCodec().readTree(parser);
            serializers.defaultSerializeValue(node, gen);
        }

    }
}