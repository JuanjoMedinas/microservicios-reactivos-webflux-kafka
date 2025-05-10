package runners;

import model.Producto;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Component //al instanciarla, se ejecuta run directamente
public class TestRunner implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        WebClient client = WebClient.create("http://localhost:8000");
        Flux<Producto> flux = client.get().uri("/productos)")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve().bodyToFlux(Producto.class);
        flux.subscribe(System.out::println);
    }

}
