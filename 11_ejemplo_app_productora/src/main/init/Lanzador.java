package init;

import components.Productor;

import java.time.LocalDateTime;

public class Lanzador {
    public static void main(String[] args) {
        Productor productor = new Productor();
        for (int i=1;i<=10;i++){
            productor.send("topicTest","Mensage generado a las "+ LocalDateTime.now()+" para  topicTest");
        }
        productor.cerrar();
    }
}
