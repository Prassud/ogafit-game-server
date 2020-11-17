import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.ogofit"})
@RequiredArgsConstructor
@Slf4j
public class OgoFitGameApplication {

    public static void main(String[] args) {
        SpringApplication.run(OgoFitGameApplication.class, args);
    }

}