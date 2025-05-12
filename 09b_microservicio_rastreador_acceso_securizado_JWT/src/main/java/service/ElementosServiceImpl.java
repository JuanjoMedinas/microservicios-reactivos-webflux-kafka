package service;

import jakarta.annotation.PostConstruct;
import model.Credentials;
import model.Elemento;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Base64;

@Service
public class ElementosServiceImpl implements ElementosService {
    String url1 = "http://localhost:8000";
    String url2 = "http://localhost:9000";
    @Value("${user}")
    String user;
    @Value("${pwd}")
    String pwd;
    String token1,token2;
    @PostConstruct
    public void init(){
        Credentials credentials1 = new Credentials(user,pwd);
        Credentials credentials2 = new Credentials(user,pwd);
        loadToken(url1,credentials1).subscribe(s -> token1 =s);
        loadToken(url2,credentials2).subscribe(s -> token2 =s);
    }
    private Mono<String> loadToken(String url, Credentials credentials){
        return WebClient.create(url)
                .post()
                .uri("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(credentials)
                .retrieve()
                .bodyToMono(String.class);
    }
    @Override
    public Flux<Elemento> elementosPrecioMax(double precioMax) {
        Flux<Elemento> flux1 = catalogo(url1, "tienda1",token1);
        Flux<Elemento> flux2 = catalogo(url2, "tienda2",token2);
        return Flux.merge(flux1, flux2)
                .filter(e -> e.getPrecioUnitario() <= precioMax);
    }

    private Flux<Elemento> catalogo(String url, String tienda,String token) {
        WebClient webClient = WebClient.create(url);
        return webClient
                .get()
                .uri("/productos")
                .accept(MediaType.APPLICATION_JSON)
                //.header("Authorization", "Basic "+getEncoderBase64Credentials(user, pwd))
                .header("Authorization", "Bearer "+token)
                .retrieve()
                .bodyToFlux(Elemento.class)
                .map(e -> {
                    e.setTienda(tienda);
                    return e;
                });
    }

}
