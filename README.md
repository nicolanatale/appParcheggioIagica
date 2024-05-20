# app-parcheggio-ottobre2022-be

###  AppParcheggio

    Creare una pagina per la gestione di un parcheggio con posti limitati
    Componente1- Inserimento nuova auto nel parcheggio e stampa ticket con orario di ingresso e id ( index dell'elemento nella lista o numero univoco generico )
    Componente2- Visualizzazione auto attualente parcheggiate con orario di ingresso e id
    Componente3- Uscita dal parcheggio con inserimento id ticket e calcolo prezzo
    
    - Il prezzo orario è di 0.5€ per la prima ora e 1€ per le ore successive
    - Non si possono parcheggiare auto se è raggiunto il limite massimo (10 di default)
    - le Auto hanno orario di ingresso, id e orario uscita
    - le auto gia uscite dovranno rimanere in memoria come cronologia
    - le auto gia uscite hanno la data di uscita registrata
    
    BONUS: Componente4- Storico auto uscite con prezzo pagato
    BONUS2: nel componente 4 calcolare il guadagnato e la media delle ore di parcheggio per le singole auto

*********avvio app**********

1- aprire il progetto con Eclipse o NetBeans

2- se appare "unloadable" fare clean&build e riprovare

3- avviare il file "ApplicationParcheggio"


per testare l'applicazione bisogna caricare il database tramite il file sql con heidiSQL o mysql


Account di test per i ruoli di admin ed operatore:

username: admin
password: qwerty

username: user
password: asdfgh