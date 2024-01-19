package learn.solarfarm.ui;

public enum MenuOption {

    EXIT("Exit"),
    FIND_PANEL_BY_SECTION("Find Panel by Section"),
    ADD("Add a Panel"),
    UPDATE("Update a Panel"),
    Delete("Remove a Panel");

    private final String title;

    MenuOption(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
