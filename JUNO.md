# JUNO

Riferimenti:

- Nome: *Simone*
- Cognome: *Baglieri*
- Matricola: *2030918*
- Corso: *Teledidattica*
- Modalità di sviluppo: *individuale*

## **Specifica del problema:**

“Progettare e sviluppare la versione giocabile in Java del gioco di carte UNO.”

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

> `https://github.com/LimonCoding/Juno2.0.git`
# 1. Introduzione

## Descrizione del gioco di carte Uno

Il gioco di carte Uno, molto popolare sia nella versione fisica che in quella digitale, consiste nel cercare di eliminare tutte le proprie carte prima degli altri giocatori.

Ogni mazzo di carte contiene 108 carte, divise in quattro colori (rosso, giallo, verde e blu). Ogni colore ha 25 carte, di cui quelle con valore da 1 a 9 duplicate, una carta con valore 0, e 6 carte speciali (2 “pesca due”, 2 “inverti giro”, 2 “salta turno”). Il mazzo contiene anche 4 carte "Jolly" e 4 carte "Jolly più quattro". 

Le carte “Jolly” consentono la scelta del colore, mentre quelle "Jolly più quattro" oltre al cambio colore obbligano il giocatore successivo a pescare quattro carte dal mazzo.

Dalle regole originali la partita inizia con i giocatori che pescano una carta dal mazzo e la posizionano davanti a sé, in modo che sia visibile a tutti. Il giocatore che ha pescato la carta con il valore più basso inizia il gioco. Inizialmente vengono distribuite 7 carte ad ogni giocatore ed una viene posta scoperta sul tavolo. Procedendo in senso orario, ogni partecipante deve scartare una carta che abbia lo stesso colore o lo stesso valore di quella già presente sul tavolo. Se il giocatore non ha carte che soddisfino questa condizione, può giocare una carta “cambia colore” (se ne è in possesso) o deve pescare una carta dal mazzo saltando il turno.

Il gioco termina quando un giocatore ha scartato tutte le proprie carte. Bisogna fare attenzione però a dichiarare quando si ha una sola carta in mano, altrimenti lo stesso giocatore non potrà scartare ma dovrà pescarne due dal mazzo. Nel gioco originale gli altri giocatori guadagnano punti pari al valore delle carte rimaste nelle loro mani, e il giocatore che ha vinto ottiene un punteggio negativo pari ai punti guadagnati dagli altri giocatori. 

## Obiettivo del progetto

L'obiettivo del progetto è quello di creare, utilizzando il linguaggio di programmazione Java,  una versione digitale del gioco di carte Uno prevedendo un giocatore reale e tre giocatori virtuali. Per il raggiungimento di tale scopo è richiesto il rispetto delle regole dettate dai design pattern MVC e Observer-Observable, utilizzando gli stream e l’adozione di Java Swing o JavaFX per la creazione dell’interfaccia utente. 

La versione digitale dovrà replicare le regole e il funzionamento del gioco di carte originale,  offrendo allo stesso tempo un'esperienza di gioco fluida e intuitiva.

L'utilizzo di design pattern e di stream permetterà di rendere il progetto più scalabile e mantenibile, facilitando eventuali aggiornamenti o modifiche future.

# 2. Decisioni di progetto

## Gestione del profilo utente

La realizzazione di questo progetto prevede la gestione del profilo utente, che permette agli utenti stessi di tenere traccia dei loro progressi nel gioco.

Il profilo utente viene creato al primo avvio del gioco e richiede, in questa prima versione di gioco, l’inserimento del nome utente. Successivamente, il giocatore può iniziare una partita selezionando il proprio account.

La gestione dell’account consente inoltre di visualizzare le statistiche di gioco, come il numero totale di partite giocate, vinte e il relativo livello. Queste statistiche vengono salvate ogni volta che l'utente conclude una partita e vengono caricate ad ogni avvio successivo.

In sintesi, la gestione del profilo utente rappresenta una funzionalità importante del progetto, poiché permette agli utenti di tenere traccia dei loro progressi nel gioco (nelle implementazioni successive di personalizzare il loro account), offrendo un'esperienza di gioco più coinvolgente e soddisfacente.

## Gestione di una partita completa

Il progetto, secondo richiesta, prevede la possibilità di giocare una partita con quattro giocatori, di cui uno reale e gli altri virtuali.

La gestione di una partita completa in questa versione avviene secondo le seguenti fasi:

