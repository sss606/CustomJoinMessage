package ru.solomka.join.core.messages.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListHandler {

    public static Placeholders replace(String from, String to) {
        Placeholders sender = new Placeholders();
        return sender.replace(from, to);
    }

   public static class Placeholders {
       private final Map<String, String> placeholders = new HashMap<>();

       public Placeholders replace(String from, String to) {
           placeholders.put(from, to);
           return this;
       }

       public List<String> list(List<String> list) {
           return replaceList(list);
       }

       private List<String> replaceList(List<String> list) {
           for (Map.Entry<String, String> entry : placeholders.entrySet()) {
               list.replaceAll(s -> s.replace(entry.getKey(), entry.getValue()));
           }
           return list;
       }
   }
}