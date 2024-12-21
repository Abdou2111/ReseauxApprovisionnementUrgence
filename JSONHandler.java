

import com.google.gson.*;

import java.io.*;
import java.util.Arrays;
import java.util.List;

public class JSONHandler {
    private static void writeJSON(Object object,String filePath) throws IOException {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.setPrettyPrinting().create();
        FileWriter writer = new FileWriter(filePath);
        writer.write(gson.toJson(object));
        writer.close();
    }

    private static <T> List<T> readJSON(String filePath, Class<T[]> classe) throws FileNotFoundException {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.setPrettyPrinting().create();
        BufferedReader bufferedReader = new BufferedReader(
                new FileReader(filePath));

        T[] object = gson.fromJson(bufferedReader, classe);
        return Arrays.asList(object);
    }
}
