package org.baibei.keyboardtrackerdesktop.pojo.keys;

import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import org.baibei.keyboardtrackerdesktop.natives.KeyBoardLayout;

public class KeyConvertor {
    public static String convert(String keyText, String keyboardLocation) {
        if (keyboardLocation.equals("EN") || keyboardLocation.equals("CN")) {
            return switch (keyText) {
                case "A" -> "a";
                case "B" -> "b";
                case "C" -> "c";
                case "D" -> "d";
                case "E" -> "e";
                case "F" -> "f";
                case "G" -> "g";
                case "H" -> "h";
                case "I" -> "i";
                case "J" -> "j";
                case "K" -> "k";
                case "L" -> "l";
                case "M" -> "m";
                case "N" -> "n";
                case "O" -> "o";
                case "P" -> "p";
                case "Q" -> "q";
                case "R" -> "r";
                case "S" -> "s";
                case "T" -> "t";
                case "U" -> "u";
                case "V" -> "v";
                case "W" -> "w";
                case "X" -> "x";
                case "Y" -> "y";
                case "Z" -> "z";
                case "0" -> "0";
                case "1" -> "1";
                case "2" -> "2";
                case "3" -> "3";
                case "4" -> "4";
                case "5" -> "5";
                case "6" -> "6";
                case "7" -> "7";
                case "8" -> "8";
                case "9" -> "9";
                case "Space", "Tab", "Enter" -> " ";
                case "Backspace" -> "<";
                default -> null;
            };
        } else if (keyboardLocation.equals("RU")) {
            return switch (keyText) {
                case "A" -> "ф";
                case "B" -> "и";
                case "C" -> "с";
                case "D" -> "в";
                case "E" -> "у";
                case "F" -> "а";
                case "G" -> "п";
                case "H" -> "р";
                case "I" -> "ш";
                case "J" -> "о";
                case "K" -> "л";
                case "L" -> "д";
                case "M" -> "ь";
                case "N" -> "т";
                case "O" -> "щ";
                case "P" -> "з";
                case "Q" -> "й";
                case "R" -> "к";
                case "S" -> "ы";
                case "T" -> "е";
                case "U" -> "г";
                case "V" -> "м";
                case "W" -> "ц";
                case "X" -> "ч";
                case "Y" -> "н";
                case "Z" -> "я";
                case "Comma" -> "б";
                case "Period" -> "ю";
                case "Semicolon" -> "ж";
                case "Quote" -> "э";
                case "Open Bracket" -> "х";
                case "Close Bracket" -> "ъ";
                case "Back Quote" -> "ё";
                case "0" -> "0";
                case "1" -> "1";
                case "2" -> "2";
                case "3" -> "3";
                case "4" -> "4";
                case "5" -> "5";
                case "6" -> "6";
                case "7" -> "7";
                case "8" -> "8";
                case "9" -> "9";
                case "Backspace" -> "<";
                default -> null;
            };
        } else {
            return null;
        }
    }

    public static String shiftedConvert(String keyText, String keyboardLocation) {
        return switch (keyboardLocation) {
            case "EN", "CN" -> switch (keyText) {
                case "A" -> "a";
                case "B" -> "b";
                case "C" -> "c";
                case "D" -> "d";
                case "E" -> "e";
                case "F" -> "f";
                case "G" -> "g";
                case "H" -> "h";
                case "I" -> "i";
                case "J" -> "j";
                case "K" -> "k";
                case "L" -> "l";
                case "M" -> "m";
                case "N" -> "n";
                case "O" -> "o";
                case "P" -> "p";
                case "Q" -> "q";
                case "R" -> "r";
                case "S" -> "s";
                case "T" -> "t";
                case "U" -> "u";
                case "V" -> "v";
                case "W" -> "w";
                case "X" -> "x";
                case "Y" -> "y";
                case "Z" -> "z";
                case "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" -> " ";
                default -> "null";
            };
            case "RU" -> switch (keyText) {
                case "A" -> "ф";
                case "B" -> "и";
                case "C" -> "с";
                case "D" -> "в";
                case "E" -> "у";
                case "F" -> "а";
                case "G" -> "п";
                case "H" -> "р";
                case "I" -> "ш";
                case "J" -> "о";
                case "K" -> "л";
                case "L" -> "д";
                case "M" -> "ь";
                case "N" -> "т";
                case "O" -> "щ";
                case "P" -> "з";
                case "Q" -> "й";
                case "R" -> "к";
                case "S" -> "ы";
                case "T" -> "е";
                case "U" -> "г";
                case "V" -> "м";
                case "W" -> "ц";
                case "X" -> "ч";
                case "Y" -> "н";
                case "Z" -> "я";
                case "Comma" -> "б";
                case "Period" -> "ю";
                case "Semicolon" -> "ж";
                case "Quote" -> "э";
                case "Open Bracket" -> "х";
                case "Close Bracket" -> "ъ";
                case "Back Quote" -> "ё";
                case "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" -> " ";
                default -> "null";
            };
            default -> "null";
        };
    }

    public static String convert(int keyboardCode) {
        return switch (keyboardCode) {
            case 1033 -> "EN";
            case 1049 -> "RU";
            case 2052 -> "CN";
            default -> "Non";
        };
    }

    public static Key convertKey(int keyCode, boolean shifted) {
        String keyName = NativeKeyEvent.getKeyText(keyCode);

        KeyBoardLayout layout = new KeyBoardLayout();
        int layoutCode = layout.getKeyBoardLayout();

        String letter;
        if (shifted) {
            letter = shiftedConvert(keyName, KeyConvertor.convert(layoutCode));
        } else {
            letter = convert(keyName, KeyConvertor.convert(layoutCode));
        }

        if (letter != null && letter.length() == 1) {
            return new Key(letter.charAt(0));
        } else {
            return new Key(' ');
        }
    }
}
