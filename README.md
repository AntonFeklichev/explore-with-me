# java-explore-with-me
Приложение ExploreWithMe мой выпускной проект Яндекс.Практикум. 
Приложение по сути афиша, которая позволяет пользователям делиться информацией об интересных событиях и находить компанию для участия в них.

В проекте использовал: 
- Spring Boot 
- Maven 
- JPA and Hibernate
- Criteria API
- PostgreSQL RESTful CRUD API
- Docker
- Lombok
- MapStruct
- Postman

Приложение содержит два микро сервиса:
- основной сервис - содержит основной функционал приложения;
- сервис статистики - хранит количество просмотров и позволяет делать различные выборки для анализа работы приложения:
    - запись информации о том, что был обработан запрос к эндпоинту API;
    - предоставление статистики за выбранные даты по выбранному эндпоинту.


API основного сервиса разделена на три части:
- публичный API предоставляет возможности поиска и фильтрации событий:
    - настроена возможность получения всех имеющихся категорий и подборок событий;
    - каждый публичный запрос для получения списка событий или полной информации о мероприятии фиксируется сервисом статистики.
      
- закрытая часть API реализовывает возможности зарегистрированных пользователей продукта:
    - возможность добавлять новые мероприятия, редактировать их и просматривать после добавления;
    - подача заявок на участие в интересующих мероприятиях;
    - возможность подтверждать заявки, которые отправили другие пользователи сервиса.
      
- административная часть API предоставляет возможности настройки и поддержки работы сервиса:
    - добавление, изменение и удаление категорий для событий;
    - добавление, удаление и закрепление подборки мероприятий;
    - модерация событий размещённых пользователями — публикация или отклонение;
    - управление пользователями — добавление, активация, просмотр и удаление.

Для обоих сервисов подробные спецификации API указаны в файлах проекта:
wm-main-service-spec.json;
ewm-stats-service.json.
