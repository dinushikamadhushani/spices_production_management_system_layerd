package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.ItemBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.ItemDAO;
import lk.ijse.dto.ItemtDto;
import lk.ijse.entity.Item;

import java.sql.SQLException;
import java.util.List;

public class ItemBOImpl implements ItemBO {

    ItemDAO itemDAO =
            (ItemDAO) DAOFactory.getDaoFactory().
                    getDAO(DAOFactory.DAOTypes.ITEM);
    // CustomerDAO customerDAO = new CustomerDAOImpl();
    @Override
    public boolean updateItem(ItemtDto itemtDto) throws SQLException, ClassNotFoundException {

        return itemDAO.update(new Item(itemtDto.getItemId(),itemtDto.getItemName(),itemtDto.getUnitPrice(),itemtDto.getQtyOnHand(),itemtDto.getRawMaterialId()));

    }

    @Override
    public List<Item> getAllItems() throws SQLException, ClassNotFoundException {
        return itemDAO.getAll();
    }
    @Override

    public boolean saveItem(ItemtDto itemtDto) throws SQLException, ClassNotFoundException {
        return itemDAO.save(new Item(itemtDto.getItemId(),itemtDto.getItemName(),itemtDto.getUnitPrice(),itemtDto.getQtyOnHand(),itemtDto.getRawMaterialId()));

    }



   /* @Override
    public boolean delateCustomer(CustomerDto customerDto) throws SQLException, ClassNotFoundException {
        return customerDAO.delate(new Customer(customerDto.getId(),customerDto.getName(),customerDto.getAddress(),customerDto.getTel()));


    }*/

    @Override
    public void deleteItem(String id) throws SQLException, ClassNotFoundException {
        itemDAO.delete(id);


    }

   /* @Override
    public boolean searchCustomer(CustomerDto customerDto) throws SQLException, ClassNotFoundException {
        return false;
    }*/

  /*  @Override
    public boolean searchCustomer(CustomerDto customerDto) throws SQLException, ClassNotFoundException {
        return false;
    }*/


  /* @Override
    public boolean searchCustomer(CustomerDto customerDto) throws SQLException, ClassNotFoundException {
        return customerDAO.search(new Customer(customerDto.getId(),customerDto.getName(),customerDto.getAddress(),customerDto.getTel()));

    }*/

    @Override
    public ItemtDto searchItem(String id) throws SQLException, ClassNotFoundException {
        Item item=itemDAO.search(id);
        // CustomerDto customerDTO=new CustomerDto(customer.getCustomerId(),customer.getCustomerName(),customer.getAddress(),customer.getTel());
        //  return customerDTO;
        if (item!=null){
            return new ItemtDto(item.getItemId(),item.getItemName(),item.getUnitPrice(),item.getQtyOnHand(),item.getRawMaterialId());


        }else{
            return  null;
        }
    }

    @Override
    public boolean existItem(String id) throws SQLException, ClassNotFoundException {
        return itemDAO.exist(id);
    }


}
