package com.ecommerce.microcommerce.web.controller;

import com.ecommerce.microcommerce.model.JwtRequest;
import com.ecommerce.microcommerce.model.JwtResponse;
import com.ecommerce.microcommerce.web.exceptions.UnAuthorizedException;
import com.ecommerce.microcommerce.web.services.JwtUserDetailsService;
import com.ecommerce.microcommerce.web.utils.JwtTokenUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
@Api(description = "Auth avec un json web token", tags = "authenticate")
@RestController
@CrossOrigin
public class JwtAuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @ApiOperation(value = "Permet de génerer un json web token")
    @PostMapping(value = "/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws UnAuthorizedException {

        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

        final String token = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(token));
    }

    private void authenticate(String username, String password) throws UnAuthorizedException {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException err) {
            throw new UnAuthorizedException("USER_DISABLED: "+ err.getMessage());
        } catch (BadCredentialsException err) {
            throw new UnAuthorizedException("INVALID_CREDENTIALS: " + err.getMessage());
        }
    }
}
