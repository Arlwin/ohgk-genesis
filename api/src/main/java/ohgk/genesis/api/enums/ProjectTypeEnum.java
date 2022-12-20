package ohgk.genesis.api.enums;

public enum ProjectTypeEnum {
    
    WEB_APP("Web Application"),
    MOBILE_IOS("Mobile Application (IOS)"),
    MOBILE_ANDROID("Mobile Application (Android)"),
    GAME_DEV("Game Development"),
    DESKTOP_APP("Desktop Application");

    private final String label;

    private ProjectTypeEnum(String label) {
        this.label = label;
    }

    public String getLabel() {
        return this.label;
    }
}
