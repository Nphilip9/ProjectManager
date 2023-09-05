package com.nphilip.utils;

import com.nphilip.models.ProjectItem;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Utils {
    public static String shortenPath(String path, int maxLength) {
        int ellipsisLength = 3;

        if (path.length() <= maxLength) {
            return path;
        } else if (maxLength <= ellipsisLength) {
            return path.substring(path.length() - maxLength);
        } else {
            int maxCharsForFirstPart = (maxLength - ellipsisLength) / 2;
            int maxCharsForLastPart = maxLength - ellipsisLength - maxCharsForFirstPart;

            String firstPart = path.substring(0, maxCharsForFirstPart);
            String lastPart = path.substring(path.length() - maxCharsForLastPart);

            return firstPart + "..." + lastPart;
        }
    }

    public static String getCurrentDate() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        return now.format(formatter);
    }

    public static void deleteItemPath(ProjectItem projectItem) {
        File file = new File(projectItem.getSubtitle());
        file.delete();
    }
}
