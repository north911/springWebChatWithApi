package com.netcracker.studPract.controllers;
import com.netcracker.devschool.dev4.studPract.entity.*;
import com.netcracker.devschool.dev4.studPract.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@Controller
public class AdminPageController {

    @Autowired
    SpecialityService specialityService;

    @RequestMapping(value = "/client", method = RequestMethod.GET)
    public String daun(){
        return "client";
    }

    @RequestMapping(value = "/webagent", method = RequestMethod.GET)
    public String daun2(){
        return "webagent";
    }

    @RequestMapping(value = "/getSpecialitiesByFacultyId/{id}", method = RequestMethod.GET)
    @ResponseBody
    public List<SpecialityEntity> getSpecilaitiesByFacultyId(@PathVariable int id) {
        return specialityService.findByFacultyId(id);
    }


}
