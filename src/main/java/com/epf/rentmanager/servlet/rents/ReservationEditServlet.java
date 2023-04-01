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

@WebServlet("/rents/edit")
public class ReservationEditServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static String rentException = "";
    @Autowired VehicleService vehicleService;
    @Autowired ClientService clientService;
    @Autowired ReservationService reservationService;
    private Reservation reservation;

    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        try
        {
            int reservationId = Integer.parseInt(request.getParameter("reservationId"));
            this.reservation = reservationService.findResaById(reservationId);
            Vehicle vehicle = reservation.getVehicule();
            Client client = reservation.getClient();

            request.setAttribute("reservation", reservation);
            request.setAttribute("vehicles", vehicleService.findAll());
            request.setAttribute("clients", clientService.findAll());
            request.setAttribute("rentException", rentException);
        }
        catch(ServiceException e)
        {
            throw new ServletException(e.getMessage());
        }
        this.getServletContext().getRequestDispatcher("/WEB-INF/views/rents/edit.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        try
        {
            int idCar = Integer.parseInt(request.getParameter("car"));
            int idClient = Integer.parseInt(request.getParameter("client"));

            Vehicle car = vehicleService.findById(idCar);
            Client client = clientService.findById(idClient);

            LocalDate begin = LocalDate.parse(request.getParameter("begin"));
            LocalDate end = LocalDate.parse(request.getParameter("end"));

            Reservation reservation = new Reservation(this.reservation.getId(), client, car, begin, end);
            reservationService.edit(reservation, this.reservation);
            rentException = "";

            response.sendRedirect("/rentmanager/rents");
        }
        catch(ServiceException e)
        {
            rentException = e.getMessage();
            request.setAttribute("rentException", rentException);
            doGet(request, response);
        }
    }
}
