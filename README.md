# Kafka avro Serialize & Deserialize showcase



## Prerequisite Kafka & SchemaRegistry
Kafka: https://kafka.apache.org/downloads
Registry: https://github.com/confluentinc/schema-registry


## init
#### start service
```bash
docker-compose up
```

#### create topic:  avro-topic
http://localhost:9021

Schema:
```json
{
  "fields": [
    {
      "name": "name",
      "type": "string"
    },
    {
      "name": "age",
      "type": "int"
    }
  ],
  "name": "AvroPersonInfo",
  "namespace": "com.demo.kafka.model",
  "type": "record"
}
```



## project

##### properties
```xml
<properties>
    <java.version>17</java.version>
    <avro.version>1.11.1</avro.version>
    <confluent.version>7.5.2</confluent.version>
</properties>
```

##### deps
```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.kafka</groupId>
        <artifactId>spring-kafka</artifactId>
    </dependency>
    <dependency>
        <groupId>org.apache.avro</groupId>
        <artifactId>avro</artifactId>
        <version>${avro.version}</version>
    </dependency>
    <dependency>
        <groupId>io.confluent</groupId>
        <artifactId>kafka-avro-serializer</artifactId>
        <version>${confluent.version}</version>
        <exclusions>
            <exclusion>
                <groupId>org.apache.zookeeper</groupId>
                <artifactId>zookeeper</artifactId>
            </exclusion>
        </exclusions>
    </dependency>
</dependencies>
```

##### plugin
```xml
<plugin>
    <groupId>org.apache.avro</groupId>
    <artifactId>avro-maven-plugin</artifactId>
    <version>${avro.version}</version>
    <executions>
        <execution>
            <phase>generate-sources</phase>
            <goals>
                <goal>schema</goal>
            </goals>
            <configuration>
                <sourceDirectory>${project.basedir}/src/main/resources/avro</sourceDirectory>
                <outputDirectory>${project.basedir}/src/main/java</outputDirectory>
                <createSetters>false</createSetters>
            </configuration>
        </execution>
    </executions>
</plugin>
```

##### repo
```xml
<repositories>
    <repository>
        <id>confluent</id>
        <url>https://packages.confluent.io/maven/</url>
    </repository>
</repositories>
```

##### init model
```shell
mvn generate-sources
```

##### test
```shell
curl localhost:8080/anna/28
```

