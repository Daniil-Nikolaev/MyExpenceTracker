Основные endpoint:
- Users:
POST http://localhost:8080/users - создание нового пользователя;
Тело запроса:
{
    "name": "имя",
    "password": "пароль",
    "balance": баланс
}
GET http://localhost:8080/users -получение всех пользователей;
GET http://localhost:8080/users/{id}-получение пользователя по id;
DELETE http://localhost:8080/users/{id}-удаление пользователя по id;
PUT http://localhost:8080/users/{id} - обновление пользователя по id;
Тело запроса:
{
    "name": "новое имя",
    "password": "новый пароль",
    "balance": баланс
}
- Categories:
POST http://localhost:8080/categories - создание новой категории;
Тело запроса:
{
    "name":"название категории"
}
GET http://localhost:8080/categories -получение всех категорий;
DELETE http://localhost:8080/categories/{id}-удаление категории по id;
-Transactions:
POST http://localhost:8080/transactions?userId={user_id}-создание транзакции по user_id;
Тело запроса:
{
    "description": "описание",
    "amount": стоимость,
    "date": "дата",
    "categories": [
        {
            "id": id_category,
            "name": "название категории"
     }
    ]
}
GET http://localhost:8080/transactions?userId={user_id}-получение всех транзакций пользователя по user_id;
GET http://localhost:8080/transactions/{transaction_id}?userId={user_id}-получение конкретной транзакций {transaction_id} пользователя по user_id;
DELETE http://localhost:8080/transactions/{transaction_id}?userId={user_id}-удаление конкретной транзакций {transaction_id} пользователя по user_id;
PUT http://localhost:8080/transactions/{transaction_id}?userId={user_id}-обновление конкретной транзакций {transaction_id} пользователя по user_id;
Тело запроса:
{
    "description": "новое описание",
    "amount": новая стоимость,
    "date": "новая дата",
    "categories": [
        {
            "id":id_category,
            "name": "название категори"
        }
    ]
}
