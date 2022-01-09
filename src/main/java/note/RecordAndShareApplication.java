package note;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("note.recordAndShare.mapper")
public class RecordAndShareApplication {
    public static void main(String[] args) {
        SpringApplication.run(RecordAndShareApplication.class, args);
    }
}

