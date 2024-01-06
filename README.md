# Kafka avro Serialize & Deserialize showcase

```java
import com.demo.kafka.framework.Producer;
import com.demo.kafka.model.PersonInfo;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;

import java.time.Duration;

@Producer
public interface KafkaProducerClient {

    @ProducerHandler(
            topic = "sample-json-topic",
            validator = "Validator",
            preprocessor = "Preprocessor",
            postprocessor = "Postprocessor"
    )
    ListenableFuture<SendResult<String, PersonInfo>> sendDemoMessage(PersonInfo message);

    @ProducerHandler(
            topic = "sample-json-topic",
            validator = "Validator",
            preprocessor = "Preprocessor",
            postprocessor = "Postprocessor"
    )
    SendResult<String, PersonInfo> blockSendDemoMessage(PersonInfo message, Duration timeout);
}
```
