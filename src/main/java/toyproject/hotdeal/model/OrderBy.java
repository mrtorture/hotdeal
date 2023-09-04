package toyproject.hotdeal.model;

public enum OrderBy {
    DATE("DATE"),
    VOTE("VOTE");

    private String description;

    OrderBy(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
