package learn.solarfarm.ui;

import learn.solarfarm.domain.PanelResult;
import learn.solarfarm.models.MaterialType;
import learn.solarfarm.models.Panel;

import java.time.Year;
import java.util.List;

public class View {

    private final TextIO io;

    public View(TextIO io){
        this.io = io;
    }
    public MenuOption DisplayMenuAndSelect() {
            MenuOption[] values = MenuOption.values();
            displayHeader("Main Menu");
            for (int i = 0; i < MenuOption.values().length; i++) {
                System.out.printf("%s. %s%n", i, values[i].getTitle());
            }
            int index = io.readInt("Select [0-4]: ", 0, 4);
            return values[index];
    }

    public void displayHeader(String message) {
        int length = message.length();
        io.println("");
        io.println(message);
        io.println("=".repeat(length));
    }


    public String readSection() {
        String section = io.readString("Enter the name of a Section: ");
        return section;
    }

    public void displayMessage(String message) {
        io.println("");
        io.println(message);
    }




    public void displayErrors(List<String> errors) {
        displayHeader("Errors: ");
        for (String err : errors) {
            System.out.println(err);
        }
    }


    public void displayPanels(List<Panel> panels, String section) {
        displayHeader("Panels: ");
        if(panels.size() == 0) {
            io.println("No panels found.");
        } else {
            io.printf("Section name: %s%n", section);
            io.println("");

            io.println("ID Row Col Year Material Tracking");
            for (Panel p : panels) {

                io.printf("%s  %s   %s   %s %s %s %n",
                        p.getPanelId(),
                        p.getRow(),
                        p.getColumn(),
                        p.getInstallationYear(),
                        p.getMaterialType(),
                        p.getIsTracking()
                );
            }
        }
    }

//    public void displayPanels() {
//        displayHeader("All Panels: ");
//        List<panels> panels =
//        if(panels.size() == 0) {
//            io.println("No panels found.");
//        } else {
//
//        }
//    }

    public Panel choosePanel(List<Panel> panels, int panelId){
        Panel result = null;
        if (panels.size() > 0) {

            for(Panel panel : panels) {
                if(panelId == panel.getPanelId()) {
                    result = panel;
                    break;
                }
            }
//            if (result == null){
//                return new Panel(panelId);
//            }
        }

        return result;
    }

    public Panel createPanel() {
        displayHeader(MenuOption.ADD.getTitle());
        Panel panel = new Panel();
        panel.setSection(readSection());
        panel.setRow(io.readInt("Row: ", 0, 250, true));
        panel.setColumn(io.readInt("Column: ", 0, 250, false));
        panel.setInstallationYear(io.readRequiredYear("Instillation Year: "));
        panel.setMaterialType(io.readType());
        panel.setIsTracking(io.readBoolean("Tracked [y/n]: "));
        return panel;
    }

    public void displayResult(PanelResult result, Panel panel, Boolean isUpdated) {
        if (result.isSuccess()) {
            if (result.getPayload() != null) {
                System.out.printf("%n[Success] %nPanel %s-%s-%s %s.%n", panel.getSection(), panel.getRow(), panel.getColumn(), isUpdated ? "updated" : "added");
            }
        } else {
            displayHeader("Errors");
            for (String msg : result.getErrorMessages()) {
                System.out.printf("- %s%n", msg);
            }
        }
    }


    public Panel editPanel(Panel panel) {
        displayHeader("Edit a Panel");
        // Only update if it changed


        MaterialType type = readType();
        if(type.toString().trim().length() > 0) {
            panel.setMaterialType(type);
        }

        Year instillationYear = io.readYear("Instillation Year (" + (panel.getInstallationYear() + "): "));
        if(instillationYear.length() > 0) {
            panel.setInstallationYear(instillationYear);
        }

        Boolean isTracking = io.readBoolean("Tracked (" + (panel.getIsTracking() + "): "));
        if(!isTracking.toString().isBlank()) {
            panel.setIsTracking(isTracking);
        }
        return panel;
    }

    public

    MaterialType readType() {
        int index = 1;
        for (MaterialType type : MaterialType.values()) {
            System.out.printf("%s. %s%n", index++, type);
        }
        index--;
        String msg = String.format("Select Material Type [1-%s]:", index);
        return MaterialType.values()[io.readInt(msg, 1, index) - 1];
    }
}
