package org.example.jpatestapplication.model;

/*
{
  "ændret": "2024-10-04T21:02:54.978Z",
  "geo_version": 21,
  "geo_ændret": "2024-10-04T21:02:54.978Z",
  "bbox": [
    12.44441133,
    55.60647026,
    12.73658638,
    55.73587822
  ],
  "visueltcenter": [
    12.49390862,
    55.7040906
  ],
  "href": "https://api.dataforsyningen.dk/kommuner/0101",
  "dagi_id": "389103",
  "kode": "0101",
  "navn": "København",
  "udenforkommuneinddeling": false,
  "regionskode": "1084",
  "region": {
    "href": "https://api.dataforsyningen.dk/regioner/1084",
    "kode": "1084",
    "navn": "Region Hovedstaden"
  }
}
 */

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Region {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String kode;
    private String navn;
    private String href;

    @OneToMany(mappedBy = "region")
    @JsonBackReference
    private Set<Kommune> kommuner = new HashSet<>();

    public Region(String kode, String navn, String href) {
        if (kode == null) {
            throw new IllegalArgumentException("wat!");
        }
        assert kode != null;
        assert navn != null;
        assert href != null;

        this.kode = kode;
        this.navn = navn;
        this.href = href;
    }

    public int getId() {
        return id;
    }

    public String getKode() {
        return kode;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    public String getNavn() {
        return navn;
    }

    public void setNavn(String navn) {
        this.navn = navn;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    @Override
    public String toString() {
        return "Region: " +
                "ID = " + id +
                ", Kode = '" + kode + '\'' +
                ", Navn = '" + navn + '\'' +
                ", Href = '" + href;
    }

}
