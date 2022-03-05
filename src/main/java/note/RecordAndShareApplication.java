package note;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author: lonelyzhou
 * @ProjectName: RecordAndShare
 * @Package: IntelliJ IDEA
 * @Description: main
 * @Date: 2022/1/26 下午 9:45
 **/

@SpringBootApplication
@MapperScan("note.recordAndShare.mapper")
public class RecordAndShareApplication {
    public static void main(String[] args) {
        SpringApplication.run(RecordAndShareApplication.class, args);
    }
}