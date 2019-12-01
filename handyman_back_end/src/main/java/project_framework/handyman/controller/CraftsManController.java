package project_framework.handyman.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import project_framework.handyman.Services.Interfaces.ArtisanService;
import project_framework.handyman.Services.Interfaces.ServiceService;
import project_framework.handyman.models.Artisan;
import project_framework.handyman.models.Role;
import project_framework.handyman.models.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")

public class CraftsManController {
    private ArtisanService artisanService;
    private ServiceService serviceService;

    @Autowired
    public CraftsManController(ArtisanService theartisanservice,ServiceService theserviceservice){
        artisanService=theartisanservice;
        serviceService=theserviceservice;
    }

    @GetMapping("/artisans")
    public List<Artisan> getArtisans(){
        return artisanService.findAll();
    }

    @GetMapping("/artisan")
    public Artisan getArtisan(@RequestParam int id){
        return artisanService.findById(id);
    }

    @GetMapping("/saveartisan")
    public void setartisan()
    {
        Artisan artisan=new Artisan("walim","krichen","walim","krichenwalim@gmail.com","58856530","27/01/1998","sfax","plumber","58856530","5/5","individuel","hi i am walim","img.png");
        Set<Service> services = new HashSet<>();
        Service s = serviceService.findById(1);
        services.add(s);
        artisan.setServices(services);
        artisanService.save(artisan);
    }

    @GetMapping("/deleteartisan")
    public void deleteArtisan(@RequestParam int id){ artisanService.deleteById(id); }
}
