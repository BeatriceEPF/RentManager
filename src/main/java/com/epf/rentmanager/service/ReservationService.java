package com.epf.rentmanager.service;

import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.dao.ReservationDao;
import com.epf.rentmanager.dao.VehicleDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicle;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class ReservationService {

    private ReservationDao reservationDao;
    public static ReservationService instance;

    private ReservationService(ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }


    public long create(Reservation reservation) throws ServiceException {
        try{
            isValidReservation(reservation);
            return this.reservationDao.create(reservation);
        }
        catch(DaoException e)
        {
            throw new ServiceException(e.getMessage());
        }
    }

    public long edit(Reservation reservation, Reservation lastRent) throws ServiceException {
        try{
            isValidReservation(reservation);
            return this.reservationDao.edit(reservation);
        }
        catch(DaoException e)
        {
            throw new ServiceException(e.getMessage());
        }
    }

    public long delete(Reservation reservation) throws ServiceException {
        try
        {
            return this.reservationDao.delete(reservation);
        }
        catch (DaoException e)
        {
            throw new ServiceException(e.getMessage());
        }
    }

    public List<Reservation> findResaByVehicleId(long vehicleId) throws ServiceException {
        try {
            return this.reservationDao.findResaByVehicleId(vehicleId);
        }
        catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public Reservation findResaById(int id) throws ServiceException {
        try {
            return this.reservationDao.findResaById(id);
        }
        catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public List<Reservation> findResaByClientId(int clientId) throws ServiceException {
        try {
            return this.reservationDao.findResaByClientId(clientId);
        }
        catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }


    public List<Reservation> findAll() throws ServiceException {
        try
        {
            return this.reservationDao.findAll();
        }
        catch (DaoException e)
        {
            throw new ServiceException(e.getMessage());
        }
    }

    public static int count() throws ServiceException, DaoException {
        return ReservationDao.count();
    }


    public void isValidReservation(Reservation reservation) throws ServiceException {
        if (is7daysReserved(reservation))
        {
            throw new ServiceException("Réservation supérieure à 7 jours");
        }
        if (isValidDates(reservation))
        {
            throw new ServiceException("Les dates de réservation sont invalides");
        }
        if (isAlreadyReserved(reservation))
        {
            throw new ServiceException("Véhicule déjà réservé pendant la période selectionnée");
        }
        if (isTooReserved(reservation))
        {
            throw new ServiceException("Véhicule déjà réservé pendant 30 jours de suite");
        }
    }

    public boolean isAlreadyReserved(Reservation reservation) throws ServiceException{
        boolean isReserved = false;
        try
        {
            List<Reservation> otherReservations = reservationDao.findResaByVehicleId(reservation.getVehicule().getId());

            for(Reservation reservations : otherReservations)
            {
                if(reservations.getDebut().isBefore(reservation.getFin()) ||
                        reservations.getFin().isAfter(reservations.getDebut()))
                {
                    if(reservations.getId()!=reservation.getId())
                    {
                        isReserved = true;
                        break;
                    }
                }
            }
        }
        catch (DaoException e)
        {
            throw new ServiceException();
        }
        return isReserved;
    }

    public boolean isTooReserved(Reservation reservation) throws ServiceException {
        boolean isReserved = false;
        try
        {
            List<Reservation> otherReservations = reservationDao.findResaByVehicleId(reservation.getVehicule().getId());
            otherReservations.sort(new BeginDateComparator());

            LocalDate debut = reservation.getDebut();

            for(Reservation reservations : otherReservations) {
                if (reservations.getFin().isEqual(debut)) debut = reservations.getDebut();
                else break;
            }

            if(debut.until(reservation.getDebut(), ChronoUnit.DAYS) > 30) isReserved = true;
        }
        catch (DaoException e)
        {
            throw new ServiceException();
        }
        return isReserved;
    }

    public boolean is7daysReserved(Reservation reservation) {
        return reservation.getDebut().until(reservation.getFin(), ChronoUnit.DAYS) > 7;
    }

    public boolean isValidDates(Reservation reservation) {
        return reservation.getDebut().isAfter(reservation.getFin());
    }

    private static class BeginDateComparator implements Comparator<Reservation> {
        public int compare(Reservation r1, Reservation r2) {
            return r1.compareBeginDate(r2);
        }
    }

}
