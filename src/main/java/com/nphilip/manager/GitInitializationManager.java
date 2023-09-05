package com.nphilip.manager;

import java.io.IOException;

public class GitInitializationManager {
    public void initializeLocalGitRepository(String path) {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("git", "init");
            processBuilder.directory(new java.io.File(path));

            Process process = processBuilder.start();
            int exitCode = process.waitFor();

            if (exitCode == 0) {
                System.out.println("Git repository initialized successfully.");
            } else {
                System.err.println("Git repository initialization failed.");
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
