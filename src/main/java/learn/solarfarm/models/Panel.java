package learn.solarfarm.models;

import java.time.Year;
import java.util.Objects;

public class Panel {

    private int panelId;
    private String section;
    private int row;
    private int column;
    private MaterialType materialType;
    private Year installationYear;
    private boolean isTracking;

    public Panel(int panelId, String section, int row, int column, MaterialType materialType, Year installationYear, boolean isTracking) {
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

    public Year getInstallationYear() {
        return installationYear;
    }

    public void setInstallationYear(Year installationYear) {
        this.installationYear = installationYear;
    }

    public boolean getIsTracking() {
        return isTracking;
    }

    public void setIsTracking(boolean tracking) {
        isTracking = tracking;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Panel panel = (Panel) o;
        return panelId == panel.panelId
                && row == panel.row
                && column == panel.column &&
                isTracking == panel.isTracking
                && Objects.equals(section, panel.section)
                && materialType == panel.materialType
                && Objects.equals(installationYear, panel.installationYear);
    }

    @Override
    public int hashCode() {
        return Objects.hash(panelId, section, row, column, materialType, installationYear, isTracking);
    }
}
