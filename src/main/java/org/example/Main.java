package org.example;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        String[] deps = {
                "K1\\SK1",
                "K1\\SK2",
                "K1\\SK1\\SSK1",
                "K1\\SK1\\SSK2",
                "K2",
                "K2\\SK1\\SSK1",
                "K2\\SK1\\SSK2"
        };

        String[] sortedDeps = sortDeps(deps);
        for (String dept : sortedDeps) {
            System.out.println(dept);
        }
    }

    /**
     * Метод для сортировки массива подразделений
     * @param departmentArray массив строк кодов подразделений
     * @return отсортированный список подразделений
     */
    public static String[] sortDeps(String[] departmentArray) {
        // Использую TreeSet, так как автоматически поддерживает порядок элементов с помощью использования компаратора
        Set<String> departmentSet = new TreeSet<>(getComparator());

        for (String department : departmentArray) {
            StringBuilder sb = new StringBuilder();
            String[] parts = department.split("\\\\");

            for (String part : parts) {
                if (sb.length() > 0) {
                    sb.append("\\");
                }
                sb.append(part);
                departmentSet.add(sb.toString());
            }
        }
        return departmentSet.toArray(new String[0]);
    }

    /**
     * Метод, возвращающий компаратор
     * 1. Первый уровень сортируется по убыванию (например, К2 перед К1).
     * 2. Все последующие уровни сортируются по возрастанию (например, SK1 перед SK2).
     *
     * @return компаратор для сортировки подразделений
     */
    private static Comparator<String> getComparator() {
        return (dep1, dep2) -> {
            String[] department1 = dep1.split("\\\\");
            String[] department2 = dep2.split("\\\\");

            // находим минимальную длину для обхода уровней обоих подразделений
            int minLength = Math.min(department1.length, department2.length);

            // сравниваем каждый уровень, начиная с самого верхнего
            for (int i = 0; i < minLength; i++) {
                // первый уровень сортируем по убыванию
                int result = department2[i].compareTo(department1[i]);

                // если уровни не равны, возвращаем результат сравнения
                if (result != 0) {
                    return result;
                }
            }
            // Если один уровень является префиксом другого, то более короткий выше
            return Integer.compare(department1.length, department2.length);
        };
    }
}