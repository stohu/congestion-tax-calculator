package congestion.calculator;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
@Slf4j
public class SpringConfiguration {

    @Autowired
    private ObjectMapper mapper;

    @Bean
    public TollRules getTollRules() {
        try {
            var loader = new ResourceLoader("rules.json");
            String json = loader.getContent();
            return mapper.readValue(json, TollRulesTemplate.class);
        } catch (IOException e) {
            log.warn("file rules.json not found in resource folder!");
            return new GothenburgRules();
        }
    }
}

