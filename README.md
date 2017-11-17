Приложение "react-comments" выполнено на react. 
1.БАЗА ДАННЫХ. 
Развернута на Firebase облачной NoSQL БД для real-time приложений, которая предоставляет API, по адресу https://test-6b7de.firebaseio.com/
Выдает API ответ по адресу https://test-6b7de.firebaseio.com/comments.json
В ответ предоставляет comments.json, следующего содержания-формата:
{
  "id записи- автоматически генерируется базой"
    {
      "name":"имя коментатора",
      "text":"текст сообщения"
     }
}

ПРИМЕР:

{"-Kz9_VzQ6nU5CqKedQkY":{"name":"Сергей","text":"Привет мир!!!"},"-Kz9__mdFFEAQB3Yysv9":{"name":"Сергей","text":"Всем привет!!!"}}

{
  "-Kz9_VzQ6nU5CqKedQkY":
    {
      "name":"Сергей",
      "text":"Привет мир!!!"
     },
  "-Kz9__mdFFEAQB3Yysv9":
    {
    "name":"Сергей",
    "text":"Всем привет!!!"
    }
}

2. ФУНКЦИОНАЛ.
- добавление / удаление комментария
- добавление Имени автора комментария
- отображение комментариев
- скрыть / показать комментарии
- счетчик колличества комментариев

![Image alt](https://github.com/dastepan/ru.mera.training/blob/SobolevSergey/react-comments/commens.png)




ЗАГОТОВКА ПОД ПРОЕКТ "SHAWERMA"

![Image alt](https://github.com/dastepan/ru.mera.training/blob/SobolevSergey/src/dstr/main.png)
![Image alt](https://github.com/dastepan/ru.mera.training/blob/SobolevSergey/src/dstr/order.png)
![Image alt](https://github.com/dastepan/ru.mera.training/blob/SobolevSergey/src/dstr/custom.png)
