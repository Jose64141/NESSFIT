package cl.italosoft.nessfit;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication()
public class NessfitApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(NessfitApplication.class, args);
    }
}



