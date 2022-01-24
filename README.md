# Sportway

### Contenido
* [Introduccion](#introduccion)
* [Ejecucion](#ejecucion)
* [Tests](#tests)


### Introducción
Sportway es una aplicación móvil nativa que permite visualizar información detallada de distintos partidos que se hayan jugado, estén jugando o incluso que se vayan a jugar, así como también, los equipos de fútbol disponibles.
Toda la información es obtenida de distintos servicios proporcionados por una API. 

### Ejecución
Para compilar y ejecutar la aplicación, se debe tener instalado Android Studio.
1. Abrir el proyecto
2. Asegurarse de que el IDE esté utilizando Java 11 (requerimiento para Android Gradle)
    2.1. Para ello, vaya a las Preferencias -> Build, Execution, Deployment -> Build Tools -> Gradle -> Gradle JDK y seleccione el path donde se encuentra Java 11
3. En la barra de herramientas, selecciona la app y luego el dispositivo donde se correrá la misma.
4. Click en "Run"

Si solo se desea obtener el APK para poder instalarlo en cualquier dispositivo, debe dirigirse a Build -> Build Bundle(s) / APK(s) -> Build APK

## Tests

Se incluyen unos unit test por cada use case utilizado en la aplicación. Para correr los tests:
1. Abra uno de los archivos presentes dentro de la carpeta de src/test/domain/use_case.
2. Espere a que el IDE sincronice
3. Ejecute los tests clickeando en el icono de Run a la izquierda de la declaracion de la clase.