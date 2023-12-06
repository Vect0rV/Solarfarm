package learn.solarfarm.ui;

import learn.solarfarm.data.DataAccessException;
import learn.solarfarm.domain.PanelService;
import learn.solarfarm.models.Panel;

import java.util.List;

public class Controller {

    private final PanelService service;

    private final View view;

    public Controller(PanelService service, View view) {
        this.service = service;
        this.view = view;
    }

    public void run() {
        try {
            runMenu();

        }catch (DataAccessException ex) {

        }
    }

    private void runMenu() throws DataAccessException {
        MenuOption option;
        do{
            option = view.DisplayMenuAndSelect();
            switch (option) {
                case EXIT:
                    System.exit(0);
                    break;
                case FIND_PANEL_BY_SECTION:
                    displayPanels();
                    break;
            }
        } while(option != MenuOption.EXIT);
    }

    private void displayPanels() throws DataAccessException {
        view.displayHeader(MenuOption.FIND_PANEL_BY_SECTION.getTitle());
        String section = view.readSection();
        List<Panel> panels = service.findBySection(section);
        view.displayPanels(panels);
    }

}
