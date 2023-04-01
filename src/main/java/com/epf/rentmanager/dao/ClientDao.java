package com.epf.rentmanager.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.persistence.ConnectionManager;
import org.springframework.stereotype.Repository;


@Repository
public class ClientDao {

	private ClientDao() {}
	
	private static final String CREATE_CLIENT_QUERY = "INSERT INTO Client(nom, prenom, email, naissance) VALUES(?, ?, ?, ?);";
	private static final String DELETE_CLIENT_QUERY = "DELETE FROM Client WHERE id=?;";
	private static final String FIND_CLIENT_QUERY = "SELECT nom, prenom, email, naissance FROM Client WHERE id=?;";
	private static final String FIND_CLIENTS_QUERY = "SELECT id, nom, prenom, email, naissance FROM Client;";
	private static final String UPDATE_CLIENT_QUERY = "UPDATE Client SET nom=?, prenom=?, email=?, naissance=? WHERE id=?;";


	public long create(Client client) throws DaoException {
		try
		{
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement stmt = connection.prepareStatement(CREATE_CLIENT_QUERY, Statement.RETURN_GENERATED_KEYS);

			stmt.setString(1, client.getName());
			stmt.setString(2, client.getFirstName());
			stmt.setString(3, client.getEmail());
			stmt.setDate(4, Date.valueOf(client.getBirthdate()));

			stmt.executeUpdate();
			ResultSet resultSet = stmt.getGeneratedKeys();

			int id = 0;
			if (resultSet.next()) {
				id = resultSet.getInt("id");
			}
			stmt.close();
			connection.close();

			return id;
		}
		catch (SQLException e)
		{
			throw new DaoException(e.getMessage());
		}
	}

	public long edit(Client client) throws DaoException {
		try
		{
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement stmt = connection.prepareStatement(UPDATE_CLIENT_QUERY);

			stmt.setString(1, client.getName());
			stmt.setString(2, client.getFirstName());
			stmt.setString(3, client.getEmail());
			stmt.setDate(4, Date.valueOf(client.getBirthdate()));
			stmt.setLong(5, client.getId());

			stmt.executeUpdate();
			stmt.close();
			connection.close();

			return client.getId();
		}
		catch (SQLException e)
		{
			throw new DaoException(e.getMessage());
		}
	}
	
	public long delete(Client client) throws DaoException {

		try
		{
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement stmt = connection.prepareStatement(DELETE_CLIENT_QUERY);

			stmt.setLong(1, client.getId());

			stmt.executeUpdate();
			stmt.close();
			connection.close();

			return client.getId();
		}
		catch (SQLException e)
		{
			throw new DaoException();
		}
	}

	public Client findById(int id) throws DaoException {
		try
		{
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement stmt = connection.prepareStatement(FIND_CLIENT_QUERY);


			stmt.setLong(1, id);
			ResultSet rs = stmt.executeQuery();

			Client client = new Client();
			if(rs.next()) {
				client.setId(id);
				client.setName(rs.getString("nom"));
				client.setFirstName(rs.getString("prenom"));
				client.setEmail(rs.getString("email"));
				client.setBirthdate(rs.getDate("naissance").toLocalDate());
			}
			stmt.close();
			connection.close();

			return client;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			throw new DaoException();
		}
	}

	public List<Client> findAll() throws DaoException {
		try
		{
			Connection connection = ConnectionManager.getConnection();
			Statement stmt = connection.createStatement();

			ResultSet rs = stmt.executeQuery(FIND_CLIENTS_QUERY);

			ArrayList<Client> clientList = new ArrayList<Client>();

			while(rs.next()) {
				Client client = new Client();

				client.setId(rs.getInt("id"));
				client.setName(rs.getString("nom"));
				client.setFirstName(rs.getString("prenom"));
				client.setEmail(rs.getString("email"));
				client.setBirthdate(rs.getDate("naissance").toLocalDate());

				clientList.add(client);
			}
			stmt.close();
			connection.close();

			return clientList;
		}
		catch (SQLException e)
		{
			throw new DaoException();
		}
	}

	public static int count() throws DaoException {
		try {
			Connection connection = ConnectionManager.getConnection();
			Statement stmt = connection.createStatement();

			ResultSet rs = stmt.executeQuery(FIND_CLIENTS_QUERY);
			int nbClients = 0;

			while(rs.next()) nbClients++;
			stmt.close();
			connection.close();

			return nbClients;
		}
		catch (SQLException e)
		{
			throw new DaoException();
		}
	}
}