1. Scelta dell’account con il quale si vuole giocare.
2. Assegnazione iniziale delle carte: ad ogni giocatore vengono assegnate 7 carte a caso. In questa versione il turno incomincia sempre con il giocatore reale.
3. Svolgimento della partita: la partita si svolge secondo le regole del gioco di carte Uno, con il giocatore reale che gioca per primo e i giocatori virtuali che seguono in base al senso di gioco (orario o antiorario). Ogni giocatore può scegliere di giocare una carta tra quelle in suo possesso o di pescarne una dal mazzo comune, secondo le regole del gioco.
4. Fase finale: bisogna dichiarare, cliccando l’apposito tasto in basso a destra dell’interfaccia, quando si ha una sola carta in mano entro 6 secondi, altrimenti l’utente pescherà in automatico due dal mazzo. 
5. Fine della partita: la partita si conclude quando uno dei giocatori elimina tutte le carte dal proprio mazzo, aggiornando così le statistiche del giocatore reale. In questa versione di gioco le suddette statistiche vengono aggiornate ogni volta che si conclude una partita, incrementando il livello dell’account secondo il numero di partite giocate (+0.5 punti), di partite vinte (+1.0 punti).

## Adozione di Java Swing per la GUI

Come scelta progettuale si è fatto uso di Java Swing per la realizzazione dell'interfaccia utente grafica (GUI).

Java Swing è una libreria grafica per Java che permette di creare interfacce grafiche per applicazioni desktop. Offre una vasta gamma di componenti grafici, come finestre, pulsanti, etichette, tabelle e molto altro, che possono essere utilizzati per creare interfacce semplici ma intuitive ed efficaci.

**(paragrafo da eliminare?)** L'adozione di Java Swing per la GUI del gioco di carte Uno ha permesso di creare un'interfaccia intuitiva e facile da utilizzare, offrendo agli utenti un'esperienza di gioco fluida e soddisfacente. Inoltre, Java Swing è una libreria stabile e ben documentata, che ha permesso lo sviluppo del progetto.

**(paragrafo da eliminare?)**In sintesi, l'adozione di Java Swing per la GUI del gioco di carte Uno è stata una scelta vantaggiosa, poiché ha permesso di creare un'interfaccia grafica professionale e facile da utilizzare, offrendo agli utenti un'esperienza di gioco piacevole e soddisfacente.

# 3. Design e implementazione

## Scelta della tecnologia **(decidere se mantenere o eliminare)**

Per la realizzazione del progetto, come da richiesta, è stato utilizzato il linguaggio di programmazione Java.

Java è un linguaggio di programmazione molto diffuso, utilizzato in molti ambiti, dal mobile al web all'embedded. Uno dei suoi punti di forza è la sua portabilità, che permette di eseguire i programmi scritti in Java su qualsiasi piattaforma che supporti il Java Virtual Machine (JVM). 

Caratteristiche principali del linguaggio Java: Ereditarietà… 

Inoltre, Java dispone di librerie e framework per la realizzazione di interfacce grafiche e l'implementazione di connessioni di rete.

Java è anche particolarmente adatto all'implementazione di giochi, grazie alla sua capacità di gestire in modo efficace grandi quantità di dati e ai suoi strumenti per la creazione di interfacce grafiche intuitive e accattivanti.

## Utilizzo dei design pattern

Per la realizzazione del progetto, sono stati utilizzati i seguenti design pattern:

- Model-View-Controller (MVC): questo pattern è stato utilizzato per separare la logica del gioco dalla sua rappresentazione grafica. In particolare, il **modello** rappresenta la logica del gioco, l’**interfaccia utente** (**View**) è responsabile della rappresentazione grafica e il **controller** gestisce gli input dell'utente e aggiorna il **modello** e di conseguenza anche **l’interfaccia utente.**
- Observer-Observable: questo pattern è stato utilizzato per implementare la notifica degli eventi di gioco agli oggetti interessati. Ad esempio, quando una carta viene giocata, tutti gli oggetti che devono essere aggiornati (come l’interfaccia utente e il modello) vengono notificati dell'evento e possono agire di conseguenza.

L'utilizzo dei design pattern ha permesso di rendere il progetto più scalabile e mantenibile, separando le diverse responsabilità all'interno del codice e rendendo più facile l'aggiunta o la modifica di funzionalità in futuro. Inoltre, l'utilizzo di pattern ben noti e consolidati ha reso il codice più facile da comprendere e mantenere anche per altri sviluppatori.

