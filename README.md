# JUNO

## **Riferimenti:**

- Nome: *Simone*
- Cognome: *Baglieri*
- Matricola: *2030918*
- Corso: *Metodologie di Programmazione in Teledidattica*
- Modalità di sviluppo: *individuale*


## **Specifica del problema:**

Progettare e sviluppare la versione giocabile in Java del gioco di carte UNO.”


## **Specifiche di progetto (modalità individuale)**

*Scrivere le decisioni di progettazione relative a ognuna di esse:*

1. Gestione del profilo utente, nickname, avatar, partite giocate, vinte e perse, livello …
2. Gestione di una partita completa in modalità classica con un giocatore umano contro 3 giocatori artificiali
3. Uso appropriato di MVC [1,2], Observer/Observable e di altri Design Pattern
4. Adozione di Java Swing [2] o JavaFX [3] per la GUI
5. Utilizzo appropriato di stream

> **Riferimenti teorici:**
> 
> 1. `https://it.wikipedia.org/wiki/Model-view-controller`
> 2. Java Swing e MVC Tutorial (Attenzione questa implementazione di MVC non prevede l’adozione di Observer/Observable, mentre è caldamente consigliato adottare anche Observer/Observable) : 
> `https://www.youtube.com/watch?v=-NiKk9UqUoo&list=PLU8dZfh0ZIUn7-TDZfSmX9Q RnBgmdJJWD`

## **GitHub Repository**

`https://github.com/LimonCoding/Juno2.0.git`


# 1. Introduzione

## Descrizione del gioco di carte Uno

Il gioco di carte Uno, molto popolare sia nella versione fisica che in quella digitale, consiste nel cercare di eliminare tutte le proprie carte prima degli altri giocatori.

Ogni mazzo di carte contiene 108 carte, divise in quattro colori (rosso, giallo, verde e blu). Ogni colore ha 25 carte, di cui quelle con valore da 1 a 9 duplicate, una carta con valore 0, e 6 carte speciali (2 “pesca due”, 2 “inverti giro”, 2 “salta turno”). Il mazzo contiene anche 4 carte "Jolly" e 4 carte "Jolly più quattro". 

Le carte “Jolly” consentono la scelta del colore, mentre quelle "Jolly più quattro" oltre al cambio colore obbligano il giocatore successivo a pescare quattro carte dal mazzo.

Dalle regole originali la partita inizia con i giocatori che pescano una carta dal mazzo e la posizionano davanti a sé, in modo che sia visibile a tutti. Il giocatore che ha pescato la carta con il valore più basso inizia il gioco. Inizialmente vengono distribuite 7 carte ad ogni giocatore ed una viene posta scoperta sul tavolo. Procedendo in senso orario, ogni partecipante deve scartare una carta che abbia lo stesso colore o lo stesso valore di quella già presente sul tavolo. Se il giocatore non ha carte che soddisfino questa condizione, può giocare una carta “cambia colore” (se ne è in possesso) o deve pescare una carta dal mazzo saltando il turno.

Il gioco termina quando un giocatore ha scartato tutte le proprie carte. Bisogna fare attenzione però a dichiarare quando si ha una sola carta in mano, altrimenti lo stesso giocatore non potrà scartare ma dovrà pescarne due dal mazzo. Nel gioco originale gli altri giocatori guadagnano punti pari al valore delle carte rimaste nelle loro mani, e il giocatore che ha vinto ottiene un punteggio negativo pari ai punti guadagnati dagli altri giocatori. 

## Obiettivo del progetto

L'obiettivo del progetto è quello di creare, utilizzando il linguaggio di programmazione Java,  una versione digitale del gioco di carte Uno. 

Per il raggiungimento di tale scopo è richiesto il rispetto delle regole dettate dai design pattern MVC e Observer-Observable, utilizzando gli stream e adoperando Java Swing o JavaFX per la creazione dell’interfaccia utente. Facendo ciò si apprenderanno le basi della progettazione di un software attraverso l’uso dell’OOP (Object Oriented Programming).

