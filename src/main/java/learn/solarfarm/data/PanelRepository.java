package learn.solarfarm.data;

import learn.solarfarm.models.Panel;

import java.util.List;

public interface PanelRepository {
    List<Panel> findAll() throws DataAccessException;

    Panel findById(int id) throws DataAccessException;

    List<Panel> findBySection(String section) throws DataAccessException;

    Panel add(Panel panel) throws DataAccessException;

    Boolean update(Panel panel) throws DataAccessException;

    Boolean delete(int panelId) throws DataAccessException;


}
