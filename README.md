

# 🛒 Sklep Internetowy 

To podstawowa emulacja sklepu internetowego stworzona przy użyciu **Java 23**, **Spring Boot** i **PostgreSQL**. Projekt zawiera zarówno backend (REST API), jak i prosty frontend wykonany w **JavaScript** i **CSS**.

## 🔧 Technologie

- **Java 23**
- **Spring Boot**
- **Spring Data JPA**
- **PostgreSQL**
- **Maven**
- **JavaScript**
- **CSS**

## 🛠️ Status projektu

Projekt jest w trakcie aktywnego rozwoju. Na ten moment dostępne są podstawowe funkcjonalności takie jak:

- CRUD dla produktów
- Prosty interfejs użytkownika (HTML/CSS/JS)
- Połączenie z bazą danych PostgreSQL

### 🔐 Obecnie pracuję nad:

- Integracją **Spring Security**:
  - logowanie / rejestracja użytkowników
  - role użytkowników (admin / klient)
  - ochrona zasobów API i interfejsu

## 🚀 Jak uruchomić

1. Sklonuj repozytorium:
   ```bash
   git clone https://github.com/twoj-user/twoj-repo.git
   ```
2. Skonfiguruj połączenie z PostgreSQL w pliku `application.properties`:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/twoja_baza
   spring.datasource.username=twoj_user
   spring.datasource.password=twoje_haslo
   ```
3. Uruchom aplikację:
   ```bash
   ./mvnw spring-boot:run
   ```
4. W przeglądarce wejdź na http://localhost:8080/
