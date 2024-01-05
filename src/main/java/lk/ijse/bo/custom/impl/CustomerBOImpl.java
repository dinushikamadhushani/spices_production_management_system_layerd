package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.CustomerBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.CustomerDAO;
import lk.ijse.dao.custom.impl.CustomerDAOImpl;
import lk.ijse.dto.CustomerDto;
import lk.ijse.entity.Customer;

import java.sql.SQLException;
import java.util.List;

public class CustomerBOImpl implements CustomerBO {

    CustomerDAO customerDAO =
            (CustomerDAO) DAOFactory.getDaoFactory().
                    getDAO(DAOFactory.DAOTypes.CUSTOMER);
   // CustomerDAO customerDAO = new CustomerDAOImpl();
    @Override
    public boolean updateCustomer(CustomerDto customerDto) throws SQLException, ClassNotFoundException {

        return customerDAO.update(new Customer(customerDto.getId(),customerDto.getName(),customerDto.getAddress(),customerDto.getTel()));

    }

    @Override
    public List<Customer> getAllCustomers() throws SQLException, ClassNotFoundException {
        return customerDAO.getAll();
    }
    @Override

    public boolean saveCustomer(CustomerDto customerDto) throws SQLException, ClassNotFoundException {
        return customerDAO.save(new Customer(customerDto.getId(),customerDto.getName(),customerDto.getAddress(),customerDto.getTel()));

    }



   /* @Override
    public boolean delateCustomer(CustomerDto customerDto) throws SQLException, ClassNotFoundException {
        return customerDAO.delate(new Customer(customerDto.getId(),customerDto.getName(),customerDto.getAddress(),customerDto.getTel()));


    }*/

    @Override
    public void deleteCustomer(String id) throws SQLException, ClassNotFoundException {
        customerDAO.delete(id);


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
    public CustomerDto searchCustomer(String id) throws SQLException, ClassNotFoundException {
        Customer customer=customerDAO.search(id);
       // CustomerDto customerDTO=new CustomerDto(customer.getCustomerId(),customer.getCustomerName(),customer.getAddress(),customer.getTel());
      //  return customerDTO;
        if (customer!=null){
            return new CustomerDto(customer.getCustomerId(), customer.getCustomerName(), customer.getAddress(), customer.getTel());


        }else{
            return  null;
        }
    }

    @Override
    public boolean existCustomer(String id) throws SQLException, ClassNotFoundException {
        return customerDAO.exist(id);
    }



}