La versione digitale dovrà prevedere la modalità con un giocatore reale contro tre giocatori virtuali, replicando le regole ed il funzionamento del gioco di carte originale.

L'utilizzo di design pattern e di stream permetterà di rendere il progetto più scalabile e mantenibile, facilitando eventuali aggiornamenti o modifiche future.

# 2. Decisioni di progetto

## Gestione del profilo utente

La realizzazione di questo progetto prevede la gestione del profilo utente, che permette agli utenti stessi di tenere traccia dei loro progressi nel gioco.

Il profilo utente viene creato al primo avvio del gioco e richiede, in questa prima versione, solo l’inserimento del nome utente. 

Successivamente, il giocatore potrà iniziare una partita selezionando il proprio account.

La gestione dell’account consente inoltre di visualizzare le statistiche di gioco, come il numero totale di partite giocate, vinte, perse ed il relativo livello. Queste statistiche vengono salvate ogni volta che l'utente conclude una partita e caricate ad ogni avvio successivo del gioco.

In sintesi, la gestione del profilo utente rappresenta una funzionalità importante del progetto, poiché permette agli utenti di tenere traccia dei loro progressi nel gioco (nelle implementazioni successive di personalizzare il loro account), offrendo un'esperienza di gioco più coinvolgente e soddisfacente.

## Gestione di una partita completa

Il progetto, secondo richiesta, prevede la possibilità di giocare una partita con quattro giocatori, di cui uno reale e gli altri virtuali.

La gestione di una partita completa avviene secondo le seguenti fasi:

1. Scelta dell’account con il quale si vuole giocare.
2. Assegnazione iniziale delle carte: ad ogni giocatore vengono assegnate 7 carte a caso. In questa versione il turno incomincia sempre con il giocatore reale.
3. Svolgimento della partita: la partita si svolge secondo le regole del gioco di carte Uno, con il giocatore reale che gioca per primo e i giocatori virtuali che seguono in base al senso di gioco (orario o antiorario). Ogni giocatore può scegliere di giocare una carta tra quelle in suo possesso o di pescarne una dal mazzo comune, secondo le regole del gioco. I giocatori virtuali giocheranno entro 4 secondi dal loro turno.
4. Fase finale: bisogna dichiarare, cliccando l’apposito tasto in basso a destra dell’interfaccia, quando si ha una sola carta in mano entro 6 secondi, altrimenti l’utente pescherà in automatico due dal mazzo. 
5. Fine della partita: la partita si conclude quando uno dei giocatori termina tutte le carte dal proprio mazzo, aggiornando così le statistiche del giocatore reale. In questa versione di gioco le suddette statistiche vengono aggiornate ogni volta che si conclude una partita, incrementando il livello dell’account secondo il numero di partite giocate (+0.5 punti), di partite vinte (+1.0 punti).

## Adozione di Java Swing per la GUI

Come scelta progettuale si è fatto uso di Java Swing per la realizzazione dell'interfaccia utente grafica (GUI).

Java Swing è una libreria per Java che permette di creare interfacce grafiche per applicazioni desktop. Offre una vasta gamma di componenti grafici, come finestre, pulsanti, etichette, tabelle e molto altro, che possono essere utilizzati per creare interfacce semplici ma intuitive ed efficaci.

Per facilitare la realizzazione dell'interfaccia grafica sono stati creati componenti custom, in particolare i “panel” o i “frame”,  che estendono le classi messe a disposizione dalla libreria Swing. Inoltre, sono stati utilizzati diversi tipi di Layout che gestiscono i componenti contenuti al suo interno, permettendo anche il ridimensionamento degli stessi secondo la dimensione della finestra (o frame) nel quale sono aggiunti.

Spiegazione di alcuni layout manager utilizzati nel progetto:

