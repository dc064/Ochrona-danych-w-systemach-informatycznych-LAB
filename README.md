Ochrona danych w systemach informatycznych LAB
Repozytorium zawiera projekty realizowane w ramach przedmiotu Ochrona danych w systemach informatycznych (5 semestr studiów).

1. Kryptografia historyczna

-Napisz program, który będzie łamać metodą brutalnej siły kryptogram zaszyfrowany RC4 (crypto.rc4). Załóż, że zaszyfrowaną treścią jest tekst w języku angielskim. Klucz składa się z trzech małych liter a-z (bez polskich znaków). Program musi wypisywać znalezione hasło. 

-Zaimplementuj szyfrowanie algorytmem RC4.

2. Szyfrowanie blokowe

-Zaszyfruj algorytmem AES, w trybach ECB i CBC obraz demo24.bmp. Porównaj entropię tak powstałych kryptogramów. 

-Dokonaj ataku brutalnej siły na kryptogram we800_CBC_encrypted_full.bmp Czy lepiej łamać hasło, czy powstały z niego klucz?

3. Funkcje hash

-Napisz program służący do pomiaru prędkości wyliczania następujących funkcji skrótu:

md5,

sha256,

bcrypt,

argon2.

Najlepiej wyznaczyć tę prędkość mierząc ile czasu zajęło wyliczenie wyników funkcji skrótu dla 1 000 - 1 000 000 haseł.

-Napisz program, który znajdzie dwa dowolne dokumenty (tj. pliki), które mają te same 5 pierwszych bajtów wyniku funkcji skrótu SHA256. Po zakończeniu poszukiwań proszę wypisać liczbę przetestowanych dokumentów oraz pierwsze 5 bajtów wyniku funkcji skrótu. Zwróć uwagę, że sha256(b"tresc dokumentu").hexdigest() zwraca bajty zakodowane szesnastkowo, po dwa znaki na bajt (od "00" do "ff"), więc pierwsze 5 bajtów jest zakodowane na pierwszych 10 znakach wyniku hexdigest().

-Napisz program łamiący metodą brutalnej siły wynik funkcji skrótu: $argon2id$v=19$m=65536,t=3,p=4$4Vzr3bvXWuvdmzMG4PxfCw$NWNunMWdo0ugkWWsL8Z+sdMKnDcJp0vDfMkr30Lmpd4 Załóż, że hasło składa się z dwóch małych liter. 
