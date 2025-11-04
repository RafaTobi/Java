# V1

---
## Pre Coaching

---
### Geschatte progress(in procent): 1%
### Status
Het was een moeilijk begin. We raakten niet vertrokken met de nieuwe technologiën.
We hadden een planning gemaakt maar achteraf bleek deze niet zo goed.
Er was veel werk gepland in het laatste weekend maar toen hadden we het beide mega druk.

### Quality
Te weinig code om te oordelen

### Vragen
Geen concrete vragen

### Post Coaching

---
### Feedback
Coaching lessen op school zijn verplicht.
Reflectie verslag ontbrak.
Onze code was in het Nederlands.
De poort klasse was overbodig. 

# V2

---
## Pre Coaching

---
### Geschatte progress(in procent): 50%
### Status
Het moeilijke begin sleepte nog even aan. Het duurde lang voor ik goed overweg kon met Spring Boot.
Maar ondertussen hadden we al een gigantische achterstand opgebouwd. Ik heb deze proberen wegwerken maar het ging niet snel genoeg naar mijn gevoel.
Nu is er in Warehouse toch al wel wat functionaliteit o.a. het laden van PO's kan gebeuren via een call vanuit waterside project, ik heb geëxperimenteerd met Messaging en Security.
Testing moet nog volledig gebeuren en er moet meer Logging toegepast worden.

### Quality
De kwaliteit van de code is nog niet 100% in orde. Er zijn wat quick and dirty fixes gebeurd maar dat komt omdat we ons nu hadden gefocust om de functionaliteiten op te stellen.

### Vragen
- Hoe werkt het plannen/uitvoeren van BO en IO's exact? Is dat simpelweg een boolean geflipt worden of wordt er echt iets gecheckt? via rest(?) maar hoe
- Wat voor http status codes als return van water apis? Wat ben ik daar mee? Bepaald de status van de operation de http code?
- Feign voor rest calls wilde niet lukken, errors bij het implementeren van dependency -> RestTemplate gebruikt
- Voor het berekenen van Invoices mag er maar 1 invoice berekend worden per client, dus invoice = Storage + PO commissie? Maar kan een supplier ook PO's sturen?
- Moet er een invoice klasse gemaakt worden om invoices op te slaan in een db? 


### Post Coaching

---
### Feedback

- Er stonden nog enkele packages met hoofdletters.
- Ongebruikte resource service. Werken volgens YAGNI.
- Testing moet nog worden toegevoegd op Warehouse project.
- API moet meer RESTful gemaakt worden.
- Er moet nog wat cleanup van de code gebeuren op de waterzijde.
- URLs naar andere projecten mogen niet hardcoded zijn. Best definieren in properties.
- Business logica mag niet bij in een service die gefocust is op het sturen van messages. Dat is nu nog niet echt aan de gang, maar het is al een tip voor mocht dat later wel zo zijn


- Het uitvoeren van BO & IO is inderdaad een boolean flippen. Het moet wel voorzien zijn voor mogelijke latere uitbreidingen.
- Er moet niet perse iets gebeuren met de http codes die we terug krijgen. Vooral logisch nadenken over wat je er mee kan doen (errors loggen, etc).
- Suppliers kunnen geen PO sturen. De 1% commissie op een verkoop wordt mee aangerekend aan de Supplier
- Invoices opslaan in db is handig.


# V3

---
## Pre Coaching

---
### Geschatte progress(in procent): 99%
### Status
De werking van ons project is af. Met zo'n project ben je natuurlijk nooit 100% klaar.
Er liggen altijd nog verbeteringen om de hoek die je de eerste keer niet opmerkte.
Maar alle functionaliteit die is vereist zit er in.

### Quality
We zijn tevreden over de kwaliteit van ons project en we hebben ons best gedaan om ons aan alle architecturale technieken te houden.

### Vragen