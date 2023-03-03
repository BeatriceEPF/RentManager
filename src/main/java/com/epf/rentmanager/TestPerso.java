package com.epf.rentmanager;

import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.dao.VehicleDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.VehicleService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TestPerso {

    public static void main(String [] args) throws ServiceException, DaoException {

        try
        {
            System.out.println(VehicleDao.getInstance().findById(2));
            System.out.println(ClientDao.getInstance().findById(2));
            System.out.println(ClientService.count());
        }
        catch (DaoException e)
        {
            e.printStackTrace();
        }
    }
}
