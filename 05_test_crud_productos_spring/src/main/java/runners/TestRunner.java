package runners;

import model.Producto;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component //al instanciarla, se ejecuta run directamente
public class TestRunner implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        WebClient client = WebClient.create("http://localhost:8000");
//        Flux<Producto> flux = client.get().uri("/productos")
//                .accept(MediaType.APPLICATION_JSON)
//                .retrieve().bodyToFlux(Producto.class);
//        flux.subscribe(System.out::println);
//        client.post().uri("/alta")
//                .body(Mono.just(new Producto(200, "prueba", "categoria test", 5.0, 20)), Producto.class)
//                .retrieve().bodyToMono(Void.class).doOnTerminate(() -> System.out.println("se ha dado de alta el producto, o  no"))
//                .block();
//        Mono<Producto> monoFind = client.get().uri("/producto?cod=190").accept(MediaType.APPLICATION_JSON)
//                .retrieve().bodyToMono(Producto.class);
//        monoFind.subscribe(System.out::println);
//        monoFind.switchIfEmpty(Mono.just(new Producto()).map(p ->
//        {
//            System.out.println("no encontrado");
//            return p;
//        })).block();
    client.delete().uri("/eliminar?cod=190")
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .onStatus(h -> h.is4xxClientError(),t->{
                System.out.println("no encontrado");
                return Mono.empty();
            })
            .bodyToMono(Producto.class)
            .subscribe(System.out::println);

    }

}
