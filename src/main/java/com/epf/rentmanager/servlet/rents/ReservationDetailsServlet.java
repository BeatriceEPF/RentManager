package com.epf.rentmanager.servlet.rents;

import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/rents/details")
public class ReservationDetailsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    @Autowired ReservationService reservationService;

    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try
        {
            int reservationId = Integer.parseInt(request.getParameter("reservationId"));

            Reservation reservation = reservationService.findResaById(reservationId);
            Vehicle vehicle = reservation.getVehicule();
            Client client = reservation.getClient();

            request.setAttribute("reservation", reservation);
            request.setAttribute("vehicle", vehicle);
            request.setAttribute("client", client);
            request.setAttribute("vehicleCount", 1);
            request.setAttribute("clientCount", 1);
        }
        catch(ServiceException e)
        {
            throw new ServletException(e.getMessage());
        }
        this.getServletContext().getRequestDispatcher("/WEB-INF/views/rents/details.jsp").forward(request, response);
    }
}
