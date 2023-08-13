import java.io.CharConversionException;
import java.io.IOException;
import java.util.zip.DataFormatException;

public class Main {
//    NumberFormatException;
    public static void main(String[] args) {
        work();
    }
    public static void work(){

        while (true){
            Client client1 = new Client();
            client1.hello();
            if (client1.allLine != null){
                try{
//                    System.out.println(client1);
                    client1.findFIO();
//                    System.out.println(client1);
                    client1.findDateOfBirth();
//                    System.out.println(client1);
                    client1.findNumberByPhone();
//                    System.out.println(client1);
                    client1.findSex();
//                    System.out.println(client1);
                    client1.writeFile();
                }catch (NoCorrectlyEnterFIO e){
                    System.out.println(e);
                }catch (NumberPhoneFormatException e){
                    System.out.println(e + ": Номер телефона введён не правильно");
                }
                catch (NumberFormatException e){
                    System.out.println(e);
                }
                catch (RuntimeException e){
                    System.out.println(e);
                } catch (DataFormatException e) {
                    System.out.println(e);
                }
                catch (CharConversionException e) {
                    System.out.println(e);
                } catch (IOException e) {
                    System.out.println(e);
                }finally {
                    if (client1.finalFlag){
                        System.out.println("Попробуйте ввести ещё раз данные.\n");
                    }else {
                        System.out.println("Поздравляем, вы правильно ввели данные.\n");
                    }
                }
            }
            client1.setAllLine();



        }
    }
}