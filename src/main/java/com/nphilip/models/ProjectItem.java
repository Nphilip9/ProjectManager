package com.nphilip.models;

public class ProjectItem {
    private final String title, subtitle, shortDescription, creationDate, deadline;

    public ProjectItem(String title, String subtitle, String shortDescription, String creationDate, String deadline) {
        this.title = title;
        this.subtitle = subtitle;
        this.shortDescription = shortDescription;
        this.creationDate = creationDate;
        this.deadline = deadline;
    }

    public String getTitle() {
        return title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public String getDeadline() {
        return deadline;
    }

    @Override
    public String toString() {
        return title + " " + subtitle + " " + creationDate;
    }
}
