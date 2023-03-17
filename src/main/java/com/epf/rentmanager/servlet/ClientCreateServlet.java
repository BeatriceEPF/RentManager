package com.epf.rentmanager.servlet;

import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
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

@WebServlet("/users/create")
public class ClientCreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static String clientException = "";
    @Autowired ClientService clientService;

    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        request.setAttribute("clientException", clientException);
        this.getServletContext().getRequestDispatcher("/WEB-INF/views/users/create.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        try
        {
            String name = request.getParameter("last_name");
            String firstName = request.getParameter("first_name");
            String email = request.getParameter("email");
            LocalDate birthdate = LocalDate.parse(request.getParameter("birthdate"));

            Client client = new Client(-1, name, firstName, email, birthdate);
            clientService.create(client);

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
