package learn.solarfarm.domain;

import learn.solarfarm.data.DataAccessException;
import learn.solarfarm.data.PanelRepository;
import learn.solarfarm.models.Panel;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Year;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class PanelService {

    private final PanelRepository repository;

    public PanelService(PanelRepository repository){
        this.repository = repository;
    }

    public List<Panel> findBySection(String section) throws DataAccessException {
        return repository.findBySection(section);
    }

    public List<Panel> findAll() throws DataAccessException {
        return repository.findAll();
    }


    public PanelResult add(Panel panel) throws DataAccessException {
        PanelResult result = validate(panel);

        if(panel.getPanelId() > 0) {
            result.addErrorMessage("Panel `id` should not be set.");
        }

        if(result.isSuccess()) {
            panel = repository.add(panel);
            result.setPayload(panel);
        }

        return result;
    }

    public PanelResult update(Panel panel) throws DataAccessException {
//        PanelResult result = validate(panel);
        PanelResult result = new PanelResult();

        if(panel.getPanelId() <= 0) {
            result.addErrorMessage("Panel ID is required");
        }

        if(result.isSuccess()) {
            if(repository.update(panel)) {
                result.setPayload(panel);
            } else {
                String message = String.format("Panel ID %s not found", panel.getPanelId());
                result.addErrorMessage(message);

            }
        }

        return result;
    }

    public PanelResult delete(int panelId) throws DataAccessException {
        PanelResult result = new PanelResult();


        if(!repository.delete(panelId)) {
            String message = String.format("Panel ID %s not found", panelId);
            result.addErrorMessage(message);
            }
        return result;
    }

    private PanelResult validate(Panel panel) throws DataAccessException {

        PanelResult result = new PanelResult();
        if (panel == null) {
            result.addErrorMessage("panel cannot be null");
            return result;
        }

        if (panel.getSection() == null || panel.getSection().trim().length() == 0) {
            System.out.println("section is required");
        }

        if (panel.getRow() <= 0 || panel.getRow() > 250) {
            System.out.println("Row must be between 1 and 250.");
            result.addErrorMessage("row is required and cannot be greater than 250");
        }

        if (panel.getColumn() <= 0 || panel.getColumn() > 250) {
            result.addErrorMessage("column is required and cannot be greater than 250");
            return result;
        }

        if (panel.getMaterialType() == null) {
            result.addErrorMessage("material type is required");
        }

        if (panel.getInstallationYear() == null || Year.from(Year.now()).isBefore(panel.getInstallationYear())){
            result.addErrorMessage("Instillation year is required and must be in the past");
        }

        List<Panel> panels = repository.findAll();
        for (Panel p : panels) {
            if (Objects.equals(panel.getSection(), p.getSection())
                    && Objects.equals(panel.getRow(), p.getRow())
                    && Objects.equals(panel.getColumn(), p.getColumn())
                    && Objects.equals(panel.getMaterialType(), p.getMaterialType())
                    && Objects.equals(panel.getInstallationYear(), p.getInstallationYear())
                    && Objects.equals(panel.getIsTracking(), p.getIsTracking())) {
                result.addErrorMessage("duplicate panel is not allowed");
                return result;
            }
        }

        return result;
    }


}
