package lk.ijse.dao;

import lk.ijse.dao.custom.impl.*;

public class DAOFactory {

    private static DAOFactory daoFactory;
    private DAOFactory(){
    }
    public static DAOFactory getDaoFactory(){
        return (daoFactory==null)?daoFactory
                =new DAOFactory():daoFactory;
    }

    public enum DAOTypes{
        CUSTOMER,RAWMATERIAL,DELIVERY,EMPLOYEE,SUPPLIER,SUPPLIER_DETAIL,ITEM,ORDER,ORDER_DETAIL,QUERY
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

            case SUPPLIER:
                return new SupplierDAOImpl();

            case SUPPLIER_DETAIL:
                return new SupplierDetailDAOImpl();

            case ITEM:
                return new ItemDAOImpl();

           /* case ITEM:
                return new ItemDAOImpl();*/
            case ORDER:
                return new OrderDAOImpl();
            case ORDER_DETAIL:
                return new OrderDetailDAOImpl();
            /*case QUERY:
                return new Query;*/
            default:
                return null;
        }
    }

}
