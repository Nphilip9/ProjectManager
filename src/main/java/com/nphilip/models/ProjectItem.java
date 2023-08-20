package com.nphilip.models;

public class ProjectItem {
    private final String title;
    private final String subtitle;

    public ProjectItem(String title, String subtitle) {
        this.title = title;
        this.subtitle = subtitle;
    }

    public String getTitle() {
        return title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    @Override
    public String toString() {
        return title; // Display the title in the JList
    }
}
