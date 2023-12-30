# Kafka avro Serialize & Deserialize showcase


### solution 1: Service based
1. one method for all topics
2. pre-process/validation/post-process can be hooked in
```java
public class KafkaProducerService {
    <V> void sendMessage(String topic, V message) {

    }
}
```

### solution 2: Proxy based 
1. one method per topic, topic is a annoation
2. pre-process/validation/post-process can be hooked in as annotation props
```java
public interface KafkaProducerClient {

    @ProducerHandler(topic = "sample-json-topic")
    void sendDemoMessage(PersonInfo message);

    @ProducerHandler(topic = "sample-avro-topic")
    void sendAvroMessage(AvroPersonInfo message);
}
```
