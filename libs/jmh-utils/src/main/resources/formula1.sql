CREATE TABLE circuits
(
    circuit_id  INT PRIMARY KEY,
    circuit_ref VARCHAR(255),
    name        VARCHAR(255),
    location    VARCHAR(255),
    country     VARCHAR(255),
    lat         FLOAT,
    lng         FLOAT,
    alt         INT,
    url         VARCHAR(255)
);

CREATE TABLE constructor_results
(
    constructor_results_id INT PRIMARY KEY,
    race_id                INT,
    constructor_id         INT,
    points                 FLOAT,
    status                 VARCHAR(255)
);

CREATE TABLE constructor_standings
(
    constructor_standings_id INT PRIMARY KEY,
    race_id                  INT,
    constructor_id           INT,
    points                   FLOAT,
    position                 INT,
    position_text            VARCHAR(255),
    wins                     INT
);

CREATE TABLE constructors
(
    constructor_id  INT PRIMARY KEY,
    constructor_ref VARCHAR(255),
    name            VARCHAR(255),
    nationality     VARCHAR(255),
    url             VARCHAR(255)
);

CREATE TABLE driver_standings
(
    driver_standings_id INT PRIMARY KEY,
    race_id             INT,
    driver_id           INT,
    points              FLOAT,
    position            INT,
    position_text       VARCHAR(255),
    wins                INT
);

CREATE TABLE drivers
(
    driver_id    INT PRIMARY KEY,
    driver_ref   VARCHAR(255),
    number       INT,
    code         VARCHAR(255),
    forename     VARCHAR(255),
    surname      VARCHAR(255),
    dob          DATE,
    nationality  VARCHAR(255),
    url          VARCHAR(255)
);

CREATE TABLE lap_times
(
    race_id      INT,
    driver_id    INT,
    lap          INT,
    position     INT,
    time         VARCHAR(255),
    milliseconds INT,
    PRIMARY KEY (race_id, driver_id, lap)
);

CREATE TABLE pit_stops
(
    race_id      INT,
    driver_id    INT,
    stop         INT,
    lap          INT,
    time         VARCHAR(255),
    duration     VARCHAR(255),
    milliseconds INT,
    PRIMARY KEY (race_id, driver_id, stop)
);

CREATE TABLE qualifying
(
    qualify_id     INT PRIMARY KEY,
    race_id        INT,
    driver_id      INT,
    constructor_id INT,
    number         INT,
    position       INT,
    q1             VARCHAR(255),
    q2             VARCHAR(255),
    q3             VARCHAR(255)
);

CREATE TABLE races
(
    race_id    INT PRIMARY KEY,
    year       INT,
    round      INT,
    circuit_id INT,
    name       VARCHAR(255),
    date       DATE,
    time       VARCHAR(255),
    url        VARCHAR(255)
);

CREATE TABLE results
(
    result_id         INT PRIMARY KEY,
    race_id           INT,
    driver_id         INT,
    constructor_id    INT,
    number            INT,
    grid              INT,
    position          INT,
    position_text     VARCHAR(255),
    position_order    INT,
    points            FLOAT,
    laps              INT,
    time              VARCHAR(255),
    milliseconds      INT,
    fastest_lap       INT,
    rank              INT,
    fastest_lap_time  VARCHAR(255),
    fastest_lap_speed VARCHAR(255),
    status_id         INT
);

CREATE TABLE seasons
(
    year INT PRIMARY KEY,
    url  VARCHAR(255)
);

CREATE TABLE sprint_results
(
    result_id        INT PRIMARY KEY,
    race_id          INT,
    driver_id        INT,
    constructor_id   INT,
    number           INT,
    grid             INT,
    position         INT,
    position_text    VARCHAR(255),
    position_order   INT,
    points           FLOAT,
    laps             INT,
    time             VARCHAR(255),
    milliseconds     INT,
    fastest_lap      INT,
    fastest_lap_time VARCHAR(255),
    status_id        INT
);

CREATE TABLE status
(
    status_id INT PRIMARY KEY,
    status    VARCHAR(255)
);