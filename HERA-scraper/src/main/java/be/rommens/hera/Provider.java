package be.rommens.hera;

/**
 * User : cederik
 * Date : 31/03/2020
 * Time : 15:42
 */
public enum Provider {

    READCOMICS("readcomics");

    private final String propertyName;

    Provider(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getPropertyName() {
        return propertyName;
    }
}
