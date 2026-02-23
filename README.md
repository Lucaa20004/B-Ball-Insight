# 🏀 Basketball Insight

**Basketball Insight** este o aplicație full-stack dedicată pasionaților de baschet, care permite explorarea echipelor din NBA, a loturilor de jucători și a clasamentelor (standings), oferind în același timp posibilitatea de a gestiona liste personalizate de jucători favoriți și notițe de scouting.

Acest proiect a fost dezvoltat în cadrul laboratorului de Ingineria Sistemelor Software (ISS) 2025-2026, respectând etapele procesului ingineresc de la colectarea cerințelor până la testare.

---

## 🌟 Funcționalități Majore

Aplicația oferă un set de funcționalități de complexitate medie , implementând operații CRUD pe entitățile din domeniul problemei:

1. **Sincronizarea Datelor (Read/Create):** Extragerea automată (via API/Scraper) a datelor despre echipe, jucători și recorduri (Win/Loss) și persistarea acestora în baza de date locală.
2. **Gestiunea Utilizatorilor (CRUD):** Creare cont, autentificare și gestionare profil utilizator.
3. **Explorare Echipe și Jucători (Read):** Vizualizarea loturilor (rosters) și a clasamentelor actualizate.
4. **Watchlist Personalizat (Create/Read/Delete):** Adăugarea jucătorilor sau echipelor favorite într-o listă personală de urmărire.
5. **Notițe de Scouting și Media (Update):** Utilizatorii pot edita profilul jucătorilor din propriul Watchlist pentru a adăuga link-uri către poze și notițe personale de analiză.
6. **Căutare și Filtrare:** Căutare rapidă a jucătorilor după nume și filtrare după poziția în teren (ex: Point Guard, Center).

---

## 🏗️ Arhitectură și Tehnologii

Aplicația este construită pe baza unei arhitecturi stratificate, separând clar responsabilitățile:

### 1. Presentation Layer (Frontend)
* **Tehnologii:** React (sau React Native) / JavaScript / CSS
* **Rol:** Interfața grafică cu utilizatorul, afișarea tabelelor cu statistici și formularele de gestionare a contului/watchlist-ului.

### 2. Business Logic Layer (Backend)
* **Tehnologii:** Java / Spring Boot
* **Rol:** Procesarea regulilor de business, expunerea endpoint-urilor REST, orchestrarea logicii de scraping/data fetching și validarea datelor.

### 3. Data Access Layer (Bază de date & ORM)
* **Tehnologii:** PostgreSQL (sau MySQL) / Hibernate (Spring Data JPA)
* **Rol:** Stocarea permanentă a datelor. Accesul la baza de date relațională se face obligatoriu prin intermediul bibliotecii ORM.

---

   ```bash
   git clone [https://github.com/username/basketball-insight.git](https://github.com/username/basketball-insight.git)
