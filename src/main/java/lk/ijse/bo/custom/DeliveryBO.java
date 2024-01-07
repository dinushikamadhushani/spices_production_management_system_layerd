package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.dto.DeliveryDto;
import lk.ijse.entity.Delivery;

import java.sql.SQLException;
import java.util.List;

public interface DeliveryBO extends SuperBO {
    boolean saveDelivery(DeliveryDto deliveryDto) throws SQLException, ClassNotFoundException;

    List<Delivery> getAllDeliveries() throws SQLException, ClassNotFoundException;

    void deleteDelivery(String id) throws SQLException, ClassNotFoundException;

    DeliveryDto searchDelivery(String id) throws SQLException, ClassNotFoundException;

    boolean updateDelivery(DeliveryDto deliveryDto) throws SQLException, ClassNotFoundException;

    boolean existDelivery(String id) throws SQLException, ClassNotFoundException;
}
