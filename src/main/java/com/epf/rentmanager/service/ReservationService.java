package com.epf.rentmanager.service;

import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.dao.ReservationDao;
import com.epf.rentmanager.dao.VehicleDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicle;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReservationService {

    private ReservationDao reservationDao;
    public static ReservationService instance;

    private ReservationService(ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }


    public long create(Reservation reservation) throws ServiceException, DaoException {
            return this.reservationDao.create(reservation);
    }

    public List<Reservation> findResaByVehicleId(long vehicleId) throws ServiceException, DaoException {
        return this.reservationDao.findResaByVehicleId(vehicleId);
    }

    public List<Reservation> findResaByClientId(long clientId) throws ServiceException, DaoException {
        return this.reservationDao.findResaByClientId(clientId);
    }


    public List<Reservation> findAll() throws ServiceException, DaoException {
        return this.reservationDao.findAll();
    }

    public void isValidVehicle(Vehicle vehicle) throws ServiceException
    {
        if(vehicle.getNb_places() < 1)
        {
            throw new ServiceException("Nombre de places inférieur à 1");
        }
        if(vehicle.getConstructeur().equals(""))
        {
            throw new ServiceException("constructeur vide");
        }
    }

    public static int count() throws ServiceException, DaoException {
        return ReservationDao.count();
    }
}
