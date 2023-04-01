package com.epf.rentmanager.service;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Year;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Vehicle;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

	private ClientDao clientDao;

	private ClientService(ClientDao clientDao){
		this.clientDao = clientDao;
	}

	public long create(Client client) throws ServiceException {
		// TODO: créer un client -- DID
		try
		{
			isValidClient(client);
			client.setName(client.getName().toUpperCase());
			return this.clientDao.create(client);
		}
		catch (DaoException e)
		{
			throw new ServiceException(e.getMessage());
		}
	}

	public long edit(Client client) throws ServiceException {
		// TODO: créer un client -- DID
		try
		{
			isValidExistedClient(client);
			client.setName(client.getName().toUpperCase());
			return this.clientDao.edit(client);
		}
		catch (DaoException e)
		{
			throw new ServiceException(e.getMessage());
		}
	}


	public long delete(Client client) throws ServiceException {
		try
		{
			return this.clientDao.delete(client);
		}
		catch (DaoException e)
		{
			throw new ServiceException(e.getMessage());
		}
	}


	public Client findById(int id) throws ServiceException {
		try
		{
			return this.clientDao.findById(id);
		}
		catch (DaoException e)
		{
			throw new ServiceException(e.getMessage());
		}
	}

	public List<Client> findAll() throws ServiceException {
		// TODO: récupérer tous les clients -- DID
		List<Client> listClients = new ArrayList<Client>();
		try
		{
			listClients = this.clientDao.findAll();
		}
		catch (DaoException e){
			throw new ServiceException(e.getMessage());
		}
		return listClients;
	}

	public static int count() throws ServiceException, DaoException {
		return ClientDao.count();
	}



	public void isValidExistedClient(Client client) throws ServiceException {
		if(!isLong(client))
		{
			throw new ServiceException("Nom ou Prénom trop court");
		}
		if(!isLegal(client))
		{
			throw new ServiceException("Client mineur");
		}
	}

	public void isValidClient(Client client) throws ServiceException {

	}

	public static boolean isLong(Client client) throws ServiceException {
		return client.getName().length() >= 3 & client.getFirstName().length() >= 3;
	}

	public static boolean isLegal(Client client) {
		return client.getBirthdate().until(LocalDate.now(), ChronoUnit.YEARS)  >= 18;
	}

	public boolean isNewEmail(Client client) throws ServiceException {
		boolean nouveau = true;
		try{
			for (Client clients : this.clientDao.findAll()) {
				if (clients.getEmail().equals(client.getEmail())) {
					nouveau = false;
					break;
				}
			}
		}
		catch (DaoException e)
		{
			e.printStackTrace();
		}
		return nouveau;
	}
}
