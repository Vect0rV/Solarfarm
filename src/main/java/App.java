import learn.solarfarm.data.PanelFileRepository;
import learn.solarfarm.domain.PanelService;
import learn.solarfarm.ui.ConsoleIO;
import learn.solarfarm.ui.Controller;
import learn.solarfarm.ui.TextIO;
import learn.solarfarm.ui.View;

public class App {

    public static void main(String[] args) {

        PanelFileRepository repository = new PanelFileRepository("./data/panels.txt");

        PanelService service = new PanelService(repository);

        TextIO io = new ConsoleIO();

        View view = new View(io);

        Controller controller = new Controller(service, view);
        controller.run();
    }
}
