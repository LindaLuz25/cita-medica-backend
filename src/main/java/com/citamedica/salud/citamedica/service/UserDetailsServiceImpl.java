package com.citamedica.salud.citamedica.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.citamedica.salud.citamedica.dto.AuthCreateUserRequest;
import com.citamedica.salud.citamedica.dto.AuthLoginRequest;
import com.citamedica.salud.citamedica.dto.AuthResponse;
import com.citamedica.salud.citamedica.models.RoleEntity;
import com.citamedica.salud.citamedica.models.UserEntity;
import com.citamedica.salud.citamedica.repository.RoleRepository;
import com.citamedica.salud.citamedica.repository.UserRepository;
import com.citamedica.salud.citamedica.utils.JwtUtils;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

        @Autowired
        private UserService userService;

        @Autowired
        private JwtUtils jwtUtils;

        @Autowired
        private PasswordEncoder passwordEncoder;

        @Autowired
        private RoleRepository roleRepository;

        @Autowired
        private UserRepository userRepository;

        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                UserEntity userEntity = userRepository.findUserEntityByUsername(username)
                                .orElseThrow(() -> new UsernameNotFoundException(
                                                "El usuario " + username + " no existe"));
                List<SimpleGrantedAuthority> authorityList = new ArrayList<>();

                userEntity.getRoles().forEach(
                                role -> authorityList.add(
                                                new SimpleGrantedAuthority("ROLE_".concat(role.getRoleEnum().name()))));

                return new User(userEntity.getUsername(), userEntity.getPassword(), userEntity.isEnabled(),
                                userEntity.isAccountNonExpired(), userEntity.isCredentialsNonExpired(),
                                userEntity.isAccountNonLocked(),
                                authorityList);
        }

        public AuthResponse createUser(UserEntity createUserRequest) {
                String username = createUserRequest.getUsername();
                UserEntity newUser = userService.create(createUserRequest);


                UserEntity userSaved = userRepository.save(newUser);

                ArrayList<SimpleGrantedAuthority> authorities = new ArrayList<>();

                userSaved.getRoles().forEach(role -> authorities
                                .add(new SimpleGrantedAuthority("ROLE_".concat(role.getRoleEnum().name()))));

                Authentication authentication = new UsernamePasswordAuthenticationToken(userSaved, null, authorities);

                String accessToken = jwtUtils.createToken(authentication);

                AuthResponse authResponse = new AuthResponse(username, "User created succesfully", accessToken, true);
                return authResponse;
        }

        public AuthResponse loginUser(AuthLoginRequest authLoginRequest) {
                String username = authLoginRequest.username();
                String password = authLoginRequest.password();

                Authentication authentication = this.authenticate(username, password);

                SecurityContextHolder.getContext().setAuthentication(authentication);

                String accessToken = jwtUtils.createToken(authentication);
                AuthResponse authResponse = new AuthResponse(username, "User loged succcesfully", accessToken, true);
                return authResponse;

        }

        public Authentication authenticate(String username, String password) {
                UserDetails userDetails = this.loadUserByUsername(username);

                if (userDetails == null) {
                        throw new BadCredentialsException(String.format("Invalid username or password"));
                }

                if (!passwordEncoder.matches(password, userDetails.getPassword())) {
                        throw new BadCredentialsException("Incorrect Password");
                }

                return new UsernamePasswordAuthenticationToken(username, password, userDetails.getAuthorities());
        }

}
