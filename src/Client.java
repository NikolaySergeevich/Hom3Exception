import javax.imageio.stream.ImageOutputStream;
import javax.sound.sampled.Line;
import java.io.*;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.FormatterClosedException;
import java.util.Scanner;
import java.util.zip.DataFormatException;

public class Client {
    String firstName;
    String lastName;
    String Surname;
    String dateOfBirth;
    int numByPhone;
    char sex;
    boolean finalFlag = true;
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
            System.out.println("Вы ввели меньше пробелов, чем требуется или не указали некоторые данные. Попробуйте заново.");
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

    /**
     * Метод ищет дату рождения
     * @throws RuntimeException
     * @throws NumberFormatException
     * @throws DataFormatException
     */
    public void findDateOfBirth() throws RuntimeException, NumberFormatException, DataFormatException {
        try{
            String date = checkDateNumSymbol();//элемент, который на данный момент подходит по некоторым параметрам к типу дата рождения
            String[] dateB = checkDateTrueSymbol(date);
            checkValidationOfDate(dateB);
//          метод, который ставит date в массиве на позицию с 3 индексом
            putDateToTruePlase(date);
        }
        catch (NumberFormatException e){
            throw new NumberFormatException("Формат даты рождения указан неправильно. Дату рождения указывайте через точку." +
                    "Пример: 11.11.1111");
        }
        catch (RuntimeException e){
            throw new RuntimeException("Дата указана неправильно. Укажите в формате 04.12.1991");
        } catch (DataFormatException e) {
            throw new DataFormatException("Неправильный формат даты" + e);
        }

    }

    /**
     *Метод проверяет что бы дата включала в себя 8 цифр(две для числа месяца, 2 для месяца и 4 для года)
     * @return
     * @throws RuntimeException
     */
    private String checkDateNumSymbol() throws RuntimeException{
        int count = 0;
        try{
            for (int i = 3; i < this.allLine.length; i++) {
                for (int j = 0; j < this.allLine[i].length(); j++) {
                    if (Character.isDigit(this.allLine[i].charAt(j))){
                        count ++;
                    }
                }
                if (count == 8){
                    return this.allLine[i];
                }else {
                    count = 0;
                }
            }
            throw new RuntimeException();
        }catch (RuntimeException e){
            throw new RuntimeException();
        }
    }

    /**
     * Метод проверяет, что бы дата была написана серез точку
     * @param date
     * @return
     * @throws RuntimeException
     */
    private String[] checkDateTrueSymbol(String date) throws NumberFormatException {
        try {
            if (date.split("\\.").length == 3){
                return date.split("\\.");
            }else{
                throw new NumberFormatException();
            }
        }catch (NumberFormatException e){
            throw new NumberFormatException();
        }
    }

    /**
     * Метод проверяет на валидность даты, которую указал пользователь. Учитываются месяца, в которых по 30 дней, февраль
     * и високосный год.
     * @param date
     * @throws DataFormatException
     */
    private void checkValidationOfDate(String[] date) throws DataFormatException {
        String textExc = "";
        LocalDate datee = LocalDate.now();
        int courentyYear = datee.getYear();
        try{
            Integer yearOfClient = Integer.valueOf(date[2]);
            Integer monthOfClient = Integer.valueOf(date[1]);
            Integer dayOfClient = Integer.valueOf(date[0]);
            if (yearOfClient < 1900 || yearOfClient > courentyYear){
                textExc = "Год рождения не валиден на текущий момент.";
                throw new DataFormatException();
            }
            if (monthOfClient < 1 || monthOfClient > 12){
                textExc = "Вы неправильно указали месяц.";
                throw new DataFormatException();
            }
            if (dayOfClient<1 || dayOfClient>31){
                textExc = "Вы неправильно указали число. Ни в одном месяце нет такой даты.";
                throw new DataFormatException();
            }
            if (dayOfClient == 31 && (monthOfClient == 4 || monthOfClient == 6 || monthOfClient == 9 || monthOfClient == 11)){
                textExc = "В указанном вами месяце нет 31 дня.";
                throw new DataFormatException();
            } else if (monthOfClient ==2) {
                if (yearOfClient % 4==0){
                    if (dayOfClient == 30 || dayOfClient ==31){
                        textExc = "В указанном вами  месяце нет 30 и 31 числа.";
                        throw new DataFormatException();
                    }
                }else {
                    if (dayOfClient == 29 ||dayOfClient == 30 || dayOfClient ==31){
                        textExc = "В указанном вами  месяце нет 29, 30 и 31 числа.";
                        throw new DataFormatException();
                    }
                }
            }
        }catch (DataFormatException e){
            throw new DataFormatException(textExc);
        }
    }

    /**
     * метод кладёт дату рождения на позицию с индексом 4 в массиве
     * @param date
     */
    private void putDateToTruePlase(String date){
        for (int i = 3; i < this.allLine.length; i++) {
            if (this.allLine[i].equals(date)){
                String time = this.allLine[3];
                this.allLine[3] = this.allLine[i];
                this.allLine[i] = time;
                break;
            }
        }
        this.dateOfBirth = date;
    }

    /**
     * Метод ищет номер телефона. Проверяет если элемент состояит из 9 символов и все 9 являются цифрами, то потонцеально это номер телефона
     * @throws NumberFormatException
     */
    public void findNumberByPhone()throws NumberFormatException{
        try{
            boolean flag = true;
            for (int i = 4; i < this.allLine.length; i++) {
                if (this.allLine[i].length() == 9){
                    Integer numPhone = Integer.valueOf(this.allLine[i]);
                    this.numByPhone = numPhone;
                    String time = this.allLine[4];
                    this.allLine[4] = this.allLine[i];
                    this.allLine[i] = time;
                    flag = false;
                }
            }
            if (flag){
                throw new NumberFormatException();
            }
        }catch (NumberFormatException e){
            throw new NumberPhoneFormatException("Номер телефона введён не правильно", e);
        }
    }

    /**
     *Метод проверяет крайний элемент в массиве - тум, к моменту вызова этого метода, будет стоять потенциальный пол.
     * @throws CharConversionException
     */
    public void findSex()throws CharConversionException{
        try {
            String sex = this.allLine[this.allLine.length - 1];
            if (sex.length() != 1){
                throw new CharConversionException();
            } else if (sex.equals("w") || sex.equals("m")) {
                char se = sex.charAt(0);
                this.sex = se;
                this.finalFlag = false;
            }else {
                throw new CharConversionException();
            }
        }catch (CharConversionException e){
            throw new CharConversionException("Пол нужно указать одним символом латинскими w или m");
        }

    }

    public void writeFile() throws IOException{
        String name = this.lastName;
        try(FileWriter file = new FileWriter(name + ".txt", true);
            FileReader fileReader= new FileReader(name + ".txt")){
            Scanner sc = new Scanner(fileReader);
            file.write(OneLineFromArr());
            file.write("\n");
        }catch (IOException e){
            throw new IOException("Какая-то ошибка с файлами");
        }
    }

    private String OneLineFromArr() {
        String line = "";
        for (String it: this.allLine) {
            line = line + it + " ";
        }
        return line;
    }
    @Override
    public String toString() {
        return "Client{" +
                "allLine=" + Arrays.toString(allLine) +
                '}';
    }
}
