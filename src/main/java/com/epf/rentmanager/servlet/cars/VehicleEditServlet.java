package com.epf.rentmanager.servlet.cars;

import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/cars/edit")
public class VehicleEditServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static String vehicleException = "";
    @Autowired VehicleService vehicleService;
    private Vehicle vehicle;

    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        try
        {
            int vehicleId = Integer.parseInt(request.getParameter("vehicleId"));
            this.vehicle = vehicleService.findById(vehicleId);

            request.setAttribute("vehicle", this.vehicle);
            request.setAttribute("vehicleException", vehicleException);
        }
        catch(ServiceException e)
        {
            throw new ServletException(e.getMessage());
        }
        this.getServletContext().getRequestDispatcher("/WEB-INF/views/cars/edit.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        try
        {
            String manufacturer = request.getParameter("manufacturer");
            String modele = request.getParameter("modele");
            int seats = Integer.parseInt(request.getParameter("seats"));

            Vehicle vehicle = new Vehicle(this.vehicle.getId(), manufacturer, modele, seats);
            vehicleService.edit(vehicle);
            vehicleException = "";

            response.sendRedirect("/rentmanager/cars");
        }
        catch(ServiceException e)
        {
            vehicleException = e.getMessage();
            request.setAttribute("vehicleException", vehicleException);
            doGet(request, response);
        }
    }
}
