package inicio;
import controller.NamesController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.test.StepVerifier;

@SpringBootTest
public class ApplicationTests {
    @Autowired
    NamesController controller;
    @Test
    void contextLoads(){
        StepVerifier.create(controller.getNames())
                .expectNext("one")
                .expectNext("two","three")
                .expectNextCount(1)
                .verifyComplete();
    }
}
