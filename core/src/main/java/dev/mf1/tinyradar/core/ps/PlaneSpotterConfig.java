package dev.mf1.tinyradar.core.ps;

import org.aeonbits.owner.Config;

@Config.Sources("classpath:ps.properties")
public interface PlaneSpotterConfig extends Config {

    @Key("latest_photo_by_reg")
    String latestPhotoByReg(String reg);

}
