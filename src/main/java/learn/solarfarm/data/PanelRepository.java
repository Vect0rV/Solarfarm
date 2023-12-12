package learn.solarfarm.data;

import learn.solarfarm.models.Panel;

import java.util.List;

public interface PanelRepository {
    List<Panel> findAll() throws DataAccessException;

    List<Panel> findBySection(String section) throws DataAccessException;

    public Panel add(Panel panel) throws DataAccessException;

    public Boolean update(Panel panel) throws DataAccessException;

}