- Il **CardLayout** permette di mostrare una sola scheda di un gruppo. Ogni scheda è rappresentata da un componente, nel nostro caso di JPanel custom (in generale di un JPanel), e può essere mostrata o nascosta in base alle necessità.
- Il **FlowLayout** permette di organizzare i componenti in una finestra in modo da creare una sequenza di componenti, come se fossero parole in una pagina di testo oppure con un esempio più pratico di carte possedute in mano.
- Il **BorderLayout** permette di organizzare i componenti in una finestra in modo da creare un bordo attorno ai componenti. Divide la finestra in cinque aree: nord, sud, est, ovest e centro, ogni componente può essere aggiunto a una di queste aree, specificando appunto la posizione che dovrà possedere nel layout.

# 3. Design e implementazione

## Caratteristiche di Java

Per la realizzazione del progetto, come da richiesta, è stato utilizzato il linguaggio di programmazione Java.

Java è un linguaggio di programmazione molto diffuso, utilizzato in molti ambiti, dal mobile al web, all'embedded. Uno dei suoi punti di forza è la sua portabilità, permette di eseguire i programmi scritti su qualsiasi piattaforma che supporti il Java Virtual Machine (JVM). 

Questo linguaggio è definito “orientato agli oggetti” (OOP - Object Oriented Programming).

Un **oggetto** (o istanza) è la rappresentazione concreta e specifica di una **classe**. La **classe** è dunque solo un modello formale che specifica lo stato e il comportamento di tutte le sue istanze (**oggetti**). Questo modello possiede **attributi** (specificano le caratteristiche che tutti gli oggetti della classe devono possedere) e **metodi** (specificano le operazioni che un certo oggetto è potenzialmente in grado di compiere).

Dopo aver dato la definizione di oggetto, qui di seguito sono riportate anche le caratteristiche principali che un linguaggio OOP possiede:

- **Ereditarietà**: permette di creare gerarchie di classi e di riutilizzare il codice esistente, aumentando le probabilità che il sistema sia implementato e manutenuto in maniera efficiente. Una classe (Sub-Class o figlio) ha la possibilità di estendere le proprietà e i metodi di un'altra classe (Super-Class o padre) aggiungendo nuove caratteristiche o migliorando quelle esistenti. Un esempio può essere la rappresentazione di una classe “Dottore”, le cui caratteristiche vengono ereditate da delle rappresentazioni più specifiche, come un “Medico di Base” o un “Chirurgo”.

![Untitled](JUNO%20d742a6ffa6b84f0688f3bdd303c09a4b/Untitled.png)

- **Incapsulamento**:  aiuta a proteggere i dati e a separare le responsabilità all'interno delle classi, semplificando e modularizzando il lavoro di sviluppo, assumendo un certo funzionamento a “scatola nera”. Una classe può chiamare tutti i metodi della stessa (senza differenza di visibilità), mentre una classe non può chiamare i metodi privati di altre. É possibile nascondere i dettagli di implementazione di una classe dietro un'interfaccia pubblica.
- **Polimorfismo**: una classe può avere più di una forma per un'operazione, ovvero un oggetto dispone di più implementazioni per un metodo. Ad esempio, come visto prima la classe “Chirurgo” ha un metodo che già è implementato nella classe “Dottore”, ma per via del polimorfismo è possibile sovrascriverlo, di conseguenza svolgeranno l’operazione in maniera differente.

## Utilizzo dei design pattern

Per la realizzazione del progetto, sono stati utilizzati i seguenti design pattern:

- Model-View-Controller (MVC): questo pattern è stato utilizzato per separare la logica del gioco dalla sua rappresentazione grafica. In particolare, il **modello** rappresenta la logica del gioco, l’**interfaccia utente** (**View**) è responsabile della rappresentazione grafica e il **controller** gestisce gli input dell'utente e aggiorna il **modello** e di conseguenza anche **l’interfaccia utente.**
- Observer-Observable: questo pattern è stato utilizzato per implementare la notifica degli eventi di gioco agli oggetti interessati. Ad esempio, quando una carta viene giocata, tutti gli oggetti che devono essere aggiornati (come l’interfaccia utente e il modello) vengono notificati dell'evento e possono agire di conseguenza.

