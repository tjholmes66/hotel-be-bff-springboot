package org.tomholmes.opensource.hotel.bff.service;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.Transactional;
import org.tomholmes.opensource.hotel.bff.HotelBeBffSpringbootApplication;

@SpringBootTest(classes = HotelBeBffSpringbootApplication.class)
@Transactional
@ComponentScan("com.tomholmes.product.jobsearch")
public class BaseServiceTests
{

}