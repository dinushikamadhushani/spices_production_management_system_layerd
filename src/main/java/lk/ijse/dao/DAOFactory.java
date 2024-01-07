package lk.ijse.dao;

import lk.ijse.dao.custom.impl.CustomerDAOImpl;
import lk.ijse.dao.custom.impl.DeliveryDAOImpl;
import lk.ijse.dao.custom.impl.EmployeeDAOImpl;
import lk.ijse.dao.custom.impl.RawMaterialDAOImpl;

public class DAOFactory {

    private static DAOFactory daoFactory;
    private DAOFactory(){
    }
    public static DAOFactory getDaoFactory(){
        return (daoFactory==null)?daoFactory
                =new DAOFactory():daoFactory;
    }

    public enum DAOTypes{
        CUSTOMER,RAWMATERIAL,DELIVERY,EMPLOYEE,ITEM,ORDER,ORDER_DETAIL,QUERY
    }
    public SuperDAO getDAO(DAOTypes daoTypes){
        switch (daoTypes) {
            case CUSTOMER:
                return new CustomerDAOImpl();

            case RAWMATERIAL:
                return new RawMaterialDAOImpl();

            case DELIVERY:
                return new DeliveryDAOImpl();

            case EMPLOYEE:
                return new EmployeeDAOImpl();

            /*case ITEM:
                return new ItemDAOImpl();
            case ORDER:
                return new OrderDAOImpl();
            case ORDER_DETAIL:
                return new OrderDetailsDAOImpl();
            case QUERY:
                return new QueryDAOImpl();*/
            default:
                return null;
        }
    }

}
