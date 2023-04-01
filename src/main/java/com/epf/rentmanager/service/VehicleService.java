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

	private final VehicleDao vehicleDao;
	public static VehicleService instance;
	
	private VehicleService(VehicleDao vehicleDao) {
		this.vehicleDao = vehicleDao;
	}

	public long create(Vehicle vehicle) throws ServiceException {
		try{
			isValidVehicle(vehicle);
			return this.vehicleDao.create(vehicle);
		}
		catch(DaoException e)
		{
			throw new ServiceException(e.getMessage());
		}
	}

	public long edit(Vehicle vehicle) throws ServiceException {
		try{
			isValidVehicle(vehicle);
			return this.vehicleDao.edit(vehicle);
		}
		catch(DaoException e)
		{
			throw new ServiceException(e.getMessage());
		}
	}

	public long delete(Vehicle vehicle) throws ServiceException {
		try
		{
			return this.vehicleDao.delete(vehicle);
		}
		catch (DaoException e)
		{
			throw new ServiceException(e.getMessage());
		}
	}

	public Vehicle findById(long id) throws ServiceException {
		try
		{
			return this.vehicleDao.findById(id);
		}
		catch (DaoException e)
		{
			throw new ServiceException(e.getMessage());
		}
	}

	public List<Vehicle> findAll() throws ServiceException {
		try{
			return this.vehicleDao.findAll();
		}
		catch (DaoException e) {
			throw new ServiceException(e.getMessage());
		}
	}

	public static int count() throws ServiceException, DaoException {
		return VehicleDao.count();
	}

	public void isValidVehicle(Vehicle vehicule) throws ServiceException {
		if(!isLong(vehicule))
		{
			throw new ServiceException("Nombre de places insuffisant");
		}
		if(!isShort(vehicule))
		{
			throw new ServiceException("Nombre de places trop important");
		}
		if(!hasConstructor(vehicule))
		{
			throw new ServiceException("constructeur vide");
		}
		if(!hasModel(vehicule))
		{
			throw new ServiceException("modèle vide");
		}
	}

	public static boolean isLong(Vehicle vehicule) throws ServiceException
	{
		return vehicule.getNb_places() >= 2;
	}

	public static boolean isShort(Vehicle vehicule) throws ServiceException
	{
		return vehicule.getNb_places() <= 9;
	}

	public static boolean hasConstructor(Vehicle vehicule) throws ServiceException
	{
		return !vehicule.getConstructeur().equals("");
	}

	public static boolean hasModel(Vehicle vehicule) throws ServiceException
	{
		return !vehicule.getModele().equals("");
	}

}
