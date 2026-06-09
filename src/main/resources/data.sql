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
    ('Pablo Otamendi ');

INSERT INTO loan(game_name, client_name, loan_date, return_date)
VALUES
    ('On Mars', 'Miguel Pires', '2026-06-05', '2026-06-10'),
    ('Aventureros al tren', 'Miguel Pires', '2026-06-09', '2026-06-15'),
    ('1920: Wall Street', 'Pablo Otamendi', '2026-06-10', '2026-06-20'),
    ('Azul', 'Marcos Platero', '2026-06-18', '2026-06-28');


