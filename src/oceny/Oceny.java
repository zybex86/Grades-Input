package oceny;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;

/**
 *
 * @author Dominik Blek
 * @version 0.1
 */
public class Oceny extends JFrame
{
     private DefaultListModel modelListy = new DefaultListModel();
    
    private JButton bDodaj;
    private JButton bEdit;
    private JButton bUsun;
    private boolean isEdited = false;
    private JMenuBar mainMenuBar = new JMenuBar();
    private boolean flagaMenuDodajUcznia = false;
    private boolean flagaMenuDodajPrzedmiot = false;
    
    private JLabel pupilListLabel = new JLabel("Lista Uczniów");
    private JLabel subjectListLabel = new JLabel("Lista Przedmiotów");
    private JLabel gradesListLabel = new JLabel("Lista Ocen");
    private JFileChooser wybieracz = new JFileChooser();
    private JList listaUczniow = new JList(modelListy);
    private JList listaPrzedmiotow = new JList(modelListy);
    private JList listaOcen = new JList(modelListy);
    
    public Oceny()
    {
        initComponents();
    }
    public void initComponents()
    {
        this.setTitle("Oceny uczniów");
        this.setBounds(300, 300, 1000, 500);
        File plik = new File("Nowa klasa.txt");
        // Tworzymy Menu Główne
        this.setJMenuBar(mainMenuBar);
        
        
        
        // Tworzę akcje wszystkich elementów menu
        Action akcjaNowejKlasy = new Akcja("Nowa Klasa", "Stwórz nową klasę", "ctrl N");
        Action akcjaWczytajKlase = new Akcja("Wczytaj Klasę", "Wczytaj zapisaną klasę", "ctrl W");
        Action akcjaZamykania = new Akcja("Zamknij", "Zapisz obecną klasę i zamknij program", "ctrl K");
        Action akcjaDrukuj = new Akcja("Drukuj", "Drukuj zapisaną klasę", "ctrl E");
        Action akcjaNowyUczen = new Akcja("Ucznia", "Dodaj nowego ucznia do obecnej klasy", "ctrl D");
        Action akcjaNowyPrzedmiot = new Akcja("Przedmiot", "Dodaj nowy przedmiot do obecnej klasy", "ctrl P");
        Action akcjaFAQ = new Akcja("FAQ", "Pokaż najczęściej zadawane pytania", "ctrl F");
        Action akcjaInfo = new Akcja("Info", "Pokaż informacje o programie", "ctrl I");
        Action akcjaDodajOcene = new Akcja("Dodaj Ocenę", "Dodaj nową ocenę", "ctrl +");
        Action akcjaEditOcene = new Akcja("Edytuj Ocenę", "Edytuj wybraną ocenę", "ctrl =");
        Action akcjaUsunOcene = new Akcja("Usuń Ocenę", "Usuń wybraną ocenę", "ctrl -");
        
        Action akcjaZapiszKlase = new Akcja("Zapisz Klasę", "Zapisz aktualną klasę", "ctrl S");
        akcjaZapiszKlase.setEnabled(isEdited);
        akcjaNowyUczen.setEnabled(flagaMenuDodajUcznia);
        akcjaNowyPrzedmiot.setEnabled(flagaMenuDodajPrzedmiot);
        
        //wszystkie elementy menu
        
        JMenu menuPlik = mainMenuBar.add(new JMenu("Plik"));
        JMenu menuDodaj = mainMenuBar.add(new JMenu("Dodaj"));
        JMenu menuPomoc = mainMenuBar.add(new JMenu("Pomoc"));
        
        
            JMenuItem menuNowa = menuPlik.add(akcjaNowejKlasy);
            menuPlik.addSeparator();
            JMenuItem menuWczytaj = menuPlik.add(akcjaWczytajKlase);
            JMenuItem menuZapisz = menuPlik.add(akcjaZapiszKlase);
            menuPlik.addSeparator();
            JMenuItem menuDrukuj = menuPlik.add(akcjaDrukuj);
            menuPlik.addSeparator();    
            JMenuItem menuZamknij = menuPlik.add(akcjaZamykania);
        
            JMenuItem menuUczen = menuDodaj.add(akcjaNowyUczen);
            JMenuItem menuPrzedmiot = menuDodaj.add(akcjaNowyPrzedmiot);

            JMenuItem menuFAQ = menuPomoc.add(akcjaFAQ);
            JMenuItem menuInfo = menuPomoc.add(akcjaInfo);
        
            bDodaj = new JButton(akcjaDodajOcene);
            bEdit = new JButton(akcjaEditOcene);
            bUsun = new JButton(akcjaUsunOcene);
                        
            JScrollPane skrolekU = new JScrollPane(listaUczniow);
            JScrollPane skrolekP = new JScrollPane(listaPrzedmiotow);
            JScrollPane skrolekO = new JScrollPane(listaOcen);
            listaUczniow.setBorder(BorderFactory.createEtchedBorder());
            listaPrzedmiotow.setBorder(BorderFactory.createEtchedBorder());
            listaOcen.setBorder(BorderFactory.createEtchedBorder());
            GroupLayout layout = new GroupLayout(this.getContentPane());
            layout.setAutoCreateContainerGaps(true);
            layout.setAutoCreateGaps(true);
            layout.setHorizontalGroup(
                    layout.createSequentialGroup()
                    .addGroup(layout.createParallelGroup().addComponent(pupilListLabel).addComponent(skrolekU, 100, 150, Short.MAX_VALUE))
                    .addGroup(layout.createParallelGroup().addComponent(subjectListLabel).addComponent(skrolekP, 100, 150, Short.MAX_VALUE))
                    .addGroup(layout.createParallelGroup().addComponent(gradesListLabel).addComponent(skrolekO, 100, 150, Short.MAX_VALUE))
                    .addGroup(layout.createParallelGroup().addComponent(bDodaj).addComponent(bEdit).addComponent(bUsun))                    
                );
            
            layout.setVerticalGroup(
                    layout.createParallelGroup()
                    .addGroup(layout.createSequentialGroup().addComponent(pupilListLabel).addComponent(skrolekU, 100, 150, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup().addComponent(subjectListLabel).addComponent(skrolekP, 100, 150, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup().addComponent(gradesListLabel).addComponent(skrolekO, 100, 150, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup().addComponent(bDodaj).addComponent(bEdit).addComponent(bUsun))  
                    
            );
            
            this.getContentPane().setLayout(layout);
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.pack();
    }
   

    public static void main(String[] args) 
    {
        new Oceny().setVisible(true);
    }
    
    private class Akcja extends AbstractAction
    {
        public Akcja(String nazwa, String opis, String klawiaturowySkrot)
        {
            this.putValue(Action.NAME, nazwa);
            this.putValue(Action.SHORT_DESCRIPTION, opis);
            this.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(klawiaturowySkrot));
        }

        public Akcja(String nazwa, String opis, String klawiaturowySkrot, Icon ikona)
        {
            this(nazwa, opis, klawiaturowySkrot);
            this.putValue(Action.SMALL_ICON, ikona);
        }
        
        @Override
        public void actionPerformed(ActionEvent ae)
        {
            switch (ae.getActionCommand()) 
            {
                case "Nowa Klasa":
                    nowaKlasa();
                    break;
                case "Wczytaj Klasę":
                    wczytajKlase();
                    break;
                case "Zapisz Klasę":
                    zapiszKlase();
                    break;
                case "Drukuj":
                    drukujKlase();
                    break;
                case "Ucznia":
                    nowyUczen();
                    break;
                case "Przedmiot":
                    nowyPrzedmiot();
                    break;
                case "FAQ":
                    pokazFAQ();
                    break;
                case "Info":
                    pokazInfo();
                    break;
                case "Zamknij":
                    System.exit(0);
                    break;
                case "Dodaj Ocenę":
                    dodajOcene();
                    break;
                case "Edytuj Ocenę":
                    edytujOcene();
                    break;
                case "Usuń Ocenę":
                    usunOcene();
                    break;
                default:
                    break;
            }
            
        }
        
        
        
        
        
        ArrayList listaUczniow = new ArrayList();

        private void nowaKlasa() 
        {
            System.out.println("Nowa Klasa");
            
            flagaMenuDodajUcznia = true;
            Oceny.this.isEdited = true;
        }

        private void wczytajKlase() 
        {
            System.out.println("Wczytuje klasę");
        }

        private void zapiszKlase() 
        {
           System.out.println("Zapisuję klasę");
           this.setEnabled(isEdited=false);
        }

        private void drukujKlase() 
        {
            System.out.println("Drukuję klasę");
        }

        private void nowyUczen() 
        {
            System.out.println("Dodaję ucznia");
            flagaMenuDodajPrzedmiot = true;
            //listaUczniow.add(new Uczen());
        }

        private void nowyPrzedmiot() 
        {
            System.out.println("Dodaję przedmiot");
        }

        private void pokazFAQ() 
        {
            System.out.println("Nikt o nic nie pyta");
        }

        private void pokazInfo() 
        {
            System.out.println("Autorem jest Dominik Blek, Program w wersji ALPHA");
        }

        private void dodajOcene() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        private void edytujOcene() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        private void usunOcene() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }
}
