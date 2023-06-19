import java.util.Scanner;
import java.util.regex.Pattern;

public class CalculatorFSS {

    enum Number {
        C(100, "C"),XC(90, "XC"),LXXX(80, "LXXX"),LXX(70, "LXX"),
        LX(60, "LX"),L(50,"L"),XL(40, "XL"),XXX(30, "XXX"),
        XX(20, "XX"),X(10, "X"),IX(9,"IX"),VIII(8,"VIII"),
        VII(7,"VII"),VI(6,"VI"),V(5, "V"),IV(4,"IV"),
        III(3,"III"),II(2,"II"),I(1,"I");

        private int i;
        private String num;
        Number(int i, String num) {
            this.i = i;
            this.num = num;
        }
        public int getI(){
            return  i;
        }
        public String getNum(){
            return num;
        }
    }
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        String input = scanner.next();
        scanner.close();
        int ind = parser(input.trim());
        switch (ind){
            case 1 -> arabicCalcPars(input.trim());
            case 2 -> romaCalcPars(input.trim());
            default -> breakingCalc(1);
        }

    }
    public static int parser(String s){
        if (Pattern.matches("[1-9][-+*/0-9]{2,4}+",s)) {
           return 1;
        } else if(Pattern.matches("[XVI][-+*/XVI]{2,8}", s)){
           return 2 ;
        }else  return 3;
    }
    static int i = 0;
    static int a = 0;
    static int b = 0;
    static int c = 0;
    static int count = 0;
    public static void arabicCalcPars(String arabic) {
        System.out.println("Arabic calc is working");
        a = 0;
        b = 0;
        c = 0;
        count = 0;
        for (i = 0; i < arabic.length(); i++) {
            if (Character.isDigit(arabic.charAt(i)) && i < 2) {
                a = a * 10 + (int) arabic.charAt(i) - 48;
            } else if (!Character.isDigit(arabic.charAt(i)) && i <= 2) {
                count++;
                c = arabic.charAt(i);
            } else if (Character.isDigit(arabic.charAt(i)) && i >= 2) {
                b = b * 10 + (int) arabic.charAt(i) - 48;
            }
        }
        int ans = arithmeticX(a, b, c);
        System.out.println(arabic +"="+ ans);
    }

    public static int arithmeticX(int a, int b, int c){
        int res = 0;
        if(a >10 || b >10){ breakingCalc(2);
        }else if(count > 1) {breakingCalc(1);}
        else {
            switch (c) {
                case 42 -> res = multiply(a, b);
                case 47 -> res = divide(a, b);
                case 43 -> res = add(a, b);
                case 45 -> res = subtract(a, b);
                default -> breakingCalc(1);
            }
        }
        return res;
    }
    public static void romaCalcPars(String roma) {

        System.out.println("Roma calc is working");
        for (i = 0; i < roma.length(); i++) {
            if (!Character.isUpperCase(roma.charAt(i)) && i < 5) {
                count++;
                c = roma.charAt(i);
                a = b;
                b = 0;
            } else {
                String aa = "";
                aa += roma.charAt(i);
                Number number = Number.valueOf(aa);
                b = b >= number.getI() ? b + number.getI() : number.getI() - b;
            }

        }

        int m = arithmeticX(a, b, c);
        if (m < 1) {
            breakingCalc(3);
        } else {
            romaString(m,roma);
        }

    }
    public static void  romaString(int m, String input){
         int d = m / 10 * 10;
         int e = m - d;

        String ms = "";
        for (Number ele : Number.values()) {
            if(ele.getI() == d || ele.getI() == e) ms += ele.getNum();
        }

        System.out.println(input +"="+ ms);
    }
        public static int multiply ( int a, int b){

            return a * b;
        }

        public static int subtract ( int a, int b){

            return a-b;
        }
        public static int add ( int a, int b){

            return a + b;
        }
        public static int divide ( int a, int b){
            int ans = b != 0 ? (Integer) a / b : 911;
            if (ans == 911) breakingCalc(ans);

            return (Integer)ans;
        }
        public static void breakingCalc ( int i){
            switch (i) {
                case 1 -> System.out.println("Entered arithmetic expression is incorrect");
                case 2 -> System.out.println("The entered digit is more then tan");
                case 3 -> System.out.println("The result is less then one");
                case 911 -> System.out.println("Entered ArithmeticException: / by zero");
            }
        }

}
