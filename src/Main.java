public class Main {
    public static void main(String[] args) {
        work();
    }
    public static void work(){

        while (true){
            Client client1 = new Client();
            client1.hello();
            if (client1.allLine != null){
//                значит на этом этапе пользователь ввел по крайней мере правильное кол-во пробелов и ниже в этом блоке
//                будут методы, которые проверяют отдельно каждую позицию
                System.out.println("Идем дальше");
                try{
                    client1.findFIO();
                    System.out.println(client1);
                }catch (NoCorrectlyEnterFIO e){
                    System.out.println(e);
                }
            }
            client1.setAllLine();



        }
    }
}