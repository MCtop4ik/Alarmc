package com.example.alarmc;

import java.util.ArrayList;
import java.util.List;

public class TaskFile {

    public static final int cnt_tasks = 5;

    public static List<Integer> banned_themes = new ArrayList<>();

    /*
    1 - Логарифмические уравнения
    2 - Обычные Уравнения
    30 - Обычный пример
     */

    public static final String[] task_l = {
            "Решите уравнение",
            "Найдите значение выражения",
            "Решите уравнение. Если в ответе несколько корней. Запишите наименьший из них",
            "Решите уравнение. Если в ответе несколько корней. Запишите наибольший из них"
    };
    // task['label of task'(String), 'index of task from task_l'(Integer),
    // 'answer on task'(String || Integer)]
    public static final Object[][] task = {
            {"㏒₂8=x", 0, 3, 1},
            {"㏒₂16=x", 0, 4, 1},
            {"⅕+⅖=?", 1, "0,6", 30},
            {"㏒₂(4-x)=7", 0, "-124", 1},
            {"㏒₂(15+x)=㏒₂3", 0, "-12", 1},
            {"㏒₅(5-x)=㏒₅3", 0, "2", 1},
            {"x³=x²-7x+7", 0, 1, 2},
            {"(2x-3)²=(1-2x)²", 0, 1, 2},
            {"(x-3)(x-4)(x-5)=(x-2)(x-4)(x-5)", 2, 4, 2},
            {"x⁶=(6x-5)³", 3, 5, 2}
    };
}
