package ohgk.genesis.api.enums;

public enum ResponseStatusEnum {
    
    SUCCESS("success"),
    FAILED("failed"),
    ERROR("error");

    private final String value;

    private ResponseStatusEnum(String value){
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
