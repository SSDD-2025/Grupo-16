package es.ticketmaster.entrega1.dto.user;

import es.ticketmaster.entrega1.model.UserEntity;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserShowTicketsDTO toShowTicketsDTO(UserEntity user);
}
