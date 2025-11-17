# JAVA gyakorlat beadandó

**Választott téma weboldalai:**
- https://themewagon.com/themes/organic/
- https://templatesjungle.com/downloads/organic-fruit-and-vegetable-store-bootstrap-5-html-css-template/
- Aloldalak kódja is elérhető itt: **https://demo.templatesjungle.com/organic/index.html**

**Választott adatbázis:**
- 2-01-Forgalom-3 táblás

## Kliens oldali JS build legenerálása

```sh
# Node.js csomagfüggőségek telepítése
npm install
# Futtatás
npm run build:client
```

## Adatbázis információk

Lokálisan XAMPP MariaDB helyett lehetséges Docker használata is kényelmi szempontból:

```sh
# Docker Compose projekt futtatása
docker compose up -d

# Docker Compose projekt leállítása
docker compose down
```

Adatbázis menedzseléshez PHPMyAdmin mellett javasolt még a [DBeaver](https://dbeaver.io/download/) is.

## Git workflow

Mivel Gitlab-al ellentétben Github-on a Collaborator-oknak is hozzáférése van a master branch-hez, ezért a fejlesztés javasolt saját névreszóló branch-ben, majd a kód átemelése master-be.

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
