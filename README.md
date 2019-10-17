Сделать отдельный репозиторий на github и сделать к нему подробное описание README.md с форматом API и примерами.

Images api(Tomcat):

1)POST
отправляет картинку на сервер в JSON:
- описание картинки (description), например "logo";
- сама картинка в формате base64 (/9j/4AAQSkZJRgABAQAAAQABAAD/2wCEAAkGBxAQEBU ...).
Сервер возвращает ID картинки и сохраняет в базу (postgreSQL).

2)GET(/OlesiiukAnna/api)
возвращает список всех картинок в формате json, без самих картинок, только описание и id.

3)GET(/OlesiiukAnna/api/{id})
возвращает картинку по id по прямой ссылке.
