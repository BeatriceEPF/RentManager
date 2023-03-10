package com.epf.rentmanager.service;

import java.sql.SQLException;
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
	public static ClientService instance;

	private ClientService(ClientDao clientDao){
		this.clientDao = clientDao;
	}

	public long create(Client client) throws DaoException, ServiceException {
		// TODO: créer un client -- DID
		try
		{
			isValidClient(client);
			client.setName(client.getName().toUpperCase());
			return this.clientDao.create(client);
		}
		catch (ServiceException e)
		{
			throw new ServiceException(e.getMessage());
		}
	}

	public Client findById(int id) throws ServiceException, DaoException {
		// TODO: récupérer un client par son id -- DID
		return this.clientDao.findById(id);
	}

	public List<Client> findAll() throws ServiceException, DaoException {
		// TODO: récupérer tous les clients -- DID
		return this.clientDao.findAll();
	}

	public void isValidClient(Client client) throws ServiceException
	{
		if(client.getName().equals("") || client.getFirstName().equals(""))
		{
			throw new ServiceException("Nom ou Prénom vide");
		}
	}

	public static int count() throws ServiceException, DaoException {
		return ClientDao.count();
	}
}
