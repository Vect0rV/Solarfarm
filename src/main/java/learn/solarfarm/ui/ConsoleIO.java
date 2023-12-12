package learn.solarfarm.ui;

import learn.solarfarm.models.MaterialType;

import java.time.Year;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class ConsoleIO implements TextIO {

    private final Scanner console = new Scanner(System.in);

    @Override
    public void println(Object value) {
        System.out.println(value);
    }

    @Override
    public void print(Object value) {
        System.out.print(value);
    }

    @Override
    public void printf(String format, Object... values) {
        System.out.printf(format, values);
    }

    @Override
    public String readString(String prompt) {
        print(prompt);
        return console.nextLine();
    }

    @Override
    public Boolean readBoolean(String prompt) {
        String result = readString(prompt);
        return result.equalsIgnoreCase("y");
    }

    private String readRequiredString(String prompt) {
        String result = null;
        do {
            result = readString(prompt).trim();
            if(result.length() == 0) {
                System.out.println("Value is required.");
            }
        } while (result.length() == 0);
        return result;
    }

//    @Override
//    public int readInt(String prompt) {
//        while(true) {
//            String value = readString(prompt);
//            try {
//                int result = Integer.parseInt(value);
//                return result;
//            } catch (NumberFormatException ex) {
//                printf("`%s` is not a value number.%n", value);
//            }
//        }
//    }

    public int readInt(String prompt) {
        int result = 0;
        boolean isValid = false;
        do{
            String value = readRequiredString(prompt);
            try {
                result = Integer.parseInt(value);
                isValid = true;
            } catch (NumberFormatException ex) {
                System.out.println("Value must be a number.");
            }
        }while(!isValid);

        return result;
    }

    @Override
    public int readInt(String prompt, int min, int max, Boolean isRow) {
        while(true) {
            int value = readInt(prompt);
            if(value >= min && value <= max) {
                return value;
            }
            printf("[Err]%n%s must be between %s and %s.%n", isRow ? "Row" : "Column", min, max);
        }
    }

    public Year readYear(String prompt) {

            String value = null;
            Year year = null;
            boolean isValidYear = false;
            do {
                value = readString(prompt);
                try {
                    if (!value.isBlank() || value.length() > 4) {
                        isValidYear = true;
                    } else {
                        System.out.println("`4 digit value is required`");
                    }
                } catch (DateTimeParseException ex) {
                    printf("The above input caused %s: %n", ex);
                }

            }while (!isValidYear);
            year = Year.parse(value);

            return year;

    }

    public MaterialType readType() {
        int index = 1;
        for (MaterialType type : MaterialType.values()) {
            System.out.printf("%s. %s%n", index++, type);
        }
        index--;
        String msg = String.format("Select Material Type [1-%s]:", index);
        return MaterialType.values()[readInt(msg, 1, index) - 1];
    }

    @Override
    public int readInt(String prompt, int min, int max) {
        while(true) {
            int value = readInt(prompt);
            if(value >= min && value <= max) {
                return value;
            }
            printf("%s must be between %s and %s.%n", value, min, max);
        }
    }
}
