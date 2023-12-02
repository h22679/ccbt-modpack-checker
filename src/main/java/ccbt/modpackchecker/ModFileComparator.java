package ccbt.modpackchecker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModFileComparator {

    /**
     * Compares the lists of current mod files and expected mod files, identifying missing and extra files.
     *
     * @param currentMods  The list of current mod files in the mods directory.
     * @param expectedMods The list of expected mod files from the API.
     * @return A map containing lists of missing and extra files.
     */
    public Map<String, List<String>> compareModFiles(List<String> currentMods, List<String> expectedMods) {
        // Create lists to hold missing and extra files
        List<String> missingFiles = new ArrayList<>(expectedMods);
        List<String> extraFiles = new ArrayList<>(currentMods);

        // Remove all common elements from both lists
        missingFiles.removeAll(currentMods);
        extraFiles.removeAll(expectedMods);

        // Create a map to return the results
        Map<String, List<String>> fileDifferences = new HashMap<>();
        fileDifferences.put("missingFiles", missingFiles);
        fileDifferences.put("extraFiles", extraFiles);

        return fileDifferences;
    }
}