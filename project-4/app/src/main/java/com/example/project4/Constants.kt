package com.example.project4

import java.lang.reflect.Field


object Constants {

    const val USER_NAME: String = "user_name"
    const val TOTAL_QUESTIONS: String = "total_questions"
    const val CORRECT_ANSWERS: String = "correct_answers"

    fun getQuestions(): ArrayList<Question> {
        val questionsList = ArrayList<Question>()
        val drawablesFields: Array<Field> = R.drawable::class.java.fields
        val imageIdsAndFlags = ArrayList<ArrayList<Any>>()
        val flags = ArrayList<String>()
        for (field in drawablesFields) {
            val fileName = field.toString().split('.')[4]
            if (fileName.startsWith("ic_flag_of_")) {
                val words = fileName.substring("ic_flag_of_".length).split("_")
                var flag = ""
                for (i in words.indices) {
                    flag += words[i][0].toUpperCase() + words[i].substring(1)
                    if (i != words.size-1) {
                        flag += " "
                    }
                }
                flags.add(flag)
                val id: Int = R.drawable::class.java.getField(fileName).getInt(null)
                val tmp = ArrayList<Any>()
                tmp.add(id)
                tmp.add(flag)
                imageIdsAndFlags.add(tmp)
            }
        }

        val shuffledImages = imageIdsAndFlags.shuffled()
        val first30Questions = shuffledImages.slice(0..9)
        val options = ArrayList<ArrayList<String>>()
        val correctAnswers = ArrayList<Int>()
        var counter = 0
        for (imageIdAndFlag in first30Questions) {
            val nonCorrect3Options = (flags.shuffled().filter { flag -> flag != (imageIdAndFlag[1] as String) }).slice(0..2)
            var finalOptions = ArrayList<String>()
            finalOptions.add(imageIdAndFlag[1] as String)
            finalOptions.addAll(nonCorrect3Options)
            finalOptions = finalOptions.shuffled() as ArrayList<String>
            correctAnswers.add(finalOptions.indexOf(imageIdAndFlag[1])+1)
            options.add(finalOptions)
            val question = Question(
                counter+1, "Which country does this flag belong to ?",
                imageIdAndFlag[0] as Int,
                finalOptions[0], finalOptions[1], finalOptions[2], finalOptions[3],
                correctAnswers[counter]
            )
            counter++
            questionsList.add(question)
        }

        return questionsList
    }
}