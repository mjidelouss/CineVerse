package com.cine.verse.Mapper;

import com.cine.verse.domain.AppUser;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AppUserMapper {
    AppUser loginVMToAppUser(LoginVM loginVM);

    UserVM AppUserToUserVM(AppUser appUser);
}
