# RESTful API для аналитики и отслеживания передвижения животных.
Был разработан в рамках международной олимпиады IT-Планета 2023 в рамках направления "Прикладное программирование". Занял 3 место, набрав 96 баллов из 100.

# Запуск
### 1) Клонирование репозитория
```
git clone https://github.com/A-Korotin/braim_challenge.git
```
### 2) Переход в корневую директорию проекта
```
cd ./braim_challenge
```
### 3) Сборка jar-архива приложения
```
./gradlew bootJar
```
### 4) Сборка Docker-образа
```
sudo docker build . -t webapi
``` 
**Важно!** Tag образа для успешной сборки должен быть именно ``webapi``
### 5) Запуск приложения
```
sudo docker compose up -d
```
Сборка включает в себя пакет автотестов. Результаты и процент выполнения можно посмотреть через веб-интерфейс ``http://localhost:8090/``

Для штатного завершения работы приложения нужно вызвать команду ``sudo docker compose down`` из корневой директории проекта.


# Документация и технические требования
[Этап 1](https://docs.google.com/document/d/1lXfve2LdLSTLqlCnwzN4jSAjz6U18l1nlgK0pivJGhA/edit?usp=sharing)

[Этап 2](https://docs.google.com/document/d/1993TlC2lphBT2r0oQe_TJvPapzgyZcHZ8QCwms6i734/edit?usp=sharing)

# Список использованных технологий
- Spring Boot
  - Spring MVC
  - Spring Data JPA
  - Spring Security
- Hibernate Validator
- Liquibase
- PostreSQL
- Docker
- Docker compose 
