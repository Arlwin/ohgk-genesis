package ohgk.genesis.api.enums;

public enum ProjectStatusEnum {
    
    TODO("To Do"),
    IN_PROGRESS("In Progress"),
    DONE("Done");

    private final String label;

    private ProjectStatusEnum(String label) {
        this.label = label;
    }

    public String getLabel() {
        return this.label;
    }
}
