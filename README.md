# DogApp

App Android nativa en Kotlin para administrar citas de una veterinaria de mascotas.
Proyecto del curso Desarrollo de Aplicaciones Móviles (Univalle).

## Arquitectura

MVVM + Repository, con XML + DataBinding (sin Jetpack Compose) y navegación con un único `NavHost`.

```
View (Fragment) → ViewModel → Repository → Room (local) / Retrofit (API dog.ceo)
```

## Requisitos

- Android Studio reciente (el proyecto usa AGP 9.3.3 y Gradle 9.5)
- JDK 17 o superior
- compileSdk 37, minSdk 24

## Cómo ejecutar

```bash
./gradlew assembleDebug
```

O abrir el proyecto en Android Studio y ejecutarlo en un emulador o dispositivo.
Se necesita conexión a internet para cargar razas e imágenes.

## Estructura de paquetes

```
com.example.dogapp
├── model/        Cita (Entity), BreedsResponse, DogImageResponse
├── data/         DogAppDB (Room), CitaDao
├── webservice/   ApiService (Retrofit), ApiUtils
├── repository/   CitaRepository
├── utils/        Constants
├── viewmodel/    CitaViewModel
└── view/
    ├── MainActivity.kt
    ├── adapter/       CitaAdapter
    ├── viewholder/    CitaViewHolder
    └── fragment/      Login, Home, AddCita, DetailCita, EditCita
```

## Pantallas y navegación

| HU | Fragment | Descripción |
|----|----------|-------------|
| 1.0 | `LoginFragment` | Login con huella (BiometricPrompt) |
| 2.0 | `HomeFragment` | Lista de citas |
| 3.0 | `AddCitaFragment` | Nueva cita |
| 4.0 | `DetailCitaFragment` | Detalle de la cita |
| 5.0 | `EditCitaFragment` | Editar cita |

Acciones en `res/navigation/nav_graph.xml`, nombradas `action_origen_to_destino`.
Los datos entre pantallas se pasan con `Bundle.putSerializable("clave", cita)`.

## API

[dog.ceo](https://dog.ceo/dog-api/) (`https://dog.ceo/api/`):

- `GET breeds/list/all`: lista de razas.
- `GET breed/{raza}/images/random`: imagen aleatoria de la raza.

La URL de la imagen se guarda en la cita al crearla o editarla.

## Convenciones

- Fragments con `lateinit var binding`, `ViewModel` por `by viewModels()` y un método `controladores()`.
- `CitaViewModel` extiende `AndroidViewModel`, expone LiveData con backing field y `progresState`.
- Las operaciones de guardar, actualizar y eliminar reciben un callback `message: (String) -> Unit`.
  **Navegar solo dentro de ese callback**, nunca justo después de llamar la función.
- El ViewModel no recarga la lista tras modificar datos: llamar `getListCitas()` en Home.
- Nombres de funciones y comentarios en español.

## Librerías

Navigation, Lifecycle (ViewModel y LiveData), Coroutines, Room (con KSP), Retrofit + Gson, Glide,
Biometric, Lottie, Material, ConstraintLayout, CardView y RecyclerView.
Versiones en `gradle/libs.versions.toml`.

## Estado

Esqueleto base: arquitectura, capa de datos, navegación y recursos compartidos.
La lógica y el diseño de cada pantalla están pendientes (ver los `// TODO HU X.0` en cada fragment).
