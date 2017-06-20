package org.launchcode.controllers;

import org.hibernate.validator.constraints.Mod10Check;
import org.launchcode.models.*;
import org.launchcode.models.forms.JobForm;
import org.launchcode.models.data.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.swing.text.Position;
import javax.validation.Valid;
import java.util.ArrayList;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping(value = "job")
public class JobController {

    private JobData jobData = JobData.getInstance();

    // The detail display for a given Job at URLs like /job?id=17
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model, int id) {

        // TODO #1 - get the Job with the given ID and pass it into the view
        Job jobOfInterest = jobData.findById(id);
        model.addAttribute("job", jobOfInterest);

        return "job-detail";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute(new JobForm());
        return "new-job";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(Model model, @ModelAttribute @Valid JobForm jobForm, Errors errors) {

        // TODO #6 - Validate the JobForm model, and if valid, create a
        // new Job and add it to the jobData data store. Then
        // redirect to the job detail view for the new Job.


        if (errors.hasErrors()) {
            model.addAttribute(jobForm);
            return "new-job";
        }

        String name = jobForm.getName();

        int newEmployer = jobForm.getEmployerId();
        Employer newEmployerObject = jobData.getEmployers().findById(newEmployer);

        int newLocation = jobForm.getLocationId();
        Location newLocationObject = jobData.getLocations().findById(newLocation);

        int newPositionType = jobForm.getPositionTypeId();
        PositionType newPositionTypeObject = jobData.getPositionTypes().findById(newPositionType);

        int newCoreCompetency = jobForm.getCoreCompetencyId();
        CoreCompetency newCoreCompetencyObject = jobData.getCoreCompetencies().findById(newCoreCompetency);


        Job newJob = new Job(name, newEmployerObject,newLocationObject, newPositionTypeObject, newCoreCompetencyObject);

        model.addAttribute("job", newJob);


        jobData.add(newJob);


        return "redirect:?id= " + jobForm.getEmployerId(); //add id here

        }
    }


