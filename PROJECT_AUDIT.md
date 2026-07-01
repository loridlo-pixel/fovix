# PROJECT_AUDIT — Android (VPN Client A)

**Дата аудита:** 2026-06-29  
**Область:** `android/`  
**Метод:** статический анализ всех Kotlin/Gradle/Manifest файлов (38 `.kt`, Gradle, Manifest, CI).  
**Сборка:** `./gradlew assembleDebug` локально не выполнена — `JAVA_HOME` не настроен на машине аудита. Выводы по Gradle/compile подтверждены анализом конфигурации и кода.

---

## Сводка

| Категория | Критичных | Высоких | Средних | Низких |
|-----------|-----------|---------|---------|--------|
| Gradle / зависимости | 3 | 2 | 3 | 1 |
| Manifest | 2 | 4 | 1 | 0 |
| Navigation / Compose UI | 0 | 4 | 3 | 2 |
| ViewModel / состояние | 0 | 3 | 2 | 0 |
| VPN / sing-box | 0 | 4 | 3 | 0 |
| Модели / дубли | 0 | 2 | 5 | 4 |
| Package / imports | 0 | 1 | 3 | 2 |
| Циклические зависимости | 0 | 0 | 0 | 0 |

**Package ↔ path:** все 38 Kotlin-файлов лежат в каталогах, соответствующих `package com.vpn.clienta.*`. Явных несовпадений пути и package нет.

**Циклические зависимости:** между пакетами (`ui` → `viewmodel` → `ui.state` / `core.model`, `vpn` → `core.model`) циклов не обнаружено.

---

## Таблица находок

