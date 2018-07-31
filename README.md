# aloes
Aloes - gabinet odnowy


ADMIN

Login:admin ,pPassword:admin;

USER

Login:user, password:user;

http://localhost:8080/login - strona do logowania.

http://localhost:8080/ - strona główna z informacjami o urzytkowniku i wizyt z klientami w dniu dzisiejszym.

http://localhost:8080/clients - lista klientów z wyszukiwarką,do wyboru szczegóły klienta,edytowanie klienta.Dla urzytkowników z  rolą admia możliwość usuwania klientów.

http://localhost:8080/client/1 -informacje o urzytkowniku i jego wizytach z możliwoscia usunięcia wizyt(dostepn tylko dla adminów) lub dodanie nowej wizyty.

http://localhost:8080/formVisit/1 -dodawanie wizyty dla konkretnego klienta,z możliwością dodania masażu za pomocą wyszukiwarki(np.massage head).Jeśli wizyta z taką samą datą i godziną jest już w bazie to wyskoczy komunikat z błędem.

http://localhost:8080/visit/1 - informacje o wizycie ,możliwość podsumowania w formie pliku PDF i wysłania Email'a z powiadomieniem (tylko raz). Możliwość zmiany statusu wizyty(np.close-wizyta zakończona).

http://localhost:8080/visitsClose - lista wizyt zakończonych.

http://localhost:8080/visitsOpen - lista wizyt otwartych.

http://localhost:8080/massages/ - lista masaży z mozliwością edycji,szczegóły masażu i usuwanie tylko dla adminów.Jeśli masaż jest juz w jakiejś  wizycie to przy próbie usunięcia wyskakuje błąd.

http://localhost:8080/formMassage - nowy masaż.

http://localhost:8080/users/ - lista urzytkowników (stona dostępna tylko dla urzytkowników z  rolą admia).

http://localhost:8080/formUser/ - dodawanie nowego urzytkowanika z możliwością wyboru roli(Admin,User)(stona dostępna tylko dla urzytkowników z  rolą admia).

http://localhost:8080/statistic/ - statystyki.

REST FULL API:
http://localhost:8080/api/client - (method GET) -lista klientów w formacie JSON.

http://localhost:8080/api/client - (metehod POST) -zapis nowego  klienta.

http://localhost:8080/api/client/1 -(method GET) - informacje szczegółowe klienta w formacie JSON.

http://localhost:8080/api/client/1 -(method DELETE) - usunięcie  klienta.

