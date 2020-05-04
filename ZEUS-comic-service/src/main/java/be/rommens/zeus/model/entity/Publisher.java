package be.rommens.zeus.model.entity;

import lombok.ToString;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * User : cederik
 * Date : 09/04/2020
 * Time : 14:16
 */
@Entity
@Table(name = "PUBLISHER")
@ToString
public class Publisher {

    private Integer publisherId;
    private String name;
    private String key;

    @Id
    @SequenceGenerator(name = "PUBLISHER_ID_GENERATOR", sequenceName = "SEQ_PUBLISHER")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PUBLISHER_ID_GENERATOR")
    @Column(name = "PUBLISHER_ID", unique = true, nullable = false)
    @MapsId(value = "publisherId")
    public Integer getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(Integer publisherId) {
        this.publisherId = publisherId;
    }

    @Basic
    @Column(name = "NAME", nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "KEY", unique = true, nullable = false)
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

}
