package learn.solarfarm.ui;

public interface TextIO {
    void println(Object value);

    void print(Object value);

    void printf(String format, Object... values);

    String readString(String prompt);

    Boolean readBoolean(String prompt);

    int readInt(String prompt);

    int readInt(String prompt, int min, int max);
}
