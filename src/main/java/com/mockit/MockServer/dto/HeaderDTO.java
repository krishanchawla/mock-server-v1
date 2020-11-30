package com.mockit.mockserver.dto;

import javax.persistence.*;

@Entity
@Table(name = "MOCK_SERVICE_HEADERS")
public class HeaderDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;
    private String value;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "HeaderDTO [name=" + name + ", value=" + value + "]";
    }
}
