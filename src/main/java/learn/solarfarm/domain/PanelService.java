package learn.solarfarm.domain;

import learn.solarfarm.data.DataAccessException;
import learn.solarfarm.data.PanelRepository;
import learn.solarfarm.models.Panel;

import java.util.List;

public class PanelService {

    private final PanelRepository repository;

    PanelService(PanelRepository repository){
        this.repository = repository;
    }

    public List<Panel> findBySection(String section) throws DataAccessException {
        return repository.findBySection(section);
    }

}
