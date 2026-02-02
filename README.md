# Aplicación de Gestión de Alumnos con Código QR

## Descripción

Aplicación Android nativa desarrollada en Kotlin que permite la gestión integral de registros de alumnos mediante códigos QR. La aplicación integra funcionalidad de lectura de códigos QR, gestión de base de datos local con SQLite y sincronización en tiempo real con Firebase Realtime Database.

Esta solución está diseñada para entornos educativos y administrativos que requieran un sistema confiable para el registro y seguimiento de estudiantes, permitiendo operaciones offline con sincronización automática cuando la conectividad está disponible.

## Características Principales

- **Escaneo de Códigos QR**: Captura e interpretación de códigos QR mediante la cámara del dispositivo
- **Gestión de Registros**: Crear, modificar, consultar y eliminar registros de alumnos
- **Almacenamiento Local**: Base de datos SQLite para persistencia de datos offline
- **Sincronización en la Nube**: Integración con Firebase Realtime Database para respaldos y acceso remoto
- **Interfaz Intuitiva**: Navegación mediante fragments con barra de navegación inferior
- **Sincronización Inteligente**: Sistema de estado que rastrea cambios pendientes y los sincroniza automáticamente
- **Datos Enriquecidos**: Captura de fotografía, matrícula, nombre, domicilio y especialidad del alumno

## Requisitos del Sistema

### Hardware
- Dispositivo Android con mínimo SDK 24 (Android 7.0)
- Cámara integrada para escaneo QR
- Mínimo 200 MB de almacenamiento disponible

### Software
- Android Studio 2024.1 o superior
- Gradle 8.13.1
- JDK 11 o superior
- Conexión a Internet para configuración inicial y sincronización

## Tecnologías Utilizadas

- **Lenguaje**: Kotlin 2.0.21
- **Framework**: Android Jetpack
- **Base de Datos Local**: SQLite
- **Base de Datos Remota**: Firebase Realtime Database
- **Autenticación**: Google Firebase Services
- **Componentes UI**: Material Design 3
- **Escaneo QR**: ZXing Library
- **Carga de Imágenes**: Glide 4.16.0

## Instalación

### Configuración Inicial

1. **Clonar el repositorio**
   ```bash
   git clone https://github.com/abramendef/appAlumnos.git
   cd Appunidad02
   ```

2. **Descargar dependencias**
   ```bash
   ./gradlew build
   ```

