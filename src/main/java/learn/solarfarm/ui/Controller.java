package learn.solarfarm.ui;

import learn.solarfarm.data.DataAccessException;
import learn.solarfarm.domain.PanelResult;
import learn.solarfarm.domain.PanelService;
import learn.solarfarm.models.Panel;

import java.util.List;

public class Controller {

    private final PanelService service;

    private final View view;

    private final TextIO io;

    public Controller(PanelService service, View view, TextIO io) {
        this.service = service;
        this.view = view;
        this.io = io;
    }

    public void run() {
        try {
            runMenu();

        }catch (DataAccessException ex) {

        }
    }

    public void runMenu() throws DataAccessException {
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
                case Delete:
                    Delete();
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
        String section;

            displayPanels();
            int panelId = io.readInt("Choose a panel Id: ");
            Panel panel = view.choosePanel(getPanels(), panelId);



        if(panel == null) {
            view.displayMessage("Panel not found");
            return;
        }
        view.editPanel(panel);

        PanelResult result = service.update(panel);

        if(result.isSuccess()) {
            view.displayResult(result, panel, true);
        } else {
            view.displayErrors(result.getErrorMessages());
        }
    }

    private void Delete() throws DataAccessException {
        view.displayHeader(MenuOption.Delete.getTitle());


        Panel panel = null;


        String section = displayPanels();
        int panelId = io.readInt("Choose a panel ID:");
        if (getPanels(section).size() == 0){
            return;
        }
        panel = view.choosePanel(getPanels(), panelId);


        if(panel != null && service.delete(panel.getPanelId()).isSuccess()) {
            System.out.printf("%nPanel %s %s-%s-%s removed.%n",
                    panel.getPanelId(),
                    panel.getSection(),
                    panel.getRow(),
                    panel.getColumn());
        } else {
            System.out.println("[Err]");
            System.out.printf("There is no panel with Id %s.%n", panelId);

        }

    }

    private List<Panel> getPanels(String section) throws DataAccessException {
        return service.findBySection(section);
    }

    private List<Panel> getPanels() throws DataAccessException {
        return service.findAll();
    }



}
