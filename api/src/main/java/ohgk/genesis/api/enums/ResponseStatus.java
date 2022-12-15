package ohgk.genesis.api.enums;

public enum ResponseStatus {
    
    SUCCESS("success"),
    FAILED("failed"),
    ERROR("error");

    private final String value;

    private ResponseStatus(String value){
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
