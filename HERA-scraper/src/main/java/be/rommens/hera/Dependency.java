package be.rommens.hera;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

/**
 * User : cederik
 * Date : 29/03/2020
 * Time : 13:32
 */
@Service
@EnableConfigurationProperties(Property.class)
public class Dependency {

    private final Property property;
    private String name;

    public Dependency(Property property) {
        this.property = property;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String hello() {
        return "dependency " + getName() + " " + property.getMessage();
    }
}
