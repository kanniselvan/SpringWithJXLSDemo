package com.kanni.SpringWithJXLSDemo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class GlobalInfo {

    @JsonProperty("API")
    private String api;
    @JsonProperty("Description")
    private String description;
    @JsonProperty("Auth")
    private String auth;
    @JsonProperty("HTTPS")
    private boolean https;
    @JsonProperty("Cors")
    private String cors;
    @JsonProperty("Link")
    private String link;
    @JsonProperty("Category")
    private String category;

}
