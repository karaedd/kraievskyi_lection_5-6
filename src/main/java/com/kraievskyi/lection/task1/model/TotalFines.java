package com.kraievskyi.lection.task1.model;

import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.List;
import lombok.Setter;

@XmlRootElement
public class TotalFines {

    @Setter
    @XmlElementWrapper(name = "totalFineList")
    private List<TotalFine> totalFine;
}
