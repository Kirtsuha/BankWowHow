## 🏦 Банковское приложение на Spring Core
### 📋 Описание проекта
Консольное банковское приложение, разработанное на Java с использованием Spring Framework. Приложение предоставляет базовые функции для управления пользователями и их банковскими счетами.

### 🚀 Функциональность
Приложение поддерживает следующие операции:

#### Управление пользователями:
USER_CREATE - Создание нового пользователя с начальным счетом

SHOW_ALL_USERS - Отображение списка всех пользователей и их счетов

#### Управление счетами:
ACCOUNT_CREATE - Создание нового счета для пользователя

ACCOUNT_CLOSE - Закрытие счета (с переводом остатка на первый счет пользователя)

ACCOUNT_DEPOSIT - Пополнение счета

ACCOUNT_WITHDRAW - Снятие средств со счета

ACCOUNT_TRANSFER - Перевод средств между счетами

### 🏗️ Архитектура проекта
```text
src/main/java/
├── domain/               # Модели данных
│   ├── User.java         # Сущность пользователя
│   └── Account.java      # Сущность банковского счета
├── repository/           # Хранилища данных
│   ├── UserRepository.java
│   └── AccountRepository.java
├── service/              # Бизнес-логика
│   ├── UserService.java
│   ├── AccountService.java
│   ├── UserAccountProxyService.java
│   └── OperationsConsoleListener.java
├── exceptions/           # Пользовательские исключения               
│   AppConfig.java        # Конфигурация Spring
│   AccountProperties.java
└── Main.java            # Точка входа
```
### ⚙️ Конфигурация
Файл application.properties:
```properties
# Начальный баланс для новых счетов
account.default-amount=500

# Комиссия за перевод между разными пользователями (в процентах)
account.transfer-commission=5
```

### 🛠️ Технологии
Java 17+

Spring Framework 6.x (только Spring Core)

Gradle - система сборки

### ▶️ Запуск приложения
Клонируйте репозиторий

Убедитесь, что установлена Java 17 или выше

Запустите приложение:

```bash
./gradlew run
```
или

```bash
gradle run
```
### 🎮 Использование
После запуска приложение отобразит меню доступных операций:

```text
Please enter one of operation type:
- ACCOUNT_CREATE
- SHOW_ALL_USERS
- ACCOUNT_CLOSE
- ACCOUNT_WITHDRAW
- ACCOUNT_DEPOSIT
- ACCOUNT_TRANSFER
- USER_CREATE
- EXIT
```
  Введите название операции и следуйте инструкциям на экране.
