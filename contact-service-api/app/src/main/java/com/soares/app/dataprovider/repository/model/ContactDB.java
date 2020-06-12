package com.soares.app.dataprovider.repository.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "contact")
public class ContactDB implements Serializable {
    private static final long serialVersionUID = 1603714798906422731L;
    private String id;
    private TypeContactDB typeContact;
    private String value;
    private String peopleId;
}
