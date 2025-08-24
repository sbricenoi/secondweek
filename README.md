# App de Accesibilidad Visual (DSY2204 - Semana 2)

## Objetivo
Aplicación de ejemplo con enfoque de accesibilidad para personas con discapacidad visual, con vistas de Login, Registro y Recuperar contraseña, desarrollada con Kotlin, Jetpack Compose y Material 3.

## Versiones y Compatibilidad
- minSdk: 24
- targetSdk: 36
- compileSdk: 36
- AGP: 8.12.1
- Kotlin: 2.0.21
- Compose BOM: 2025.01.00
- Material 3: alineado al BOM
- Navigation Compose: 2.8.5

### Matriz de compatibilidad (AGP ↔ Kotlin ↔ Compose ↔ targetSdk)
- AGP 8.12.1 ↔ Kotlin 2.0.21: Compatible
- Kotlin 2.0.21 ↔ Compose BOM 2025.01.00: Compatible (plugin compose)
- compile/targetSdk 36 ↔ Core KTX 1.17.0: Requiere compileSdk 36 (cumplido)

## Cómo compilar/ejecutar
```bash
./gradlew lint test assembleDebug
```
Abrir en Android Studio y ejecutar la app.

## Arquitectura
- MVVM, state hoisting.
- Navigation Compose para navegación.
- Repositorio en memoria con capacidad de 5 usuarios.

## Accesibilidad aplicada
- contentDescription/semántica en elementos clave.
- Tamaños táctiles ≥48dp en botones.
- Tipografía base ≥16sp.
- Contraste de Material 3 por defecto.
- Validaciones con mensajes claros.

## Trazabilidad
- RF1 Autenticación → `LoginScreen`, `RegisterScreen`, `ForgotPasswordScreen` → Pruebas manuales de flujo.
- RF2 Usuarios (5) → `UserRepository` → `UserRepositoryTest`.
- RF3 Navegación → `AppNavHost` → Recorrido entre pantallas.
- RT4 Componentes UI → Inputs, botones, vínculos, combo (dropdown), check, radio, tabla/grilla en `RegisterScreen`.
- RT3 Accesibilidad → Semántica y tamaños táctiles.

## Entregables
- Proyecto compila con `assembleDebug`.
- README y `docs/arquitectura.md`.
- ZIP del proyecto y repositorio Git.
