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
        import java.util.List;

@WebServlet("/cars/delete")
public class VehicleDeleteServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    @Autowired VehicleService vehicleService;
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
            int vehicleId = Integer.parseInt(request.getParameter("vehicleId"));
            Vehicle vehicle = vehicleService.findById(vehicleId);

            List<Reservation> reservations = reservationService.findResaByVehicleId(vehicleId);

            if(!reservations.isEmpty()) {
                for(Reservation reservation : reservations) {
                    reservationService.delete(reservation);
                }
            }
            vehicleService.delete(vehicle);
        }
        catch(ServiceException e)
        {
            throw new ServletException(e.getMessage());
        }
        response.sendRedirect("/rentmanager/cars");
    }
}
