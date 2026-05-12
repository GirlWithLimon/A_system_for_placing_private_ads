"endpoints" : {
    "GET /api/advertisements?category={category}" : "Фильтр по категории",
    "GET /api/users" : "Список пользователей",
    "POST /api/chats" : "Создать чат",
    "POST /api/users/{id}/score" : "Поставить оценку",
    "PUT /api/users/{id}/score/{idscore}" : "Изменить оценку",
    "POST /api/chats/{id}" : "Отправить сообщение",
    "GET /api/my/advertisements" : "Мои объявления",
    "DELETE /api/chats/{id}" : "Удалить чат",
    "GET /api/advertisements/{id}" : "Объявление по ID",
    "PATCH /api/my/advertisements/{id}" : "Изменить объявление",
    "PATCH /api/my/advertisements/{id}/byestatus" : "Оплатить топ (ADMIN)",
    "POST /api/register" : "Регистрация",
    "GET /api/chats/{id}" : "Чат с сообщениями",
    "DELETE /api/users/{id}/score/{idscore}/admin" : "Удалить оценку (ADMIN)",
    "POST /api/my/advertisements" : "Создать объявление",
    "DELETE /api/users/{id}/score/{idscore}" : "Удалить оценку",
    "GET /api/chats" : "Список чатов",
    "DELETE /api/chats/{id}/{idmessage}" : "Удалить сообщение",
    "GET /api/users/{id}" : "Пользователь по ID",
    "DELETE /api/advertisements/{id}" : "Удалить объявление (ADMIN)",
    "PUT /api/chats/{id}/{idmessage}" : "Изменить сообщение",
    "GET /api/my/profile" : "Мой профиль",
    "PUT /api/my/profile" : "Изменить профиль",
    "POST /api/login" : "Аутентификация",
    "GET /api/advertisements" : "Список объявлений",
    "GET /api/users/{id}/score" : "Оценки пользователя",
    "DELETE /api/my/advertisements/{id}" : "Удалить объявление"

    Данные для базового пользователя { "username": "user1", "password": "12345" }

Данные для администратора: { "username": "admin", "password": "admin" }

При регистрации можно создать только пользователя. Права администратора назначаются через: docker exec bookstore_postgres psql -U bookstore_user -d bookstore_db -c "UPDATE users SET role = 'ROLE_ADMIN' WHERE username = 'admin';"
в IntelliJ IDEA Community Edition
