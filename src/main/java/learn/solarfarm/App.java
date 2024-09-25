package learn.solarfarm;

import learn.solarfarm.data.PanelFileRepository;
import learn.solarfarm.domain.PanelService;
import learn.solarfarm.ui.ConsoleIO;
import learn.solarfarm.ui.Controller;
import learn.solarfarm.ui.TextIO;
import learn.solarfarm.ui.View;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@ComponentScan
//@PropertySource("classpath:data.properties")
public class App {

    public static void main(String[] args) {

//        PanelFileRepository repository = new PanelFileRepository("./data/panels.txt");

        ApplicationContext context = new AnnotationConfigApplicationContext(App.class);

        Controller controller = context.getBean(Controller.class);
        controller.run();
    }
}
