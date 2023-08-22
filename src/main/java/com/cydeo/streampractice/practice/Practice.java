package com.cydeo.streampractice.practice;

import com.cydeo.streampractice.model.*;
import com.cydeo.streampractice.service.*;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;

@Component
public class Practice {

    public static CountryService countryService;
    public static DepartmentService departmentService;
    public static EmployeeService employeeService;
    public static JobHistoryService jobHistoryService;
    public static JobService jobService;
    public static LocationService locationService;
    public static RegionService regionService;

    public Practice(CountryService countryService, DepartmentService departmentService,
                    EmployeeService employeeService, JobHistoryService jobHistoryService,
                    JobService jobService, LocationService locationService,
                    RegionService regionService) {

        Practice.countryService = countryService;
        Practice.departmentService = departmentService;
        Practice.employeeService = employeeService;
        Practice.jobHistoryService = jobHistoryService;
        Practice.jobService = jobService;
        Practice.locationService = locationService;
        Practice.regionService = regionService;

    }

    // You can use the services above for all the CRUD (create, read, update, delete) operations.
    // Above services have all the required methods.
    // Also, you can check all the methods in the ServiceImpl classes inside the service.impl package, they all have explanations.

    // Display all the employees
    public static List<Employee> getAllEmployees() {
        return employeeService.readAll();
    }

    // Display all the countries
    public static List<Country> getAllCountries() {
        return countryService.readAll();
    }

    // Display all the departments
    public static List<Department> getAllDepartments() {
        return departmentService.readAll();
    }

    // Display all the jobs
    public static List<Job> getAllJobs() {
        return jobService.readAll();
    }

    // Display all the locations
    public static List<Location> getAllLocations() {
        return locationService.readAll();
    }

    // Display all the regions
    public static List<Region> getAllRegions() {
        return regionService.readAll();
    }

    // Display all the job histories
    public static List<JobHistory> getAllJobHistories() {
        return jobHistoryService.readAll();
    }

    // Display all the employees' first names
    public static List<String> getAllEmployeesFirstName() {
        return getAllEmployees().stream()
                //.map(p-> p.getFirstName()) // used method reference instead per IntelliJ suggestion.
                .map(Employee::getFirstName)
                .collect(Collectors.toList());
    }

    // Display all the countries' names
    public static List<String> getAllCountryNames() {
        return getAllCountries().stream()
                //.map(p->p.getCountryName())
                .map(Country::getCountryName)
                .collect(Collectors.toList());
    }

    // Display all the departments' managers' first names
    public static List<String> getAllDepartmentManagerFirstNames() {
        return getAllDepartments().stream()
                .map(p->p.getManager().getFirstName())
                .collect(Collectors.toList());
    }

    // Display all the departments where manager name of the department is 'Steven'
    public static List<Department> getAllDepartmentsWhichManagerFirstNameIsSteven() {
        return getAllDepartments().stream()
                .filter(p->p.getManager().getFirstName().equals("Steven"))
                .collect(Collectors.toList());
    }

    // Display all the departments where postal code of the location of the department is '98199'
    public static List<Department> getAllDepartmentsWhereLocationPostalCodeIs98199() {
        return getAllDepartments().stream()
                .filter(p-> p.getLocation().getPostalCode().equals("98199"))
                .collect(Collectors.toList());
    }

    // Display the region of the IT department
    public static Region getRegionOfITDepartment() throws Exception {
        /*
        Department dep = getAllDepartments().stream()
                        .filter(p->p.getDepartmentName().equals("IT"))
                .findFirst().get();
        return dep.getLocation().getCountry().getRegion();
         */
        return getAllDepartments().stream()
                .filter(department -> department.getDepartmentName().equals("IT"))
                .findFirst().orElseThrow(()-> new Exception("Department not found!"))
                .getLocation().getCountry().getRegion();


    }

    // Display all the departments where the region of department is 'Europe'
    public static List<Department> getAllDepartmentsWhereRegionOfCountryIsEurope() {

        return getAllDepartments().stream()
                .filter(p->p.getLocation().getCountry().getRegion().getRegionName().equals("Europe"))
                .collect(Collectors.toList());
    }

