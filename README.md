# Book Lovers Community (BLC)

Projekt zaliczeniowy realizowany w ramach przedmiotów JAZZ oraz MPR. Jest to aplikacja webowa napisana w języku Java (Spring Boot), służąca do zarządzania wirtualną biblioteką, recenzowania książek oraz śledzenia statystyk czytelnictwa.

## Struktura Projektu

Projekt został zrealizowany w architekturze wielomodułowej (Maven Multi-Module), co zapewnia separację warstw aplikacji i spełnia wymogi projektowe:

* **blc-data**: Warstwa trwałości. Zawiera encje JPA, repozytoria oraz konfigurację bazy danych H2.
* **blc-services**: Warstwa logiki biznesowej. Zawiera serwisy, DTO, klienta HTTP do zewnętrznego API oraz logikę harmonogramu zadań.
* **blc-web**: Warstwa prezentacji. Zawiera kontrolery REST i MVC, konfigurację bezpieczeństwa (Spring Security) oraz obsługę błędów.

## Zrealizowane Wymagania Funkcjonalne i Techniczne

### 1. Integracja i Dane
* **Import danych z zewnętrznego API:** Aplikacja integruje się z serwisem OpenLibrary.org przy użyciu `RestTemplate` w celu pobierania informacji o książkach.
* **Harmonogram zadań (Scheduler):** Zaimplementowano metodę cykliczną (adnotacja `@Scheduled`), która automatycznie aktualizuje bazę książek w tle.
* **Czysty SQL (JDBC):** Zgodnie z wymaganiami, statystyki biblioteki są obliczane przy użyciu `JdbcTemplate` (z pominięciem Hibernate) dla optymalizacji wydajności zapytań agregujących.

### 2. Architektura i Interfejs
* **Hybrydowy interfejs:** Aplikacja udostępnia zarówno widoki renderowane po stronie serwera (Thymeleaf/MVC), jak i API zwracające format JSON (REST Controllers).
* **Bezpieczeństwo:** Zaimplementowano pełny mechanizm rejestracji i logowania z wykorzystaniem Spring Security. Hasła użytkowników są haszowane (BCrypt).

### 3. Testy i Jakość Kodu (MPR)
Projekt posiada wysokie pokrycie kodu testami (powyżej 80%), wykorzystując różne techniki weryfikacji oprogramowania:
* **Testy Integracyjne:** Weryfikacja działania kontrolerów i kontekstu Springa (`MockMvc`).
* **Testy Jednostkowe:** Testowanie logiki serwisowej z wykorzystaniem biblioteki Mockito.
* **Testy Manualne (Stubs):** Implementacja testów z wykorzystaniem ręcznie pisanych atrap (Stub), bez użycia frameworków mockujących.

## Technologie

* **Język:** Java 17 / 21
* **Framework:** Spring Boot 3.4.1 (Data JPA, Security, Web, JDBC)
* **Baza danych:** H2 Database (In-Memory)
* **Budowanie projektu:** Maven
* **Testy:** JUnit 5, Mockito, AssertJ

## Instrukcja Uruchomienia

1. Pobranie projektu i budowanie (wymaga zainstalowanego JDK 17+):
   W głównym katalogu projektu należy uruchomić polecenie, które pobierze zależności i uruchomi wszystkie testy:

   ```bash
   ./mvnw clean install
