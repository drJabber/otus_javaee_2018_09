package rnk.t02.utils;

public class Snils {
    private static int minCheckableNumber = 1001998;
//used code from here
//    https://github.com/diman4ik/rusnaloglib/tree/master/src/main/java/com/rusnalog/rusnaloglib
    static public boolean isValid( String snils ) {
        String[] parts = snils.split("-| ");

        String first = parts[0];
        String second = parts[1];
        String third = parts[2];
        String fourth = parts[3];

        int number = Integer.parseInt(first)*1000000 + Integer.parseInt(second)*1000 +
                Integer.parseInt(third);

        if( number < Snils.minCheckableNumber )
            return true;

        int control_computed = Snils.computeControlNumber( number );

        int control = Integer.parseInt( fourth );

        return control_computed == control;
    }

    static private int computeControlNumber(long no) {

        if( no < Snils.minCheckableNumber )
            return 0;

        int ret = 0;
        int pos = 1;
        long remains = no;

        do
        {
            int next_digit = (int )remains%10;
            remains = remains/10;

            ret += next_digit*pos++;
        }while( remains != 0 );

        if( ret > 101 )
            ret = ret/101;

        if( ( ret == 100 ) || ( ret == 101 ) )
            ret = 0;

        return ret;
    }
}
