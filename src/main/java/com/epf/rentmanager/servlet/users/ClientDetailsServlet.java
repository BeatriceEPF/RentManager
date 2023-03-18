package com.epf.rentmanager.servlet.users;


import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;
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

@WebServlet("/users/details")
public class ClientDetailsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    @Autowired ClientService clientService;
    @Autowired ReservationService resaService;

    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        try
        {
            int clientId = Integer.parseInt(request.getParameter("clientId"));

            Client client = clientService.findById(clientId);
            List<Reservation> reservationList = resaService.findResaByClientId(clientId);

            ArrayList<Vehicle> vehicleList = new ArrayList<>();
            for(Reservation reservation : reservationList)
            {
                if(!vehicleList.contains(reservation.getVehicule()))
                {
                    vehicleList.add(reservation.getVehicule());
                }
            }

            request.setAttribute("client", client);

            request.setAttribute("reservationCount", reservationList.size());
            request.setAttribute("vehicleCount", vehicleList.size());

            request.setAttribute("reservations", reservationList);
            request.setAttribute("vehicles", vehicleList);
        }
        catch(ServiceException e)
        {
            throw new ServletException(e.getMessage());
        }
        this.getServletContext().getRequestDispatcher("/WEB-INF/views/users/details.jsp").forward(request, response);
    }
}
