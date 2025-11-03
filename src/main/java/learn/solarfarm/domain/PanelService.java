package learn.solarfarm.domain;

import learn.solarfarm.data.DataAccessException;
import learn.solarfarm.data.PanelRepository;
import learn.solarfarm.models.Panel;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Year;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Set;


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

        PanelResult result = new PanelResult();

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set< ConstraintViolation<Panel>> violations = validator.validate(panel);

        if (!violations.isEmpty()) {
            for (ConstraintViolation<Panel> violation : violations) {
                result.addErrorMessage(violation.getMessage());
            }
            return result;
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




}