## Descrizione dell'implementazione

Per la realizzazione del progetto del gioco di carte Uno, è stato seguito il pattern MVC per separare la logica del gioco dalla sua rappresentazione grafica.

Il modello è stato implementato utilizzando la tecnologia JavaBeans **(da rivedere perché javabeans è la creazione di classi (oggetti) secondo una particolare convenzione)**, che permette di creare oggetti Java con proprietà modificabili e notifiche di modifica automatiche. Il modello rappresenta la logica del gioco, memorizzando le informazioni sullo stato attuale del gioco (ad esempio, le carte in mano a ogni giocatore, il mazzo, il colore e il valore della carta in cima al mazzo, ecc.) e fornendo i metodi per modificare queste informazioni (ad esempio, per giocare una carta, pescarne una dal mazzo, cambiare il colore in gioco, ecc.).

**(Già è presente un capitolo per esplicare l’adozione di Java Swing)**L’interfaccia utente è stata implementata utilizzando un framework per la creazione di interfacce grafiche, in questo caso Java Swing. La GUI si occupa di rappresentare graficamente lo stato del gioco, utilizzando il modello come fonte di informazione. Ad esempio, la vista mostra le carte in mano a ogni giocatore e quella in cima al mazzo e al mazzo degli scarti, visualizza il nome di ogni giocatore e mostra gli eventi di gioco (ad esempio, quando una carta viene giocata o pescata dal mazzo).

**(da rivedere poiché nella mia implementazione aggiorno il modello direttamente dalla view GUI)** Il controller è stato implementato utilizzando il pattern Observer-Observable per gestire gli input dell'utente e aggiornare il modello e la vista di conseguenza. Ad esempio, quando l'utente clicca su una carta per giocarla, il controller riceve l'input, aggiorna il modello giocando la carta e notifica la vista dell'evento, affinché possa aggiornare la sua rappresentazione grafica.

Inoltre, è stato implementato il salvataggio e il caricamento delle statistiche di gioco utilizzando gli stream per la lettura e scrittura di file di testo **(da rivedere perché non ho usato gli stream per salvare o leggere da file ma per riordinare le carte o filtrare**). Questo permette di conservare le statistiche del gioco anche dopo che l'applicazione è stata chiusa, offrendo agli utenti la possibilità di tenere traccia dei loro progressi nel gioco.

# 4. Utilizzo degli stream

## Stream per riordino giocatori

l progetto prevede l'utilizzo di stream, i quali sono stati usati per riordinare i giocatori secondo il loro id.

```java

```

## Stream per la scelta delle carte

Gli stream sono stati usati anche per filtrare le carte nella mano di un giocatore e successivamente sceglierle secondo determinate condizioni.

Gli stream sono una caratteristica di Java che consente di lavorare con i flussi di dati, sia in entrata che in uscita, in modo efficiente e versatile. La logica di scelta delle carte da giocare per i giocatori virtuali è basata su alcuni criteri predefiniti, dettati dalla strategia impostata al relativo giocatore.

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

Il progetto ha previsto l'utilizzo dei design pattern Model-View-Controller (MVC) e Observer-Observable per separare le diverse responsabilità all'interno del codice e rendere il progetto più scalabile e mantenibile. Inoltre, è stato utilizzato il JavaBeans per implementare il modello del gioco e un framework per la creazione di interfacce grafiche (in questo caso Swing) per implementare la GUI. Il controller è stato implementato gestendo gli input dell'utente e aggiornando il modello e di conseguenza l’interfaccia, utilizzando il pattern Observer-Observable.

Con la realizzazione di questo progetto ho avuto l'opportunità di approfondire e migliorare la conoscenza del linguaggio Java e delle sue caratteristiche, sperimentando diverse soluzioni e tecniche di programmazione per risolvere i problemi che si sono presentati durante le diverse fasi del lavoro.

## Aspetti positivi e negativi dell'implementazione

Ancora è una versione essenziale ma completamente funzionante del gioco di carte UNO.

## Possibili miglioramenti futuri

Una possibile implementazione potrà riguardare la gestione da parte dell’utente di poter scegliere diverse opzioni di personalizzazione, come la selezione di un icona per ogni account creato, la scelta di un tema grafico del gioco o l’aggiunta di musica e/o animazioni grafiche ed inoltre, ulteriori modalità di gioco.