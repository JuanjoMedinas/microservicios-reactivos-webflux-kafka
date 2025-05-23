package inicio;


import model.Producto;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.test.StepVerifier;
import service.ProductosService;
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
public class ApplicationTests {
    @Autowired
    ProductosService service;
    @Order(1)
    @Test
   void testProductosCategoria(){
        StepVerifier.create(service.productosCategoria("Alimentación"))
                .expectNextMatches(p->p.getNombre().equals("Azucar"))
                .expectNextMatches(p->p.getNombre().equals("Leche"))
                .expectNextMatches(p->p.getNombre().equals("Huevos"))
                .verifyComplete();
    }
    @Order(2)
    @Test
    void testEliminarProducto(){
        StepVerifier.create(service.eliminarProducto(103))
                .expectNextMatches(p->p.getNombre().equals("Mesa"))
                .verifyComplete();
    }
    @Order(3)
    @Test
    void testAltaProducto(){
        Producto pr = new Producto(250,"ptest","cat1",10,2);
        StepVerifier.create(service.altaProducto(pr))
                .expectComplete()
                .verify();
    }

    @Test
    @Order(4)
    void textCatalogo(){
        StepVerifier.create(service.catalogo())
                .expectNextCount(8)
                .verifyComplete();
    }

}
