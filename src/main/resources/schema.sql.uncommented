-- -----------------------------------------------------------------------------
-- Táblák törlése fordított sorrendben (idegen kulcsok miatt)
-- Ez biztosítja a tiszta lapot minden indításnál
-- -----------------------------------------------------------------------------

DROP TABLE IF EXISTS ELADAS;
DROP TABLE IF EXISTS ARU;
DROP TABLE IF EXISTS KATEGORIA;
DROP TABLE IF EXISTS messages;
DROP TABLE IF EXISTS users;

-- -----------------------------------------------------------------------------
-- Táblák létrehozása
-- -----------------------------------------------------------------------------

CREATE TABLE users (
    id          INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    role        ENUM('ROLE_ADMIN', 'ROLE_USER') NOT NULL DEFAULT 'ROLE_USER',
    username    VARCHAR(255) UNIQUE NOT NULL,
    email       VARCHAR(255) UNIQUE NOT NULL,
    password    VARCHAR(255) NOT NULL,  -- SHA512 hash 128 karakter hosszú (Bcrypt/Argon2 támogatás miatt 255)
    created_at  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE messages (
    id          INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    user_id     INT UNSIGNED NULL,
    name        VARCHAR(255) NOT NULL,  -- Vendégeknek is kötelező megadni
    email       VARCHAR(255) NOT NULL,  -- Vendégeknek is kötelező megadni
    phone       VARCHAR(20) NULL,
    subject     VARCHAR(255) NOT NULL,
    message     TEXT NOT NULL,
    created_at  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,

    FOREIGN KEY (user_id) REFERENCES users(id)
        ON UPDATE CASCADE   -- Ha users(id) megváltozik, frissüljön
        ON DELETE SET NULL  -- Ha egy felhasználó törli a fiókját, az üzenetei megmaradnak, de anonimizálódik
);

CREATE TABLE KATEGORIA (
    kat_kod     INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    kat_nev     VARCHAR(255) NOT NULL,
    created_at  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE ARU (
    aru_kod     INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    kat_kod     INT UNSIGNED NOT NULL,
    nev         VARCHAR(255) NOT NULL,
    egyseg      ENUM('darab', 'doboz', 'kg', 'liter') NOT NULL,
    ar          DECIMAL(10,2) UNSIGNED NOT NULL,  -- Ha forintban + fillérekben lenne az ár
    created_at  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,

    FOREIGN KEY (kat_kod) REFERENCES KATEGORIA(kat_kod)
        ON UPDATE CASCADE   -- Ha KATEGORIA(kat_kod) megváltozik, frissüljön
        ON DELETE RESTRICT  -- Megakadályozza egy KATEGORIA törlését, amíg van legalább egy termék
);

CREATE TABLE ELADAS (
    aru_kod     INT UNSIGNED PRIMARY KEY,
    mennyiseg   DECIMAL(10,2) UNSIGNED NOT NULL,  -- mennyiség lehet tizedestört is (pl. kg-nál)
    created_at  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,

    FOREIGN KEY (aru_kod) REFERENCES ARU(aru_kod)
        ON UPDATE CASCADE   -- Ha ARU(aru_kod) megváltozik, frissüljön
        ON DELETE CASCADE   -- Ha ARU törlődik, az ELADAS rekord is törlődjön
);

-- -----------------------------------------------------------------------------
-- Indexek
-- -----------------------------------------------------------------------------

-- Indexek az idegen kulcsokra
-- ELADAS.aru_kod már indexelt, mert elsődleges kulcs
CREATE INDEX idx_messages_user_id ON messages(user_id);
CREATE INDEX idx_aru_kat_kod ON ARU(kat_kod);

-- Kiegészítő indexek teljesítményre
CREATE INDEX idx_aru_ar ON ARU(ar);  -- Ár szerinti szűréshez
CREATE INDEX idx_aru_nev ON ARU(nev);  -- Név szerinti szűréshez
CREATE INDEX idx_messages_created_at ON messages(created_at);  -- Üzenet dátuma szerinti szűréshez
