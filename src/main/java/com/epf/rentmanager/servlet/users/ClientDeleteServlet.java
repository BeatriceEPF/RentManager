package com.epf.rentmanager.servlet.users;


import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
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
import java.util.List;

@WebServlet("/users/delete")
public class ClientDeleteServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
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
            int clientId = Integer.parseInt(request.getParameter("clientId"));
            Client client = clientService.findById(clientId);

            List<Reservation> reservations = reservationService.findResaByClientId(clientId);

            if(!reservations.isEmpty()) {
                for(Reservation reservation : reservations) {
                    reservationService.delete(reservation);
                }
            }

            clientService.delete(client);
        }
        catch(ServiceException e)
        {
            throw new ServletException(e.getMessage());
        }
        response.sendRedirect("/rentmanager/users");
    }
}
