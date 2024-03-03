package dev.archimedes.image.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "file_name")
    private String fileName;
    private String extension;
    private long size;
    @Lob
    @Column(name = "file_data", length = 200000)
    private byte[] fileData;
}
