# 🏀 Basketball Insight

**Basketball Insight** este o aplicație full-stack dedicată pasionaților de baschet, care permite explorarea echipelor din NBA, a loturilor de jucători și a clasamentelor (standings), oferind în același timp posibilitatea de a gestiona liste personalizate de jucători favoriți și notițe de scouting.

[cite_start]Acest proiect a fost dezvoltat în cadrul laboratorului de Ingineria Sistemelor Software (ISS) 2025-2026 [cite: 1][cite_start], respectând etapele procesului ingineresc de la colectarea cerințelor până la testare[cite: 5].

---

## 🌟 Funcționalități Majore

[cite_start]Aplicația oferă un set de funcționalități de complexitate medie [cite: 4][cite_start], implementând operații CRUD pe entitățile din domeniul problemei[cite: 10]:

1. **Sincronizarea Datelor (Read/Create):** Extragerea automată (via API/Scraper) a datelor despre echipe, jucători și recorduri (Win/Loss) și persistarea acestora în baza de date locală.
2. **Gestiunea Utilizatorilor (CRUD):** Creare cont, autentificare și gestionare profil utilizator.
3. **Explorare Echipe și Jucători (Read):** Vizualizarea loturilor (rosters) și a clasamentelor actualizate.
4. **Watchlist Personalizat (Create/Read/Delete):** Adăugarea jucătorilor sau echipelor favorite într-o listă personală de urmărire.
5. **Notițe de Scouting și Media (Update):** Utilizatorii pot edita profilul jucătorilor din propriul Watchlist pentru a adăuga link-uri către poze și notițe personale de analiză.
6. **Căutare și Filtrare:** Căutare rapidă a jucătorilor după nume și filtrare după poziția în teren (ex: Point Guard, Center).

---

## 🏗️ Arhitectură și Tehnologii

[cite_start]Aplicația este construită pe baza unei arhitecturi stratificate[cite: 3], separând clar responsabilitățile:

### 1. Presentation Layer (Frontend)
* **Tehnologii:** React (sau React Native) / JavaScript / CSS
* **Rol:** Interfața grafică cu utilizatorul, afișarea tabelelor cu statistici și formularele de gestionare a contului/watchlist-ului.

### 2. Business Logic Layer (Backend)
* [cite_start]**Tehnologii:** Java / Spring Boot [cite: 8]
* **Rol:** Procesarea regulilor de business, expunerea endpoint-urilor REST, orchestrarea logicii de scraping/data fetching și validarea datelor.

### 3. Data Access Layer (Bază de date & ORM)
* **Tehnologii:** PostgreSQL (sau MySQL) / Hibernate (Spring Data JPA)
* **Rol:** Stocarea permanentă a datelor. [cite_start]Accesul la baza de date relațională se face obligatoriu prin intermediul bibliotecii ORM[cite: 9].

---

## 🚀 Rulare și Instalare (Setup)

### Cerințe preliminare:
* Node.js & npm (pentru Frontend)
* Java 17+ & Maven (pentru Backend)
* Bază de date PostgreSQL/MySQL instalată și rulând local.

### Pași:
1. **Clonarea repository-ului:**
   ```bash
   git clone [https://github.com/username/basketball-insight.git](https://github.com/username/basketball-insight.git)
