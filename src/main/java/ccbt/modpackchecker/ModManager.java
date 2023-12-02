package ccbt.modpackchecker;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;
import java.net.http.*;
import java.net.URI;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

public class ModManager {

    private final Path modsDirectory;
    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final Gson gson = new Gson();

    public ModManager(String modsFolderPath) {
        this.modsDirectory = Paths.get(modsFolderPath);
    }

    /**
     * Generates a list of all mod files in the 'mods' folder.
     *
     * @return A list of file names.
     * @throws IOException If there's an error accessing the file system.
     */
    public List<String> listModFiles() throws IOException {
        if (!Files.isDirectory(modsDirectory)) {
            throw new IOException("Mods directory path is not a directory.");
        }

        List<String> modFiles = new ArrayList<>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(modsDirectory, "*.jar")) {
            for (Path entry : stream) {
                modFiles.add(entry.getFileName().toString());
            }
        }
        return modFiles;
    }

    /**
     * Sends a GET request to an API to fetch the expected mod files list.
     *
     * @param apiUrl The URL of the API to fetch the mod files list.
     * @return A list of expected mod file names.
     * @throws IOException          If there's an error with the network IO.
     * @throws InterruptedException If the request is interrupted.
     */
    public List<String> fetchExpectedModFiles(String apiUrl) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiUrl))
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        return gson.fromJson(response.body(), new TypeToken<List<String>>(){}.getType());
    }

    public String fetchNews(String apiUrl) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiUrl))
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        // Parse the JSON response as a single string
        JsonObject jsonObject = gson.fromJson(response.body(), JsonObject.class);
        String newsText = jsonObject.get("news").getAsString();

        return newsText;
    }

    public String fetchUpdate(String apiUrl) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiUrl))
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        Map<String, String> resultMap = gson.fromJson(response.body(), new TypeToken<Map<String, String>>(){}.getType());

        if (resultMap != null && resultMap.containsKey("message")) {
            return resultMap.get("message");
        } else {
            return "No message found in the response.";
        }
    }

    /**
     * Downloads missing mod files to a specified directory.
     *
     * @param missingFiles List of missing mod file names.
     * @param downloadBaseUrl Base URL for downloading the mods.
     * @param modsFolderPath Path to the mods directory where files should be downloaded.
     */
    public void downloadMissingMods(List<String> missingFiles, String downloadBaseUrl, String modsFolderPath) {
        Path modsDirectory = Paths.get(modsFolderPath);
        missingFiles.forEach(fileName -> {
            // Skip downloading if the file is a zip file
            if (fileName.endsWith(".zip")) {
                System.out.println("Skipping download of zip file: " + fileName);
                return;
            }
            try {
                // Encode the filename part of the URL
                String encodedFileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8.toString()).replaceAll("\\+", "%20");
                String fullUrl = downloadBaseUrl + encodedFileName;

                downloadMod(fullUrl, modsDirectory.resolve(fileName));
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Helper method to download a file from a URL to a specified path.
     *
     * @param fileUrl URL of the file to download.
     * @param outputPath Path to save the downloaded file.
     * @throws IOException          If there's an error with file IO.
     * @throws InterruptedException If the request is interrupted.
     */
    private void downloadMod(String fileUrl, Path outputPath) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(fileUrl))
                .GET()
                .build();

        HttpResponse<InputStream> response = httpClient.send(request, HttpResponse.BodyHandlers.ofInputStream());

        // Check response status code
        if (response.statusCode() == 200) {
            // Write response InputStream to file
            Files.copy(response.body(), outputPath, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Downloaded: " + outputPath.getFileName());
        } else {
            System.out.println("Failed to download: " + outputPath.getFileName());
        }
    }
}
