package com.example.kos.mysecrect.utils;

import java.util.Arrays;
import java.util.List;

public class DecryptionUtils {

   private static final List<String> supplierNames = Arrays.asList("1","2","3","4","5","6","7","8","9",
            "A","B","C","D","E","F","G","H","I","J","K","L","M","N",
            "O","P","Q","R","S","T","U","V","W","X","Y","Z","0");
   private static final List<String> aliasNames = Arrays.asList("8","9",
           "A","B","C","D","E","F","G","H","I","J","K","L","M","N",
           "O","P","Q","4","5","6","7","R","S","T","U","V","W","X","Y","Z","0","1", "2","3");

   private static final int indexBuff = 7;

    public static String ecryptToMacAddr(String input) {
        StringBuilder newMac = new StringBuilder();
        for (char ch: input.toCharArray()) {
            int index = supplierNames.indexOf(String.valueOf(ch));
            if (index >= 0) {
                index = index + indexBuff;
                if (index > supplierNames.size()) {
                    index = index - supplierNames.size();
                }
            }
            newMac.append(supplierNames.get(index));

        }
        return newMac.toString();
    }

    public static String decryptToMacAddr(String input) {
        if (input.length() < 5) {
            return "";
        }
        input = input.substring(2);
        input = input.substring(0, input.length()-2);
        StringBuilder newMac = new StringBuilder();
        for (char ch: input.toCharArray()) {
            int index = aliasNames.indexOf(String.valueOf(ch));
            if (index >= 0) {
                newMac.append(supplierNames.get(index));
            }
        }
        return newMac.toString();
    }
}
