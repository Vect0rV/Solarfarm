package learn.solarfarm.ui;

import learn.solarfarm.models.Panel;

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
            int index = io.readInt("Select [0-4]: ", 0, 1);
            return values[index];
    }

    public void displayHeader(String message) {
        int length = message.length();
        io.println("");
        io.println(message);
        io.println("=".repeat(length));
    }


    public String readSection() {
        String section = io.readString("Enter the name of an existing section: ");
        return section;
    }

    public void displayPanels(List<Panel> panels) {
        displayHeader("Panels: ");
        if(panels.size() == 0) {
            io.println("No panels found.");
        } else {
            for (Panel p : panels) {
                io.printf("%s - %s, %s, %s, %s, %s, %s%n",
                        p.getPanelId(),
                        p.getSection(),
                        p.getRow(),
                        p.getColumn(),
                        p.getMaterialType(),
                        p.getInstallationYear(),
                        p.getIsTracking());
            }
        }
    }
}
