package com.swim.community.controller;

import com.swim.community.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/dataservice")
public class DataController {

    @Autowired
    private DataService dataService;

    @RequestMapping("/recordUV/{ip}")
    public void recordUV(@PathVariable("ip") String ip) {
        dataService.recordUV(ip);
    }

    @RequestMapping("/calculateUV")
    public ResponseEntity<String> calculateUV(@RequestParam("start") Date start, @RequestParam("end") Date end) {
        return ResponseEntity.ok(dataService.calculateUV(start, end)+"");
    }

    @RequestMapping("/recordDAU/{userId}")
    public void recordDAU(@PathVariable("userId") int userId) {
        dataService.recordDAU(userId);
    }

    @RequestMapping("/calculateDAU")
    public ResponseEntity<String> calculateDAU(@RequestParam("start") Date start, @RequestParam("end") Date end) {
        return ResponseEntity.ok(dataService.calculateDAU(start, end)+"");
    }

}