L'utilizzo dei design pattern ha permesso di rendere il progetto più scalabile e mantenibile, separando le diverse responsabilità all'interno del codice e rendendo più facile l'aggiunta o la modifica di funzionalità in futuro. Inoltre, l'utilizzo di pattern ben noti e consolidati ha reso il codice più facile da comprendere e mantenere anche per altri sviluppatori.

## Descrizione dell'implementazione

Per la realizzazione del progetto del gioco di carte Uno, è stato seguito il pattern MVC per separare la logica del gioco dalla sua rappresentazione grafica.

Il modello rappresenta la logica del gioco, memorizzando le informazioni sullo stato attuale del gioco (ad esempio, le carte in mano di ogni giocatore, il mazzo, il colore e il valore della carta in cima al mazzo degli scarti, ecc.) e fornendo i metodi per modificare queste informazioni (ad esempio, per giocare una carta, pescarne una dal mazzo, cambiare il colore in gioco, ecc.).

In particolare per gestire una partita completa è stata creata la classe “Game” che comprende la creazione del mazzo di carte (**Deck**) e del mazzo degli scarti (**Discarded**), ovvero due pile di carte (**Card**), dei tre giocatori virtuali (**AiPlayer**) e del giocatore reale (**Player**). La classe “Game” possiede degli attributi, per determinare il turno ed il verso di gioco, e dei metodi che permettono di iniziare, resettare o mettere in pausa il gioco, oltre che a gestire le regole di gioco, come ad esempio le funzionalità delle carte speciali oppure il controllo della dichiarazione quando si ha una sola carta in mano.

# 4. Utilizzo degli stream

Gli Stream introdotti da Java 8 sono una caratteristica importante del linguaggio che permettono di lavorare con sequenze di dati in modo più efficiente e flessibile.

La sorgente di dati può essere una Collection, ma al contrario di quest’ultime uno Stream non memorizza né modifica i dati della sorgente, ma lavora su di essi.

In particolare su questa sequenza di elementi (**Stream**) è possibile effettuare una o più operazioni intermedie (restituiscono un altro stream su cui continuare a
lavorare) o terminali (restituiscono il tipo atteso).

## Stream per riordino giocatori

Nel progetto gli Stream sono stati usati per riordinare i giocatori secondo l’ordine crescente dei loro id, ma non solo.

La funzione ***sorted()*** è una delle tante operazioni che è possibile eseguire sugli stream e permette di ordinare gli elementi in base alle specifiche dettate nell’istanza di un Comparator.

Un Comparator è un'interfaccia utilizzata per definire una logica di confronto per gli oggetti di un determinato tipo.

```java
playersList = new ArrayList<>(Arrays.asList(bottomPlayer, topPlayer,
																					  rightPlayer, leftPlayer));
aiPlayersList = new ArrayList<>(Arrays.asList(topPlayer, rightPlayer, leftPlayer));
sortedPlayerList = playersList.stream()
                                .sorted(Comparator.comparing(Player::getGameId))
                                          .collect(Collectors.toList());
```

## Stream per la scelta delle carte

Gli stream sono stati usati anche per filtrare le carte nella mano di un giocatore e successivamente sceglierle secondo determinate condizioni.

La logica di scelta delle carte da giocare per i giocatori virtuali è basata su alcuni criteri predefiniti, dettati dalla strategia impostata al relativo giocatore.

Ad esempio, i giocatori virtuali possono scegliere di giocare la prima carta disponibile del colore uguale all’ultima carta scartata, oppure la prima carta con lo stesso valore dello scarto, o in alternativa la scelta di carte speciali oppure di carte cambio colore, altrimenti se nessuna delle carte disponibili nella mano del giocatore sono utilizzabili, lo stesso prenderà una carta dal mazzo. La scelta delle carte avviene in modo efficiente e veloce, utilizzando gli stream per filtrare e riordinare le carte disponibili.

