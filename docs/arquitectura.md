# Arquitectura y Lineamientos

## Stack
- Kotlin + Jetpack Compose + Material 3.
- MVVM con state hoisting.
- Navigation Compose.

## Estructura
- `data/` → `UserRepository` (memoria, máx 5 usuarios).
- `ui/screens/` → `Login`, `Register`, `ForgotPassword`.
- `ui/components/` → `UsersTable`, `UsersGrid`.
- `navigation/` → `AppNavHost`.
- `ui/theme/` → `SecondWeekTheme`.

## Accesibilidad (a11y)
- contentDescription/semántica para lectores de pantalla.
- Tamaños táctiles ≥48dp en botones/targets.
- Tipografía base ≥16sp.
- Contraste suficiente (Material 3 por defecto).
- Enlaces claramente distinguibles.

## Reglas de validación
- Email válido.
- Contraseñas coinciden y ≥6 caracteres.
- Términos aceptados para registro.

## Trazabilidad
| Requisito | Componente | Prueba |
|---|---|---|
| RF1 Auth (Login/Registro/Recuperar) | Pantallas correspondientes + `UserRepository` | Navegación y validación manual |
| RF2 Usuarios (5) | `UserRepository` | `UserRepositoryTest` |
| RF3 Navegación | `AppNavHost` | Acceso a 3 rutas |
| RT4 UI requerida | `RegisterScreen` + components | Presente en UI |
| RT3 Accesibilidad | Semántica, tamaños | Verificación con TalkBack |

## Riesgos y mitigaciones
- Incompatibilidades de versiones → Version Catalog bloqueado.
- Errores de runtime por navegación → rutas tipadas y popBackSafe.
- Accesibilidad insuficiente → checklist básico a11y y revisión manual.

## Criterios de aceptación
- 3 pantallas navegables.
- UI requerida visible (inputs, botones, vínculos, combo, check, radio, tabla, grilla).
- Array de 5 usuarios desde Registro.
- Accesibilidad básica aplicada.
- Compila y ejecuta en dispositivos con conexión activa.
