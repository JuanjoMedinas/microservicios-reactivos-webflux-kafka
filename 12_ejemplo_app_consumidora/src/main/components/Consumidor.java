package components;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;


import java.time.Duration;
import java.util.List;
import java.util.Properties;

public class Consumidor {
    private KafkaConsumer<String, String> consumer;

    public Consumidor() {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", "grupoA");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        consumer = new KafkaConsumer<>(props);
    }
    public void suscribir(String topic){
        consumer.subscribe(List.of(topic));
        while(true){
          ConsumerRecords<String, String> records =  consumer.poll(Duration.ofMillis(200));
          for(var record:records){
              System.out.println(record.value());
          }
        }

    }
}
