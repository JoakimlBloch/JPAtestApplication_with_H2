package org.example.jpatestapplication.model;

import jakarta.persistence.*;

@Entity
public class Region {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;

    @Column(unique = true)
    private String kode;
    private String navn;
    private String href;

    public Region(String kode, String navn, String href) {
        this.kode = kode;
        this.navn = navn;
        this.href = href;
    }

    public int getID() {
        return ID;
    }

    public String getKode() {
        return kode;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getNavn() {
        return navn;
    }

    public void setNavn(String navn) {
        this.navn = navn;
    }

    @Override
    public String toString() {
        return "Region:" +
                " ID = " + ID +
                ", Kode: '" + kode + '\'' +
                ", Navn: '" + navn + '\'' +
                ", Href: '" + href;
    }

}
