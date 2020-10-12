package com.svetlicic.filip.model;

import java.util.*;

public class Artikl {

    private final String naziv;
    private int zaliha;
    private Map<String, Integer> listaProdanihArtikala;

    public Artikl(String naziv) {
        this.naziv = naziv;
        this.zaliha = 0;
        this.listaProdanihArtikala = new HashMap<>();
    }

    public Artikl(String naziv, int zaliha) {
        this.naziv = naziv;
        this.zaliha = zaliha;
        this.listaProdanihArtikala = new HashMap<>();
    }

    public String getNaziv() {
        return naziv;
    }

    public int getZaliha() {
        return zaliha;
    }

    public Map<String, Integer> getListaProdanihArtikala() {
        return listaProdanihArtikala;
    }

    public void setZaliha(int zaliha) {
        this.zaliha += zaliha;
    }

    public void setListaProdanihArtikala(Map<String, Integer> listaProdanihArtikala) {
        this.listaProdanihArtikala = listaProdanihArtikala;
    }

    public void dodajUListuProdanihArtikala(String datum, int kolicina, String nazivProdavaonice){
        Datasource.getInstance().insertProdaniArtikl(datum, kolicina, this.naziv, nazivProdavaonice);
        int kolicinaPrijeProdaje = this.listaProdanihArtikala.getOrDefault(datum, 0);
        int novaKolicina = kolicinaPrijeProdaje + kolicina;

        this.listaProdanihArtikala.put(datum, novaKolicina);
    }

    public double prosjekProdaje(){

        double suma = 0.0;
        for(String imeArtikla : this.listaProdanihArtikala.keySet()){
            double kolicina = (double)this.listaProdanihArtikala.get(imeArtikla);
            suma += kolicina;
        }

        return suma/listaProdanihArtikala.size();
    }

    @Override
    public String toString() {
        return "Artikl{" +
                "ime='" + naziv + '\'' +
                ", zaliha=" + zaliha +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Artikl artikl = (Artikl) o;
        return zaliha == artikl.zaliha &&
                naziv.equals(artikl.naziv);
    }

    @Override
    public int hashCode() {
        return Objects.hash(naziv, zaliha);
    }
}
