import java.util.Arrays;
import java.util.NoSuchElementException;

public class Client {
    String firstName;
    String lastName;
    String Surname;
    String dateOfBirth;
    int numByPhone;
    char sex;
    String[] allLine;
    Contact con = new Contact();

    public void setAllLine() {
        this.allLine = null;
    }

    /**
     * Метод приветствует пользователя и проверяет на правильность кол-ва введённых данных
     */
    public void hello(){
        con.hello();
        int numCheck = con.enterInform();
        if (numCheck == 1){
            System.out.println("Номер ошибки = " + numCheck);
            System.out.println("Вы ввели лишний пробел. Попробуйте заново.");
        } else if (numCheck == -1) {
            System.out.println("Номер ошибки = " + numCheck);
            System.out.println("Вы ввели меньше пробелов, чем требуется. Попробуйте заново.");
        } else{
            this.allLine = con.informLine;
        }
    }

    /**
     * Метод ищет в массиве данные, которые ввёл пользователь - 3 подряд размещённых элемента, которые в себе не содержат
     * цифр или не являются символами "m" и "w". Затем переставляет ФИО в начало массива.
     * @throws RuntimeException
     */
    public void findFIO() throws RuntimeException{
        try {
            int count = 0;
            boolean flag = true;
            for (int j = 0; j < this.allLine.length; j++) {
                for (int i = 0; i < this.allLine[j].length(); i++) {
                    if (Character.isDigit(this.allLine[j].charAt(i)) || this.allLine[j].equals("w") || this.allLine[j].equals("m")) {
                        flag = false;
                    }
                }
                if (flag) {
                    count++;
                } else {
                    count = 0;
                    flag = true;
                }
                if (count == 3) {
                    change(0, j - 2);
                    change(1, j - 1);
                    change(2, j);
                    this.firstName = this.allLine[1];
                    this.lastName = this.allLine[0];
                    this.Surname = this.allLine[2];
                    break;
                }
            }
            if (count != 3){
                throw new RuntimeException();
            }
        }catch (RuntimeException e) {
            throw new NoCorrectlyEnterFIO("Вы неправильно ввели ФИО. ФИО не может содержать цифр." +
                    " Введите ФИО по образцу: Пупкин Пётр Николаевич ", e);
        }
    }

    /**
     * Помогает переставить 2 элемента массива, индексы которых принимает этот метод
     * @param wrong
     * @param tru
     */
    private void change(Integer wrong, Integer tru){
        String time;
        time = this.allLine[wrong];
        this.allLine[wrong] = this.allLine[tru];
        this.allLine[tru] = time;
    }

    @Override
    public String toString() {
        return "Client{" +
                "allLine=" + Arrays.toString(allLine) +
                '}';
    }
}
