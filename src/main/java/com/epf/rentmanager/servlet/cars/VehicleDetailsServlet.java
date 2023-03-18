package com.epf.rentmanager.servlet.cars;

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

@WebServlet("/cars/details")
public class VehicleDetailsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    @Autowired VehicleService vehicleService;
    @Autowired ReservationService resaService;

    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try
        {
            int vehicleId = Integer.parseInt(request.getParameter("vehicleId"));

            Vehicle vehicle = vehicleService.findById(vehicleId);
            List<Reservation> reservationList = resaService.findResaByVehicleId(vehicleId);

            ArrayList<Client> clientList = new ArrayList<>();
            for(Reservation reservation : reservationList)
            {
                if(!clientList.contains(reservation.getClient()))
                {
                    clientList.add(reservation.getClient());
                }
            }

            request.setAttribute("vehicle", vehicle);

            request.setAttribute("reservationCount", reservationList.size());
            request.setAttribute("clientCount", clientList.size());

            request.setAttribute("reservations", reservationList);
            request.setAttribute("clients", clientList);
        }
        catch(ServiceException e)
        {
            throw new ServletException(e.getMessage());
        }
        this.getServletContext().getRequestDispatcher("/WEB-INF/views/cars/details.jsp").forward(request, response);
    }
}
