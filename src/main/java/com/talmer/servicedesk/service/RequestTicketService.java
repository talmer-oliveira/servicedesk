package com.talmer.servicedesk.service;

import java.util.Date;

import com.talmer.servicedesk.domain.RequestTicket;
import com.talmer.servicedesk.domain.builder.RequestTicketBuilder;
import com.talmer.servicedesk.domain.enums.Status;
import com.talmer.servicedesk.dto.AuthorDTO;
import com.talmer.servicedesk.dto.RequestTicketDTO;
import com.talmer.servicedesk.dto.ServiceCategoryDTO;
import com.talmer.servicedesk.dto.UserDTO;
import com.talmer.servicedesk.repository.RequestTicketRepository;
import com.talmer.servicedesk.security.AuthService;
import com.talmer.servicedesk.security.AuthUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RequestTicketService {
    
    @Autowired
    private RequestTicketRepository requestTicketRepository;

    @Autowired
    private ServiceCategoryService serviceCategoryService;

    @Autowired
	private AuthService authService;

    @Autowired
    private UserService userService;

    public RequestTicket createTicket(RequestTicketDTO ticketDTO){
        ServiceCategoryDTO serviceCategoryDTO = serviceCategoryService.findByName(ticketDTO.getServiceCategoryName());
        RequestTicketBuilder builder = new RequestTicketBuilder();
        AuthUser authUser = authService.authenticated();
        UserDTO userDTO = userService.findById(authUser.getId());
        AuthorDTO authorDTO = new AuthorDTO(userDTO.getId(), userDTO.getEmail(), userDTO.getName());
        RequestTicket requestTicket =  builder.title(ticketDTO.getTitle())
                                    .description(ticketDTO.getDescryption())
                                    .status(Status.PENDING.getCode())
                                    .serviceCategory(serviceCategoryDTO)
                                    .requester(authorDTO)
                                    .timestamp(new Date())
                                    .build();

        return requestTicketRepository.save(requestTicket);
    }
}
