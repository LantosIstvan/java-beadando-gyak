# JAVA gyakorlat beadandó

**Választott téma weboldalai:**
- https://themewagon.com/themes/organic/
- https://templatesjungle.com/downloads/organic-fruit-and-vegetable-store-bootstrap-5-html-css-template/
- Aloldalak kódja is elérhető itt: **https://demo.templatesjungle.com/organic/index.html**

**Választott adatbázis:**
- 2-01-Forgalom-3 táblás

## Kliens oldali JS build legenerálása

```txt
Maven -> Lifecycle -> clean
Maven -> Lifecycle -> compile
```

## Végleges WAR állomány elkészítése

```txt
Maven -> Lifecycle -> package
```

## Adatbázis információk

Lokális fejlesztés során ezen adatbázis adatok használata szükséges, XAMPP-ot eszerint kell bekonfigurálni:

- **HOST:** localhost
- **PORT:** 3306
- **DATABASE:** db111
- **USER:** studb111
- **PASSWORD:** abc123

Ezen elérési adatok HOST kivételével tükrözik a majdani éles adatbázis adatait.

Lokálisan XAMPP MariaDB helyett lehetséges még Docker használata is kényelmi szempontból, az ehhez szükséges Docker Compose projekt mellékelve van a gyökérkönyvtárban:

```sh
# Docker Compose projekt futtatása
docker compose up -d

# Docker Compose projekt leállítása
docker compose down
```

Adatbázis menedzseléshez PHPMyAdmin mellett javasolt még a [DBeaver](https://dbeaver.io/download/) is.

## Git workflow

Minden kollaborátor számára a fejlesztés javasolt saját névreszóló branch-ben, majd a kód átemelése master-be.

```sh
git branch bencze

git fetch origin

git checkout bencze
git merge origin/master
# Branch létrehozásakor
git push --set-upstream origin bencze
# Sima push-nal
git push
```
