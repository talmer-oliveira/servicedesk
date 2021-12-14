package com.talmer.servicedesk.service;

import static com.talmer.servicedesk.domain.enums.ServiceCategoryType.SERVICE_REQUEST;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.talmer.servicedesk.dto.AuthorDTO;
import com.talmer.servicedesk.dto.RequestTicketDTO;
import com.talmer.servicedesk.dto.ServiceCategoryDTO;
import com.talmer.servicedesk.dto.UserDTO;
import com.talmer.servicedesk.repository.RequestTicketRepository;
import com.talmer.servicedesk.security.AuthService;
import com.talmer.servicedesk.security.AuthUser;
import com.talmer.servicedesk.service.exception.ServiceCategoryNotFoundException;
import com.talmer.servicedesk.domain.RequestTicket;
import com.talmer.servicedesk.domain.enums.Role;
import com.talmer.servicedesk.domain.enums.Status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class RequestTicketServiceTest {
    
    @Mock
    private RequestTicketRepository requestTicketRepository;
    
    @Mock
    private ServiceCategoryService serviceCategoryService;

    @Mock
    private AuthService authService;

    @Mock
    private UserService userService;

    @InjectMocks
    private RequestTicketService requestTicketService;

    @Test
    public void whenCreateTicketIsCalledWithAValidRequestTicketThenCreateOne(){
        UserDTO expectUserDTO = new UserDTO("teste-email@tmail.com", "Test Person", "01561607061", "o7,%kdy45LL?)p0");
        expectUserDTO.setId("sdjflkjdskfjdslfkj");
        ServiceCategoryDTO expectedCategoryDTO = 
				new ServiceCategoryDTO("Criação de Funcionalidade", SERVICE_REQUEST.getCode());
        AuthorDTO authorDTO = new AuthorDTO("sdjflkjdskfjdslfkj", "teste-email@tmail.com", "Test Person");
        RequestTicket expectedRequestTicket = 
                                new RequestTicket(
                                                "Criar Funcionalidade",
                                                "Implementar funcionalidade para listar todas as minhas solicitações",
                                                new Date(), Status.PENDING, expectedCategoryDTO, authorDTO);
        RequestTicketDTO ticketDTO = 
                    new RequestTicketDTO("Criar Funcionalidade", "Implementar funcionalidade para listar todas as minhas solicitações",
                                        "Criação de Funcionalidade", null);
        Set<Role> roles = new HashSet<>();
        roles.add(Role.USER);
        AuthUser authenticatedUser = new AuthUser(expectUserDTO.getId(), expectUserDTO.getEmail(), null, true, roles);
        
        when(authService.authenticated()).thenReturn(authenticatedUser);
        when(userService.findById(expectUserDTO.getId())).thenReturn(expectUserDTO);
        when(requestTicketRepository.save(Mockito.any(RequestTicket.class))).thenReturn(expectedRequestTicket);
        when(serviceCategoryService.findByName(expectedCategoryDTO.getName())).thenReturn(expectedCategoryDTO);

        RequestTicket requestTicket = requestTicketService.createTicket(ticketDTO);

        assertThat(requestTicket.getTitle(), is(equalTo(expectedRequestTicket.getTitle())));
        assertThat(requestTicket.getDescription(), is(equalTo(expectedRequestTicket.getDescription())));
        assertThat(requestTicket.getStatus(), is(equalTo(expectedRequestTicket.getStatus())));
        assertThat(requestTicket.getServiceCategoryDTO(), is(equalTo(expectedRequestTicket.getServiceCategoryDTO())));
        assertThat(requestTicket.getRequester(), is(equalTo(expectedRequestTicket.getRequester())));
    }

    @Test
    public void whenCrateTicketIsCalledWithNonExistentCategoryThenThrowAnException(){

      RequestTicketDTO ticketDTO = 
                    new RequestTicketDTO("Criar Funcionalidade", "Implementar funcionalidade para listar todas as minhas solicitações",
                                        "Criação de Funcionalidade", null);

      when(serviceCategoryService.findByName("Criação de Funcionalidade"))
      .thenThrow(new ServiceCategoryNotFoundException("Criação de Funcionalidade"));

      assertThrows(ServiceCategoryNotFoundException.class, () -> requestTicketService.createTicket(ticketDTO));
    }
}
