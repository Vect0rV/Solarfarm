package learn.solarfarm.data;

import learn.solarfarm.models.MaterialType;
import learn.solarfarm.models.Panel;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Year;

public class PanelMapper implements RowMapper<Panel> {

    @Override
    public Panel mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Panel panel = new Panel();
        panel.setPanelId(resultSet.getInt("panel_id"));
        panel.setSection(resultSet.getString("section"));
        panel.setRow(resultSet.getInt("row"));
        panel.setColumn(resultSet.getInt("column"));
        panel.setMaterialType(MaterialType.valueOf(resultSet.getString("material_type")));
        panel.setInstallationYear(Year.parse(resultSet.getString("installation_year")));
        panel.setIsTracking(resultSet.getBoolean("isTracking"));
        return panel;

    }
}
