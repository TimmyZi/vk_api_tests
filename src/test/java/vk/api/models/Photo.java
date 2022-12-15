package vk.api.models;

import lombok.Data;

@Data
public class Photo {
    private final String server;
    private final String photo;
    private final String hash;

    public Photo(String server, String photo, String hash) {
        this.server = server;
        this.photo = photo;
        this.hash = hash;
    }
}