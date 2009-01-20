package cz.muni.stanse.utils;

import java.util.LinkedList;
import java.util.HashSet;

public final class Make {

    public static <T> LinkedList<T> linkedList() {
        return new LinkedList<T>();
    }

    public static <T> LinkedList<T> linkedList(final T a1) {
        return add(Make.<T>linkedList(),a1);
    }

    public static <T> LinkedList<T> linkedList(final T a1, final T a2) {
        return add(Make.<T>linkedList(a1),a2);
    }

    public static <T> LinkedList<T> linkedList(final T a1, final T a2,
                                               final T a3) {
        return add(Make.<T>linkedList(a1,a2),a3);
    }

    public static <T> LinkedList<T> linkedList(final T a1, final T a2,
                                               final T a3, final T a4) {
        return add(Make.<T>linkedList(a1,a2,a3),a4);
    }

    public static <T> HashSet<T> hashSet() {
        return new HashSet<T>();
    }

    public static <T> HashSet<T> hashSet(final T a1) {
        return add(Make.<T>hashSet(),a1);
    }

    public static <T> HashSet<T> hashSet(final T a1, final T a2) {
        return add(Make.<T>hashSet(a1),a2);
    }

    public static <T> HashSet<T> hashSet(final T a1, final T a2,
                                               final T a3) {
        return add(Make.<T>hashSet(a1,a2),a3);
    }

    public static <T> HashSet<T> hashSet(final T a1, final T a2,
                                               final T a3, final T a4) {
        return add(Make.<T>hashSet(a1,a2,a3),a4);
    }

    // private section

    private static <T> LinkedList<T> add(final LinkedList<T> list, final T a) {
        list.add(a);
        return list;
    }

    private static <T> HashSet<T> add(final HashSet<T> list, final T a) {
        list.add(a);
        return list;
    }

    private Make() {
    }
}
