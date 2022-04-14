package prog3.complementos;

import prog3.util.PilaGenerica;

public class TestBalanceo {

    public static void main(String[] args) {
        String[] tests = new String[]{
                "Hola (esto es un ejemplo)",
                "Texto incorrecto [falta cerrar",
                "Texto incorrecto, falta abrir?",
                "Hey! ¿Cómo están?",
                "Todo bien, ¿y vos? (esperando)...",
                "{( ) [ ( ) ] }",
                "( [ ) ]",
                "}{()",
                "{})("
        };

        for (String s : tests)
            System.out.println('\"'+s+"\" is balanced? "+isStringBalanced(s));
    }

    // TODO: All right!
    public static boolean isStringBalanced(String str){
        if (str == null) return false;

        PilaGenerica<Character> reqChars = new PilaGenerica<>();
        char[] openChars = new char[]{'(','[','{','¿','¡'};
        char[] closeChars = new char[]{')',']','}','?','!'};

        for (int i=0; i<str.length(); i++){
           char actual = str.charAt(i);
           if (!reqChars.esVacia() && actual == reqChars.tope()) reqChars.desapilar();
           else for (int j=0; j<openChars.length; j++){
               if (actual == openChars[j]) reqChars.apilar(closeChars[j]);
               else if (actual == closeChars[j]) return false;
           }
        }

        return reqChars.esVacia();
    }
}
