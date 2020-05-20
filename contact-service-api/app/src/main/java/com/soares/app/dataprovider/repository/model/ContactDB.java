package com.soares.app.dataprovider.repository.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "contact")
public class ContactDB {
    private String id;
    private TypeContactDB typeContact;
    private String value;
    private String peopleId;
}
