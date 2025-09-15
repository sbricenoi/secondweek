# Arquitectura y Lineamientos - Semana 5 (Integración Kotlin Avanzada)

## Stack Tecnológico
- **Kotlin 2.0.21** + **Jetpack Compose** + **Material 3**
- **MVVM** con state hoisting y gestión de estado inmutable
- **Navigation Compose 2.8.5** para navegación tipada
- **AGP 8.12.1** con compatibilidad Android 14 (API 36)

## Estructura del Proyecto
```
app/src/main/java/com/example/secondweek/
├── data/
│   └── UserRepository.kt          # Repositorio singleton con validaciones
├── ui/
│   ├── screens/
│   │   ├── LoginScreen.kt         # Pantalla de autenticación
│   │   ├── RegisterScreen.kt      # Pantalla de registro con formulario completo
│   │   └── ForgotPasswordScreen.kt # Pantalla de recuperación de contraseña
│   ├── components/
│   │   ├── UsersTable.kt          # Tabla responsiva con accesibilidad
│   │   └── UsersGrid.kt           # Grilla adaptativa de usuarios
│   └── theme/
│       ├── Theme.kt               # Tema Material 3 con soporte dark/light
│       └── Type.kt                # Tipografía optimizada para accesibilidad
├── navigation/
│   └── NavGraph.kt                # Navegación con rutas tipadas
└── MainActivity.kt                # Actividad principal con edge-to-edge
```

## Mejoras Implementadas en Kotlin

### 1. Gestión de Estado Avanzada
- **ViewModels** con estado inmutable y funciones de actualización específicas
- **State hoisting** completo con separación de responsabilidades
- **Validaciones robustas** con manejo de errores y estados de carga
- **Result API** para manejo de operaciones que pueden fallar

### 2. Patrones de Kotlin Aplicados
- **Data classes** con validaciones en `init`
- **Object singleton** para el repositorio
- **Extension functions** para validaciones
- **Sealed classes** para estados de UI (implícito en Result)
- **Higher-order functions** para callbacks de navegación
- **Null safety** completo sin `!!` operators

### 3. Arquitectura MVVM Refinada
```kotlin
// Ejemplo de ViewModel optimizado
class RegisterViewModel : ViewModel() {
    // Estado privado con setters públicos específicos
    var name by mutableStateOf("")
        private set
    
    // Funciones de actualización específicas
    fun updateName(value: String) { name = value }
    
    // Lógica de negocio encapsulada
    fun onRegister() { /* validación y procesamiento */ }
}
```

## Accesibilidad Avanzada (WCAG 2.1 AA)

### Implementaciones Específicas
- **Semántica completa**: `contentDescription` detallado en todos los elementos
- **Tamaños táctiles**: Mínimo 48dp en todos los controles interactivos
- **Tipografía accesible**: Mínimo 16sp en todos los textos
- **Contraste alto**: Material 3 con esquemas de color optimizados
- **Estados de carga**: Indicadores visuales y semánticos
- **Navegación por teclado**: Soporte completo para dispositivos externos

### Componentes Optimizados
```kotlin
// Ejemplo de componente accesible
Button(
    onClick = viewModel::onLogin,
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
- **OutlinedTextField** con validación en tiempo real
- **ExposedDropdownMenuBox** para selección de preferencias
- **RadioButton** groups con navegación por teclado
- **Checkbox** con descripciones semánticas detalladas

### Visualización de Datos
- **UsersTable**: Tabla responsiva con encabezados semánticos
- **UsersGrid**: Grilla adaptativa con tarjetas informativas
- **Estados vacíos**: Mensajes claros cuando no hay datos

### Navegación y Estados
- **Loading states**: Indicadores de progreso accesibles
- **Error handling**: Mensajes de error en tarjetas destacadas
- **Success feedback**: Confirmaciones visuales y semánticas

## Validaciones y Reglas de Negocio

### Validaciones del Usuario
```kotlin
data class User(/* ... */) {
    init {
        require(name.isNotBlank()) { "El nombre no puede estar vacío" }
        require(email.isNotBlank()) { "El email no puede estar vacío" }
        require(password.length >= 6) { "Contraseña mínima de 6 caracteres" }
        require(acceptTerms) { "Debe aceptar los términos y condiciones" }
    }
}
```

### Repositorio con Límites
- Máximo 5 usuarios registrados
- Validación de emails únicos
- Autenticación segura con comparación case-insensitive

## Trazabilidad de Requisitos

| Requisito | Implementación | Validación |
|-----------|----------------|------------|
| **RF1** - Autenticación completa | `LoginScreen`, `RegisterScreen`, `ForgotPasswordScreen` + ViewModels | Flujos de navegación y validación |
| **RF2** - Gestión de 5 usuarios | `UserRepository` con límite y validaciones | Array dinámico con contador |
| **RF3** - Navegación fluida | `AppNavHost` con rutas tipadas | Navegación entre 3 pantallas |
| **RF4** - Componentes UI completos | Todos los elementos requeridos implementados | Formulario completo funcional |
| **RF5** - Accesibilidad avanzada | Semántica, tamaños, contraste, tipografía | Compatible con TalkBack |
| **RF6** - Arquitectura MVVM | ViewModels, state hoisting, separación de responsabilidades | Código mantenible y testeable |

## Criterios de Aceptación Cumplidos

### Funcionalidad
- ✅ 3 pantallas completamente navegables
- ✅ Formulario de registro con todos los componentes UI requeridos
- ✅ Array de usuarios con capacidad de 5 elementos
- ✅ Validaciones robustas en tiempo real
- ✅ Estados de carga y manejo de errores

### Accesibilidad
- ✅ Tamaños táctiles ≥48dp en todos los controles
- ✅ Tipografía ≥16sp en todos los textos
- ✅ Descripciones semánticas completas para TalkBack
- ✅ Contraste adecuado con Material 3
- ✅ Navegación por teclado funcional

### Código Kotlin
- ✅ 100% del código en Kotlin con buenas prácticas
- ✅ Arquitectura MVVM implementada correctamente
- ✅ Gestión de estado inmutable
- ✅ Manejo de errores con Result API
- ✅ Documentación completa en KDoc

### Compatibilidad
- ✅ Compilación exitosa con AGP 8.12.1 + Kotlin 2.0.21
- ✅ Soporte para Android 7.0+ (API 24-36)
- ✅ Adaptabilidad a múltiples tamaños de pantalla
- ✅ Conexión a internet requerida (validación de conectividad implícita)

## Riesgos Mitigados

| Riesgo | Probabilidad | Impacto | Mitigación Implementada |
|--------|--------------|---------|-------------------------|
| Incompatibilidad de versiones | Baja | Alto | Version Catalog con versiones probadas |
| Errores de navegación | Baja | Medio | Rutas tipadas y validación de estados |
| Accesibilidad insuficiente | Baja | Alto | Implementación completa WCAG 2.1 AA |
| Performance en dispositivos antiguos | Media | Medio | Optimización de Compose y lazy loading |
| Validaciones inconsistentes | Baja | Medio | Validaciones centralizadas en data classes |


