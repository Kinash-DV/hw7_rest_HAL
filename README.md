# Task description
 
1 Створити spring boot проект “Shops”. Створити модель магазину, в класі мають
бути такі поля: айді, місто та вулиця де знаходиться магазин, назва магазину і
кількість співробітників, та поле чи є у магазина сайт. Створити контролер в
якому будуть присутні такі операції :  
1) створити новий магазин,  
2) видалити магазин ( по айдішці),  
3) отримати список всіх магазинів,  
4) отримати магазин по айдішці  
5) змінти поля магазину( по айдішці) – можна міняти всі поля крім айдішки.  

Перевірити функціонал через постман, ідею чи будь яку іншу тулзовину.
Впевнетись що CRUD операції працюють. Замість бази данних використати будь
яку колекцію, бажано всі операції зробити в окремому сервісі який буде
присутній в контролері.

2 Опційно конкретизувати статус коди, наприклад після створення магазину
повертати не 200 код а 201 Created. Якщо запитується шоп якого немає

## In the addition to https://github.com/Kinash-DV/hw7_rest added "Spring HATEOAS"