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

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.persistence.ConnectionManager;

public class ReservationDao {

	private static ReservationDao instance = null;
	private ReservationDao() {}
	public static ReservationDao getInstance() {
		if(instance == null) {
			instance = new ReservationDao();
		}
		return instance;
	}
	
	private static final String CREATE_RESERVATION_QUERY = "INSERT INTO Reservation(client_id, vehicle_id, debut, fin) VALUES(?, ?, ?, ?);";
	private static final String DELETE_RESERVATION_QUERY = "DELETE FROM Reservation WHERE id=?;";
	private static final String FIND_RESERVATIONS_BY_CLIENT_QUERY = "SELECT id, vehicle_id, debut, fin FROM Reservation WHERE client_id=?;";
	private static final String FIND_RESERVATIONS_BY_VEHICLE_QUERY = "SELECT id, client_id, debut, fin FROM Reservation WHERE vehicle_id=?;";
	private static final String FIND_RESERVATIONS_QUERY = "SELECT id, client_id, vehicle_id, debut, fin FROM Reservation;";
		
	public long create(Reservation reservation) throws DaoException {
		try
		{
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement stmt = connection.prepareStatement(CREATE_RESERVATION_QUERY, Statement.RETURN_GENERATED_KEYS);

			stmt.setInt(1, reservation.getClient_id());
			stmt.setInt(2, reservation.getVehicule_id());
			stmt.setDate(2, Date.valueOf(reservation.getDebut()));
			stmt.setDate(2, Date.valueOf(reservation.getFin()));

			stmt.executeUpdate();
			ResultSet resultSet = stmt.getGeneratedKeys();

			stmt.close();
			connection.close();

			int id = 0;
			if (resultSet.next()) {
				id = resultSet.getInt("id");
			}
			return id;
		}
		catch (SQLException e)
		{
			throw new DaoException();
		}
	}
	
	public long delete(Reservation reservation) throws DaoException {
		try
		{
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement stmt = connection.prepareStatement(DELETE_RESERVATION_QUERY);

			stmt.setLong(1, reservation.getId());

			stmt.executeUpdate();
			stmt.close();
			connection.close();

			return reservation.getId();
		}
		catch (SQLException e)
		{
			throw new DaoException();
		}
	}

	public List<Reservation> findResaByClientId(long clientId) throws DaoException {
		try
		{
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement stmt = connection.prepareStatement(FIND_RESERVATIONS_BY_CLIENT_QUERY);

			stmt.setLong(1, clientId);
			ResultSet rs = stmt.executeQuery();

			stmt.close();
			connection.close();

			ArrayList<Reservation> reservationList = new ArrayList<Reservation>();

			while(rs.next()) {
				Reservation reservation = new Reservation();

				reservation.setId(rs.getInt("id"));
				reservation.setClient_id(rs.getInt("client_id"));
				reservation.setVehicule_id(rs.getInt("vehicle_id"));
				reservation.setDebut(rs.getDate("debut").toLocalDate());
				reservation.setFin(rs.getDate("fin").toLocalDate());

				reservationList.add(reservation);
			}
			return reservationList;
		}
		catch (SQLException e)
		{
			throw new DaoException();
		}
	}
	
	public List<Reservation> findResaByVehicleId(long vehicleId) throws DaoException {
		try
		{
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement stmt = connection.prepareStatement(FIND_RESERVATIONS_BY_VEHICLE_QUERY);

			stmt.setLong(1, vehicleId);
			ResultSet rs = stmt.executeQuery();

			stmt.close();
			connection.close();

			ArrayList<Reservation> reservationList = new ArrayList<Reservation>();

			while(rs.next()) {
				Reservation reservation = new Reservation();

				reservation.setId(rs.getInt("id"));
				reservation.setClient_id(rs.getInt("client_id"));
				reservation.setVehicule_id(rs.getInt("vehicle_id"));
				reservation.setDebut(rs.getDate("debut").toLocalDate());
				reservation.setFin(rs.getDate("fin").toLocalDate());

				reservationList.add(reservation);
			}
			return reservationList;
		}
		catch (SQLException e)
		{
			throw new DaoException();
		}
	}

	public List<Reservation> findAll() throws DaoException {
		try
		{
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement stmt = connection.prepareStatement(FIND_RESERVATIONS_QUERY);

			ResultSet rs = stmt.executeQuery();

			stmt.close();
			connection.close();

			ArrayList<Reservation> reservationList = new ArrayList<Reservation>();

			while(rs.next()) {
				Reservation reservation = new Reservation();

				reservation.setId(rs.getInt("id"));
				reservation.setClient_id(rs.getInt("client_id"));
				reservation.setVehicule_id(rs.getInt("vehicle_id"));
				reservation.setDebut(rs.getDate("debut").toLocalDate());
				reservation.setFin(rs.getDate("fin").toLocalDate());

				reservationList.add(reservation);
			}
			return reservationList;
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

			ResultSet rs = stmt.executeQuery(FIND_RESERVATIONS_QUERY);
			int nbReservations = 0;

			while(rs.next()) nbReservations++;
			stmt.close();
			connection.close();

			return nbReservations;
		}
		catch (SQLException e)
		{
			throw new DaoException();
		}
	}

}
