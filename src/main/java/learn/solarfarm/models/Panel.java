package learn.solarfarm.models;

public class Panel {

    private int panelId;
    private String section;
    private int row;
    private int column;
    private MaterialType materialType;
    private String installationYear;
    private boolean isTracking;

    public Panel(int panelId, String section, int row, int column, MaterialType materialType, String installationYear, boolean isTracking) {
        this.panelId = panelId;
        this.section = section;
        this.row = row;
        this.column = column;
        this.materialType = materialType;
        this.installationYear = installationYear;
        this.isTracking = isTracking;
    }

    public Panel() {};


    public int getPanelId() {
        return panelId;
    }

    public void setPanelId(int panelId) {
        this.panelId = panelId;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public MaterialType getMaterialType() {
        return materialType;
    }

    public void setMaterialType(MaterialType materialType) {
        this.materialType = materialType;
    }

    public String getInstallationYear() {
        return installationYear;
    }

    public void setInstallationYear(String installationYear) {
        this.installationYear = installationYear;
    }

    public boolean isTracking() {
        return isTracking;
    }

    public void setTracking(boolean tracking) {
        isTracking = tracking;
    }
}
