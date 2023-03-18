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
import com.epf.rentmanager.service.ClientService;
import org.springframework.stereotype.Repository;

@Repository
public class ReservationDao {

	private static ReservationDao instance = null;
	private final ClientDao clientDao;
	private final VehicleDao vehicleDao;
	private ReservationDao(ClientDao clientDao, VehicleDao vehicleDao) {
		this.clientDao = clientDao;
		this.vehicleDao = vehicleDao;
	}
	
	private static final String CREATE_RESERVATION_QUERY = "INSERT INTO Reservation(client_id, vehicle_id, debut, fin) VALUES(?, ?, ?, ?);";
	private static final String DELETE_RESERVATION_QUERY = "DELETE FROM Reservation WHERE id=?;";
	private static final String FIND_RESERVATIONS_BY_CLIENT_QUERY = "SELECT id, vehicle_id, debut, fin FROM Reservation WHERE client_id=?;";
	private static final String FIND_RESERVATIONS_BY_VEHICLE_QUERY = "SELECT id, client_id, debut, fin FROM Reservation WHERE vehicle_id=?;";

	private static final String FIND_RESERVATION_QUERY = "SELECT id, client_id, vehicle_id, debut, fin FROM Reservation WHERE id=?";
	private static final String FIND_RESERVATIONS_QUERY = "SELECT id, client_id, vehicle_id, debut, fin FROM Reservation;";
		
	public long create(Reservation reservation) throws DaoException {
		try
		{
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement stmt = connection.prepareStatement(CREATE_RESERVATION_QUERY, Statement.RETURN_GENERATED_KEYS);

			stmt.setInt(1, reservation.getClient().getId());
			stmt.setInt(2, reservation.getVehicule().getId());
			stmt.setDate(3, Date.valueOf(reservation.getDebut()));
			stmt.setDate(4, Date.valueOf(reservation.getFin()));

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
	
	public int delete(Reservation reservation) throws DaoException {
		try
		{
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement stmt = connection.prepareStatement(DELETE_RESERVATION_QUERY);

			stmt.setInt(1, reservation.getId());

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


	public Reservation findResaById(int id) throws DaoException {
		try
		{
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement stmt = connection.prepareStatement(FIND_RESERVATION_QUERY);

			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();

			Reservation reservation = new Reservation();

			if(rs.next()) {
				reservation.setId(rs.getInt("id"));
				reservation.setClient(clientDao.findById(rs.getInt("client_id")));
				reservation.setVehicule(vehicleDao.findById(rs.getInt("vehicle_id")));
				reservation.setDebut(rs.getDate("debut").toLocalDate());
				reservation.setFin(rs.getDate("fin").toLocalDate());
			}
			stmt.close();
			connection.close();

			return reservation;
		}
		catch (SQLException e)
		{
			throw new DaoException(e.getMessage());
		}
	}

	public List<Reservation> findResaByClientId(int clientId) throws DaoException {
		try
		{
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement stmt = connection.prepareStatement(FIND_RESERVATIONS_BY_CLIENT_QUERY);

			stmt.setInt(1, clientId);
			ResultSet rs = stmt.executeQuery();

			ArrayList<Reservation> reservationList = new ArrayList<Reservation>();

			while(rs.next()) {
				Reservation reservation = new Reservation();

				reservation.setId(rs.getInt("id"));
				reservation.setClient(clientDao.findById(clientId));
				reservation.setVehicule(vehicleDao.findById(rs.getInt("vehicle_id")));
				reservation.setDebut(rs.getDate("debut").toLocalDate());
				reservation.setFin(rs.getDate("fin").toLocalDate());

				reservationList.add(reservation);
			}
			stmt.close();
			connection.close();

			return reservationList;
		}
		catch (SQLException e)
		{
			throw new DaoException(e.getMessage());
		}
	}
	
	public List<Reservation> findResaByVehicleId(long vehicleId) throws DaoException {
		try
		{
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement stmt = connection.prepareStatement(FIND_RESERVATIONS_BY_VEHICLE_QUERY);

			stmt.setLong(1, vehicleId);
			ResultSet rs = stmt.executeQuery();

			ArrayList<Reservation> reservationList = new ArrayList<Reservation>();

			while(rs.next()) {
				Reservation reservation = new Reservation();

				reservation.setId(rs.getInt("id"));
				reservation.setClient(clientDao.findById(rs.getInt("client_id")));
				reservation.setVehicule(vehicleDao.findById(vehicleId));
				reservation.setDebut(rs.getDate("debut").toLocalDate());
				reservation.setFin(rs.getDate("fin").toLocalDate());

				reservationList.add(reservation);
			}
			stmt.close();
			connection.close();

			return reservationList;
		}
		catch (SQLException e)
		{
			throw new DaoException();
		}
	}

	public List<Reservation> findAll() throws DaoException {
		ArrayList<Reservation> reservationList = new ArrayList<Reservation>();
		try
		{
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement stmt = connection.prepareStatement(FIND_RESERVATIONS_QUERY);

			ResultSet rs = stmt.executeQuery();

			while(rs.next()) {
				Reservation reservation = new Reservation();

				reservation.setId(rs.getInt("id"));
				reservation.setClient(clientDao.findById(rs.getInt("client_id")));
				reservation.setVehicule(vehicleDao.findById(rs.getInt("vehicle_id")));
				reservation.setDebut(rs.getDate("debut").toLocalDate());
				reservation.setFin(rs.getDate("fin").toLocalDate());

				reservationList.add(reservation);
			}

			stmt.close();
			connection.close();

			return reservationList;
		}
		catch (SQLException e)
		{
			throw new DaoException(e.getMessage());
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
