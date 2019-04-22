package ru.molefed.hw.books;

import java.io.Serializable;

public class CatalogType implements Serializable {
    public static final CatalogType CLOSE = new CatalogType(1L, "Close");
    public static final CatalogType OPEN = new CatalogType(2L, "Open");

    private Long id;
    private String name;

    protected CatalogType() {

    }

    protected CatalogType(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof CatalogType))
            return false;

        CatalogType that = (CatalogType) obj;
        return getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return getId().intValue();
    }

}
