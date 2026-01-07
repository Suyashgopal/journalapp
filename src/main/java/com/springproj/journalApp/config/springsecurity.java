package com.springproj.journalApp.config;

import com.springproj.journalApp.service.userdetailserviceimpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity//this annotation signals spring to enable web security support  this is what makes application secured its used in conjuction with cofiguration
//websecurityconfurationadapter is a utility class in the spring security framework that provides default configurations and allows coustomization of certain
//features by extending it u can configure and coustomize spring security for your applications needs

public class springsecurity extends WebSecurityConfigurerAdapter {


@Autowired
private userdetailserviceimpl userdetailservice;



    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);
        //this method provides a way to configure how request are sequres
        //it defines how request matchinh should be done and what security actions should be applied
        http
                .authorizeRequests()//tels spring to start authorising requests
                .antMatchers("/journal/**").permitAll()//the request matching path jounal should be permited /allowedfor all the users wether they are authenticated or not||** is wild card pattern
                .anyRequest().authenticated()//any request which is not matched by previous matcher should be authenticated yani users ko password dena padega iske lia
                .and()//this method join several configurations it helps to continue the configuration from root firse http pe chalajayega --redirected
                .formLogin();// enables form based authentication by default it will provide a form for the user to enter their username and pswd if the user is not authenticated an they try to access the secured endpts they will be redirected to the default login form

        // the multiple dots is method chaining

        //u can access /helo without any authentication however if u try to acces another endpt u will be redirected to a login form

        //when we use the .formlogin() method in out security config without specifying the .loginpage("coustom path") the default login page becomes active  }
//spring security provides an in built controller that handles the /login path this controler is responsible for rendering the default login form when a get request is made to /login
//by default spring security also provides a logout functionality when .logout() is cofigured a post request to/logout wil log the user out and invalidate their session
//basic authentication is by desing stateless- har baar  har req mai username aur password bhejna padega
   /*
   some applicatiom do mix basic authentication with session management for various reasons
   thi isnt stdandard behavour an requres additional setup and logic in such scenarios behaviour and requires additional setup and logic
   once user credentias are verified via basic authentication a session might be establshied and the client provided a session cookie
   this way client wont need to send any authorization heaer with every request and the server can rely on the session cookie to identify the authenticated user



   when u login via spring security it manages to authenticate u across the multiple requests despite
    http being stateless
    1 session creation after sucessful authentication an http session is formed ur authentication details  are stored in this session
    2 session cookie a JSESSIONID cookie is sent to ur browser which gets sent back when subsequent requests helping the server recognise ut session  //agli req m hum jessionid bhejenge wapis with the request
    3 security context --using the jessionid spring security fetches ur authentication details for each request
    4 session timeout sessions hae a limited life if u are inactive past the limit u are logged out
    5 logout when logging out ur session ends and related cookies are removed
    remember me-- spring security can remember u after the session  the session ends using a diff persistent cookie

    spring security uses sessions and cookies maily jsessionid to esure u remain authenticated for multuple req


    */
  }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userdetailservice).passwordEncoder(passwordEncoder());

    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();

    }

}

// we want our sb application to authenticate users based on their credentials stored in mongodb database
//this means that our users and their pswd {hashed} wi be stored in mongodb and when a user tries to log in the system should check the provided credentials
//against whats stored in databse



/*  imp points for spring security
1a user entity to represent the user data model
2 a repository userrespositry to interace with mongodb
3 userdetailservice implimentation to fetch user details
5 a configuration securityconfig to integrate everyting with spring security
 */