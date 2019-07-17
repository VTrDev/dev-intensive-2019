package ru.skillbranch.devintensive.models

class Bender(var status: Status = Status.NORMAL, var question: Question = Question.NAME) {

    fun askQuestion(): String = when (question) {
        Question.NAME -> Question.NAME.question
        Question.PROFESSION -> Question.PROFESSION.question
        Question.MATERIAL -> Question.MATERIAL.question
        Question.BDAY -> Question.BDAY.question
        Question.SERIAL -> Question.SERIAL.question
        Question.IDLE -> Question.IDLE.question
    }

    fun listenAnswer(answer: String): Pair<String, Triple<Int, Int, Int>> {
        if (!question.validate(answer)) {
            return when(question) {
                Question.NAME -> "Имя должно начинаться с заглавной буквы"
                Question.PROFESSION -> "Профессия должна начинаться со строчной буквы"
                Question.MATERIAL -> "Материал не должен содержать цифр"
                Question.BDAY -> "Год моего рождения должен содержать только цифры"
                Question.SERIAL -> "Серийный номер содержит только цифры, и их 7"
                Question.IDLE -> ""//игнорировать валидацию
            } + "\n${question.question}" to status.color
        }

        return if (question.answers.contains(answer)) {
            question = question.nextQuestion()
            "Отлично - ты справился\n${question.question}" to status.color
        } else {
            status = status.nextStatus()
            var negativeMsg = "Это неправильный ответ\n${question.question}" to status.color
            if (status == Status.NORMAL) {
                question = Question.NAME
                negativeMsg = "Это неправильный ответ. Давай все по новой\n${question.question}" to status.color
            }
            negativeMsg
        }
    }

    enum class Status(val color: Triple<Int, Int, Int>) {
        NORMAL(Triple(255, 255, 255)),
        WARNING(Triple(255, 120, 0)),
        DANGER(Triple(255, 60, 60)),
        CRITICAL(Triple(255, 0, 0));

        fun nextStatus(): Status {
            return if (this.ordinal < values().lastIndex) {
                values()[this.ordinal + 1]
            } else {
                values()[0]
            }
        }
    }

    enum class Question(val question: String, val answers: List<String>) {
        NAME("Как меня зовут?", listOf("Бендер", "Bender")) {
            override fun validate(answer: String) = answer.matches("[A-ZА-Я].*".toRegex())
            override fun nextQuestion(): Question = PROFESSION
        },
        PROFESSION("Назови мою профессию?", listOf("сгибальщик", "bender")) {
            override fun validate(answer: String) = answer.matches("[a-zа-я].*".toRegex())
            override fun nextQuestion(): Question = MATERIAL
        },
        MATERIAL("Из чего я сделан?", listOf("металл", "дерево", "metal", "iron", "wood")) {
            override fun validate(answer: String) = answer.matches("\\D+".toRegex())
            override fun nextQuestion(): Question = BDAY
        },
        BDAY("Когда меня создали?", listOf("2993")) {
            override fun validate(answer: String) = answer.matches("\\d+".toRegex())
            override fun nextQuestion(): Question = SERIAL
        },
        SERIAL("Мой серийный номер?", listOf("2716057")) {
            override fun validate(answer: String) = answer.matches("\\d{7}".toRegex())
            override fun nextQuestion(): Question = IDLE
        },
        IDLE("На этом все, вопросов больше нет", listOf()) {
            override fun validate(answer: String) = true
            override fun nextQuestion(): Question = IDLE
        };

        abstract fun nextQuestion(): Question
        abstract fun validate(answer: String): Boolean
    }
}