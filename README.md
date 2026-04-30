🏥 Clinic Management System API

REST API приложение для управления процессами поликлиники. Проект предоставляет функционал для регистрации пользователей, управления расписанием и медицинскими картами.

🛠 Технологии и Инструменты

**Backend:**
* **Java** (Core)
* **Spring Boot** (Web, Data JPA, Security)
* **Maven** (Сборка проекта и управление зависимостями)

**База данных и Миграции:**
* **PostgreSQL** (Основная реляционная БД)
* **Liquibase** (Управление версиями схемы базы данных)

**DevOps и Документация:**
* **Docker & Docker Compose** (Контейнеризация БД и окружения)
* **OpenAPI (Swagger)** (Автоматическая генерация документации API)

---

🚀 Быстрый старт (Локальный запуск)

### Требования
Для запуска проекта на вашем компьютере должны быть установлены:
* Java 17 (или выше)
* Docker и Docker Compose
* Maven

```bash
1. Клонирование репозитория

git clone [https://github.com/darkvelton69/Clinic-Management-System.git](https://github.com/darkvelton69/Clinic-Management-System.git)
cd Clinic-Management-System

2. Настройка переменных окружения
Проект использует переменные окружения(.env) для защиты секретных данных.
Создайте файл .env в корне проекта на основе файла-примера выполнив следующую команду:
cp .env.example .env
Впишите в .env ваши локальные пароли и секретные ключи. (Файл .env добавлен в .gitignore и не попадет в репозиторий).

3. Запуск Базы Данных (Docker)
Для запуска изолированного контейнера с PostgreSQL выполните команду:
docker-compose up -d db
Не забудьте перед этим прописать в терминале команду: cd Clinic-Management-System .
Доступ к базе данных будет доступен по порту 5433

4. Запуск приложения
Осуществить запуск приложения вы можете двумя способами:
1. Перейти в класс runner, сверху где 'run' и 'debug' будут три точки, нажмите на них и нажмите edit там в поле Active profile пропишите local, а также нужно будет поставить галочку на Enable EnvFile, после этого добавить свой envfile нажав на '+' и указав место до вашего .env файла перед этим вам нужно в Settings во вкладке Plugins нажать на Marketplaces и там найти EnvFile и установить его.
2. В терминале в корне проекта прописать команду: docker-compose up --build, в этом способе вам не нужно прописывать команду docker-compose up -d db и делать что-либо с раннеромю

📚 Документация API (Swagger)
После успешного запуска приложения, интерактивная документация ко всем эндпоинтам будет доступна в браузере по адресу:
👉 http://localhost:8080/swagger-ui/index.html

Через интерфейс Swagger вы можете протестировать регистрацию, авторизацию и другие функции API без использования Postman.

🗄 Структура БД (Liquibase)
При старте приложения Liquibase автоматически накатывает все необходимые миграции. Файлы миграций (changelogs) находятся в директории:
src/main/resources/db/changelog/

  Телеграмм-Бот (TelegramAPI)
  Docker запуск - При старте приложения код автоматически импортируется в вашего ранее созданного бота. НЕ ЗАБУДЬТЕ ВНЕСТИ СВОИ ДАННЫЕ В .env.example, А ПОСЛЕ ЭТОГО ПЕРЕИМЕНОВАТЬ ЕГО В .env ИНАЧЕ СТОЛКНЕТЕСЬ С ОШИБКОЙ

Лично я советую запуск через docker-compose up --build

В .env.example вы должны поменять все данные под себя как показано в следующем примере ------>>>

```text
  Пример настроек базы данных
DB_URL=jdbc:postgresql://localhost:5433/polyclinic_db
DB_USERNAME=your_username_here
DB_PASSWORD=your_password_here
JWT_SECRET=generate_your_random_string_here
TELEGRAM_NAME=your_telegram_name
TELEGRAM_TOKEN=your_telegram_token
MAIL_USERNAME=your_username_or_email
MAIL_PASSWORD=your_password_email

  Пример секретного ключа для JWT (если используешь)
JWT_SECRET=your_secret_key_here


В СЛУЧАЕ ОШИБОК НАПИШИТЕ
