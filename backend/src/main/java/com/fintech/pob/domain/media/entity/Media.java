package com.fintech.pob.domain.media.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter

@NoArgsConstructor
@AllArgsConstructor
public class Media {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "media_id")
    private Long mediaId;


    private Long transactionUniqueNo;
    private Enum type;
    private String url;

}
