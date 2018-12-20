import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author 李品先
 * @description:
 * @Date 2018-12-20 18:59
 */
@SpringBootApplication
@ComponentScan({"io.renren.*","com.andrService"})
@MapperScan(basePackages = {"com.andrService.*.dao"})
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