3. **Configurar Firebase**
   - Crear un proyecto en [Firebase Console](https://console.firebase.google.com)
   - Descargar el archivo `google-services.json`
   - Colocar el archivo en el directorio `app/`
   - **Nota**: Este archivo está excluido de Git por seguridad

4. **Compilar y ejecutar**
   ```bash
   ./gradlew run
   ```
   O importar el proyecto en Android Studio y ejecutar desde el IDE

## Estructura del Proyecto

```
Appunidad02/
├── app/
│   ├── src/
│   │   └── main/
│   │       ├── java/com/example/appunidad02/
│   │       │   ├── MainActivity.kt              # Actividad principal
│   │       │   ├── InicioFragment.kt            # Pantalla de inicio y escaneo QR
│   │       │   ├── AlumnosFragment.kt           # Gestión de alumnos
│   │       │   ├── ListaFragment.kt             # Listado de alumnos
│   │       │   ├── AcercaFragment.kt            # Información de la app
│   │       │   ├── SalirFragment.kt             # Opción de salida
│   │       │   ├── QrUtils.kt                   # Utilidades para códigos QR
│   │       │   ├── DbAdapter.kt                 # Adaptador de base de datos
│   │       │   └── database/
│   │       │       ├── Alumno.kt                # Modelo de datos
│   │       │       ├── AlumnoDB.kt              # Operaciones SQLite
│   │       │       ├── AlumnoDbHelper.kt        # Helper de SQLite
│   │       │       ├── DefinirTabla.kt          # Esquema de tablas
│   │       │       ├── FirebaseAlumnosRepo.kt   # Repositorio Firebase
│   │       │       ├── SyncUtil.kt              # Utilidades de sincronización
│   │       │       └── SyncAlumnosWorker.kt     # Worker para sincronización automática
│   │       └── res/                             # Recursos (layouts, strings, etc.)
│   ├── build.gradle.kts                         # Configuración de construcción
│   └── google-services.json                     # Configuración Firebase (ignorado en Git)
├── gradle/
│   └── libs.versions.toml                       # Definición de dependencias
├── build.gradle.kts
├── settings.gradle.kts
└── README.md
```

## Uso de la Aplicación

### Flujo Principal

1. **Inicio**: Acceder a la pantalla de inicio mediante la pestaña "Inicio"
2. **Escaneo QR**: Presionar el botón "Escanear" para capturar un código QR
3. **Gestión de Alumnos**: 
   - Crear nuevo alumno con formulario completo
   - Modificar datos existentes
   - Eliminar registros (marcado como borrado lógico)
4. **Visualización**: Consultar listado completo de alumnos en la pestaña "Lista"
5. **Sincronización**: Los cambios se sincronizan automáticamente con Firebase cuando hay conectividad

### Gestión de Fragmentos

La aplicación utiliza navigation fragments para cambiar entre pantallas:

- **InicioFragment**: Punto de entrada, opción para escanear QR
- **AlumnosFragment**: Formulario para CRUD de alumnos
- **ListaFragment**: Visualización de todos los registros
- **AcercaFragment**: Información de la aplicación
- **SalirFragment**: Confirmación y salida de la aplicación

## Arquitectura de Datos

### Modelo de Alumno

Cada registro de alumno contiene:

```kotlin
data class Alumno(
    var id: Int,              // Identificador único
    var matricula: String,    // Matrícula del alumno
    var nombre: String,       // Nombre completo
    var domicilio: String,    // Dirección de residencia
    var especialidad: String, // Campo de especialización
    var foto: String,         // URL o ruta de fotografía
    var syncState: Int,       // Estado de sincronización (0=OK, 1=pendiente, 2=borrar)
    var updatedAt: Long,      // Timestamp de última modificación
    var deleted: Int          // Bandera de borrado lógico
)
```

### Sistema de Sincronización

El sistema implementa un mecanismo robusto de sincronización:

- **syncState = 0**: Registro sincronizado correctamente
- **syncState = 1**: Cambios pendientes de subir a Firebase
- **syncState = 2**: Registro pendiente de eliminación en Firebase
- **Borrado Lógico**: Los registros no se eliminan físicamente, se marcan como eliminados

Cuando hay conectividad, el `SyncAlumnosWorker` sincroniza automáticamente los cambios pendientes.

## Configuración de Firebase

### Estructura en Firebase

```
alumnos/
├── {alumno_id}/
│   ├── id: integer
│   ├── matricula: string
│   ├── nombre: string
│   ├── domicilio: string
│   ├── especialidad: string
│   ├── foto: string
│   ├── syncState: integer
│   ├── updatedAt: long
│   └── deleted: integer
```

### Reglas de Seguridad Recomendadas

```json
{
  "rules": {
    "alumnos": {
      ".read": true,
      ".write": true,
      ".indexOn": ["matricula", "syncState", "deleted"]
    }
  }
}
```

**Nota**: Ajustar según políticas de seguridad de su institución.

## Desarrollo y Contribución

### Estándares de Código

- Utilizar nomenclatura en camelCase para variables y funciones
- Nombres de clases en PascalCase
- Comentarios en español para claridad
- Mantener separación de responsabilidades entre capas

### Compilación

```bash
# Compilación debug
./gradlew assembleDebug

# Compilación release
./gradlew assembleRelease

# Ejecución de pruebas
./gradlew test
```

## Consideraciones de Seguridad

- El archivo `google-services.json` está excluido del control de versiones
- Las credenciales de Firebase no deben ser expuestas en el repositorio
- Se recomienda implementar autenticación en Firebase en producción
- Utilizar HTTPS para todas las comunicaciones

## Dependencias Principales

- androidx-core-ktx: 1.17.0
- androidx-appcompat: 1.7.1
- material: 1.13.0
- firebase-database: 22.0.1
- glide: 4.16.0
- zxing-android-embedded: (incluida vía integrator)

Para la lista completa, consultar `gradle/libs.versions.toml`

## Limitaciones y Mejoras Futuras

### Limitaciones Actuales

- Requiere permisos de cámara e Internet
- No implementa autenticación de usuarios
- Base de datos local no cifrada

### Mejoras Propuestas

- Implementar autenticación con Firebase Authentication
- Cifrado de base de datos local SQLite
- Exportación de reportes en PDF/Excel
- Interfaz mejorada con Material Design 3
- Soporte para múltiples idiomas
- Validación avanzada de datos

## Troubleshooting

### La aplicación no se conecta a Firebase

1. Verificar que el archivo `google-services.json` está en `app/`
2. Confirmar que el proyecto ID coincide
3. Revisar que la red tiene acceso a Internet

### El escaneo de QR no funciona

1. Verificar que el permiso de cámara está otorgado
2. Asegurar que la cámara no está siendo utilizada por otra aplicación
3. Verificar que el código QR es válido

### Sincronización no completada

1. Verificar conexión a Internet
2. Revisar los logs de Firebase
3. Confirmar que los registros tienen `syncState` válido

## Autores y Créditos

Desarrollado como proyecto de gestión de alumnos para institución educativa.

## Licencia

Este proyecto está bajo licencia MIT. Ver detalles en LICENSE.

## Contacto y Soporte

Para reportar problemas o sugerencias, abrir un issue en el repositorio de GitHub o contactar al equipo de desarrollo.

---

Última actualización: Febrero 2026
