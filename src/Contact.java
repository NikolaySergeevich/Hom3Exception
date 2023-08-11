import java.util.NoSuchElementException;
import java.util.Scanner;

public class Contact {
    String[] informLine = null;


    public void hello(){
        System.out.println("Привет, Наша компания поможет вам найти первую работу. Но тебе необходимо ввести некоторые\n" +
                "данные о себе в следующем формате:\n" +
                "фамилия, имя, отчество - строки\n" +
                "дата рождения - строка формата dd.mm.yyyy\n" +
                "номер телефона - целое беззнаковое число без форматирования(9 символов)\n" +
                "пол - символ латиницей w или m.\n" +
                "Но будь внимателен, вводи каждый параметр через пробел и так как указано.\n");
    }

    public int enterInform() {
        System.out.println("Введите данные!");
        Scanner sc = new Scanner(System.in);

        String inf = sc.nextLine();
        String[] parsInf;
        parsInf = inf.split(" ");

        if (parsInf.length == 6){
            this.informLine = parsInf;
            return 0;
        } else if (parsInf.length > 6) {
            return 1;
        } else {
            return -1;
        }
    }
}
