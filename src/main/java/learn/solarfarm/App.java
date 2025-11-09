package learn.solarfarm;

import learn.solarfarm.data.PanelFileRepository;
import learn.solarfarm.domain.PanelService;
import learn.solarfarm.ui.ConsoleIO;
import learn.solarfarm.ui.TextIO;
import learn.solarfarm.ui.View;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
//@PropertySource("classpath:data.properties")
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
