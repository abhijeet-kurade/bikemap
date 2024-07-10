package com.stripe.interview;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import com.jsoniter.JsonIterator;
import com.jsoniter.any.Any;
import com.jsoniter.output.JsonStream;
import com.jsoniter.spi.Config;
import okhttp3.*;

public class App 
{
    public static void main( String[] args ) throws IOException
    {
        task1();
    }

    // See 1-json-primer.md for an explanation
    public static void handle_json()
    {
        String json_text = "{\"foo\": {\"bar\": [{\"paint\": \"red\"}, {\"paint\": \"green\"}, {\"paint\": \"blue\"}]}}";
        Any data = JsonIterator.deserialize(json_text);

        // Use toString(), toDouble(), toInt(), etc with types
        String demoLetter = data.toString("foo", "bar", 1, "paint");
        System.out.printf("toString: %s\n", demoLetter); // "green"

        // Or use get() and Any
        Any demoLetter2 = data.get("foo", "bar", 1, "paint");
        System.out.printf("Any: %s\n", demoLetter2); // "green"

        // Can also use Map.of for homogenous key-value pairs in Java 9+
        Map<String, Object> quux = new HashMap<>();
        quux.put("stuff", "nonsense");
        quux.put("nums", Arrays.asList(2.718, 3.142));

        // data["foo"]["quux"] = {"stuff": "nonsense", "nums": [2.718, 3.142]}
        data.get("foo").asMap().put("quux", Any.wrap(quux));

        // Use Config to get pretty-printed indentation
        Config cfg = new Config.Builder().indentionStep(2).build();

        // serialize object to string as json
        String result = JsonStream.serialize(cfg, data);
        System.out.println(result);
    }

    public static  void task1() throws IOException{
        String json_text = extractJsonData();

        // Converting into json object
        Any data = JsonIterator.deserialize(json_text);

        for(int i=0; i<10; i++){
            String val = data.toString("features", 0, "geometry", "coordinates", i);
            System.out.println(val);
        }

        task2(json_text);

    }

    // This can be moved to Utils

    public static String extractJsonData(){
        // path can be parameterized
        Path filePath = Path.of("C:\\Users\\ashwi\\Downloads\\SpringProj\\bikemap\\java\\ride-simple.json");
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(filePath.toUri()), StandardCharsets.UTF_8)) {

            stream.forEach(s -> contentBuilder.append(s).append("\n"));
        } catch (IOException e) {
            //handle exception
        }

        String fileContent = contentBuilder.toString();

        return fileContent;
    }

    public static String task2(String json)  throws IOException{

        json = "{\n" +
                "  \"center\": {\n" +
                "    \"lat\": 47.579,\n" +
                "    \"lon\": -122.31\n" +
                "  },\n" +
                "  \"width\": 400,\n" +
                "  \"height\": 600,\n" +
                "  \"zoom\": 13\n" +
                "}";
        final MediaType JSON = MediaType.get("application/json");

        String url = "https://stripe-bikemap.appspot.com/map.png";
        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(json, JSON);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            InputStream inputStream = response.body().byteStream();

            File file = new File("C:\\Users\\ashwi\\Downloads\\SpringProj\\bikemap\\java\\img.png");

            copyInputStreamToFile(inputStream, file);

            return response.body().string();
        }

    }

    private static void copyInputStreamToFile(InputStream inputStream, File file)
            throws IOException {

        // append = false
        try (FileOutputStream outputStream = new FileOutputStream(file, false)) {
            int read;
            byte[] bytes = new byte[8192];
            while ((read = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }
        }

    }

}
