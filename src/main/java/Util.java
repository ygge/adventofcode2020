import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

class Util {

    private static final String YEAR = "2020";

    private Util() {}

    static String readString() {
        final List<String> strings = readStrings();
        if (strings.size() != 1) {
            throw new IllegalStateException(String.format("File does not contain exactly one row, got %d", strings.size()));
        }
        return strings.get(0);
    }

    static List<String> readStrings() {
        return readFile(Function.identity());
    }

    static List<Integer> readInts() {
        return readFile(Integer::parseInt);
    }

    private static <T> List<T> readFile(Function<String, T> converter) {
        final var className = getCallerClassName();
        final var day = className.substring(3);
        final var fileName = String.format("/%s.in", day);
        final var filePath = filePath(fileName);
        if (!Files.exists(Path.of(filePath))) {
            var data = fetchData(day);
            writeData(data, fileName);
        }
        try {
            return readData(Files.newInputStream(Path.of(filePath))).stream()
                    .map(converter)
                    .collect(Collectors.toList());
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static List<String> readData(InputStream ins) {
        final var in = new BufferedReader(new InputStreamReader(ins));
        final var input = new ArrayList<String>();
        String row;
        try {
            while ((row = in.readLine()) != null) {
                input.add(row);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return input;
    }

    private static void writeData(String data, String fileName) {
        String path = filePath(fileName);
        try {
            var out = new BufferedWriter(new FileWriter(path));
            out.write(data);
            out.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String filePath(String fileName) {
        return System.getProperty("user.dir") + File.separator
                + "src" + File.separator
                + "main" + File.separator
                + "resources" + File.separator
                + fileName;
    }

    private static String getCallerClassName() {
        for (StackTraceElement stackTraceElement : new RuntimeException().getStackTrace()) {
            if (!stackTraceElement.getClassName().equals("Util")) {
                return stackTraceElement.getClassName();
            }
        }
        return "";
    }

    private static String fetchData(String day) {
        try {
            var url = new URL("https://adventofcode.com/" + YEAR + "/day/" + day + "/input");
            var urlConnection = url.openConnection();
            urlConnection.setRequestProperty("Cookie", "session=" + Files.readString(Path.of("cookie.txt")));
            return Util.readData(urlConnection.getInputStream()).stream()
                    .reduce(null, (a, b) -> a == null ? b : a + "\n" + b);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
