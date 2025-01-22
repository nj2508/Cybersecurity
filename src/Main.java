import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        String phrase = "The quick brown fox jumps over the lazy dog.";

        System.out.println(encryptKey(phrase, "elephant"));
        System.out.println(decryptKey(encryptKey(phrase, "elephant"), "elephant"));
    }

    public static String encryptKey(String phrase, String key) {
        phrase = phrase.toLowerCase();
        key = key.toLowerCase();

        /*
         * get unique characters from key
         */
        ArrayList<Character> uniqueCharacters = new ArrayList<>();
        for (Character ch : key.toCharArray()) {
            if (!uniqueCharacters.contains(ch)) {
                uniqueCharacters.add(ch);
            }
        }

        /*
         * create 2d char array with length of the # of unique characters and size >=26
         */
        int rows = (int) Math.ceil(26.0 / uniqueCharacters.size());
        char[][] map = new char[rows][uniqueCharacters.size()];

        /*
         * Fill first row with unique characters
         */
        for (int i = 0; i < map[0].length; i++) {
            map[0][i] = uniqueCharacters.get(i);
        }

        /*
         * Create alphabet to pass to findNext
         */
        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        ArrayList<Character> abc = new ArrayList<>();

        for (int i = 0; i < alphabet.length(); i++) {
            abc.add(alphabet.charAt(i));
        }

        /*
         * Fill rest of map, duplicate uniqueCharacters to check for duplicates
         */
        ArrayList<Character> used = new ArrayList<>(uniqueCharacters);

        for (int i = 1; i < rows; i++) {
            for (int j = 0; j < uniqueCharacters.size(); j++) {
                char next = findNext(used, abc);
                used.add(next);
                map[i][j] = next;
            }
        }

        /*
         * Create new alphabet to pass to findNext again
         */
        String alphabet2 = "abcdefghijklmnopqrstuvwxyz";
        ArrayList<Character> abc2 = new ArrayList<>();

        for (int i = 0; i < alphabet2.length(); i++) {
            abc2.add(alphabet2.charAt(i));
        }

        /*
         * Create alphabet to reference and make it a matching map
         */
        ArrayList<Character> usedABC = new ArrayList<>();
        char[][] abcMap = new char[rows][uniqueCharacters.size()];

        for (int i = 0; i < uniqueCharacters.size(); i++) {
            for (int j = 0; j < rows; j++) {
                if (map[j][i] != '0') {
                    char next = findNext(usedABC, abc2);
                    abcMap[j][i] = next;
                    usedABC.add(next);
                } else {
                    abcMap[j][i] = '0';
                 }
            }
        }

        /*
         * Finished generating grids, time to encrypt.
         */

        String encrypted = "";
        for (int n = 0; n < phrase.length(); n++) {
            char ch = phrase.charAt(n);
            if (Character.isLetter(ch)) {
                int column = 0;
                int row = 0;

                /*
                 * get location of the char in the abc map
                 */
                for (int i = 0; i < abcMap.length; i++) {
                    for (int j = 0; j < abcMap[0].length; j++) {
                        if (ch == abcMap[i][j]) {
                            column = i;
                            row = j;
                            break;
                        }
                    }
                }

                /*
                 * get char at that location in encrypted map
                 */
                encrypted += map[column][row];
            } else { // keep non-letters as-is
                encrypted += ch;
            }

        }

        /*
         * Prints the maps
         */
//        for (int i = 0; i < map.length; i++) {
//            for (int j = 0; j < map[0].length; j++) {
//                System.out.print(map[i][j]);
//            }
//            System.out.println("");
//        }
//
//        for (int i = 0; i < abcMap.length; i++) {
//            for (int j = 0; j < abcMap[0].length; j++) {
//                System.out.print(abcMap[i][j]);
//            }
//            System.out.println("");
//        }

        return encrypted;
    }

    public static String decryptKey(String phrase, String key) {
        phrase = phrase.toLowerCase();
        key = key.toLowerCase();

        /*
         * get unique characters from key
         */
        ArrayList<Character> uniqueCharacters = new ArrayList<>();
        for (Character ch : key.toCharArray()) {
            if (!uniqueCharacters.contains(ch)) {
                uniqueCharacters.add(ch);
            }
        }

        /*
         * create 2d char array with length of the # of unique characters and size >=26
         */
        int rows = (int) Math.ceil(26.0 / uniqueCharacters.size());
        char[][] map = new char[rows][uniqueCharacters.size()];

        /*
         * Fill first row with unique characters
         */
        for (int i = 0; i < map[0].length; i++) {
            map[0][i] = uniqueCharacters.get(i);
        }

        /*
         * Create alphabet to pass to findNext
         */
        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        ArrayList<Character> abc = new ArrayList<>();

        for (int i = 0; i < alphabet.length(); i++) {
            abc.add(alphabet.charAt(i));
        }

        /*
         * Fill rest of map, duplicate uniqueCharacters to check for duplicates
         */
        ArrayList<Character> used = new ArrayList<>(uniqueCharacters);

        for (int i = 1; i < rows; i++) {
            for (int j = 0; j < uniqueCharacters.size(); j++) {
                char next = findNext(used, abc);
                used.add(next);
                map[i][j] = next;
            }
        }

        /*
         * Create new alphabet to pass to findNext again
         */
        String alphabet2 = "abcdefghijklmnopqrstuvwxyz";
        ArrayList<Character> abc2 = new ArrayList<>();

        for (int i = 0; i < alphabet2.length(); i++) {
            abc2.add(alphabet2.charAt(i));
        }

        /*
         * Create alphabet to reference and make it a matching map
         */
        ArrayList<Character> usedABC = new ArrayList<>();
        char[][] abcMap = new char[rows][uniqueCharacters.size()];

        for (int i = 0; i < uniqueCharacters.size(); i++) {
            for (int j = 0; j < rows; j++) {
                if (map[j][i] != '0') {
                    char next = findNext(usedABC, abc2);
                    abcMap[j][i] = next;
                    usedABC.add(next);
                } else {
                    abcMap[j][i] = '0';
                }
            }
        }

        /*
         * Finished generating grids, time to decrypt.
         */

        String decrypted = "";
        for (int n = 0; n < phrase.length(); n++) {
            char ch = phrase.charAt(n);
            if (Character.isLetter(ch)) {
                int column = 0;
                int row = 0;

                /*
                 * get location of the char in the decrypting map
                 */
                for (int i = 0; i < map.length; i++) {
                    for (int j = 0; j < map[0].length; j++) {
                        if (ch == map[i][j]) {
                            column = i;
                            row = j;
                            break;
                        }
                    }
                }

                /*
                 * get char at that location in abc map
                 */
                decrypted += abcMap[column][row];
            } else { // keep non-letters as-is
                decrypted += ch;
            }
        }
        return decrypted;
    }
    public static char findNext(ArrayList<Character> used, ArrayList<Character> alphabet) {
        if (alphabet.size() > 0) {
            if (used.contains(alphabet.get(0))) {
                alphabet.remove(0);
                return findNext(used, alphabet);
            } else {
                return alphabet.get(0);
            }
        } else {
            return '0';
        }
    }

    public static String encryptBasic(String phrase) {
        /*
         * get int values
         */
        ArrayList<Integer> charVals = new ArrayList<>();
        for (char ch : phrase.toCharArray()) {
            charVals.add(ch - 32);
        }

        /*
         * encrypt via method
         * C = 3*P
         */
        String encrypted = "";
        for (int i = 0; i < charVals.size(); i++) {
            //System.out.println(charVals.get(i));
            encrypted += (char) ((3 * charVals.get(i)) % 95);
        }
        return encrypted;
    }
    public static String decryptBasic(String phrase) {

        /*
         * get int values
         */
        ArrayList<Integer> charVals = new ArrayList<>();
        for (char ch : phrase.toCharArray()) {
            charVals.add(ch - 0);
        }

        /*
         * decrypt via method
         */
        String decrypted = "";
        for (int i = 0; i < charVals.size(); i++) {
            decrypted += (char) ((32 * charVals.get(i)) % 95 + 32);
        }
        return decrypted;
    }

}