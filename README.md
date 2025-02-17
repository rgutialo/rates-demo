## Ejercicio

En la base de datos de comercio electrónico de la compañía disponemos de la tabla PRICES que refleja el precio final (pvp) y la tarifa que aplica a un producto de una cadena entre unas fechas determinadas. A continuación se muestra un ejemplo de la tabla con los campos relevantes:

| BRAND_ID   | START_DATE          | END_DATE            | PRODUCT_ID | PRICE_LIST | PRIORITY | PRICE | CURR |
|------------|---------------------|---------------------|------------|------------|----------|-------|------|
| 1          | 2020-06-14 00:00:00 | 2020-12-31 23:59:59 | 35455      | 1          | 0        | 35.50 | EUR  |
| 1          | 2020-06-14 15:00:00 | 2020-06-14 18:30:00 | 35455      | 2          | 1        | 25.45 | EUR  |
| 1          | 2020-06-15 00:00:00 | 2020-06-15 11:00:00 | 33455      | 3          | 1        | 30.50 | EUR  |
| 1          | 2020-06-15 16:00:00 | 2020-12-31 23:59:59 | 33455      | 4          | 1        | 38.95 | EUR  |
|------------|---------------------|---------------------|------------|------------|----------|-------|------|

Campos:

- BRAND_ID: foreign key de la cadena del grupo (1 = ZARA). 
- START_DATE , END_DATE: rango de fechas en el que aplica el precio tarifa indicado. 
- PRICE_LIST: Identificador de la tarifa de precios aplicable. 
- PRODUCT_ID: Identificador código de producto. 
- PRIORITY: Desambiguador de aplicación de precios. Si dos tarifas coinciden en un rago de fechas se aplica la de mayor prioridad (mayor valor numérico). 
- PRICE: precio final de venta. 
- CURR: iso de la moneda.

Se pide:

Construir una aplicación/servicio en SpringBoot que provea una end point rest de consulta  tal que:

Acepte como parámetros de entrada: fecha de aplicación, identificador de producto, identificador de cadena.
Devuelva como datos de salida: identificador de producto, identificador de cadena, tarifa a aplicar, fechas de aplicación y precio final a aplicar.

Se debe utilizar una base de datos en memoria (tipo h2) e inicializar con los datos del ejemplo, (se pueden cambiar el nombre de los campos y añadir otros nuevos si se quiere, elegir el tipo de dato que se considere adecuado para los mismos).

Desarrollar unos test al endpoint rest que  validen las siguientes peticiones al servicio con los datos del ejemplo:

- Test 1: petición a las 10:00 del día 14 del producto 35455   para la brand 1 (ZARA)
- Test 2: petición a las 16:00 del día 14 del producto 35455   para la brand 1 (ZARA)
- Test 3: petición a las 21:00 del día 14 del producto 35455   para la brand 1 (ZARA)
- Test 4: petición a las 10:00 del día 15 del producto 35455   para la brand 1 (ZARA)
- Test 5: petición a las 21:00 del día 16 del producto 35455   para la brand 1 (ZARA)

## Solución

Esta aplicación se desarrolló siguiendo la arquitectura hexagonal. La arquitectura hexagonal,
o el patrón de puertos y adaptadores, es ideal para este proyecto en Spring Boot porque garantiza una separación clara entre
la lógica empresarial principal y las dependencias externas como las API, las bases de datos o las interfaces de usuario.
Esta disociación hace que la aplicación sea más fácil de mantener, probar y flexible, ya que se puede reemplazar por otros frameworks
o modificar sistemas externos (base de datos) sin afectar la lógica principal.
Se alinea bien con la modularidad y la inyección de dependencias de Spring Boot, lo que facilita la creación de componentes
limpios, reutilizables e independientes.

Por otro lado, se ha definido un metodo CUSTOM sobre la interfaz de JPA para poder buscar el precio de un producto en una fecha determinada.
Ello se ha basado en uns custom query que devuelve el precio de un producto en una fecha determinada, teniendo en cuenta su prioridad

Finalmente, para la parte de testing, aparte de crear un script sql con los inserts necesarios, se ha definido un conjunto
de pruebas para validad que efectivamente, dependiendo de los parámetros de entrada recibidos, se devuelve el precio de la tarifa
requerida.
