package com.elCosito.Ferreteria.controlador;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class PortalControlador {
    public String inicio() {
        return "index";
    }
}
