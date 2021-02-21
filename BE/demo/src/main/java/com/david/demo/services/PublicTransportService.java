package com.david.demo.services;

import com.david.demo.repository.BuslineRepository;
import com.david.demo.types.BusLine;
import com.david.demo.types.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PublicTransportService {

    @Autowired
    BuslineRepository buslineRepository;

    public List<BusLine> getBusLines(String orderBy, SortOrder sortOrder, Integer limit) {
        List<BusLine> busLines = buslineRepository.getBusLines();

        busLines = sort(busLines, orderBy, sortOrder);

        if(limit != null) {
            busLines = busLines.stream().limit(limit).collect(Collectors.toList());
        }

        return busLines;
    }

    private List<BusLine> sort(List<BusLine> busLines, String orderBy, SortOrder sortOrder) {
        Comparator comparator = null;
        if("lineNumber".equals(orderBy)) {
            comparator = Comparator.comparingInt(BusLine::getLineNumber);
        } else if("nrOfStops".equals(orderBy)) {
            comparator = Comparator.comparingInt(BusLine::getNrOfStops);
        }

        if(comparator != null) {
            if(sortOrder.equals(SortOrder.DESC)) {
                comparator = comparator.reversed();
            }

            busLines.sort(comparator);
        }

        return busLines;
    }
}
