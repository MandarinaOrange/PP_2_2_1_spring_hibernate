package hiber.service;

import hiber.dao.CarDao;
import hiber.dao.CarDaoImp;
import hiber.model.Car;

import java.util.List;

public interface CarService {
    void add(Car car);
    List<Car> listCars();
}
