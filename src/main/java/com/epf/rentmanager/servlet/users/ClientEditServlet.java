package com.epf.rentmanager.servlet.users;

import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.ClientService;
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

@WebServlet("/users/edit")
public class ClientEditServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static String clientException = "";
    @Autowired ClientService clientService;

    private Client client;

    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        try
        {
            if(this.client == null)
            {
                int clientId = Integer.parseInt(request.getParameter("clientId"));
                this.client = clientService.findById(clientId);
            }

            request.setAttribute("client", this.client);
            request.setAttribute("clientException", clientException);
        }
        catch(ServiceException e)
        {
            throw new ServletException(e.getMessage());
        }
        this.getServletContext().getRequestDispatcher("/WEB-INF/views/users/edit.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        try
        {
            String name = request.getParameter("last_name");
            String firstName = request.getParameter("first_name");
            String email = request.getParameter("email");
            LocalDate birthdate = LocalDate.parse(request.getParameter("birthdate"));

            Client client = new Client(this.client.getId(), name, firstName, email, birthdate);
            clientService.edit(client);
            clientException = "";

            response.sendRedirect("/rentmanager/users");
        }
        catch(ServiceException e)
        {
            clientException = e.getMessage();
            request.setAttribute("clientException", clientException);
            doGet(request, response);
        }
    }
}
