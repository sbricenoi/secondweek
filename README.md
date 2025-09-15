# App de Accesibilidad Visual (DSY2204 - Semana 5)
## Integración Kotlin Avanzada

## Objetivo
Aplicación móvil optimizada para personas con discapacidad sensorial visual, implementando las mejores prácticas de Kotlin moderno, arquitectura MVVM avanzada y accesibilidad WCAG 2.1 AA. Desarrollada con Jetpack Compose, Material 3 y navegación fluida entre múltiples pantallas.

## Versiones y Compatibilidad
- **minSdk:** 24 (Android 7.0+)
- **targetSdk:** 36 (Android 14)
- **compileSdk:** 36
- **AGP:** 8.12.1
- **Kotlin:** 2.0.21
- **Compose BOM:** 2025.01.00
- **Material 3:** Alineado al BOM
- **Navigation Compose:** 2.8.5

### Matriz de Compatibilidad Validada
- ✅ AGP 8.12.1 ↔ Kotlin 2.0.21: Compatible
- ✅ Kotlin 2.0.21 ↔ Compose BOM 2025.01.00: Compatible (plugin compose)
- ✅ compile/targetSdk 36 ↔ Core KTX 1.17.0: Compatible
- ✅ Navigation 2.8.5 ↔ Compose BOM: Compatible

## Instalación y Ejecución

### Requisitos Previos
- Android Studio Hedgehog | 2023.1.1 o superior
- JDK 17 o superior
- Dispositivo/Emulador Android 7.0+ (API 24+)
- Conexión a internet activa

### Compilación
```bash
# Limpiar y compilar
./gradlew clean assembleDebug

# Ejecutar tests
./gradlew test

# Análisis de código
./gradlew lint

# Compilación completa con validaciones
./gradlew lint test assembleDebug
```

### Ejecución
1. Abrir el proyecto en Android Studio
2. Sincronizar dependencias (Gradle Sync)
3. Seleccionar dispositivo/emulador
4. Ejecutar la aplicación (Run 'app')

## Arquitectura Avanzada

### Stack Tecnológico
- **100% Kotlin** con patrones modernos
- **MVVM** con state hoisting y gestión inmutable
- **Jetpack Compose** para UI declarativa
- **Material 3** con temas adaptativos
- **Navigation Compose** con rutas tipadas
- **Repository Pattern** con singleton thread-safe

### Estructura del Proyecto
```
app/src/main/java/com/example/secondweek/
├── data/
│   └── UserRepository.kt          # Repositorio con validaciones robustas
├── ui/
│   ├── screens/
│   │   ├── LoginScreen.kt         # Autenticación con estados de carga
│   │   ├── RegisterScreen.kt      # Formulario completo con validaciones
│   │   └── ForgotPasswordScreen.kt # Recuperación con verificación
│   ├── components/
│   │   ├── UsersTable.kt          # Tabla responsiva accesible
│   │   └── UsersGrid.kt           # Grilla adaptativa con tarjetas
│   └── theme/
│       ├── Theme.kt               # Material 3 con dark/light mode
│       └── Type.kt                # Tipografía optimizada (≥16sp)
├── navigation/
│   └── NavGraph.kt                # Navegación tipada entre pantallas
└── MainActivity.kt                # Edge-to-edge con Surface
```

## Características de Kotlin Implementadas

### 1. Gestión de Estado Avanzada
```kotlin
class RegisterViewModel : ViewModel() {
    // Estado inmutable con setters privados
    var name by mutableStateOf("")
        private set
    
    // Funciones específicas de actualización
    fun updateName(value: String) { name = value }
    
    // Validaciones robustas con when expressions
    fun onRegister() {
        when {
            name.isBlank() -> errorMessage = "Nombre requerido"
            !isValidEmail(email) -> errorMessage = "Email inválido"
            // ... más validaciones
        }
    }
}
```

### 2. Patrones Kotlin Aplicados
- **Data classes** con validaciones en `init`
- **Object singleton** para repositorio thread-safe
- **Result API** para manejo de errores
- **Higher-order functions** para callbacks
- **Extension functions** para validaciones
- **Null safety** completo sin `!!` operators

### 3. Manejo de Errores Robusto
```kotlin
fun addUser(user: User): Result<Unit> {
    return when {
        users.size >= MAX_USERS -> Result.failure(IllegalStateException("Capacidad máxima"))
        users.any { it.email.equals(user.email, ignoreCase = true) } -> 
            Result.failure(IllegalArgumentException("Email ya registrado"))
        else -> {
            users.add(user)
            Result.success(Unit)
        }
    }
}
```

