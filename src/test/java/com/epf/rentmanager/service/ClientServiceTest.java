package com.epf.rentmanager.service;

import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ClientServiceTest {
    @InjectMocks private ClientService clientService;
    @Mock private ClientDao clientDao;

    @Test
    public void isLegal_true_if_age_over_18() {
        // Given
        Client legalClient = new Client(1, "Doe", "John", "john.doe@ensta.fr", LocalDate.of(2001, Month.JANUARY, 19));

        // Then
        assertTrue(ClientService.isLegal(legalClient));
    }

    @Test
    public void isLegal__false_if_age_under_18() {
        // Given
        Client illegaClient = new Client(1, "Doe", "John", "john.doe@ensta.fr", LocalDate.of(2010, Month.FEBRUARY, 9));

        // Then
        assertFalse(ClientService.isLegal(illegaClient));
    }

    @Test
    public void findAll_should_fail_when_dao_throws_exception() throws DaoException
    {
        when(this.clientDao.findAll()).thenThrow(DaoException.class); // When
        assertThrows(ServiceException.class, () -> clientService.findAll()); // Then
    }

}

