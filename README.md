# ENTREGA FINAL DEL PROYECTO

Enrique Padrón Hernández
 & Nereida Mª Pérez Mena

 ## Descripción

El programa es una implementación de una terminal financiera básica, mostrando información útil para un inversor de comodidades:

1. Datos para un análisis fundamental del precio (media, % de cambio, rango entre el mayor y el menor valor registrado)
2. Herramientas para análisis técnico (gráficas con capacidad para definir el rango temporal deseado)
3. Acceso a las últimas noticias relevantes para el sector.

El programa tiene accesso a datos en tiempo real sobre los precios del petróleo crudo Brent y del petróleo crudo West Texas Intermediate (WTI), así como las noticias acerca del petróleo y otros componentes relacionados a estos.

Por medio de esta aplicación podemos observar y analizar a tiempo real la situación del petróleo crudo así como observar las repercusiones públicas de esta. Hemos tomado como referencias el petróleo Brent y el WTI debido a que el petróleo Brent es el referente para los mercados europeos, el petróleo WTI se utiliza como punto de referencia en la fijación de precios del petróleo.

## APIs y diseño del Datamart

Las APIs utilizadas son NewsAPI y AlphaVantage, APIs públicas que ofrecen información a tiempo real de la información necesaria para nuestro programa. 

NewsAPI ofrece artículos de distintas fuentes de información globales de cualquier tema de interés que podemos filtrar de cualquier forma que consideremos apropiada para obtener los artículos que queramos. 

AlphaAdvantage ofrece información del mercado de valores de distintos elementos obtenida a través de distintas fuentes de confianza.

## Instrucciones de Ejecución

1. Crear un broker de ActiveMQ en la máquina en la que se esté ejecutando.
3. Ejecutar event-store-builder para inicializar suscriptores.
4. Introducir keys en los argumentos de arranque de los feeders.
5. Ejecutar feeders.
6. Ejecutar unidad de negocio.

Así se asegura que se creen los ficheros de eventstore para que la unidad de negocio pueda acceder a ellos. A partir de ahí, probar las funcionalidades del programa con la interfaz CLI.

La primera ejecución debería tardar alrededor de un minuto en completarse.

## Ejemplos de Uso

Podemos utilizar la aplicación para:
1. Obtener la gráfica con la tendencia durante el periodo de tiempo que le interese del precio del petróleo Brent.
2. Obtener la gráfica con la tendencia durante el periodo de tiempo que quiera estudiar del precio del petróleo WTI.
3. Obtener la tabla de las noticias más recientes relacionadas con el petróleo.
4. Obtener un informe financiero con los datos del petróleo Brent y WTI por separado.

## Arquitectura de sistema y arquitectura de la aplicación:

Nuestro programa lo conforma un feeder para obtener los precios del petróleo Brent y WTI, otro feeder para obener las noticias, el constructor para almacenar los eventos, y la unidad de negocio que permite obsevar los datos a través de informes, gráficas y tablas. A continuación se presentan los diagramas de clases para mayor explicación del funcionamiento del programa.  

![Imagen de WhatsApp 2025-05-18 a las 21 46 27_b8c43b1c](https://github.com/user-attachments/assets/24083d3b-e7d8-4e22-b139-1d62c7bdd2c3)

![Imagen de WhatsApp 2025-05-18 a las 21 47 05_66f7c7ab](https://github.com/user-attachments/assets/0a20947c-e972-480c-b99b-dc19ccfb318f)

![Imagen de WhatsApp 2025-05-18 a las 21 50 57_94d6f60b](https://github.com/user-attachments/assets/f0bcca03-5f49-448e-b5c8-072d43f8b914)

![Imagen de WhatsApp 2025-05-18 a las 21 51 46_ffb85c91](https://github.com/user-attachments/assets/dbcdbed4-50af-40b7-9594-3567853fe212)

![Imagen de WhatsApp 2025-05-18 a las 21 52 22_16c6ab79](https://github.com/user-attachments/assets/bb9404ae-33e7-4770-a8f4-66341781c625)



## Principios y patrones de diseño aplicados

Se han aplicado principios de arquitectura hexagonal para todos los módulos, facilitando así el control de flujo de los datos.
