package com.epf.rentmanager.service;

import java.util.ArrayList;
import java.util.List;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.dao.VehicleDao;
import org.springframework.stereotype.Service;


@Service
public class VehicleService {

	private VehicleDao vehicleDao;
	public static VehicleService instance;
	
	private VehicleService(VehicleDao vehicleDao) {
		this.vehicleDao = vehicleDao;
	}

	public long create(Vehicle vehicle) throws ServiceException, DaoException {
		// TODO: créer un véhicule --DID
		try{
			isValidVehicle(vehicle);
			return this.vehicleDao.create(vehicle);
		}
		catch(ServiceException e)
		{
			throw new ServiceException(e.getMessage());
		}
	}

	public Vehicle findById(long id) throws ServiceException, DaoException {
		// TODO: récupérer un véhicule par son id --DID
		return this.vehicleDao.findById(id);
	}

	public List<Vehicle> findAll() throws ServiceException, DaoException {
		// TODO: récupérer tous les clients --DID
		return this.vehicleDao.findAll();
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
		return VehicleDao.count();
	}
}
