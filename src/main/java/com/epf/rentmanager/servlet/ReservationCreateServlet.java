package com.epf.rentmanager.servlet;

import com.epf.rentmanager.exception.DaoException;
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

@WebServlet("/rents/create")
public class ReservationCreateServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Autowired VehicleService vehicleService;
    @Autowired ClientService clientService;
    @Autowired ReservationService reservationService;
    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        try
        {
            request.setAttribute("vehicles", vehicleService.findAll());
            request.setAttribute("clients", clientService.findAll());
        }
        catch (ServiceException | DaoException e)
        {
            throw new RuntimeException(e);
        }
        this.getServletContext().getRequestDispatcher("/WEB-INF/views/rents/create.jsp").forward(request, response);
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

            Reservation reservation = new Reservation(-1, client, car, begin, end);
            reservationService.create(reservation);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new ServletException(e.getMessage());
        }
        response.sendRedirect("/rentmanager/rents");
    }
}
