# Ejercicio 5 · Refactoriza el GestorPartida

Arquitectura de Software · Unidad II · ITSON
Individual · Entrega sugerida: semana 4

Este proyecto es un Parchís para dos jugadores, rojo y azul, en una sola computadora. Funciona, pero casi todo vive en una clase de unas 600 líneas, `GestorPartida`, que:

- dibuja el tablero en un `JPanel`;
- valida las reglas de movimiento;
- envía cada jugada por un `Socket` a la computadora del rival;
- guarda las estadísticas con SQL en MySQL;
- escribe los colores como texto en doce lugares;
- usa `JugadorRojo` y `JugadorAzul`, que heredan de `Jugador`.

No lo tomes como modelo: es el punto de partida del ejercicio.

## Qué hacer

Refactoriza el código aplicando los cuatro principios de la sección:

- una sola responsabilidad;
- no te repitas (DRY);
- composición sobre herencia;
- diseñar orientado a la interfaz.

El juego debe seguir funcionando igual que antes del cambio.

## Qué se entrega

1. El diagrama de clases de antes y el de después, en StarUML, indicando qué principio aplica cada cambio.
2. El código de al menos dos de las clases resultantes, en tu repositorio.

## Cómo correrlo

1. En NetBeans: **File › Open Project** y elige esta carpeta.
2. **Run**. Se abre el tablero; rojo y azul juegan por turnos en la misma ventana.

Reglas:

- Con 5 se saca una ficha de la base.
- Si una ficha cae en una ficha rival, la rival regresa a su base.
- Con 6 se vuelve a tirar, máximo tres veces seguidas.
- Para entrar a la meta se necesita el número exacto.
- Gana quien lleva primero sus cuatro fichas a la meta.

## La red y la base de datos (opcional)

El juego funciona sin red y sin MySQL: lo anota en la bitácora y sigue.

- **Red.** Cada jugada se envía a `localhost:5000`. Para ver cómo llegan, corre una segunda copia en modo espectador: en NetBeans, **Properties › Run › Arguments** con el valor `espectador`, y luego **Run**. Las jugadas aparecen en la ventana Output.
- **MySQL.** Con un servidor en `localhost:3306`, la base `parchis` y la tabla `estadisticas` se crean solas. Ajusta `URL_BD`, `USUARIO_BD` y `CLAVE_BD` al inicio de `EstadisticasMySql` con los datos de tu servidor.

## Refactorización realizada
- **SRP:** las reglas pasaron a `ReglasPartida`, la red a `SocketCanalComunicacion` y las estadísticas a `EstadisticasMySql`. El dibujo del tablero sigue en `GestorPartida`.
- **DRY:** los colores como texto se reemplazaron por el enum `ColorJugador`.
- **Composición sobre herencia:** `JugadorRojo` y `JugadorAzul` se eliminaron; `Jugador` recibe su `ColorJugador`.
- **Orientación a la interfaz:** `GestorPartida` depende de `CanalComunicacion` y `RepositorioEstadisticas`.

Diagramas de clases (antes y después) en la carpeta `docs/`.
