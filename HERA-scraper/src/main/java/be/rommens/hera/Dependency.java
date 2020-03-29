package be.rommens.hera;

/**
 * User : cederik
 * Date : 29/03/2020
 * Time : 13:32
 */
public class Dependency {
    private String name;

    public Dependency(String name) {
        this.name = name;
    }

    public String hello() {
        return "dependency " + name;
    }
}
