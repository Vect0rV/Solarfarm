package learn.solarfarm.data;

import learn.solarfarm.models.MaterialType;
import learn.solarfarm.models.Panel;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Types;
import java.time.Year;
import java.util.List;

@Repository
public class SolarFarmJdbcTemplateRepository implements PanelRepository {

    private final JdbcTemplate jdbcTemplate;

    public SolarFarmJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    private final RowMapper<Panel> mapper = (resultSet, rowNum) -> {
        Panel panel = new Panel();
        panel.setPanelId(resultSet.getInt("panel_id"));
        panel.setSection(resultSet.getString("section"));
        panel.setRow(resultSet.getInt("row"));
        panel.setColumn(resultSet.getInt("column"));

        String materialTypeStr = resultSet.getString("material_type");

        try {
            MaterialType materialType = MaterialType.valueOf(materialTypeStr.toUpperCase());
            panel.setMaterialType(materialType);
        } catch (IllegalArgumentException e) {
            panel.setMaterialType(null);
        }

        panel.setMaterialType(MaterialType.valueOf(resultSet.getString("material_type")));
        panel.setInstallationYear(Year.of(resultSet.getInt("installation_year")));
        panel.setIsTracking(resultSet.getBoolean("isTracking"));
        return panel;
    };

    @Override
    public List<Panel> findAll() {
        final String sql = "select panel_id, section, `row`, `column`, material_type, installation_year, isTracking from panel;";
        return jdbcTemplate.query(sql, new PanelMapper());
    }

    @Override
    public List<Panel> findBySection(String section) {
        final String sql = "select panel_id, section, `row`, `column`, material_type, installation_year, isTracking from panel where section = ?;";
        try {
            return jdbcTemplate.query(sql, mapper, section);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public Panel add(Panel panel) {
        final String sql = "insert into panel (panel_id, section, `row`, `column`, material_type, installation_year, isTracking) values (?,?,?,?,?,?,?)";

        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql);
                    ps.setInt(1, panel.getPanelId());
                    ps.setString(2, panel.getSection());
                    ps.setInt(3, panel.getRow());
                    ps.setInt(4, panel.getColumn());

                    MaterialType materialType = panel.getMaterialType();
                    if(materialType != null) {
                        ps.setString(5, materialType.name());
                    } else {
                        ps.setNull(5, Types.VARCHAR);
                    }
                    ps.setString(6, panel.getInstallationYear().toString());
                    ps.setBoolean(7, panel.getIsTracking());
                    return ps;
        });

        if (rowsAffected <= 0) {
            return null;
        }

        return panel;
    }

    @Override
    public Boolean update(Panel panel) {
        final String sql = "update panel set "
                + "section = ?, "
                + "`row` = ?, "
                + "`column` = ?, "
                + "material_type = ?, "
                + "installation_year = ?, "
                + "isTracking = ? "
                + "where panel_id = ?;";

        int rowsUpdated = jdbcTemplate.update(sql,
                panel.getSection(),
                panel.getRow(),
                panel.getColumn(),
                panel.getMaterialType().name(),
                panel.getInstallationYear(),
                panel.getIsTracking(),
                panel.getPanelId()
        );

        return rowsUpdated > 0;
    }

    @Override
    public Boolean delete(int panelId) {
        final String sql = "delete from panel where panel_id = ?;";
        return jdbcTemplate.update(sql, panelId) > 0;
    }
}
