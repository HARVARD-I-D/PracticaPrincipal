# ENTREGA FINAL DEL PROYECTO

Enrique Padrón Hernández
 & Nereida Mª Pérez Mena

 ## Descripción

El programa es una implementación de una terminal financiera básica, mostrando información útil para un inversor de comodidades:

1. Datos para un análisis fundamental del precio (media, % de cambio, rango entre el mayor y el menor valor registrado)
2. Herramientas para análisis técnico (gráficas con capacidad para definir el rango temporal deseado)
3. Acceso a las últimas noticias relevantes para el sector.

El programa tiene accesso a datos en tiempo real, y 

## APIs y diseño del Datamart

Las APIs utilizadas son NewsAPI y AlphaVantage, 

## Instrucciones de Ejecución

1. Crear un broker de ActiveMQ en la máquina en la que se esté ejecutando.
2. Ejecutar event-store-builder para inicializar suscriptores.
3. Introducir keys en los argumentos de arranque de los feeders.
4. Ejecutar feeders.
5. Ejecutar unidad de negocio.

Así se asegura que se creen los ficheros de eventstore para que la unidad de negocio pueda acceder a ellos. A partir de ahí, probar las funcionalidades del programa con la interfaz CLI.

La primera ejecución debería tardar alrededor de un minuto en completarse.

(Ejemplos de uso (consultas, peticiones REST, etc.))

(Arquitectura de sistema y arquitectura de la aplicación (con diagramas))

## Principios y patrones de diseño aplicados

Se han aplicado principios de arquitectura hexagonal para todos los módulos, facilitando así el control de flujo de los datos.
