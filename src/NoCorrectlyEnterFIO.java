import java.rmi.NoSuchObjectException;

public class NoCorrectlyEnterFIO extends RuntimeException {
    public NoCorrectlyEnterFIO(String mes, RuntimeException e){

        super(mes, e);
    }
}
