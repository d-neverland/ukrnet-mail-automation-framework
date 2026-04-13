package driver;

public enum Browser {

    CHROME,
    FIREFOX;

    public static Browser getByNameIgnoreCase(String name) {
        for (Browser browser : values()) {
            if (browser.name().equalsIgnoreCase(name)) {
                return browser;
            }
        }
        throw new IllegalArgumentException("No enum constant for browser name: " + name);
    }
}
