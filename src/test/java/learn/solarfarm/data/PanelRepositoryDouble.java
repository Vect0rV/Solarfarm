package learn.solarfarm.data;

import learn.solarfarm.models.MaterialType;
import learn.solarfarm.models.Panel;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;

public class PanelRepositoryDouble implements PanelRepository{

    private ArrayList<Panel> panels = new ArrayList<>();

    public PanelRepositoryDouble() {
        panels.add(new Panel(1,"Section 1", 1,5, MaterialType.ASI, Year.of(2020), true));
        panels.add(new Panel(2,"Section 2", 4,50, MaterialType.CD_TE,Year.of(2021), true));
        panels.add(new Panel(3,"Section 3", 100,43, MaterialType.MONO_SI,Year.of(2022), true));
    }

    public List<Panel> findAll(){
        return new ArrayList<>(panels);
    }

    public List<Panel> findBySection(String section){
        ArrayList<Panel> results = new ArrayList<>();
        for (Panel p : panels) {
            if(section.equals(p.getSection())) {
                results.add(p);
            }
        }
        return results;
    }

//    @Override
//    public List<Panel> findBySectionRowColumn(String section, int row, int column) throws DataAccessException {
//        return null;
//    }

    public Panel add(Panel panel) throws DataAccessException{
        return null;
    }

    @Override
    public Boolean update(Panel panel) throws DataAccessException {
        return true;
    }

    @Override
    public Boolean delete(int panelId) throws DataAccessException {
        ArrayList<Panel> results = new ArrayList<>();
        for (Panel p : panels) {
            if(panelId == p.getPanelId()) {
                results.remove(p);
                return true;
            }
        }

        return false;
    }
}
