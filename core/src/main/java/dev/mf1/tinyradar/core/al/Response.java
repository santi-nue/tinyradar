package dev.mf1.tinyradar.core.al;

import lombok.Data;

import java.util.List;

@Data
public class Response {

//    @JsonDeserialize(using = AircraftListDeserializer.class)
    private List<Aircraft> ac;
    private String msg;
    private long now;
    private int total;
    private long ctime;
    private long ptime;

}
