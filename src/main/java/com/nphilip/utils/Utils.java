package com.nphilip.utils;

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
}
