package learn.solarfarm.ui;

import learn.solarfarm.models.MaterialType;

import java.time.Year;

public interface TextIO {
    void println(Object value);

    void print(Object value);

    void printf(String format, Object... values);

    String readString(String prompt);

    Boolean readBoolean(String prompt);

    int readInt(String prompt);

    int readInt(String prompt, int min, int max);

    int readInt(String prompt, int min, int max, Boolean isRow);

    public Year readYear(String prompt);

    public MaterialType readType();
}
