package dev.mf1.tinyradar.core.ps;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class PlaneSpottersResponse {

    private List<Photo> photos;

    @Data
    public static class Photo {

        private String id;
        private Thumbnail thumbnail;

        @JsonProperty(value = "thumbnail_large")
        private Thumbnail thumbnailLarge;
        private String link;
        private String photographer;

        @Data
        public static class Thumbnail {

            private String src;
            private Size size;

            @Data
            public static class Size {
                private String width;
                private String height;
            }

        }

    }

}
