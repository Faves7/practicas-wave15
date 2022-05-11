/* 
1. Mostrar todos los registros de la tabla de movies.
*/
select * from movies_db.movies;


/* 
2. Mostrar el nombre, apellido y rating de todos los actores.
*/
select first_name, last_name, rating from movies_db.actors;

/*
3. Mostrar el título de todas las series y usar alias para que tanto el nombre de la tabla como el campo estén en español.
*/
select title as titulo from movies_db.series as serie;

/*
4. Mostrar el nombre y apellido de los actores cuyo rating sea mayor a 7.5.
*/
select first_name, last_name from movies_db.actors where rating > 7.5;

/*
5. Mostrar el título de las películas, el rating y los premios de las películas con un rating mayor a 7.5 y con más de dos premios.
*/
select title, rating, awards from movies_db.movies	where rating >7.5 and awards>2;

/*
6. Mostrar el título de las películas y el rating ordenadas por rating en forma ascendente.
*/
select title, rating from movies_db.movies order by rating asc;

/*
7. Mostrar los títulos de las primeras tres películas en la base de datos.
*/
select title from movies_db.movies limit 3;

/*
8. Mostrar el top 5 de las películas con mayor rating.
*/
select * from movies_db.movies order by rating desc limit 5;

/*
9. Listar los primeros 10 actores.
*/
select * from movies_db.actors limit 10;

/*
10. Mostrar el título y rating de todas las películas cuyo título sea de Toy Story.
*/
select title, rating from movies_db.movies where title = 'Toy Story';

/*
11. Mostrar a todos los actores cuyos nombres empiezan con Sam.
*/
select * from movies_db.actors where first_name like '%Sam';

/*
12. Mostrar el título de las películas que salieron entre el 2004 y 2008.
*/
select title from movies_db.movies where release_date BETWEEN '2004-01-01 00:00:00' AND '2008-12-12 23:59:59';

/*
13. Traer el título de las películas con el rating mayor a 3, con más de 1 premio y con fecha de lanzamiento entre el año 1988 al 2009. Ordenar los resultados por rating.
*/
select title from movies_db.movies 
where rating > 3 
and awards >1 
and release_date BETWEEN '1988-01-01 00:00:00' AND '2009-12-12 23:59:59'
order by rating;