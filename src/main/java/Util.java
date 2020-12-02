import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

class Util {

    private static final String YEAR = "2020";
    private static final String COOKIE_FILE = "cookie.txt";

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

    static void submitPart1(int answer) {
        submitPart1(Integer.toString(answer));
    }

    static void submitPart1(String answer) {
        submit(1, answer);
    }

    static void submitPart2(int answer) {
        submitPart2(Integer.toString(answer));
    }

    static void submitPart2(String answer) {
        submit(2, answer);
    }

    private static void submit(int part, String answer) {
        System.out.printf("Submitting part %d: %s\n", part, answer);
        final String day = getDay();
        final var data = String.format("level=%d&answer=%s", part, URLEncoder.encode(answer, StandardCharsets.UTF_8));
        try {
            final var url = new URL(baseUrl(day) + "answer");
            final var urlConnection = createUrlConnection(url);
            urlConnection.setDoOutput(true);

            final var out = new OutputStreamWriter(urlConnection.getOutputStream());
            out.write(data);
            out.flush();

            final var in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line;
            boolean print = false;
            while ((line = in.readLine()) != null) {
                if (line.equals("</main>")) {
                    print = false;
                }
                if (print) {
                    System.out.println(line);
                }
                if (line.equals("<main>")) {
                    print = true;
                }
            }
            out.close();
            in.close();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static <T> List<T> readFile(Function<String, T> converter) {
        final String day = getDay();
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

    private static String getDay() {
        final var className = getCallerClassName();
        return className.substring(3);
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
            var url = new URL(baseUrl(day) + "input");
            URLConnection urlConnection = createUrlConnection(url);
            return Util.readData(urlConnection.getInputStream()).stream()
                    .reduce(null, (a, b) -> a == null ? b : a + "\n" + b);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String baseUrl(String day) {
        return "https://adventofcode.com/" + YEAR + "/day/" + day + "/";
    }

    private static URLConnection createUrlConnection(URL url) throws IOException {
        var urlConnection = url.openConnection();
        urlConnection.setRequestProperty("Cookie", "session=" + Files.readString(Path.of(COOKIE_FILE)));
        return urlConnection;
    }
}
