Общая информация
  Представьте, что компания, в которой вы работаете, начинает разработку новой игры. Вашей задачей является проектирование и разработка Rest-сервиса (включая выбор технологии и продумывание структуры хранилища данных), который позволял бы выполнять синхронизацию пользовательских данных, а так же принимать от пользователя и накапливать игровую статистику по каждому игроку. 

  Синхронизация
  Передаваемая от пользователя информация для синхронизации - это строковый Json о структуре которого ничего не известно за исключением следующего: в корне документа должно присутствовать поле "money", в котором будет содержаться целое числовое значение и поле "country" с буквенным идентификатором страны пользователя (предполагается, что страна для конкретного пользователя не изменяется). Также известно, что передаваемый от пользовател JSON не должен превышать 10кб. Прогнозируемое  количество пользователей приложения - 100 000 000 человек, из которых активными ежедневно будут около 1 000 000 пользователей. Распределение активности пользователей по времени дня будет равномерным. Предполагается, что каждый день в среднем активный пользователь будет 100 раз отправлять на сервер информацию о синхронизации и 1 раз запрашивать информацию о своем текущем состоянии с сервера.

  Игровая статистика
  Пользовательская активность в игре за период измеряется на клиенте с помощью некоторого целочисленного показателя "activity". Каждый активный пользователь в день будет присылать 10000 раз значение этого показателя на сервер, эти данные необходимо принимать и накапливать.

  Требования от отдела аналитиков
  При разработке решения нужно учитывать требования, которые были поставлены перед вами аналитическим отделом, им требуется (Х,Y - параметры запросов):
  - Быстро выбирать Х пользователей с самым большим текущим значением "money" по каждой стране "country". 
  - Иметь возможность быстро подсчитать количество новых пользователей по каждой стране за период Х.
  - Для каждого конкретного пользователя X быстро получить отсортированный по дате список значений показателя "activity" и даты их получения за период Y.

////////////////////////////////////
Формальная постановка задачи:
  - Реализовать рест сервис с тремя эндпоинтами: 
      - прием данных синхронизации от пользователя (вход: строка - данные пользователя в формате Json, строка - UUID пользователя | выход: подтверждение получения | 100 раз в день на пользователя)
      - отправка данных пользователю (вход: строка - UUID пользователя | выход: строка - сохраненные данные пользователя в формате Json | 1 раз в день на пользователя)
      - прием игровой статистики от пользователя (вход: числовое значение - activity пользователя, строка - UUID пользователя | выход: подтверждение получения | 10000 раз в день на пользователя)
  - Выбрать технологии хранения данных (их может быть несколько), спроектировать и реализовать структуру хранения, соответствующую решаемым задачам.
  - Реализовать три запроса к системам хранения данных (на соответствующем им языке), которые позволят выбирать информацию в соответствии с требованиями аналитического отдела. 
  - Написать unit тесты, соответствующие вашему понимаю "хороших тестов", позволяющие автоматически верифицировать логику хотя бы одного рест эндпоинта. 

Требования к используемым технологиям:
  - Необходимо использовать J8, Spring IoC, фреймворк для Rest сервисов http://sparkjava.com/, Maven. 
  - Технологии хранения данных определяются вами в соответствии с задачей, но предпочтение следует отдавать популярным вариантам http://db-engines.com/en/ranking. 
  - При выборе технических решений следует исходить из того, что выделение ресурсов для горизонтального масштабирования инфраструктуры не является проблемой для компании.