    // Display if there is any employee with salary less than 1000. If there is none, the method should return true
    public static boolean checkIfThereIsNoSalaryLessThan1000() {
        return getAllEmployees().stream()
                //.map(p->p.getSalary())
                .map(Employee::getSalary)
                .noneMatch(p->p<1000);
    }

    // Check if the salaries of all the employees in IT department are greater than 2000 (departmentName: IT)
    public static boolean checkIfThereIsAnySalaryGreaterThan2000InITDepartment() {
        return getAllEmployees().stream()
                .filter(p->p.getDepartment().getDepartmentName().equals("IT"))
                .anyMatch(p->p.getSalary()>2000);

    }

    // Display all the employees whose salary is less than 5000
    public static List<Employee> getAllEmployeesWithLessSalaryThan5000() {

        return getAllEmployees().stream().filter(p->p.getSalary()<5000).collect(Collectors.toList());

    }

    // Display all the employees whose salary is between 6000 and 7000
    public static List<Employee> getAllEmployeesSalaryBetween() {
        return getAllEmployees().stream()
                .filter(p-> p.getSalary()<7000 && p.getSalary()>6000)
                .collect(Collectors.toList());
    }

    // Display the salary of the employee Grant Douglas (lastName: Grant, firstName: Douglas)
    public static Long getGrantDouglasSalary() throws Exception {
        return getAllEmployees().stream()
                .filter(p-> p.getLastName().equals("Grant") &&p.getFirstName().equals("Douglas"))
                //.map(p->p.getSalary())
                .map(Employee::getSalary)
                .findFirst().get();
    }

    // Display the maximum salary an employee gets
    public static Long getMaxSalary() throws Exception {
        return getAllEmployees().stream()
                //.map(p->p.getSalary())
                .map(Employee::getSalary)
                .reduce((a,b) -> ( a>b? a:b)).get();
    }

