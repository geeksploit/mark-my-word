# mark-my-word

## Цель проекта:</br>
Проект создается с цель не просто помочь  людям в изучении иностранного языка, но и ускорить чтение литературы на иностранном языке.
Все мы знаем множество приложений которые позволяют увеличить словарный запас путем просмотра карточек со словами и переводом к ним. Слов в языке довольно много и как скоро вы запомните достаточное колличество слов для прочтения конкретной книги, которую Вы хотите прочитать, сейчас?
Наше приложение Приложение призвано дать ответ на этот вопрос, Вам для изучения будут предложены именно те слова с которыми вы столкнетесь в книге. Что позволит Вам начать читать книгу на иностранном языке с минимумом отвлечения на перевод в кротчайшие сроки.

## Функционал [x] <- реализовано, [ ] <- в процессе реализации
[x] 1. Считывание книг следующих форматов:</br>
[x] `txt` - plain text UTF-8;</br>
[ ] `pdf` - Portable Document Format (not bitmap);</br>
[ ] `fb2` - Fiction Book / Feed book;</br>
</br>
[x] 2. Разбирать книгу на уникальные слова (отсеивать повторения);</br>
[x] 3. Сохранять полученные данные в БД, формируя словари под каждую обработтаную книгу;</br>
[x] 4. Получать перевод слов из сервиса YandexTranslate и фиксировать полученые данные в БД;</br>
[x] 5. Предоставлять пользльзователю возможность открывать ранее сформированные словари;</br>
[x] 6. Отображение слов в виде списка или карточек;</br>
[ ] 7. Поиск по по словарю;</br>
[ ] 8. Сортировка слов в словаре;</br>
[ ] 9. Загрузка изображений соответствующих словам для закрепления выученых слов с помощью зрительной памяти.</br>
<img src="https://github.com/geeksploit/mark-my-word/blob/master/list_view.png" width=108px height=192px alt="List view"/>
<img src="https://github.com/geeksploit/mark-my-word/blob/master/card_view.png"  width=108px height=192px alt="Card view"/>
<img src="https://github.com/geeksploit/mark-my-word/blob/master/card_view_image.png" width=108px height=192px alt="Card view with image"/>
<img src="https://github.com/geeksploit/mark-my-word/blob/master/parse_in_progress.png"  width=108px height=192px alt="Parse in progress"/>
<img src="https://github.com/geeksploit/mark-my-word/blob/master/parse_cancel.png"  width=108px height=192px alt="Cancel parsing"/>
<img src="https://github.com/geeksploit/mark-my-word/blob/master/parse_complete.png" width=108px height=192px alt="Parse complete"/>
<img src="https://github.com/geeksploit/mark-my-word/blob/master/navigation.png"  width=108px height=192px alt="Navigation"/>
<img src="https://github.com/geeksploit/mark-my-word/blob/master/select_from_lib.png"  width=108px height=192px alt="Select from lib"/>

## Архитектура и технологии.
Проект реализован на архитектуре `MVP` на языке `Java` с применением следующих технологий:</br>
0. Визуальное представление `RecyclerView` и `CardView`;</br>
1. Асинхронная обработка данных с помощью `RxJava`;</br>
2. Получение данных из сети с использованием `Retrofit`;</br>
3. База данных `SQLite`;</br>
4. ORM для работы с БД `Room persistence`;</br>
5. Работа с изображениями `Glide`;</br>
6. Внедрение зависимостей реализовано с помощью `Dagger2`;</br>
7. Работа с JSON с использованием `Gson`;
8. Сохранение настроек в `SharedPreferences` реализованос применением JSON;

### Архитектура БД:<br>
<image src="https://github.com/geeksploit/mark-my-word/blob/master/scheme_db.jpg?raw=true">Архитектура БД</image>

## Команда проекта:</br>
### [Максим Ремыга](https://github.com/geeksploit) </br></br>
#### - Идея;</br>
#### - Архитектура БД;</br></br>
### [Артем Шумилов](https://github.com/SupNacho)</br>
#### - [Прототипирование](https://xd.adobe.com/view/c088cdd4-d0fd-4618-5b4e-459997207a44-b6b9/);</br>
#### - Архитектура приложения;</br>
#### - Выбор технологий;</br>
#### - Написание кода;</br></br>
### [Ильяс Султанов](https://github.com/qervot) </br>
#### - Разработка парсера книг в формате `pdf`;</br>

