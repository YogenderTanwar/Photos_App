package com.example.photos.service.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Image {

    @SerializedName("message")
    private List<String> message;

    @SerializedName("status")
    private String status;

    public Image(List<String> message, String status) {
        this.message = message;
        this.status = status;
    }

    public List<String> getMessage() {
        return message;
    }

    public void setMessage(List<String> message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Image{" +
                "message=" + message +
                ", status='" + status + '\'' +
                '}';
    }
}
