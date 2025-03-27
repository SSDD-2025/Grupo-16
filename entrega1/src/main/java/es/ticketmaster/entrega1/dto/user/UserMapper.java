package es.ticketmaster.entrega1.dto.user;

import java.util.Collection;
import java.util.List;

import org.mapstruct.Mapper;

import es.ticketmaster.entrega1.model.UserEntity;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO toPrincipalDTO(UserEntity user);
    List<UserDTO> toPrincipalDTOs(Collection<UserEntity> users);
    UserEntity toDomain(UserDTO userDTO);


    ShowUserDTO toShowUserDTO(UserEntity user);
    List<ShowUserDTO> toShowUserDTOs(Collection<UserEntity> users);
    UserEntity toShowUserDTO(ShowUserDTO userDTO);
    UserShowTicketsDTO toShowTicketsDTO(UserEntity user);
}