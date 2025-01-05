package dev.mf1.tinyradar.core.al;

import org.aeonbits.owner.Config;

@Config.Sources("classpath:al.properties")
public interface AirplanesLiveConfig extends Config {

    @Key("get_hex")
    String getIcaoAddress(String hex);

    @Key("get_callsign")
    String getCallsign(String callsign);

    @Key("get_reg")
    String getReg(String callsign);

    @Key("get_squawk")
    String getSquawk(String squawk);

    @Key("get_mil")
    String getMil();

    @Key("get_point")
    String getPoint(String lat, String lon, String radius);

}
