package com.curves.franchise.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class Club implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private int id;
    private String name;
    private String nameEn;
    private String owner;
    private String ownerEn;
    private String manager;
}
