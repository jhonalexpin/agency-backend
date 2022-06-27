package com.flixbus.agency.model;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder(toBuilder = true)
@Setter
@Getter
@Document(collection = "agency")
public class Agency {

    @Id
    private String id;
    private String name;
    private String country;
    private String countryCode;
    private String city;
    private String street;
    private String currency;
    private String contactPerson;

}
