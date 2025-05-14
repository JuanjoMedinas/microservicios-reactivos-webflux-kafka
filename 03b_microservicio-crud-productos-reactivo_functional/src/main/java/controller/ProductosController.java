package controller;

import model.Producto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import service.ProductosService;

//@CrossOrigin("*")
//@RestController
@Configuration
public class ProductosController {
    @Autowired
    ProductosService productosService;

    //    @GetMapping(value = "productos")
//    public ResponseEntity<Flux<Producto>> productos() {
//        return new ResponseEntity<>(productosService.catalogo(), HttpStatus.OK);
//    }
//
//    @GetMapping(value = "productos/{categoria}")
//    public ResponseEntity<Flux<Producto>> productosCategoria(@PathVariable("categoria") String categoria) {
//        return new ResponseEntity<>(productosService.productosCategoria(categoria), HttpStatus.OK);
//    }
//
//    @GetMapping(value = "producto")
//    public ResponseEntity<Mono<Producto>> productoCodigo(@RequestParam("cod") int cod) {
//        return new ResponseEntity<>(productosService.productoCodigo(cod), HttpStatus.OK);
//    }
//
//    @PostMapping(value = "alta")
//    public ResponseEntity<Mono<Void>> altaProducto(@RequestBody Producto producto) {
//        return new ResponseEntity<>(productosService.altaProducto(producto), HttpStatus.OK);
//    }
//
//    @DeleteMapping(value = "eliminar")
//    public Mono<ResponseEntity<Producto>> eliminarProducto(@RequestParam("cod") int cod) {
//        return productosService.eliminarProducto(cod) //Mono<Producto>
//                .map(p -> new ResponseEntity<>(p, HttpStatus.OK)) //Mono<ResponseEntity<Producto>>
//                .switchIfEmpty(Mono.just(new ResponseEntity<>(HttpStatus.NOT_FOUND)));
//    }
//
//    @PutMapping(value = "actualizar")
//    public Mono<ResponseEntity<Producto>> actualizarPrecio(@RequestParam("cod") int cod, @RequestParam("precio") double precio) {
//        return productosService.actualizarPrecio(cod, precio) //Mono<Producto>
//                .map(p -> new ResponseEntity<>(p, HttpStatus.OK)) //Mono<ResponseEntity<Producto>>)
//                .switchIfEmpty(Mono.just(new ResponseEntity<>(HttpStatus.NOT_FOUND)));
//    }
    @Bean
    public RouterFunction<ServerResponse> respuestas() {
        return RouterFunctions.route(RequestPredicates.GET("productos"),
                        request -> ServerResponse.ok() //BodyBuilder
                                .body(productosService.catalogo(), Producto.class)//Mono<ServerResponse>
                )//RouterFunction<ServerResponse>
                .andRoute(RequestPredicates.GET("productos/{categoria}"),
                        request -> ServerResponse.ok() //BodyBuilder
                                .body(productosService.productosCategoria(request.pathVariable("categoria")), Producto.class)//Mono<ServerResponse>
                )//Router
                .andRoute(RequestPredicates.GET("producto"),
                        request -> ServerResponse.ok() //BodyBuilder
                                .body(productosService.productoCodigo(Integer.parseInt(request.queryParam("cod").get())), Producto.class) //Mono<ServerResponse>

                )//RouterFunction<ServerResponse>
                .andRoute(RequestPredicates.POST("alta"),
                        request -> request.bodyToMono(Producto.class)//Mono<Producto>
                                .flatMap(p -> productosService.altaProducto(p)) //Mono<Void>
                                .flatMap(v -> ServerResponse.ok()//BodyBuilder
                                        .build())//Mono<ServeResponse
                )//RouterFunction<ServerResponse>
                .andRoute(RequestPredicates.DELETE("eliminar"),
                        request -> productosService.eliminarProducto(Integer.parseInt(request.queryParam("cod").get())) //Mono<Producto>
                                .flatMap(p -> ServerResponse.ok() //BodyBuilder
                                        .bodyValue(p)) //Mono<ServerResponse>)
                                .switchIfEmpty(ServerResponse.status(HttpStatus.NOT_FOUND)//BodyBuilder
                                        .build()//Mono<ServerResponse>
                                ) //Mono<ServerResponse>))
                )//RouterFunction<ServerResponse>
                .andRoute(RequestPredicates.PUT("actualizar"),
                        request -> productosService.actualizarPrecio(Integer.parseInt(request.queryParam("cod").get()), Double.parseDouble(request.queryParam("precio").get())) //Mono<Producto>
                                .flatMap(p -> ServerResponse.ok() //BodyBuilder
                                        .bodyValue(p) //Mono<ServerResponse>
                                ) //Mono<ServerResponse>
                                .switchIfEmpty(ServerResponse.status(HttpStatus.NOT_FOUND)//BodyBuilder
                                        .build()//Mono<ServerResponse
                                )//Mono<ServerResponse>
                );//RouterFunction<ServerResponse>
    }

    //en reactivo no nos vale la anotación CrossOrigin("*"), hay que hacer el filtro:
    @Bean
    CorsWebFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return new CorsWebFilter(source);
    }
}
