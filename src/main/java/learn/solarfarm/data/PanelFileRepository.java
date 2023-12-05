package learn.solarfarm.data;

import learn.solarfarm.models.MaterialType;
import learn.solarfarm.models.Panel;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PanelFileRepository implements PanelRepository {

    private final String filePath;

    private final String delimiter = ",";

    public PanelFileRepository(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public List<Panel> findAll() throws DataAccessException {
        ArrayList<Panel> result = new ArrayList<>();
        try(BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            reader.readLine();
           for (String line = reader.readLine(); line != null; line = reader.readLine()) {
               Panel p = lineToPanel(line);
                result.add(p);
           }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new DataAccessException("could not open the file path: " + filePath, e);
        }
        return result;
    }

    @Override
    public List<Panel> findBySection(String section) throws DataAccessException {
        ArrayList<Panel> panels = new ArrayList<>();
        for(Panel p : findAll()) {
            if(p.getSection().equals(section)) {
                 panels.add(p);
            }
        }

        return panels;
    }

    private Panel lineToPanel(String line) {
        String[] fields = line.split(",");

        if(fields.length != 7){
            return null;
        }

        return new Panel(
                Integer.parseInt(fields[0]),
                fields[1],
                Integer.parseInt(fields[2]),
                Integer.parseInt(fields[3]),
                MaterialType.valueOf(fields[4]),
                fields[5],
                "true".equals(fields[6]));

    }
}
