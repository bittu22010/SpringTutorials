package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.example.demo.service.LoginService;

@Controller
@SessionAttributes("name")
public class LoginController {

    @Autowired
    LoginService service;

    // Display the login page (GET request)
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String viewLoginPage(ModelMap model) {
        return "login";  // Return the login view (login.jsp or login.html)
    }

    // Handle the login form submission (POST request)
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String showWelcomePage(ModelMap model, 
                                  @RequestParam("name") String name, 
                                  @RequestParam("password") String password) {

        // Validate the user credentials
        boolean isValidUser = service.validateUser(name, password);

        if (!isValidUser) {
            // If invalid, return the login page with an error message
            model.put("errorMessage", "Access Denied, Invalid Credentials");
            return "login";
        }

        // If valid, store the user's name in the session and proceed to welcome page
        model.put("name", name);

        return "welcome";  // Return the welcome view (welcome.jsp or welcome.html)
    }

    // Handle logout (GET request)
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String showLogoutPage(ModelMap model) {
        // Optionally clear the session attributes here
        model.clear();
        return "login";  // Redirect to login page after logout
    }
}