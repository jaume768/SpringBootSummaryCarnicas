package com.example.api.Controller;

import com.example.api.DTO.LineOrderDto;
import com.example.api.DTO.ListingDto;
import com.example.api.Model.LiniesNoFacturades;
import com.example.api.Repository.LineasNoFacturadesRepository;
import com.example.api.Service.SummaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/rest/orderPreparation")
public class SummaryController {

    private final LineasNoFacturadesRepository lineasNoFacturadesRepository;
    private final SummaryService summaryService;

    @Autowired
    public SummaryController(LineasNoFacturadesRepository lineasNoFacturadesRepository, SummaryService summaryService) {
        this.lineasNoFacturadesRepository = lineasNoFacturadesRepository;
        this.summaryService = summaryService;
    }

    @GetMapping("/all")
    public List<LiniesNoFacturades> getAllGcarnalls() {
        return lineasNoFacturadesRepository.findAll();
    }

    @GetMapping("/initSummary")
    public ListingDto getCombinedDetails() {
        return summaryService.getListings();
    }

    @GetMapping("/summary")
    public List<LineOrderDto> getLineOrders(
            @RequestParam String fecha,
            @RequestParam String filterType,
            @RequestParam Integer filterCode) {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date parsedDate = null;
        try {
            parsedDate = formatter.parse(fecha);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(parsedDate);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            parsedDate = calendar.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Timestamp timestamp = parsedDate != null ? new Timestamp(parsedDate.getTime()) : null;

        if (timestamp != null) {
            return summaryService.getLineOrdersByDateAndFilter(timestamp, filterType, filterCode);
        } else {
            return null;
        }
    }

}
