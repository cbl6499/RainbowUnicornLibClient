package at.fhv.client.domain;

import java.util.HashMap;

/**
 * Created by ClemensB on 06.11.17.
 */
public abstract class DTO {
    protected int _id;

    public abstract void setId(int id);

    public abstract int getId();

    public abstract HashMap<String, String> getAllData();

    public abstract boolean equals(DTO dto);
}
