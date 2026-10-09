package metier;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import dao.IDao;
@Service
public class MetierIMP implements IMetier{
    @Autowired
    private IDao dao;
    @Override
    public double calcul () {
        double val = dao.getValue();
        return val*7;
    }
    public void setDao(IDao dao){
        this.dao = dao;
    }
}