## Accesibilidad Avanzada (WCAG 2.1 AA)

### Implementaciones Específicas
- **Semántica completa:** `contentDescription` detallado en todos los elementos
- **Tamaños táctiles:** Mínimo 48dp en controles interactivos
- **Tipografía accesible:** Mínimo 16sp en todos los textos
- **Contraste alto:** Material 3 con esquemas optimizados
- **Estados de carga:** Indicadores visuales y semánticos
- **Navegación por teclado:** Soporte completo

### Ejemplo de Componente Accesible
```kotlin
Button(
    onClick = viewModel::onLogin,
    enabled = !viewModel.isLoading,
    modifier = Modifier
        .height(56.dp) // Tamaño táctil mínimo
        .semantics { 
            contentDescription = "Botón iniciar sesión"
        }
) {
    if (viewModel.isLoading) {
        CircularProgressIndicator(/* ... */)
    } else {
        Text("Iniciar Sesión")
    }
}
```

## Componentes UI Implementados

### Formularios Avanzados
- ✅ **OutlinedTextField** con validación en tiempo real
- ✅ **ExposedDropdownMenuBox** para preferencias de accesibilidad
- ✅ **RadioButton** groups con navegación por teclado
- ✅ **Checkbox** con descripciones semánticas detalladas

### Visualización de Datos
- ✅ **UsersTable:** Tabla responsiva con encabezados semánticos
- ✅ **UsersGrid:** Grilla adaptativa con tarjetas informativas
- ✅ **Estados vacíos:** Mensajes claros cuando no hay datos

### Navegación y Estados
- ✅ **Loading states:** Indicadores de progreso accesibles
- ✅ **Error handling:** Mensajes en tarjetas destacadas
- ✅ **Success feedback:** Confirmaciones visuales y semánticas

## Trazabilidad de Requisitos

| Requisito | Implementación | Validación |
|-----------|----------------|------------|
| **RF1** - Autenticación completa | `LoginScreen`, `RegisterScreen`, `ForgotPasswordScreen` + ViewModels | Flujos navegación funcionales |
| **RF2** - Gestión 5 usuarios | `UserRepository` con límite y validaciones | Array dinámico con contador |
| **RF3** - Navegación fluida | `AppNavHost` con rutas tipadas | 3 pantallas navegables |
| **RF4** - Componentes UI completos | Todos los elementos implementados | Formulario funcional completo |
| **RF5** - Accesibilidad WCAG 2.1 AA | Semántica, tamaños, contraste | Compatible con TalkBack |
| **RF6** - Arquitectura MVVM | ViewModels, state hoisting | Código mantenible y testeable |

## Testing y Validación

### Pruebas Implementadas
```bash
# Ejecutar todas las pruebas
./gradlew test

# Pruebas específicas del repositorio
./gradlew testDebugUnitTest --tests="*UserRepositoryTest*"

# Análisis de código estático
./gradlew lint
```

### Validación de Accesibilidad
- Testing manual con **TalkBack** activado
- Verificación de tamaños táctiles con **Layout Inspector**
- Validación de contraste con **Accessibility Scanner**
- Pruebas de navegación por teclado

## Documentación Completa

### Archivos de Documentación
- 📄 **`docs/arquitectura.md`** - Arquitectura técnica detallada
- 📄 **`README.md`** - Este archivo con guía completa

### KDoc en Código
Todas las funciones públicas incluyen documentación KDoc:
```kotlin
/**
 * Autentica un usuario con email y contraseña
 * @param email Email del usuario
 * @param password Contraseña del usuario
 * @return true si las credenciales son válidas, false en caso contrario
 */
fun authenticate(email: String, password: String): Boolean
```

## Entregables Completados

### Código Fuente
- ✅ Proyecto Android Studio compilable
- ✅ 100% código en Kotlin con buenas prácticas
- ✅ Arquitectura MVVM implementada correctamente
- ✅ Accesibilidad WCAG 2.1 AA completa

### Documentación
- ✅ Informe técnico completo (Formato B)
- ✅ Documentación de arquitectura actualizada
- ✅ README con instrucciones detalladas
- ✅ KDoc en funciones públicas

### Validaciones
- ✅ Compilación exitosa sin errores
- ✅ Testing funcional en múltiples dispositivos
- ✅ Validación de accesibilidad con TalkBack
- ✅ Performance optimizada para API 24+

---

**Proyecto desarrollado para DSY2204 - Desarrollo de Aplicaciones Móviles**  
**Instituto Profesional AIEP - Semana 5 - Septiembre 2025**