| Файл | Ошибка | Причина | Исправление |
|------|--------|---------|-------------|
| `android/app/build.gradle.kts` | Плагины AGP/Kotlin без версии | Модуль использует `id("com.android.application")` и `id("org.jetbrains.kotlin.android")` без версий, тогда как корневой `build.gradle.kts` объявляет плагины только через `alias(libs.plugins.*) apply false`. В `settings.gradle.kts` нет `pluginManagement { plugins { ... } }` с версиями | Перейти на `alias(libs.plugins.android.application)` и `alias(libs.plugins.kotlin.android)` в `app/build.gradle.kts`, либо явно указать версии плагинов в `pluginManagement` |
| `android/app/build.gradle.kts` | `libs.versions.toml` не используется | Каталог версий описан в `gradle/libs.versions.toml`, но `app/build.gradle.kts` дублирует зависимости строками с другими версиями (Compose BOM, material3) | Унифицировать: подключать зависимости через `libs.*` или удалить неиспользуемый catalog |
| `android/app/build.gradle.kts` | Несогласованные версии Compose | В `app/build.gradle.kts`: BOM `2024.06.00`, material3 `1.2.1`, compiler `1.5.14`. В `libs.versions.toml`: BOM `2024.10.00`, Kotlin `1.9.24` | Синхронизировать BOM / Material3 / Compose Compiler с Kotlin 1.9.24 по [таблице совместимости](https://developer.android.com/jetpack/androidx/releases/compose-kotlin) |
| `android/app/build.gradle.kts` | Отсутствуют test-зависимости | `ExampleUnitTest.kt` импортирует `org.junit.Test`, но `testImplementation("junit:junit:...")` не объявлен | Добавить `testImplementation("junit:junit:4.13.2")` |
| `android/app/build.gradle.kts` | Отсутствуют androidTest-зависимости | `ExampleInstrumentedTest.kt` использует `AndroidJUnit4`, `InstrumentationRegistry`, но `androidTestImplementation(...)` отсутствуют | Добавить `androidx.test.ext:junit`, `androidx.test:runner`, `androidx.test:rules` |
| `android/app/build.gradle.kts` | Отсутствует `material-icons-extended` | `FexiBottomBar.kt` использует `Icons.Default.Settings` — иконка из артефакта `material-icons-extended`, не из core | Добавить `implementation("androidx.compose.material:material-icons-extended")` |
| `android/app/build.gradle.kts` | Release minify без правил | `release { isMinifyEnabled = true }`, но нет кастомных ProGuard/R8 правил для OkHttp, Gson, ML Kit, CameraX | Добавить `proguard-rules.pro` с keep-правилами для используемых библиотек или отключить minify до готовности |
| `android/settings.gradle.kts` | Несогласованное имя проекта | `rootProject.name = "fovix"`, тогда как applicationId/package — `com.vpn.clienta`, label — «Fovix VPN» / «VPNClientA» | Привести naming к одному бренду (VPN Client A) |
| `android/app/src/main/AndroidManifest.xml` | Неверное permission `BIND_VPN_SERVICE` | `BIND_VPN_SERVICE` указан в `<uses-permission>` — это signature/system permission для сервиса, не для приложения | Удалить `<uses-permission android:name="android.permission.BIND_VPN_SERVICE"/>`; оставить только `android:permission` на `<service>` |
| `android/app/src/main/AndroidManifest.xml` | VPN-сервис без intent-filter | `FovixVpnService` extends `VpnService`, но нет `<action android:name="android.net.VpnService" />` | Добавить intent-filter с action `android.net.VpnService` внутрь `<service>` |
| `android/app/src/main/AndroidManifest.xml` | Неверный / неполный foreground service type | `foregroundServiceType="connectedDevice"` для VPN; на API 34+ нужен `FOREGROUND_SERVICE_CONNECTED_DEVICE`. Тип не соответствует VPN-сценарию | Выбрать корректный тип FGS для VPN (или `specialUse` с декларацией в Play Console) и добавить соответствующие permissions |
| `android/app/src/main/AndroidManifest.xml` | `android:exported="true"` на VPN-сервисе | Сервис экспортирован наружу без необходимости | Установить `android:exported="false"` (система подключается через VpnService API) |
| `android/app/src/main/AndroidManifest.xml` | Нет permission для QR/Camera | В Gradle подключены CameraX и ML Kit Barcode, но нет `CAMERA` и runtime-запроса | Добавить `<uses-permission android:name="android.permission.CAMERA"/>` и runtime permission flow |
| `android/app/src/main/AndroidManifest.xml` | Нет permission для логов на storage | `LogExporter` пишет в `Environment.getExternalStorageDirectory()` | Добавить scoped storage / SAF, либо `WRITE_EXTERNAL_STORAGE` (legacy) / `MANAGE_EXTERNAL_STORAGE` — предпочтительно писать в `context.filesDir` |
| `android/app/src/main/java/com/vpn/clienta/ui/navigation/FexiNavHost.kt` | Маршрут Settings отсутствует | `FexiDestination` содержит `Settings`, `FexiBottomBar` имеет вкладку Settings, но в `NavHost` только `"home"` и `"servers"` | Добавить `composable("settings") { SettingsScreen() }` и связать с bottom bar |
| `android/app/src/main/java/com/vpn/clienta/ui/navigation/FexiNavHost.kt` | Bottom bar не подключён | `FexiBottomBar` нигде не вызывается; навигация между экранами недоступна из UI | Обернуть `NavHost` в `Scaffold` с `FexiBottomBar` и `navController.navigate(...)` |
| `android/app/src/main/java/com/vpn/clienta/ui/navigation/FexiNavHost.kt` | Enum маршрутов не используется | `FexiDestination` не связан со строками `"home"`, `"servers"` — риск опечаток | Использовать sealed class / enum с route string или Navigation Compose type-safe API |
| `android/app/src/main/java/com/vpn/clienta/ui/navigation/FexiNavHost.kt` | QR-экран не в графе | `QrScannerScreen.kt` существует, зависимости Camera/ML Kit подключены, маршрута нет | Добавить route `"qr"` и точку входа (кнопка Import / Scan) |
| `android/app/src/main/java/com/vpn/clienta/ui/screens/HomeScreen.kt` | ViewModel не общий между экранами | `viewModel()` вызывается отдельно в `HomeScreen` и `ServersScreen` без общего `ViewModelStoreOwner` / nav graph scope | Использовать `viewModel(navController.getBackStackEntry("root"))` или Activity-scoped ViewModel |
| `android/app/src/main/java/com/vpn/clienta/viewmodel/VPNViewModel.kt` | Состояние VPN не связано с движком | `connect()` / `disconnect()` только меняют `isConnected`, не вызывают `VpnEngine`, `FovixVpnService`, `VpnService.prepare()` | Интегрировать `VpnEngine.connect(server)` → Intent в `FovixVpnService` + обработка permission |
| `android/app/src/main/java/com/vpn/clienta/viewmodel/VPNViewModel.kt` | Нет состояния `Connecting` | `VPNState.Connecting` объявлен в domain, но `VpnUiState` хранит только `Boolean isConnected` | Заменить `isConnected: Boolean` на `vpnState: VPNState` или добавить поле `connectionState` |
| `android/app/src/main/java/com/vpn/clienta/viewmodel/VPNViewModel.kt` | Серверы не загружаются | `setServers()` нигде не вызывается; `VpnRepository`, `SubscriptionRepository`, парсеры не подключены | Внедрить repository в ViewModel; загрузка из QR/subscription/local config |
| `android/app/src/main/java/com/vpn/clienta/ui/state/VpnUiState.kt` | Модель состояния расходится с UI-компонентами | `VpnUiState.isConnected: Boolean` vs `ConnectButton` / `AnimatedConnectButton` ожидают `VPNState` enum | Унифицировать на `VPNState` во всём UI-слое |
| `android/app/src/main/java/com/vpn/clienta/domain/VPNState.kt` | Enum не используется в активном UI | Активные экраны (`HomeScreen`) работают с `Boolean`, enum используется только в не подключённых компонентах | Перевести `HomeScreen` на `ConnectButton(state = VPNState, ...)` или удалить дублирующие компоненты |
| `android/app/src/main/java/com/vpn/clienta/ui/components/ConnectButton.kt` | Компонент не используется (orphan) | Не импортируется ни одним экраном | Подключить в `HomeScreen` или удалить как dead code |
| `android/app/src/main/java/com/vpn/clienta/ui/components/AnimatedConnectButton.kt` | Компонент не используется (orphan) | Дублирует `ConnectButton`, не подключён к NavHost/Home | Оставить один компонент, второй удалить или использовать |
| `android/app/src/main/java/com/vpn/clienta/ui/components/ConnectionPanel.kt` | Компонент не используется (orphan) | Не вызывается из экранов | Интегрировать в `HomeScreen` или удалить |
| `android/app/src/main/java/com/vpn/clienta/ui/components/ServerCard.kt` | Компонент не используется (orphan) | `ServersScreen` рисует `Text` вместо `ServerCard` | Заменить список `Text` на `ServerCard` |
| `android/app/src/main/java/com/vpn/clienta/ui/components/FexiBottomBar.kt` | Потенциальный unresolved reference `Icons.Default.Settings` | Нет зависимости `material-icons-extended` | См. строку Gradle выше |
| `android/app/src/main/java/com/vpn/clienta/ui/screens/SettingsScreen.kt` | Экран недоступен пользователю | Не добавлен в `FexiNavHost` | Добавить маршрут и навигацию |
| `android/app/src/main/java/com/vpn/clienta/ui/screens/ServersScreen.kt` | Неиспользуемый import | `import com.vpn.clienta.core.model.VpnServer` не используется явно | Удалить import (lint warning) |
| `android/app/src/main/java/com/vpn/clienta/ui/theme/Theme.kt` | Неиспользуемый import | `import android.app.Activity` не используется | Удалить import |
| `android/app/src/main/java/com/vpn/clienta/ui/qr/QrScannerScreen.kt` | Заглушка вместо QR-сканера | Только `Text("QR Scanner placeholder")`, CameraX/ML Kit не используются | Реализовать CameraX Preview + ML Kit analyzer или убрать зависимости из MVP |
| `android/app/src/main/java/com/vpn/clienta/vpn/VpnEngine.kt` | Stub — VPN не подключается | `connect()` / `disconnect()` пустые; ViewModel их не вызывает | Реализовать запуск `FovixVpnService` через Intent + extras (`host`, `port`, `uuid`) |
| `android/app/src/main/java/com/vpn/clienta/vpn/FovixVpnService.kt` | TUN не создаётся | Нет `Builder().establish()` — Android VPN interface не поднимается, sing-box не получит TUN fd | Вызвать `VpnService.Builder` → `establish()` и передать fd в sing-box (или использовать userspace tun) |
| `android/app/src/main/java/com/vpn/clienta/vpn/FovixVpnService.kt` | Неверный путь к бинарнику sing-box | Ожидает `File(filesDir, "sing-box")` | `SingBoxBinaryInstaller` кладёт в `filesDir/singbox/sing-box` — пути не совпадают |
| `android/app/src/main/java/com/vpn/clienta/vpn/FovixVpnService.kt` | Неиспользуемый import | `import com.vpn.clienta.core.model.VpnServer` не используется | Удалить import |
| `android/app/src/main/java/com/vpn/clienta/vpn/FovixVpnService.kt` | Конфиг sing-box inline, дублирует builder | JSON собран строкой; `SingBoxConfigBuilder` не используется и неполный (нет inbounds/tun) | Единый `SingBoxConfigBuilder` с TUN inbound + outbounds |
| `android/app/src/main/java/com/vpn/clienta/vpn/SingBoxBinaryInstaller.kt` | Installer никогда не вызывается | Нет вызова из `Application`, `MainActivity` или `FovixVpnService.onCreate` | Вызвать `install(context)` при старте приложения / перед connect |
| `android/app/src/main/java/com/vpn/clienta/vpn/SingBoxConfigBuilder.kt` | Некорректная JSON-сериализация | `root.put("outbounds", listOf(out))` — `JSONObject.put` с Kotlin `List` не создаёт JSON-массив | Использовать `JSONArray` для outbounds/inbounds |
| `android/app/src/main/java/com/vpn/clienta/vpn/SingBoxConfigBuilder.kt` | Неполный конфиг | Только outbounds, нет inbounds/tun/log/route — несовместимо с `FovixVpnService` inline-конфигом | Добавить inbounds (tun), log, route; унифицировать с service |
| `android/app/src/main/java/com/vpn/clienta/debug/SingboxDebug.kt` | Путь проверки не совпадает с installer | Проверяет `filesDir/sing-box`, installer пишет в `filesDir/singbox/sing-box` | Унифицировать путь через константу / метод installer |
| `android/app/src/main/java/com/vpn/clienta/vpn/RealVpnChecker.kt` | Дублирует `VpnConnectivityChecker` | Оба проверяют IP через api.ipify.org | Объединить в один checker |
| `android/app/src/main/java/com/vpn/clienta/vpn/VpnConnectivityChecker.kt` | Дублирует `RealVpnChecker` | Та же логика, разные сигнатуры (`isVpnActive` vs `check`) | Объединить; ни один не вызывается из ViewModel |
| `android/app/src/main/java/com/vpn/clienta/core/parser/VpnParser.kt` | Дублирует `QrParser.parse` | Оба парсят `vless://`; `VpnParser` без валидации (crash на malformed URI) | Оставить один parser (`QrParser` с null-safe логикой) |
| `android/app/src/main/java/com/vpn/clienta/data/parser/QrParser.kt` | Не подключён к UI/ViewModel | Используется только в `UnifiedParser`, который тоже orphan | Связать с QR-экраном и ViewModel.setServers |
| `android/app/src/main/java/com/vpn/clienta/data/VpnRepository.kt` | Repository не используется | Нет DI / вызовов из ViewModel | Внедрить в ViewModel как единый источник списка серверов |
| `android/app/src/main/java/com/vpn/clienta/subscription/SubscriptionRepository.kt` | Дублирует data-layer + stub | Параллельно `SubscriptionLoader` + `UnifiedParser`; метод возвращает `emptyList()` | Объединить subscription flow или удалить до реализации |
| `android/app/src/main/java/com/vpn/clienta/data/network/SubscriptionLoader.kt` | Не используется | Нет вызовов из ViewModel / Repository | Подключить к `SubscriptionRepository.loadFromUrl` |
| `android/app/src/main/java/com/vpn/clienta/smart/VpnRulesEngine.kt` | Логическая ошибка smart-routing | `smartDecision(domain)` сравнивает hostname с `"finance"`, `"social"`, `"media"`, но туда приходит URL/host, не категория | Сначала вызывать `DomainClassifier.classify(domain)`, затем решение по категории |
| `android/app/src/main/java/com/vpn/clienta/smart/DomainClassifier.kt` | Модуль smart не интегрирован | Нигде не вызывается | Подключить к rules engine или исключить из MVP |
| `android/app/src/main/java/com/vpn/clienta/logger/LogExporter.kt` | Запись на external storage без permission | `Environment.getExternalStorageDirectory()` на API 29+ требует scoped storage / permissions | Писать в `context.getExternalFilesDir(null)` или `filesDir` |
| `android/app/src/main/java/com/vpn/clienta/MainActivity.kt` | sing-box не инициализируется при старте | Нет вызова `SingBoxBinaryInstaller.install()` / `SingboxDebug` | Установить бинарник при первом запуске |
| `android/app/src/main/res/values/strings.xml` | Расхождение имён приложения | `app_name` = «VPNClientA», Manifest label = «Fovix VPN» | Унифицировать строки брендинга |
| `android/app/src/main/res/values/themes.xml` | Material Components + Compose | XML-theme на `Theme.MaterialComponents`, UI полностью Compose/Material3 | Допустимо для Activity shell; убедиться, что `Theme.VpnClientA` соответствует Compose palette (Nord) |
| `android/app/src/test/.../ExampleUnitTest.kt` | Тест не связан с проектом | Шаблонный тест `2+2`; нет тестов парсера/VPN | Добавить unit-тесты для `QrParser`, `UnifiedParser` после фикса Gradle test deps |
| `android/app/src/androidTest/.../ExampleInstrumentedTest.kt` | Сборка androidTest упадёт | Нет androidTest dependencies в Gradle | См. Gradle-строку выше |
| `android/local.properties` | Файл присутствует в рабочей копии | Содержит локальный SDK path; в `.gitignore` указан, но файл есть в дереве | Не коммитить; убедиться, что не попадает в git |
| `.github/workflows/android-build.yml` | Рабочая директория CI | Workflow корректно собирает `android/`, но полная очистка `~/.gradle` на каждый push замедляет CI | Оставить `clean`, убрать удаление глобального Gradle cache (опционально) |

---

## Дубли классов и функций

| Сущность | Где дублируется | Риск |
|----------|-----------------|------|
| Connect UI | `ConnectButton`, `AnimatedConnectButton`, inline Button в `HomeScreen` | Три реализации одной кнопки с разными API |
| VPN status model | `VpnUiState.isConnected: Boolean` vs `VPNState` enum vs `ConnectionPanel(isConnected: Boolean)` | Несогласованное отображение Connecting/Connected |
| VLESS parser | `VpnParser.parseVless`, `QrParser.parse` | Разная обработка ошибок, разные имена серверов |
| IP checker | `RealVpnChecker.check`, `VpnConnectivityChecker.isVpnActive` | Copy-paste логики |
| Subscription loading | `SubscriptionLoader`, `SubscriptionRepository`, `VpnRepository` | Три слоя без связки |
| sing-box config | Inline JSON в `FovixVpnService`, `SingBoxConfigBuilder.build` | Расхождение форматов конфига |
| sing-box binary path | `FovixVpnService`, `SingBoxBinaryInstaller`, `SingboxDebug` | Три разных пути к файлу |

**Дублирующихся class/object с одинаковым FQCN не найдено.**

---

## Несовпадения моделей

| Модель A | Модель B | Проблема |
|----------|----------|----------|
| `VpnUiState.isConnected: Boolean` | `VPNState` (Disconnected / Connecting / Connected) | Состояние Connecting недостижимо в активном UI |
| `ConnectButton(vpnState: VPNState)` | `HomeScreen` использует Boolean | Компоненты и экраны несовместимы без адаптера |
| `FovixVpnService` Intent extras (`host`, `port`, `uuid`, `name`) | `VpnServer(name, host, port, uuid)` | Нет mapper/service launcher из `VpnServer` |
| `DomainClassifier.classify(url) → String` | `VpnRulesEngine.smartDecision(domain)` | Engine ожидает категорию, получает hostname |
| `SingBoxConfigBuilder` output | `FovixVpnService` inline config | Разная структура JSON (inbounds/tun отсутствуют в builder) |

---

## Отсутствующие файлы / ресурсы

| Ожидается | Статус |
|-----------|--------|
| `proguard-rules.pro` (для release minify) | Отсутствует |
| `Application` class (инициализация sing-box) | Отсутствует |
| `ViewModelFactory` / Hilt module (DI) | Отсутствует — допустимо для MVP, но repository не внедряются |
| Gradle wrapper | Присутствует |
| sing-box binary asset `assets/singbox/sing-box` | Файл присутствует (проверка ELF/arch не выполнялась) |
| iOS часть monorepo | Вне scope Android-аудита |

---

## Compose — потенциальные проблемы

1. **Два theme-файла:** `Theme.kt` (`VPNClientATheme` + Material3 dynamic colors) и `VpnTheme.kt` (Nord palette). `SettingsScreen` / кнопки используют Nord; `HomeScreen` — hardcoded `Color.Green/Red`.
2. **`ServerCard` `Card(onClick)`** — требует Material3 ≥ 1.2 (заявлено 1.2.1) — OK при успешной сборке.
3. **`AnimatedConnectButton.animateFloat`** — использует positional args; в новых версия Compose рекомендуется явный `label` (warning, не blocker).
4. **Orphan UI:** большинство polish-компонентов не подключены к NavHost.

---

## Navigation — карта текущего состояния

```
MainActivity → FexiNavHost
                 ├── "home"    → HomeScreen      ✅
                 ├── "servers" → ServersScreen     ✅
                 ├── "settings"→ SettingsScreen   ❌ отсутствует
                 └── "qr"      → QrScannerScreen  ❌ отсутствует

FexiBottomBar (Home / Servers / Settings) → ❌ не используется
FexiDestination enum                       → ❌ не связан с routes
```

---

## Gradle — ожидаемый результат сборки

При исправлении JDK и запуске `./gradlew assembleDebug` на текущем коде **ожидаются ошибки**:

1. **Plugin not found / no version** — из-за `plugins { id("com.android.application") }` без catalog.
2. **Unresolved reference `Icons.Default.Settings`** — без `material-icons-extended` (если core не тянет Settings).
3. **androidTest / unit test compile** — без JUnit/AndroidX Test deps.

Debug-сборка основного `main` source set может пройти после фикса п.1 и п.2; test source sets — после фикса p.3.

---

## Рекомендуемый порядок исправлений (без автоприменения)

1. Gradle: plugin aliases + test deps + icons-extended.
2. Manifest: VPN service intent-filter, permissions, exported=false.
3. Унификация `VPNState` + общий ViewModel scope.
4. Navigation: Scaffold + BottomBar + Settings route.
5. VPN pipeline: Installer → VpnEngine → FovixVpnService → единый config path.
6. Удаление/слияние дублей (parsers, checkers, connect buttons).

---

*Аудит выполнен без изменений исходного кода проекта. Единственный созданный артефакт — этот файл.*
