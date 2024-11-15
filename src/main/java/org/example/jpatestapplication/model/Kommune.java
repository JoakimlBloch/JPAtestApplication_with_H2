package org.example.jpatestapplication.model;

import jakarta.persistence.*;

@Entity
public class Kommune {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 8)
    private String kode;

    private String navn;
    private String href;

    public Kommune(String kode, String navn, String href, Region region) {
        this.kode = kode;
        this.navn = navn;
        this.href = href;
        this.region = region;
    }

    @ManyToOne
    @JoinColumn(name = "region_id", referencedColumnName = "id")
    private Region region;

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

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }
}