    // Display the employee(s) who gets the maximum salary
    public static List<Employee> getMaxSalaryEmployee() {
        return getAllEmployees().stream()
                .filter(p -> {
                    try {
                        return p.getSalary() == getMaxSalary();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toList());
    }

    // Display the max salary employee's job
    public static Job getMaxSalaryEmployeeJob() throws Exception {
        List<Employee> maxsalEmps = getAllEmployees().stream()
                .filter(p -> {
                    try {
                        return p.getSalary() == getMaxSalary();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toList());

        Job thejob = maxsalEmps.stream().map(Employee::getJob).distinct().findFirst().get();
        return thejob;
    }

    // Display the max salary in Americas Region
    public static Long getMaxSalaryInAmericasRegion() throws Exception {
        return employeeService.readAll().stream()
                .filter(p-> p.getDepartment().getLocation().getCountry().getRegion().getRegionName().equals("Americas"))
                //.map(p->p.getSalary())
                .map(Employee::getSalary)
                .reduce((a,b)->a>b? a : b).get();
    }

    // Display the second maximum salary an employee gets
    public static Long getSecondMaxSalary() throws Exception {
        return  employeeService.readAll().stream()
                .sorted(comparing(Employee::getSalary).reversed())
                //.map(p->p.getSalary())
                .map(Employee::getSalary)
                .distinct()
                .skip(1)
                .findFirst().get();
    }

    // Display the employee(s) who gets the second maximum salary
    public static List<Employee> getSecondMaxSalaryEmployee() throws Exception {
        Long max2 = getSecondMaxSalary();
        return employeeService.readAll().stream()
                .filter(p-> p.getSalary().equals(max2))
                .collect(Collectors.toList());
    }

    // Display the minimum salary an employee gets
    public static Long getMinSalary() throws Exception {
        return employeeService.readAll().stream()
                //.map(p->p.getSalary())
                .map(Employee::getSalary)
                .sorted()
                .reduce((a,b) -> a<b?a:b).get();
    }

    // Display the employee(s) who gets the minimum salary
    public static List<Employee> getMinSalaryEmployee() {
        return employeeService.readAll().stream()
                .filter(p-> {
                    try {
                        return p.getSalary()== (getMinSalary());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toList());
    }

    // Display the second minimum salary an employee gets
    public static Long getSecondMinSalary() {

        return employeeService.readAll().stream()
                //.map(p->p.getSalary())
                .map(Employee::getSalary)
                .sorted()
                .distinct()
                .skip(1)
                .findFirst().get();
    }

    // Display the employee(s) who gets the second minimum salary
    public static List<Employee> getSecondMinSalaryEmployee()  {
        Long min2 = getSecondMinSalary();
        return employeeService.readAll().stream()
                .filter(p-> p.getSalary().equals(min2))
                .collect(Collectors.toList());
    }

    // Display the average salary of the employees
    public static Double getAverageSalary() {
        return employeeService.readAll().stream()
                //.collect(Collectors.averagingDouble(p->p.getSalary()));
                .collect(Collectors.averagingDouble(Employee::getSalary));
    }

    // Display all the employees who are making more than average salary
    public static List<Employee> getAllEmployeesAboveAverage() {

        return employeeService.readAll().stream()
                .filter(p-> p.getSalary() > getAverageSalary())
                .collect(Collectors.toList());
    }

    // Display all the employees who are making less than average salary
    public static List<Employee> getAllEmployeesBelowAverage() {
        return employeeService.readAll().stream()
                .filter(p -> p.getSalary() < getAverageSalary())
                .collect(Collectors.toList());
    }

    // Display all the employees separated based on their department id number
    public static Map<Long, List<Employee>> getAllEmployeesForEachDepartment() {
        return employeeService.readAll().stream()
                .collect(Collectors.groupingBy(p->p.getDepartment().getId()));
    }

    // Display the total number of the departments
    public static Long getTotalDepartmentsNumber() {
        //return departmentService.readAll().stream().collect(Collectors.counting());
        return departmentService.readAll().stream().count();
    }

    // Display the employee whose first name is 'Alyssa' and manager's first name is 'Eleni' and department name is 'Sales'
    public static Employee getEmployeeWhoseFirstNameIsAlyssaAndManagersFirstNameIsEleniAndDepartmentNameIsSales() throws Exception {
        return employeeService.readAll().stream()
                .filter(p-> p.getFirstName().equals("Alyssa") && p.getDepartment().getDepartmentName().equals("Sales") && p.getManager().getFirstName().equals("Eleni"))
                .findFirst().get();
    }

    // Display all the job histories in ascending order by start date
    public static List<JobHistory> getAllJobHistoriesInAscendingOrder() {
        return jobHistoryService.readAll().stream()
                .sorted(comparing(JobHistory::getStartDate))
                .collect(Collectors.toList());
    }

    // Display all the job histories in descending order by start date
    public static List<JobHistory> getAllJobHistoriesInDescendingOrder() {
        return jobHistoryService.readAll().stream()
                .sorted(comparing(JobHistory::getStartDate).reversed())
                .collect(Collectors.toList());
    }

    // Display all the job histories where the start date is after 01.01.2005
    public static List<JobHistory> getAllJobHistoriesStartDateAfterFirstDayOfJanuary2005() {
        return jobHistoryService.readAll().stream()
                .filter(p-> p.getStartDate().isAfter(LocalDate.of(2005,01,01)))
                .collect(Collectors.toList());
    }

    // Display all the job histories where the end date is 31.12.2007 and the job title of job is 'Programmer'
    public static List<JobHistory> getAllJobHistoriesEndDateIsLastDayOfDecember2007AndJobTitleIsProgrammer() {
        return jobHistoryService.readAll().stream()
                .filter(p->p.getEndDate().isEqual(LocalDate.of(2007,12,31)))
                .filter(p->p.getJob().getJobTitle().equals("Programmer"))
                .collect(Collectors.toList());
    }

    // Display the employee whose job history start date is 01.01.2007
    // and job history end date is 31.12.2007 and department's name is 'Shipping'
    public static Employee getEmployeeOfJobHistoryWhoseStartDateIsFirstDayOfJanuary2007AndEndDateIsLastDayOfDecember2007AndDepartmentNameIsShipping() throws Exception {
        return jobHistoryService.readAll().stream()
                .filter(p->p.getStartDate().isEqual(LocalDate.of(2007,01,01))
                        && p.getEndDate().isEqual(LocalDate.of(2007,12,31))
                        && p.getDepartment().getDepartmentName().equals("Shipping"))
                .map(m->m.getEmployee())
                .findFirst().get();
    }

    // Display all the employees whose first name starts with 'A'
    public static List<Employee> getAllEmployeesFirstNameStartsWithA() {
        return employeeService.readAll().stream()
                .filter(p-> p.getFirstName().startsWith("A"))
                .collect(Collectors.toList());
    }

    // Display all the employees whose job id contains 'IT'
    public static List<Employee> getAllEmployeesJobIdContainsIT() {
        return employeeService.readAll().stream()
                .filter(p->p.getJob().getId().contains("IT"))
                .collect(Collectors.toList());
    }

    // Display the number of employees whose job title is programmer and department name is 'IT'
    public static Long getNumberOfEmployeesWhoseJobTitleIsProgrammerAndDepartmentNameIsIT() {
        return employeeService.readAll().stream()
                .filter(p->
                        p.getJob().getJobTitle().equals("Programmer")
                && p.getDepartment().getDepartmentName().equals("IT"))
                .collect(Collectors.counting());
    }

    // Display all the employees whose department id is 50, 80, or 100
    public static List<Employee> getAllEmployeesDepartmentIdIs50or80or100() {
        return employeeService.readAll().stream()
                .filter(p->
                        p.getDepartment().getId() == 50L||
                        p.getDepartment().getId() == 80L||
                        p.getDepartment().getId() == 100L)
                .collect(Collectors.toList());
    }

    // Display the initials of all the employees
    // Note: You can assume that there is no middle name
    public static List<String> getAllEmployeesInitials() {
        return employeeService.readAll().stream()
                .map(m ->
                        m.getFirstName().charAt(0) +
                        "" +
                        m.getLastName().charAt(0))
                .collect(Collectors.toList());
    }

    // Display the full names of all the employees
    public static List<String> getAllEmployeesFullNames() {
        return employeeService.readAll().stream()
                .map(m->
                        m.getFirstName() +
                        " " +
                        m.getLastName())
                .collect(Collectors.toList());
    }

    // Display the length of the longest full name(s)
    public static Integer getLongestNameLength() throws Exception {
        return getAllEmployeesFullNames().stream()
                .map(m-> m.length())
                .reduce(Integer::max).get();
    }

    // Display the employee(s) with the longest full name(s)
    public static List<Employee> getLongestNamedEmployee() {
        return employeeService.readAll().stream()
                .filter(p-> {
                    try {
                        return ( (p.getLastName()+" "+p.getFirstName()).length() == getLongestNameLength());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toList());
    }

    // Display all the employees whose department id is 90, 60, 100, 120, or 130
    public static List<Employee> getAllEmployeesDepartmentIdIs90or60or100or120or130() {
        return employeeService.readAll().stream()
                .filter(p->
                        p.getDepartment().getId() == 90L ||
                        p.getDepartment().getId() == 60L ||
                        p.getDepartment().getId() == 100L ||
                        p.getDepartment().getId() == 120L ||
                        p.getDepartment().getId() == 130L)
                .collect(Collectors.toList());
    }

    // Display all the employees whose department id is NOT 90, 60, 100, 120, or 130
    public static List<Employee> getAllEmployeesDepartmentIdIsNot90or60or100or120or130() {
        return employeeService.readAll().stream()
                .filter(p->
                        p.getDepartment().getId() != 90L &&
                                p.getDepartment().getId() != 60L &&
                                p.getDepartment().getId() != 100L &&
                                p.getDepartment().getId() != 120L &&
                                p.getDepartment().getId() != 130L)
                .collect(Collectors.toList());
    }

}
