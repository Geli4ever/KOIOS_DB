package com.svetlicic.filip;

import com.svetlicic.filip.model.Artikl;
import com.svetlicic.filip.model.Datasource;
import com.svetlicic.filip.model.Prodavaonica;

import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Main {

    public static void main(String[] args) {

        if(!Datasource.getInstance().open()){
            System.out.println("Can't open datasource");
        }

        Scanner scanner = new Scanner(System.in);
        boolean quit = false;

        System.out.print("Upisite naziv prodavaonice: ");
        Prodavaonica prodavaonica = new Prodavaonica(scanner.nextLine());
        System.out.println("Otvorili ste Prodavaonicu " + prodavaonica.getNaziv());

        ispisMenua();
        System.out.println();

        while (!quit){
            System.out.print("Izaberi akciju: ");
            while (!scanner.hasNextInt()) {
                System.out.println("To nije broj!");
                scanner.next();
            }
            int akcija = scanner.nextInt();
            if(akcija < 0 || akcija > 8){
                scanner.nextLine();
                continue;
            }
            switch (akcija){
                case 0:
                    quit = true;
                    break;
                case 1:
                    scanner.nextLine();
                    System.out.print("Upisite naziv prodavaonice: ");
                    prodavaonica = new Prodavaonica(scanner.nextLine());
                    System.out.println("Otvorili ste Prodavaonicu " + prodavaonica.getNaziv());
                    break;
                case 2:
                    scanner.nextLine();
                    System.out.print("Upisite naziv artikla: ");
                    String nazivArtikla = scanner.nextLine();
                    System.out.print("Upisite zalihu: ");
                    int zaliha = scanner.nextInt();
                    prodavaonica.dodajArtikl(new Artikl(nazivArtikla, zaliha));
                    scanner.nextLine();
                    break;
                case 3:
                    scanner.nextLine();
                    System.out.print("Upisi naziv artikla kojeg brisemo: ");
                    String nazivArtiklaZaBrisanje = scanner.nextLine();
                    boolean status = false;
                    for(Artikl artikl : prodavaonica.getArtikli()){
                        if(artikl.getNaziv().equalsIgnoreCase(nazivArtiklaZaBrisanje)){
                            status = true;
                            break;
                        }
                    }
                    if(status){
                        prodavaonica.izbrisiArtikl(nazivArtiklaZaBrisanje);
                    } else {
                        System.out.println("Ne postoji artikl sa tim nazivom!");
                    }
                    break;
                case 4:
                    scanner.nextLine();
                    System.out.print("Upisi datum prodaje(dd-mm-yyyy): ");
                    String datum = scanner.nextLine();
                    System.out.print("Upisi naziv artikla: ");
                    String nazivProdanogArtikla = scanner.nextLine();
                    boolean statusArtikla = false;
                    for(Artikl artikl : prodavaonica.getArtikli()){
                        if(artikl.getNaziv().equalsIgnoreCase(nazivProdanogArtikla)){
                            statusArtikla = true;
                            break;
                        }
                    }
                    if(statusArtikla){
                        System.out.print("Kolicina: ");
                        int kolicina = scanner.nextInt();
                        prodavaonica.prodajArtikl(datum, nazivProdanogArtikla, kolicina);
                        scanner.nextLine();
                        System.out.println();
                    } else {
                        System.out.println("Ne postoji artikl sa tim nazivom!");
                    }
                    break;
                case 5:
                    System.out.println("Ispis svih artikala u " + prodavaonica.getNaziv() + ":\n");
                    ispisArtikala(prodavaonica.getArtikli());
                    System.out.println();
                    break;
                case 6:
                    System.out.println("Ispis svih prodanih artikala u " + prodavaonica.getNaziv() + ":\n");
                    ispisProdanihArtikala(prodavaonica.getArtikli());
                    System.out.println();
                    break;
                case 7:
                    scanner.nextLine();
                    System.out.print("Upisi za koliko dana narucujes robu: ");
                    int brojDana = scanner.nextInt();
                    prodavaonica.makeNarudzba(brojDana);
                    scanner.nextLine();
                    System.out.println();
                    break;
                case 8:
                    ispisMenua();
                    System.out.println();
                    break;
            }
            if(!quit && akcija != 8){
                ispisMenua();
            }
        }

        scanner.close();
        Datasource.getInstance().close();
    }

    public static void ispisMenua(){
        System.out.println("0 - Izgasi program\n" +
                           "1 - Otvori prodavaonicu\n" +
                           "2 - Dodaj artikl\n" +
                           "3 - Izbrisi artikl\n" +
                           "4 - Prodaj artikl\n" +
                           "5 - Ispis svih artikala\n" +
                           "6 - Ispis svih prodanih artikala\n" +
                           "7 - Naruci artikle\n" +
                           "8 - Ispisi menu");
    }

    public static void ispisArtikala(Set<Artikl> artikli){
        for(Artikl artikl : artikli){
            System.out.println("Naziv artikla: " + artikl.getNaziv() + ", Zaliha: " + artikl.getZaliha());
        }
    }

    private static void ispisProdanihArtikala(Set<Artikl> artikli) {
        for(Artikl artikl : artikli){
            Map<String, Integer> listaProdanihArtikala = artikl.getListaProdanihArtikala();
            if(listaProdanihArtikala.isEmpty()){
                System.out.println("Nismo prodali niti jedan artikl " + artikl.getNaziv());
            } else {
                System.out.println(artikl.getNaziv() + ":\n");
                for(String datum : artikl.getListaProdanihArtikala().keySet()){
                    System.out.println(datum + "- prodana kolicina " + listaProdanihArtikala.get(datum));
                }
            }
            System.out.println();
        }
    }
}