```java
public Card autoChooseCard(List<Card> validCards, Card rejected) {
	/** Collect in 'handNoWild' list the cards that aren't Wild */
	List<Card> handNoWild = validCards.stream()
				    .filter(card -> !(card.getColor().equals(Color.WILD)))
	             .collect(Collectors.toList());
	/** Find and store on an Optional a wild card */
	Optional<Card> validCardByWild = validCards.stream()
	           .filter(card -> card.isWild() || card.isWildFour())
	              .findAny();
	/** Find and store on an Optional a special card */
	Optional<Card> validCardBySpecial = validCards.stream()
	            .filter(card -> ( card.getColor().equals(rejected.getColor()) || 
	                card.getValue().equals(rejected.getValue()) )  && card.isSpecial())
		                .findAny();
	/** Find and store on an Optional a card valid by color */
	Optional<Card> validCardByColor = handNoWild.stream()
	             .filter(card -> card.getColor().equals(rejected.getColor()))
	                .findAny();
	/** Find and store on an Optional a card valid by value */
	Optional<Card> validCardByValue = handNoWild.stream()
	              .filter(card -> card.getValue().equals(rejected.getValue()))
	                .findAny();
}
```

# 5. Test e risultati

## Descrizione dei test effettuati

Per verificare il corretto funzionamento del progetto, sono stati effettuati i seguenti test:

- Test di base: sono stati eseguiti alcuni test di base per verificare il corretto funzionamento delle principali funzionalità del gioco, come ad esempio il pescaggio di una carta dal mazzo, il cambio del colore in gioco, la possibilità di giocare una carta in base al colore o al valore, la fine del gioco e il conteggio delle partite giocate, vinte ed il livello raggiunto.
- Test con input errati: sono stati eseguiti alcuni test per verificare come il gioco gestisce gli input errati, come ad esempio il tentativo di giocare una carta non valida o durante il turno di un altro giocatore.
- Test di salvataggio e caricamento delle statistiche: sono stati eseguiti alcuni test per verificare il corretto funzionamento del salvataggio e del caricamento delle statistiche di gioco per i diversi account.

## Risultati ottenuti

I test sono stati eseguiti con successo, verificando il corretto funzionamento del gioco. Tuttavia, è sempre possibile che in futuro vengano individuati nuovi bug o problemi di funzionamento oppure di implementare nuove funzionalità, pertanto è importante continuare a testare e mantenere il progetto nel tempo.

# 6. Conclusioni

## Riepilogo del progetto

Il progetto ha avuto come obiettivo quello di sviluppare una versione digitale del famoso gioco di carte, utilizzando il linguaggio di programmazione Java.

Il progetto ha previsto l'utilizzo dei design pattern Model-View-Controller (MVC) e Observer-Observable per separare le diverse responsabilità all'interno del codice e rendere il progetto più scalabile e mantenibile. Per implementare il Model sono state create le classi che rappresentano gli oggetti necessari per il gioco, mentre per la View è stato usato un framework per la creazione di interfacce grafiche (in questo caso Swing). Il Controller è stato implementato gestendo gli input dell'utente e aggiornando il modello e di conseguenza l’interfaccia, utilizzando il pattern Observer-Observable.

Con la realizzazione di questo progetto ho avuto l'opportunità di approfondire e migliorare la conoscenza del linguaggio Java e delle sue caratteristiche, sperimentando diverse soluzioni e tecniche di programmazione per risolvere i problemi che si sono presentati durante le diverse fasi del lavoro.

## Possibili miglioramenti futuri

Una possibile implementazione potrà riguardare la gestione da parte dell’utente di poter scegliere diverse opzioni di personalizzazione, come la selezione di un icona per ogni account creato, la scelta di un tema grafico del gioco o l’aggiunta di musica e/o animazioni grafiche ed inoltre, ulteriori modalità di gioco.