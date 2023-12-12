package learn.solarfarm.data;

import learn.solarfarm.models.MaterialType;
import learn.solarfarm.models.Panel;

import java.io.*;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

public class PanelFileRepository implements PanelRepository {

    private final String filePath;

    private final String delimiter = ",";

    private static final String HEADER = "panelId,section,row,column,materialType,instillationYear,isTracking";

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

    public Panel add(Panel panel) throws DataAccessException {
        List<Panel> allPanels = findAll();
        int nextId = getNextId(allPanels);
        panel.setPanelId(nextId);
        allPanels.add(panel);
        writeToFile(allPanels);

        return panel;
    }

    public Boolean update(Panel panel) throws DataAccessException {
        List<Panel> allPanels = findAll();
        for(int i = 0; i < allPanels.size(); i++) {
            if(panel.getPanelId() == allPanels.get(i).getPanelId()) {
                allPanels.set(i, panel);
                writeToFile(allPanels);
                return true;
            }
        }
        return false;
    }

    private void writeToFile(List<Panel> allPanels) {
        try(PrintWriter writer = new PrintWriter(filePath)) {
            writer.println("panelId,section,row,column,materialType,instillationYear,isTracking");
            for(Panel panel : allPanels) {
                writer.println(panelToLine(panel));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String panelToLine(Panel panel) {
        StringBuilder buffer = new StringBuilder(100);
        buffer.append(panel.getPanelId()).append(delimiter);
        buffer.append(cleanField(panel.getSection())).append(delimiter);
        buffer.append(panel.getRow()).append(delimiter);
        buffer.append(panel.getColumn()).append(delimiter);
        buffer.append(cleanField(panel.getMaterialType().toString())).append(delimiter);
        buffer.append(panel.getInstallationYear()).append(delimiter);
        buffer.append(panel.getIsTracking());

        return buffer.toString();

    }

    private String cleanField(String field) {
        return field.replace(delimiter, "")
                .replace("/n", "")
                .replace("/r", "");
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
                Year.parse(fields[5]),
                "true".equals(fields[6]));
    }

    private int getNextId(List<Panel> allPanels) {
        int maxId = 0;
        for (Panel p : allPanels) {
            if(maxId < p.getPanelId()) {
                maxId = p.getPanelId();
            }
        }
        return maxId +1;
    }
}
