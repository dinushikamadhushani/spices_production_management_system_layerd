package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.DeliveryBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.DeliveryDAO;
import lk.ijse.dto.DeliveryDto;
import lk.ijse.entity.Delivery;

import java.sql.SQLException;
import java.util.List;

public class DeliveryBOImpl implements DeliveryBO {

    DeliveryDAO deliveryDAO =
            (DeliveryDAO) DAOFactory.getDaoFactory().
                    getDAO(DAOFactory.DAOTypes.DELIVERY);

    @Override


    public boolean saveDelivery(DeliveryDto deliveryDto) throws SQLException, ClassNotFoundException {
        return deliveryDAO.save(new Delivery(deliveryDto.getDeliveryId(),deliveryDto.getOrderId(),deliveryDto.getEmployeeId(),deliveryDto.getLocation(),deliveryDto.getDeliveryStatus(),deliveryDto.getEmail()));

    }

    @Override
    public List<Delivery> getAllDeliveries() throws SQLException, ClassNotFoundException {
        return deliveryDAO.getAll();
    }

    @Override
    public void deleteDelivery(String id) throws SQLException, ClassNotFoundException {
        deliveryDAO.delete(id);
    }

    @Override
    public DeliveryDto searchDelivery(String id) throws SQLException, ClassNotFoundException {
        Delivery delivery=deliveryDAO.search(id);
        if (delivery!=null){
            return new DeliveryDto(delivery.getOrderId(),delivery.getDeliveryId(),delivery.getEmployeeId(),delivery.getLocation(),delivery.getDeliveryStatus(),delivery.getEmail());
        }else{
            return  null;
        }
    }

    @Override
    public boolean updateDelivery(DeliveryDto deliveryDto) throws SQLException, ClassNotFoundException {

        return deliveryDAO.update(new Delivery(deliveryDto.getDeliveryId(),deliveryDto.getOrderId(),deliveryDto.getEmployeeId(),deliveryDto.getLocation(),deliveryDto.getDeliveryStatus(),deliveryDto.getEmail()));

    }

    @Override
    public boolean existDelivery(String id) throws SQLException, ClassNotFoundException {
        return deliveryDAO.exist(id);
    }
}
