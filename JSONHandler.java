import com.google.gson.*;

import java.io.*;
import java.util.Arrays;
import java.util.List;

public class JSONHandler {
    public static void writeJSON(Object object) throws IOException {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.setPrettyPrinting().create();
        FileWriter writer = new FileWriter("output.json");
        writer.write(gson.toJson(object));
        writer.close();
    }

    static <T> List<T> readJSON(Class<T[]> classe) throws FileNotFoundException {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.setPrettyPrinting().create();
        BufferedReader bufferedReader = new BufferedReader(
                new FileReader("output.json"));

        T[] object = gson.fromJson(bufferedReader, classe);
        return Arrays.asList(object);
    }
}
