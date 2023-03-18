package com.epf.rentmanager.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.persistence.ConnectionManager;
import org.springframework.stereotype.Repository;

@Repository
public class VehicleDao {
	
	private static VehicleDao instance = null;
	private VehicleDao() {}
	
	private static final String CREATE_VEHICLE_QUERY = "INSERT INTO Vehicle(constructeur, modele, nb_places) VALUES(?, ?, ?);";
	private static final String DELETE_VEHICLE_QUERY = "DELETE FROM Vehicle WHERE id=?;";
	private static final String FIND_VEHICLE_QUERY = "SELECT id, constructeur, modele, nb_places FROM Vehicle WHERE id=?;";
	private static final String FIND_VEHICLES_QUERY = "SELECT id, constructeur, modele, nb_places FROM Vehicle;";


	public long create(Vehicle vehicle) throws DaoException {
		try
		{
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement stmt = connection.prepareStatement(CREATE_VEHICLE_QUERY, Statement.RETURN_GENERATED_KEYS);

			stmt.setString(1, vehicle.getConstructeur());
			stmt.setString(2, vehicle.getModele());
			stmt.setInt(3, vehicle.getNb_places());

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

	public long delete(Vehicle vehicle) throws DaoException {
		try
		{
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement stmt = connection.prepareStatement(DELETE_VEHICLE_QUERY);

			stmt.setLong(1, vehicle.getId());

			stmt.executeUpdate();
			stmt.close();
			connection.close();

			return vehicle.getId();
		}
		catch (SQLException e)
		{
			throw new DaoException(e.getMessage());
		}
	}

	public Vehicle findById(long id) throws DaoException {
		try
		{
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement stmt = connection.prepareStatement(FIND_VEHICLE_QUERY);

			stmt.setLong(1, id);
			ResultSet rs = stmt.executeQuery();

			Vehicle vehicle = new Vehicle();
			if(rs.next()) {
				vehicle.setId(rs.getInt("id"));
				vehicle.setConstructeur(rs.getString("constructeur"));
				vehicle.setModele(rs.getString("modele"));
				vehicle.setNb_places(rs.getInt("nb_places"));
			}
			stmt.close();
			connection.close();

			return vehicle;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			throw new DaoException();
		}
	}

	public List<Vehicle> findAll() throws DaoException {
		try
		{
			Connection connection = ConnectionManager.getConnection();
			Statement stmt = connection.createStatement();

			ResultSet rs = stmt.executeQuery(FIND_VEHICLES_QUERY);

			ArrayList<Vehicle> vehicles = new ArrayList<Vehicle>();

			while(rs.next()) {
				Vehicle vehicle = new Vehicle();
				vehicle.setId(rs.getInt("id"));
				vehicle.setConstructeur(rs.getString("constructeur"));
				vehicle.setModele(rs.getString("modele"));
				vehicle.setNb_places(rs.getInt("nb_places"));

				vehicles.add(vehicle);
			}
			stmt.close();
			connection.close();

			return vehicles;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			throw new DaoException(e.getMessage());
		}
		
	}

	public static int count() throws DaoException {
		try {
			Connection connection = ConnectionManager.getConnection();
			Statement stmt = connection.createStatement();

			ResultSet rs = stmt.executeQuery(FIND_VEHICLES_QUERY);
			int nbVehicles = 0;

			while(rs.next()) nbVehicles++;
			stmt.close();
			connection.close();

			return nbVehicles;
		}
		catch (SQLException e)
		{
			throw new DaoException(e.getMessage());
		}
	}
}
