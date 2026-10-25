# GameLluvia 2024

Proyecto completo corregido de LibGDX, con los módulos `core` y `lwjgl3`.
El código coincide con el Anexo A del informe GM 1 a GM 9 corregido.

## Abrir y ejecutar

1. Clonar este repositorio o descargar su ZIP y abrir la raíz del proyecto.
2. Usar un JDK; se recomienda JDK 17. El código del juego es compatible con Java 11.
3. Tener conexión a Internet para que Gradle descargue sus dependencias en el primer inicio.
4. Ejecutar desde la carpeta que contiene `gradlew.bat` o `gradlew`.

Windows:

```bat
gradlew.bat core:compileJava lwjgl3:compileJava
gradlew.bat lwjgl3:run
```

Linux y macOS:

```sh
./gradlew core:compileJava lwjgl3:compileJava
./gradlew lwjgl3:run
```

También se puede importar como proyecto Gradle en Eclipse o IntelliJ IDEA.

## Controles

| Tecla | Acción |
| --- | --- |
| Flechas izquierda y derecha | Mover el tarro. |
| 1 | Cambiar a caída recta. |
| 2 | Cambiar a caída acelerada. |
| 3 | Cambiar a zigzag. |
| ENTER al terminar | Reiniciar la partida. |

Las teclas 1, 2 y 3 cambian la estrategia de las gotas que ya existen y de las nuevas.
La gota conserva su identidad, posición y efecto al cambiar de movimiento.
Cada gota tiene su propia estrategia para no compartir la velocidad o dirección interna.

## Cambios incluidos

- Strategy reemplazable durante la ejecución mediante `cambiarEstrategia`.
- Tres tipos de gotas: buena (+10), mala (-1 vida) y dorada (+50).
- Estado FIN y reinicio con ENTER.
- Método plantilla en `ObjetoCaida.actualizar`.
- Singleton `GestorRecursos` con propiedad de texturas y audio.
- `TarroBuilder` con validación.
- Generación ponderada por `FabricaGotasPonderada` mediante registros.
- Interfaces pequeñas e inyección de dependencias en los objetos del juego.
- Actualización del daño separada del dibujo y duración de 0,8 segundos.
- Ventana fija de 800 x 480 y recorrido seguro al eliminar gotas.

## Estructura

- `core/src/main/java/puppy/code`: 18 tipos de nivel superior del núcleo.
- `lwjgl3/src/main/java/puppy/code/lwjgl3`: lanzador y StartupHelper.
- `assets`: imágenes y sonidos originales, más `dropGold.png` conservada del aporte del equipo.
- `documentacion/UML_completo.png`: diagrama de los 21 tipos propios conectados.
- `documentacion/UML_completo.dot`: fuente editable del diagrama en formato Graphviz.

La gota dorada reutiliza `drop.png` con el tinte `Color.GOLD`; no requiere otro archivo de imagen.
StartupHelper conserva su implementación y sus avisos de licencia originales.
Los archivos de Gradle y la configuración de Eclipse se conservan del proyecto suministrado.

## Verificación

Los 20 archivos Java del proyecto se compilaron con las bibliotecas reales de LibGDX 1.12.1 y LWJGL, con destino Java 11.

Se superaron 20 comprobaciones de lógica, incluyendo cambio de estrategia sobre la misma
gota, modo de gotas futuras, instancias independientes, puntajes, daño, reinicio,
pesos de generación y validación del Builder. Estas comprobaciones usan dobles para
los servicios de gráficos y audio y no sustituyen una prueba visual de la ventana.

La ejecución gráfica y sonora completa no se ha validado en este entorno.
La comprobación con Gradle no pudo completarse aquí porque su proceso no pudo descargar
la distribución a través de la red. Los archivos Java sí se compilaron de forma directa
con las dependencias reales.

## Integración del equipo

Las contribuciones se integran en las carpetas de la raíz. Los estados anteriores permanecen en el historial de Git.
