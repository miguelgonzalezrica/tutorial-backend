INSERT INTO category(name)
VALUES 
  ('Eurogames'),
  ('Ameritrash'),
  ('Familiar');

INSERT INTO author(name, nationality)
VALUES
    ('Alan R. Moon', 'US'),
    ('Vital Lacerda', 'PT'),
    ('Simone Luciani', 'IT'),
    ('Perepau Llistosella', 'ES'),
    ('Michael Kiesling', 'DE'),
    ('Phil Walker-Harding', 'US');

INSERT INTO game(title, age, category_id, author_id)
VALUES
    ('On Mars', '14', 1, 2),
    ('Aventureros al tren', '8', 3, 1),
    ('1920: Wall Street', '12', 1, 4),
    ('Barrage', '14', 1, 3),
    ('Los viajes de Marco Polo', '12', 1, 3),
    ('Azul', '8', 3, 5);

INSERT INTO client(name)
VALUES
    ('Miguel Pires'),
    ('Marcos Platero'),
    ('Daniel Deltoya'),
    ('Francisco Juárez'),
    ('Pablo Otamendi'),
    ('Marta Alcalá');

INSERT INTO loan(game_id, client_id, loan_date, return_date)
VALUES
    (1, 1, '2026-06-05', '2026-06-10'),
    (2, 1, '2026-06-09', '2026-06-15'),
    (3, 5, '2026-06-10', '2026-06-20'),
    (6, 6, '2026-07-10', '2026-07-20'),
    (4, 3, '2026-06-10', '2026-06-12'),
    (4, 3, '2026-06-13', '2026-06-15'),
    (6, 2, '2026-06-18', '2026-06-28');


