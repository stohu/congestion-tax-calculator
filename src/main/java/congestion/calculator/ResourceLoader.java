package congestion.calculator;

import lombok.Getter;
import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

@Getter
public class ResourceLoader {

    private String content;

    public ResourceLoader(String path) throws IOException {
        var input = new ClassPathResource(path).getInputStream();
        try (var reader = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8))) {
            content = reader.lines().collect(Collectors.joining("\n"));
        }
    }
}
