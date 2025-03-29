package es.ticketmaster.entrega1.dto.user;

import java.util.Collection;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import es.ticketmaster.entrega1.model.UserEntity;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO toPrincipalDTO(UserEntity user);
    List<UserDTO> toPrincipalDTOs(Collection<UserEntity> users);

    @Mapping(target = "artistsList", ignore = true)
    @Mapping(target = "artistList", ignore = true)
    @Mapping(target = "ticketList", ignore = true)
    @Mapping(target = "profilePicture", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "roles", ignore = true)
    UserEntity toDomain(UserDTO userDTO);

    ShowUserDTO toShowUserDTO(UserEntity user);

    @Mapping(target = "artistList", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "profilePicture", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "ticketList", ignore = true)
    @Mapping(target = "artistsList", ignore = true)
    UserEntity toShowUserEntity(ShowUserDTO userDTO);
    
    List<ShowUserDTO> toShowUserDTOs(Collection<UserEntity> users);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "email", ignore = true)
    @Mapping(target = "country", ignore = true)
    @Mapping(target = "artistList", ignore = true)
    @Mapping(target = "profilePicture", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "artistsList", ignore = true)
    UserEntity toUserShowTicketsEntity(UserShowTicketsDTO userShowTicketsDTO);
    
    @Mapping(target = "ticketList", source = "ticketList")
    UserShowTicketsDTO toShowTicketsDTO(UserEntity user);
}