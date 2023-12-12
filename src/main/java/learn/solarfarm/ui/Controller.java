package learn.solarfarm.ui;

import learn.solarfarm.data.DataAccessException;
import learn.solarfarm.domain.PanelResult;
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
                case ADD:
                    Add();
                    break;
                case UPDATE:
                    Update();
                    break;
            }
        } while(option != MenuOption.EXIT);
    }

    private String displayPanels() throws DataAccessException {
        view.displayHeader(MenuOption.FIND_PANEL_BY_SECTION.getTitle());
        String section = view.readSection();
        List<Panel> panels = service.findBySection(section);
        view.displayPanels(panels, section);

        return section;
    }
    private void Add() throws DataAccessException {
        Panel panel = view.createPanel();
        PanelResult result = service.add(panel);
        view.displayResult(result, panel, false);
    }

    private void Update() throws DataAccessException {
        String section = null;
        Panel panel = null;
        do {
            section = displayPanels();
            panel = view.choosePanel(getPanels(section));
        }while(getPanels(section).size() == 0);

        view.editPanel(panel);
        if(panel == null) {
            view.displayMessage("Panel not found");
            return;
        }

        PanelResult result = service.update(panel);

        if(result.isSuccess()) {
            view.displayResult(result, panel, true);
        } else {
            view.displayErrors(result.getErrorMessages());
        }
    }

    private List<Panel> getPanels(String section) throws DataAccessException {
        return service.findBySection(section);
    }

}
