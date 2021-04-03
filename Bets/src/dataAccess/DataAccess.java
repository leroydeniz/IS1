package dataAccess;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Vector;

//import javax.persistence.CascadeType;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
//import javax.persistence.FetchType;
//import javax.persistence.OneToMany;
import javax.persistence.Persistence;
import javax.persistence.RollbackException;
import javax.persistence.TypedQuery;
import javax.swing.JOptionPane;

import configuration.ConfigXML;
import configuration.UtilDate;
import domain.Admin;
import domain.Answer;
import domain.Apuesta;
import domain.Cliente;
import domain.CuentaGlobal;
import domain.Deporte;
import domain.Event;
import domain.Question;
import domain.Usuario;
//import exceptions.EventFinished;
import exceptions.QuestionAlreadyExist;

/**
 * It implements the data access to the objectDb database
 */
public class DataAccess  {
	protected static EntityManager  db;
	protected static EntityManagerFactory emf;


	ConfigXML c;

	public DataAccess(boolean initializeMode)  {
		
		c=ConfigXML.getInstance();
		
		System.out.println("Creating DataAccess instance => isDatabaseLocal: "+c.isDatabaseLocal()+" getDatabBaseOpenMode: "+c.getDataBaseOpenMode());

		String fileName=c.getDbFilename();
		if (initializeMode)
			fileName=fileName+";drop";
		
		if (c.isDatabaseLocal()) {
			  emf = Persistence.createEntityManagerFactory("objectdb:"+fileName);
			  db = emf.createEntityManager();
		} else {
			Map<String, String> properties = new HashMap<String, String>();
			  properties.put("javax.persistence.jdbc.user", c.getUser());
			  properties.put("javax.persistence.jdbc.password", c.getPassword());

			  emf = Persistence.createEntityManagerFactory("objectdb://"+c.getDatabaseNode()+":"+c.getDatabasePort()+"/"+fileName, properties);

			  db = emf.createEntityManager();
    	   }
	}

	public DataAccess()  {	
		 new DataAccess(false);
	}
	
	
	/**
	 * This is the data access method that initializes the database with some events and questions.
	 * This method is invoked by the business logic (constructor of BLFacadeImplementation) when the option "initialize" is declared in the tag dataBaseOpenMode of resources/config.xml file
	 */	
	public void initializeDB(){
		
		db.getTransaction().begin();
		try {


			Deporte d1 = new Deporte("Atletismo");
			Deporte d2 = new Deporte("Baloncesto");
			Deporte d3 = new Deporte("Béisbol");
			Deporte d4 = new Deporte("Boxeo");
			Deporte d5 = new Deporte("Ciclismo");
			Deporte d6 = new Deporte("Críquet");
			Deporte d7 = new Deporte("Fútbol");
			Deporte d8 = new Deporte("Rugby");
			Deporte d9 = new Deporte("Golf");
			Deporte d10 = new Deporte("Hockey");
			Deporte d11 = new Deporte("Natación");
			Deporte d12 = new Deporte("Polo");
			Deporte d13 = new Deporte("Tenis");
			Deporte d14 = new Deporte("Voleibol");
			
		   Calendar today = Calendar.getInstance();
		   
		   int month=today.get(Calendar.MONTH);
		   month+=1;
		   int year=today.get(Calendar.YEAR);
		   int day=today.get(Calendar.DATE);
		   if (month==12) { month=0; year+=1;}  
	    
			Event ev1=new Event(1, "Atlético-Athletic22", UtilDate.newDate(year,month,day+7), d7);
			Event ev2=new Event(2, "Eibar-Barcelona", UtilDate.newDate(year,month,day+7), d7);
			Event ev3=new Event(3, "Andorra-Bilbao Basket", UtilDate.newDate(year,month,day+7), d2);
			Event ev4=new Event(4, "Alavés-Deportivo", UtilDate.newDate(year,month,day+1), d7);
			Event ev5=new Event(5, "Español-Villareal", UtilDate.newDate(year,month,day+3), d7);
			Event ev6=new Event(6, "Las Palmas-Sevilla", UtilDate.newDate(year,month,day+12), d7);
			Event ev7=new Event(7, "Malaga-Valencia", UtilDate.newDate(year,month,day+11), d7);
			Event ev8=new Event(8, "Girona-Leganés", UtilDate.newDate(year,month,day+8), d7);
			Event ev9=new Event(9, "Real Sociedad-Levante", UtilDate.newDate(year,month,day+6), d7);
			Event ev10=new Event(10, "Betis-Real Madrid", UtilDate.newDate(year,month,day+9), d7);
			Event ev11=new Event(11, "Atlético-Athletic", UtilDate.newDate(year,month,day+1), d7);
			Event ev12=new Event(12, "Eibar-Barcelona", UtilDate.newDate(year,month,day+1), d7);
			Event ev13=new Event(13, "Copa del Rey de Béisbol", UtilDate.newDate(year,month,day+1), d3);
			Event ev14=new Event(14, "Alavés-Deportivo", UtilDate.newDate(year,month,day+1), d7);
			Event ev15=new Event(15, "Español-Villareal", UtilDate.newDate(year,month,day+1), d7);
			Event ev16=new Event(16, "Las Palmas-Sevilla", UtilDate.newDate(year,month,day+2), d7);
			Event ev17=new Event(17, "Málaga-Valencia2", UtilDate.newDate(year,month,day+3), d7);
			Event ev18=new Event(18, "Girona-Leganés", UtilDate.newDate(year,month,day+28), d7);
			Event ev19=new Event(19, "Real Sociedad-Levante", UtilDate.newDate(year,month,day+4), d7);
			Event ev20=new Event(20, "Betis-Real Madrid", UtilDate.newDate(year,month,day+5), d7);
			Event ev21=new Event(21, "VI Torneo Royal Bliss", UtilDate.newDate(year,month,day+5), d9);
			Event ev22=new Event(22, "Passion Golf Tour 2020", UtilDate.newDate(year,month,day+6), d9);
			Event ev23=new Event(23, "VIII Torneo San Juan", UtilDate.newDate(year,month,day+3), d9);
			Event ev24=new Event(24, "Volta Ciclista a Catalunya", UtilDate.newDate(year,month,13), d5);
			Event ev25=new Event(25, "Tour de Flandes", UtilDate.newDate(year,month,day+15), d5);
			Event ev26=new Event(26, "RideLondon-Surrey Classic", UtilDate.newDate(year,month,day+1), d5);
			Event ev27=new Event(27, "Grand Prix Cycliste de Québec", UtilDate.newDate(year,month,day+7), d5);
			

			d2.addEvent(ev3);
			d7.addEvent(ev1);
			d7.addEvent(ev2);
			d7.addEvent(ev4);
			d7.addEvent(ev5);
			d7.addEvent(ev6);
			d7.addEvent(ev7);
			d7.addEvent(ev8);
			d7.addEvent(ev9);
			d7.addEvent(ev10);
			d7.addEvent(ev11);
			d7.addEvent(ev12);
			d7.addEvent(ev13);
			d7.addEvent(ev14);
			d7.addEvent(ev15);
			d7.addEvent(ev16);
			d7.addEvent(ev17);
			d7.addEvent(ev18);
			d7.addEvent(ev19);
			d7.addEvent(ev20);
			d9.addEvent(ev21);
			d9.addEvent(ev22);
			d9.addEvent(ev23);
			d5.addEvent(ev24);
			d5.addEvent(ev25);
			d5.addEvent(ev26);
			d5.addEvent(ev27);
			
			
			Question q1=ev1.addQuestion("¿Quién ganará el partido?",1);
			Question q2=ev1.addQuestion("¿Quién meterá el primer gol?",2);
			Question q3=ev11.addQuestion("¿Quién ganará el partido?",1);
			Question q4=ev11.addQuestion("¿Cuántos goles se marcarán?",2);
			Question q5=ev17.addQuestion("¿Quién ganará el partido?",1);
			Question q6=ev17.addQuestion("¿Habrá goles en la primera parte?",2);
			Question q7=ev13.addQuestion("¿Quién ganará la copa?",3);
			Question q8=ev21.addQuestion("¿Quién ganará el torneo?",8);
			Question q9=ev22.addQuestion("¿Quién ganará el torneo?",4);
			Question q10=ev23.addQuestion("¿Quién ganará el torneo?",5);
			
			
			
			

			Answer ans01 = q1.addAnswer("Gana Atlético",(float) 1.5);
			Answer ans02 = q1.addAnswer("Gana Athletic", (float) 2.0);
			Answer ans03 = q1.addAnswer("Empate", (float) 3.5);
			Answer ans04 = q1.addAnswer("Sin goles", (float) 2.5);

			Answer ans05 = q2.addAnswer("Jugador 1",(float) 4.5);
			Answer ans06 = q2.addAnswer("Jugador 2", (float) 1.6);
			Answer ans07 = q2.addAnswer("Jugador 3", (float) 6.0);
			Answer ans08 = q2.addAnswer("Jugador 4", (float) 2.5);

			Answer ans09 = q10.addAnswer("Jugador 1",(float) 7.2);
			Answer ans10 = q10.addAnswer("Jugador 2", (float) 8.6);
			Answer ans11 = q10.addAnswer("Jugador 3", (float) 6.1);
			Answer ans12 = q10.addAnswer("Jugador 4", (float) 1.5);

			Answer ans13 = q7.addAnswer("Jugador 1",(float) 6.5);
			Answer ans14 = q7.addAnswer("Jugador 2", (float) 3.6);
			Answer ans15 = q7.addAnswer("Jugador 3", (float) 5.0);
			Answer ans16 = q7.addAnswer("Jugador 4", (float) 1.5);

			Answer ans17 = q9.addAnswer("Jugador 1",(float) 6.5);
			Answer ans18 = q9.addAnswer("Jugador 2", (float) 3.6);
			Answer ans19 = q9.addAnswer("Jugador 3", (float) 5.0);
			Answer ans20 = q9.addAnswer("Jugador 4", (float) 1.5);
			

			db.persist(d1);
			db.persist(d2);
			db.persist(d3);
			db.persist(d4);
			db.persist(d5);
			db.persist(d6);
			db.persist(d7);
			db.persist(d8);
			db.persist(d9);
			db.persist(d10);
			db.persist(d11);
			db.persist(d12);
			db.persist(d13);
			db.persist(d14);
			
			
			
			db.persist(q1);
			db.persist(q2);
			db.persist(q3);
			db.persist(q4);
			db.persist(q5);
			db.persist(q6);
			db.persist(q7);
			db.persist(q8);
			db.persist(q9);
			db.persist(q10);
	
			
	        
			db.persist(ev1);
			db.persist(ev2);
			db.persist(ev3);
			db.persist(ev4);
			db.persist(ev5);
			db.persist(ev6);
			db.persist(ev7);
			db.persist(ev8);
			db.persist(ev9);
			db.persist(ev10);
			db.persist(ev11);
			db.persist(ev12);
			db.persist(ev13);
			db.persist(ev14);
			db.persist(ev15);
			db.persist(ev16);
			db.persist(ev17);
			db.persist(ev18);
			db.persist(ev19);
			db.persist(ev20);
			db.persist(ev21);
			db.persist(ev22);
			db.persist(ev23);	
			db.persist(ev24);	
			db.persist(ev25);	
			db.persist(ev26);	
			db.persist(ev27);		
			
			

			db.persist(ans01);
			db.persist(ans02);
			db.persist(ans03);
			db.persist(ans04);
			db.persist(ans05);
			db.persist(ans06);
			db.persist(ans07);
			db.persist(ans08);
			db.persist(ans09);
			db.persist(ans10);
			db.persist(ans11);
			db.persist(ans12);
			db.persist(ans13);
			db.persist(ans14);
			db.persist(ans15);
			db.persist(ans16);
			db.persist(ans17);
			db.persist(ans18);
			db.persist(ans19);
			db.persist(ans20);


			
			
			//USUARIOS CLIENTES
			Cliente cli01 = new Cliente("user1", "pass1", "user1@mail.com", (float) 10.11 , "Usuario1 Apellido1", "ES12 3445 4567 2320 3456 8654 1234", "1245 6543 3456 8766");
			Cliente cli02 = new Cliente("user2", "pass2", "user2@mail.com", (float) 20.22 , "Usuario2 Apellido2", "ES23 3445 4567 2320 3456 8654 1234", "2345 6543 3456 8766");
			Cliente cli03 = new Cliente("user3", "pass3", "user3@mail.com", (float) 30.33 , "Usuario3 Apellido3", "ES34 3445 4567 2320 3456 8654 1234", "3445 6543 3456 8766");
			Cliente cli04 = new Cliente("user4", "pass4", "user4@mail.com", (float) 40.44 , "Usuario4 Apellido4", "ES45 3445 4567 2320 3456 8654 1234", "4545 6543 3456 8766");
			Cliente cli05 = new Cliente("user5", "pass5", "user5@mail.com", (float) 50.55 , "Usuario5 Apellido5", "ES56 3445 4567 2320 3456 8654 1234", "5645 6543 3456 8766");
			
			
			
			db.persist(cli01);
			db.persist(cli02);
			db.persist(cli03);
			db.persist(cli04);
			db.persist(cli05);

			
			
			//USUARIOS ADMINISTRADORES
			Admin adm = new Admin("", "", "admin@mail.com");
			Admin adm01 = new Admin("admin1", "pass1", "admin1@mail.com");
			Admin adm02 = new Admin("admin2", "pass2", "admin2@mail.com");


			
			db.persist(adm);
			db.persist(adm01);
			db.persist(adm02);
			
			

			//CUENTA GLOBAL
			CuentaGlobal cg = new CuentaGlobal(1, (float) 0.0);
			db.persist(cg);
			

			
			//PUESTAS DE PRUEBA EN CLIENTE 1
			Apuesta apu01 = ans01.addDummyBet((float) 101.0, cli01, UtilDate.newDate(year,month,1));
			Apuesta apu02 = ans02.addDummyBet((float) 224.0, cli04, UtilDate.newDate(year,month,2));
			Apuesta apu03 = ans03.addDummyBet((float) 309.0, cli01, UtilDate.newDate(year,month,2));
			Apuesta apu04 = ans04.addDummyBet((float) 478.0, cli01, UtilDate.newDate(year,month,3));
			Apuesta apu05 = ans04.addDummyBet((float) 558.0, cli02, UtilDate.newDate(year,month,3));
			Apuesta apu06 = ans02.addDummyBet((float) 698.0, cli01, UtilDate.newDate(year,month,4));
			Apuesta apu07 = ans04.addDummyBet((float) 145.0, cli01, UtilDate.newDate(year,month,4));
			Apuesta apu08 = ans03.addDummyBet((float) 110.0, cli03, UtilDate.newDate(year,month,4));
			Apuesta apu09 = ans04.addDummyBet((float) 10.0, cli02, UtilDate.newDate(year,month,5));
			Apuesta apu10 = ans04.addDummyBet((float) 8.0, cli01, UtilDate.newDate(year,month,6));
			Apuesta apu11 = ans20.addDummyBet((float) 55.0, cli04, UtilDate.newDate(year,month,6));
			Apuesta apu12 = ans19.addDummyBet((float) 889.0, cli01, UtilDate.newDate(year,month,7));
			Apuesta apu13 = ans01.addDummyBet((float) 690.0, cli04, UtilDate.newDate(year,month,7));
			Apuesta apu14 = ans02.addDummyBet((float) 558.0, cli01, UtilDate.newDate(year,month,8));
			Apuesta apu15 = ans08.addDummyBet((float) 44.0, cli02, UtilDate.newDate(year,month,8));
			Apuesta apu16 = ans09.addDummyBet((float) 86.0, cli03, UtilDate.newDate(year,month,9));
			Apuesta apu17 = ans15.addDummyBet((float) 98.0, cli04, UtilDate.newDate(year,month,9));
			Apuesta apu18 = ans13.addDummyBet((float) 121.0, cli01, UtilDate.newDate(year,month,9));
			Apuesta apu19 = ans12.addDummyBet((float) 87.0, cli02, UtilDate.newDate(year,month,10));
			Apuesta apu20 = ans01.addDummyBet((float) 88.0, cli03, UtilDate.newDate(year,month,10));
			Apuesta apu21 = ans02.addDummyBet((float) 52.0, cli04, UtilDate.newDate(year,month,11));
			Apuesta apu22 = ans03.addDummyBet((float) 155.0, cli01, UtilDate.newDate(year,month,11));
			Apuesta apu23 = ans10.addDummyBet((float) 74.0, cli02, UtilDate.newDate(year,month,12));
			Apuesta apu24 = ans04.addDummyBet((float) 322.0, cli03, UtilDate.newDate(year,month,12));
			Apuesta apu25 = ans01.addDummyBet((float) 45.0, cli04, UtilDate.newDate(year,month,12));
			Apuesta apu26 = ans14.addDummyBet((float) 8.0, cli01, UtilDate.newDate(year,month,12));
			Apuesta apu27 = ans13.addDummyBet((float) 89.0, cli02, UtilDate.newDate(year,month,12));
			Apuesta apu28 = ans13.addDummyBet((float) 199.0, cli03, UtilDate.newDate(year,month,13));
			Apuesta apu29 = ans18.addDummyBet((float) 200.0, cli04, UtilDate.newDate(year,month,13));
			Apuesta apu30 = ans06.addDummyBet((float) 208.0, cli01, UtilDate.newDate(year,month,13));
			Apuesta apu31 = ans01.addDummyBet((float) 98.0, cli02, UtilDate.newDate(year,month,14));
			Apuesta apu32 = ans01.addDummyBet((float) 65.0, cli03, UtilDate.newDate(year,month,14));
			Apuesta apu33 = ans02.addDummyBet((float) 112.0, cli04, UtilDate.newDate(year,month,15));
			Apuesta apu34 = ans03.addDummyBet((float) 100.0, cli01, UtilDate.newDate(year,month,16));
			Apuesta apu35 = ans04.addDummyBet((float) 10.0, cli02, UtilDate.newDate(year,month,16));
			Apuesta apu36 = ans05.addDummyBet((float) 30.0, cli03, UtilDate.newDate(year,month,16));
			Apuesta apu37 = ans05.addDummyBet((float) 15.0, cli04, UtilDate.newDate(year,month,17));
			Apuesta apu38 = ans04.addDummyBet((float) 188.0, cli01, UtilDate.newDate(year,month,17));
			Apuesta apu39 = ans08.addDummyBet((float) 22.0, cli02, UtilDate.newDate(year,month,18));
			Apuesta apu40 = ans09.addDummyBet((float) 99.0, cli03, UtilDate.newDate(year,month,19));
			Apuesta apu41 = ans01.addDummyBet((float) 63.0, cli04, UtilDate.newDate(year,month,19));
			Apuesta apu42 = ans01.addDummyBet((float) 12.0, cli01, UtilDate.newDate(year,month,19));
			Apuesta apu43 = ans02.addDummyBet((float) 447.0, cli02, UtilDate.newDate(year,month,19));
			Apuesta apu44 = ans15.addDummyBet((float) 500.0, cli03, UtilDate.newDate(year,month,19));
			Apuesta apu45 = ans18.addDummyBet((float) 470.0, cli04, UtilDate.newDate(year,month,20));
			Apuesta apu46 = ans16.addDummyBet((float) 600.0, cli01, UtilDate.newDate(year,month,20));
			Apuesta apu47 = ans01.addDummyBet((float) 55.0, cli02, UtilDate.newDate(year,month,21));
			Apuesta apu48 = ans02.addDummyBet((float) 123.0, cli03, UtilDate.newDate(year,month,22));
			Apuesta apu49 = ans03.addDummyBet((float) 234.0, cli04, UtilDate.newDate(year,month,22));
			Apuesta apu50 = ans04.addDummyBet((float) 345.0, cli01, UtilDate.newDate(year,month,22));
			Apuesta apu51 = ans05.addDummyBet((float) 456.0, cli02, UtilDate.newDate(year,month,23));
			Apuesta apu52 = ans06.addDummyBet((float) 567.0, cli03, UtilDate.newDate(year,month,23));
			Apuesta apu53 = ans07.addDummyBet((float) 678.0, cli04, UtilDate.newDate(year,month,23));
			Apuesta apu54 = ans08.addDummyBet((float) 789.0, cli01, UtilDate.newDate(year,month,24));
			Apuesta apu55 = ans10.addDummyBet((float) 890.0, cli02, UtilDate.newDate(year,month,25));
			Apuesta apu56 = ans09.addDummyBet((float) 901.0, cli02, UtilDate.newDate(year,month,25));
			Apuesta apu57 = ans11.addDummyBet((float) 120.0, cli03, UtilDate.newDate(year,month,25));
			Apuesta apu58 = ans12.addDummyBet((float) 652.0, cli03, UtilDate.newDate(year,month,25));
			Apuesta apu59 = ans13.addDummyBet((float) 987.0, cli04, UtilDate.newDate(year,month,26));
			Apuesta apu60 = ans14.addDummyBet((float) 876.0, cli04, UtilDate.newDate(year,month,26));
			Apuesta apu61 = ans15.addDummyBet((float) 765.0, cli01, UtilDate.newDate(year,month,27));
			Apuesta apu62 = ans16.addDummyBet((float) 654.0, cli03, UtilDate.newDate(year,month,28));
			Apuesta apu63 = ans17.addDummyBet((float) 543.0, cli02, UtilDate.newDate(year,month,28));
			Apuesta apu64 = ans18.addDummyBet((float) 432.0, cli04, UtilDate.newDate(year,month,29));
			Apuesta apu65 = ans19.addDummyBet((float) 321.0, cli03, UtilDate.newDate(year,month,30));
			Apuesta apu66 = ans20.addDummyBet((float) 210.0, cli01, UtilDate.newDate(year,month,31));

			cli01.addBet(apu01);
			cli04.addBet(apu02);
			cli01.addBet(apu03);
			cli01.addBet(apu04);
			cli02.addBet(apu05);
			cli01.addBet(apu06);
			cli01.addBet(apu07);
			cli03.addBet(apu08);
			cli02.addBet(apu09);
			cli01.addBet(apu10);
			cli01.addBet(apu11);
			cli01.addBet(apu12);
			cli01.addBet(apu13);
			cli01.addBet(apu14);
			cli01.addBet(apu15);
			cli01.addBet(apu16);
			cli01.addBet(apu17);
			cli01.addBet(apu18);
			cli01.addBet(apu19);
			cli01.addBet(apu20);
			cli01.addBet(apu21);
			cli01.addBet(apu22);
			cli01.addBet(apu23);
			cli01.addBet(apu24);
			cli01.addBet(apu25);
			cli01.addBet(apu26);
			cli01.addBet(apu27);
			cli01.addBet(apu28);
			cli01.addBet(apu29);
			cli01.addBet(apu30);
			cli01.addBet(apu31);
			cli01.addBet(apu32);
			cli01.addBet(apu33);
			cli01.addBet(apu34);
			cli02.addBet(apu35);
			cli02.addBet(apu36);
			cli02.addBet(apu37);
			cli02.addBet(apu38);
			cli02.addBet(apu39);
			cli02.addBet(apu40);
			cli02.addBet(apu41);
			cli02.addBet(apu42);
			cli02.addBet(apu43);
			cli02.addBet(apu44);
			cli02.addBet(apu45);
			cli02.addBet(apu46);
			cli02.addBet(apu47);
			cli02.addBet(apu48);
			cli02.addBet(apu49);
			cli02.addBet(apu50);
			cli03.addBet(apu51);
			cli03.addBet(apu52);
			cli03.addBet(apu53);
			cli03.addBet(apu54);
			cli03.addBet(apu55);
			cli03.addBet(apu56);
			cli03.addBet(apu57);
			cli03.addBet(apu58);
			cli03.addBet(apu59);
			cli04.addBet(apu60);
			cli04.addBet(apu61);
			cli04.addBet(apu62);
			cli04.addBet(apu63);
			cli04.addBet(apu64);
			cli04.addBet(apu65);
			cli04.addBet(apu66);


			cg.addBet(apu01);
			cg.addBet(apu02);
			cg.addBet(apu03);
			cg.addBet(apu04);
			cg.addBet(apu05);
			cg.addBet(apu06);
			cg.addBet(apu07);
			cg.addBet(apu08);
			cg.addBet(apu09);
			cg.addBet(apu10);
			cg.addBet(apu11);
			cg.addBet(apu12);
			cg.addBet(apu13);
			cg.addBet(apu14);
			cg.addBet(apu15);
			cg.addBet(apu16);
			cg.addBet(apu17);
			cg.addBet(apu18);
			cg.addBet(apu19);
			cg.addBet(apu20);
			cg.addBet(apu21);
			cg.addBet(apu22);
			cg.addBet(apu23);
			cg.addBet(apu24);
			cg.addBet(apu25);
			cg.addBet(apu26);
			cg.addBet(apu27);
			cg.addBet(apu28);
			cg.addBet(apu29);
			cg.addBet(apu30);
			cg.addBet(apu31);
			cg.addBet(apu32);
			cg.addBet(apu33);
			cg.addBet(apu34);
			cg.addBet(apu35);
			cg.addBet(apu36);
			cg.addBet(apu37);
			cg.addBet(apu38);
			cg.addBet(apu39);
			cg.addBet(apu40);
			cg.addBet(apu41);
			cg.addBet(apu42);
			cg.addBet(apu43);
			cg.addBet(apu44);
			cg.addBet(apu45);
			cg.addBet(apu46);
			cg.addBet(apu47);
			cg.addBet(apu48);
			cg.addBet(apu49);
			cg.addBet(apu50);
			cg.addBet(apu51);
			cg.addBet(apu52);
			cg.addBet(apu53);
			cg.addBet(apu54);
			cg.addBet(apu55);
			cg.addBet(apu56);
			cg.addBet(apu57);
			cg.addBet(apu58);
			cg.addBet(apu59);
			cg.addBet(apu60);
			cg.addBet(apu61);
			cg.addBet(apu62);
			cg.addBet(apu63);
			cg.addBet(apu64);
			cg.addBet(apu65);
			cg.addBet(apu66);

			db.persist(apu01);
			db.persist(apu02);
			db.persist(apu03);
			db.persist(apu04);
			db.persist(apu05);
			db.persist(apu06);
			db.persist(apu07);
			db.persist(apu08);
			db.persist(apu09);
			db.persist(apu10);
			db.persist(apu11);
			db.persist(apu12);
			db.persist(apu13);
			db.persist(apu14);
			db.persist(apu15);
			db.persist(apu16);
			db.persist(apu17);
			db.persist(apu18);
			db.persist(apu19);
			db.persist(apu20);
			db.persist(apu21);
			db.persist(apu22);
			db.persist(apu23);
			db.persist(apu24);
			db.persist(apu25);
			db.persist(apu26);
			db.persist(apu27);
			db.persist(apu28);
			db.persist(apu29);
			db.persist(apu30);
			db.persist(apu31);
			db.persist(apu32);
			db.persist(apu33);
			db.persist(apu34);
			db.persist(apu35);
			db.persist(apu36);
			db.persist(apu37);
			db.persist(apu38);
			db.persist(apu39);
			db.persist(apu40);
			db.persist(apu41);
			db.persist(apu42);
			db.persist(apu43);
			db.persist(apu44);
			db.persist(apu45);
			db.persist(apu46);
			db.persist(apu47);
			db.persist(apu48);
			db.persist(apu49);
			db.persist(apu50);
			db.persist(apu51);
			db.persist(apu52);
			db.persist(apu53);
			db.persist(apu54);
			db.persist(apu55);
			db.persist(apu56);
			db.persist(apu57);
			db.persist(apu58);
			db.persist(apu59);
			db.persist(apu60);
			db.persist(apu61);
			db.persist(apu62);
			db.persist(apu63);
			db.persist(apu64);
			db.persist(apu65);
			db.persist(apu66);
			
			//LA CUENTA GLOBAL DEL SISTEMA SE PRECARGA CON LA SUMA DE LAS APUESTAS REALIZADAS
			Float sumaTotal = 	 apu01.getAmount()+apu02.getAmount()+apu03.getAmount()+apu04.getAmount()+apu05.getAmount()+apu06.getAmount()+apu07.getAmount()+apu08.getAmount()+apu09.getAmount()+apu10.getAmount()
								+apu11.getAmount()+apu12.getAmount()+apu13.getAmount()+apu14.getAmount()+apu15.getAmount()+apu16.getAmount()+apu17.getAmount()+apu18.getAmount()+apu19.getAmount()+apu20.getAmount()
								+apu21.getAmount()+apu22.getAmount()+apu23.getAmount()+apu24.getAmount()+apu25.getAmount()+apu26.getAmount()+apu27.getAmount()+apu28.getAmount()+apu29.getAmount()+apu30.getAmount()
								+apu31.getAmount()+apu32.getAmount()+apu33.getAmount()+apu34.getAmount()+apu35.getAmount()+apu36.getAmount()+apu37.getAmount()+apu38.getAmount()+apu39.getAmount()+apu40.getAmount()
								+apu41.getAmount()+apu42.getAmount()+apu43.getAmount()+apu44.getAmount()+apu45.getAmount()+apu46.getAmount()+apu47.getAmount()+apu48.getAmount()+apu49.getAmount()+apu50.getAmount()
								+apu51.getAmount()+apu52.getAmount()+apu53.getAmount()+apu54.getAmount()+apu55.getAmount()+apu56.getAmount()+apu57.getAmount()+apu58.getAmount()+apu59.getAmount()+apu60.getAmount()
								+apu61.getAmount()+apu62.getAmount()+apu63.getAmount()+apu64.getAmount()+apu65.getAmount()+apu66.getAmount();
			
			cg.setSaldoGlobal(sumaTotal);
			
			
			db.getTransaction().commit();
			System.out.println("Db initialized");
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}
	
	

	
	public void close(){
		db.close();
		System.out.println("DataBase closed");
	}

	
	

	
	public Question createQuestion(Event event, String question, float betMinimum) throws  QuestionAlreadyExist {
		System.out.println(">> DataAccess: createQuestion=> event= "+event+" question= "+question+" betMinimum="+betMinimum);
		
			Event ev = db.find(Event.class, event.getEventNumber());
			
			if (ev.DoesQuestionExists(question)) throw new QuestionAlreadyExist(ResourceBundle.getBundle("Etiquetas").getString("ErrorQueryAlreadyExist"));
			
			db.getTransaction().begin();
			Question q = ev.addQuestion(question, betMinimum);
			db.persist(q);
			db.persist(ev); // db.persist(q) not required when CascadeType.PERSIST is added in questions property of Event class
							// @OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
			db.getTransaction().commit();
			return q;
		
	}
	
	
	
	
	
	
	public boolean insertAnswer(String pAnswer, float pCoeficienteFloat, Question q) {
		
		System.out.println(">> DataAccess: insertAnswer=> question= "+q+" answer="+pAnswer);
		
		try {
			Question qdb = db.find(Question.class, q);
			
			db.getTransaction().begin();
			qdb.addAnswer(pAnswer, pCoeficienteFloat);
			//db.persist(q);
			db.persist(qdb); // db.persist(q) not required when CascadeType.PERSIST is added in questions property of Event class
							// @OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
			db.getTransaction().commit();
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	
	
	
	
	
	public boolean insertEvent(Event pev) {
		System.out.println(">> DataAccess: insertEvent=> event= "+pev.getDescription()+" date="+pev.getEventDate());
		
		try {
			db.getTransaction().begin();
			db.persist(pev);
			TypedQuery<Deporte> query = db.createQuery("SELECT sp FROM Deporte sp WHERE sp.nombre=?1",Deporte.class);
			query.setParameter(1, pev.getTipoDeporte().getNombre());
			List<Deporte> deportes = query.getResultList();
			Deporte deporte=null;
		 	for (Deporte sp:deportes){		 
		 		deporte = sp;
			}
			deporte.addEvent(pev);
			db.persist(deporte);
			db.getTransaction().commit();
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	

	
	
	
	
	
	public Vector<Event> getEvents(Date date) {
		System.out.println(">> DataAccess: getEvents");
		Vector<Event> res = new Vector<Event>();	
		TypedQuery<Event> query = db.createQuery("SELECT ev FROM Event ev WHERE ev.eventDate=?1",Event.class);   
		query.setParameter(1, date);
		List<Event> events = query.getResultList();
	 	 for (Event ev:events){
	 	   System.out.println(ev.toString());		 
		   res.add(ev);
		  }
	 	return res;
	}
	
	
	
	
	//DEVUELVE EVENTOS DE HOY EN ADELANTE
	@SuppressWarnings("deprecation")
	public Vector<Event> getNextEvents() {
		
		Date today=Calendar.getInstance().getTime();
		
		System.out.println(">> DataAccess: getEvents");
		Vector<Event> res = new Vector<Event>();	
		TypedQuery<Event> query = db.createQuery("SELECT ev FROM Event ev",Event.class);
		List<Event> events = query.getResultList();
	 	 for (Event ev:events){
	 		 if (ev.getEventDate().getYear() >= today.getYear()) {
	 			 if (ev.getEventDate().getMonth() >= today.getMonth()) {
	 				 if (ev.getEventDate().getDate() >= today.getDate()) {
	 					System.out.println(ev.toString());		 
	 					res.add(ev);
	 				 }
	 			 }
	 		 }
		  }
	 	return res;
	}
	
	

	
	
	
	public Vector<Question> getQuestions(Event pevent) {
		System.out.println(">> DataAccess: getEvents");
		Vector<Question> res = new Vector<Question>();	
		TypedQuery<Question> query = db.createQuery("SELECT q FROM Question q WHERE q.event=?1",Question.class);   
		query.setParameter(1, pevent);
		List<Question> questions = query.getResultList();
	 	 for (Question q:questions){
	 	   System.out.println(q.toString());		 
		   res.add(q);
		  }
	 	return res;
	}
	

	
	
	
	
	
	public Vector<Date> getEventsMonth(Date date) {
		System.out.println(">> DataAccess: getEventsMonth");
		Vector<Date> res = new Vector<Date>();	
		
		Date firstDayMonthDate= UtilDate.firstDayMonth(date);
		Date lastDayMonthDate= UtilDate.lastDayMonth(date);
				
		
		TypedQuery<Date> query = db.createQuery("SELECT DISTINCT ev.eventDate FROM Event ev WHERE ev.eventDate BETWEEN ?1 and ?2",Date.class);   
		query.setParameter(1, firstDayMonthDate);
		query.setParameter(2, lastDayMonthDate);
		List<Date> dates = query.getResultList();
	 	 for (Date d:dates){
	 	   System.out.println(d.toString());		 
		   res.add(d);
		  }
	 	return res;
	}
	
	
	
	
	

	public boolean doLogin (String pusername, String ppassword) {

		TypedQuery<Usuario> query = db.createQuery("SELECT us FROM Usuario us WHERE us.username=?1 and us.password=?2", Usuario.class);
		query.setParameter(1, pusername);
		query.setParameter(2, ppassword);
		List<Usuario> usuarios = query.getResultList();
		
		if (usuarios.isEmpty()) {
			return false;
		} else {
			return true;
		}
	}
	
	
	
	
	
	public boolean doRegister(String pusername, String ppassword, String pemail) throws RollbackException {
		
		//Verifico que el usuario no exista
		TypedQuery<Usuario> query = db.createQuery("SELECT us FROM Usuario us WHERE us.username=?1 and us.email=?2", Usuario.class);
		query.setParameter(1, pusername);
		query.setParameter(2, pemail);
		List<Usuario> usuarios = query.getResultList();
		if (usuarios.isEmpty()) {
			db.getTransaction().begin();
			Cliente c = new Cliente (pusername, ppassword, pemail);
			db.persist(c);
			db.getTransaction().commit();
			System.out.println("Usuario registrado ");
			return true;
		} else {
			return false;
		}
	}
	
	
	
	
	
	public boolean isAdmin(String pusername, String ppassword) {
		TypedQuery<Usuario> query = db.createQuery("SELECT us FROM Usuario us WHERE us.username=?1 and us.password=?2", Usuario.class);
		query.setParameter(1, pusername);
		query.setParameter(2, ppassword);
		List<Usuario> usuarios = query.getResultList();

		for (Usuario u:usuarios){
			return (u instanceof Admin);
		}
		
		
		if (usuarios instanceof Admin) {
			return true;
		} else {
			return false;
		}
	}
	
	
	
	
	
	public List<Event> getAllEvents(){
		//TODO Conseguir todos los eventos
		
		TypedQuery<Event> query = db.createQuery("SELECT e FROM Event e ORDER BY e.eventNumber", Event.class);
		
		List<Event> eventos = query.getResultList();
		
		return eventos;
	}
	
	
	
	
	
	public List<Question> getQuestionsByEvent(Event pevent){
		//TODO Devuelve las preguntas que tengas como evento @event
		
		TypedQuery<Event> query = db.createQuery("SELECT ev FROM Event ev", Event.class);
		List<Event> evList = query.getResultList();
		
		if(evList.isEmpty()){
			System.out.println("No events for that date");
			return null;	
		} else {
	       ArrayList<Question> result = new ArrayList<Question>();
	        for (Event ev : evList) 
	            if (ev.getEventNumber().equals(pevent.getEventNumber()))
	                for (Question q : ev.getQuestions()) 
	                        result.add(q);
	        			return result;
	        
		}
		
	}
	
	
	
	
	
	public List<Question> getOpenQuestionsByEvent(Event pevent){
		
		TypedQuery<Event> query = db.createQuery("SELECT ev FROM Event ev WHERE ev.result=?1", Event.class);
		query.setParameter(1, false);
		List<Event> evList = query.getResultList();
		
		if(evList.isEmpty()){
			System.out.println("No events for that date");
			return null;	
		} else {
			ArrayList<Question> result = new ArrayList<Question>();
	       	for (Event ev : evList) {
	    	   	if (ev.getEventNumber().equals(pevent.getEventNumber())) {
	    	   		for (Question q : ev.getQuestions()) {
	    	   			if(!q.getResult()) {
	    	   				result.add(q);
	    	   			}
	    	   		}
	    	   	}
	       	}
	        return result;
		}
		
	}
	
	
	
	
	
	public List<Answer> getAnswersByQuestion(Question pselectedQuestion) {
		TypedQuery<Question> query = db.createQuery("SELECT q FROM Question q", Question.class);
		List<Question> qList = query.getResultList();
		
		if(qList.isEmpty()){
			System.out.println("No questions for that event");
			return null;	
		} else {
		       ArrayList<Answer> result = new ArrayList<Answer>();
		        for (Question q : qList) 
		            if (q.getQuestionNumber().equals(pselectedQuestion.getQuestionNumber()))
		                for (Answer ans : q.getAnswers()) {
		                	ans.toString();
		                        result.add(ans);
		                }		                        
		        			return result;
		        
			}
		
	}	
	
	
	
	
	
	
	public List<Answer> getOpenAnswersByQuestion(Question pselectedQuestion) {
		TypedQuery<Question> query = db.createQuery("SELECT q FROM Question q WHERE q.questionNumber=?1", Question.class);
		query.setParameter(1, pselectedQuestion.getQuestionNumber());
		List<Question> qList = query.getResultList();
		
		if(qList.isEmpty()){
			System.out.println("No questions for that event");
			return null;	
		} else {
		       ArrayList<Answer> result = new ArrayList<Answer>();
		        for (Question q : qList) 
	                for (Answer ans : q.getAnswers()) {
	                	if(ans.getActiva()==true) {
		                	ans.toString();
	                        result.add(ans);
	                	}
	                }		                        
        			return result;
		        
			}
		
	}
	
	
	
	
	
	
	public int createApuesta(Answer pselectedAnswer, Cliente pselectedClient, Float pselectedAmount) {
			//VALIDACIÓN DE NÚMERO POSITIVO
			if (pselectedAmount < 0){
				
				// 4 - NÚMERO NEGATIVO
				return 4;
			} else {
				
				//VALIDACIÓN DE MONTO MAYOR AL MÍNIMO
				//if (pselectedAmount < pselectedQuestion.getBetMinimum()) {
				if (pselectedAmount < pselectedAnswer.getQuestion().getBetMinimum()) {
					
					// 3 - NO ALCANZA APUESTA MÍNIMA
					return 3;
					
				} else {

					Cliente clientdb = db.find(Cliente.class, pselectedClient.getUsername());
					
					//VALIDACIÓN DE SALDO EN CUENTA
					if (pselectedAmount >= clientdb.getSaldo()) {
						// 2 - FALTA DE SALDO
						return 2;
						
					} else {

						System.out.println(">> DataAccess: createApuesta=> answer= "+pselectedAnswer+" client= "+clientdb.getUsername()+" amount="+pselectedAmount+"€");
						
						try {
							db.getTransaction().begin();
							CuentaGlobal cuentaGlobal = db.find(CuentaGlobal.class, 1);
							Apuesta ap = pselectedAnswer.addBet(pselectedAmount, clientdb);
							cuentaGlobal.addBet(ap);
							db.persist(ap);
							
								cuentaGlobal.setSaldoGlobal(cuentaGlobal.getSaldoGlobal()+pselectedAmount);
								clientdb.setSaldo(clientdb.getSaldo()-pselectedAmount);
								//JOptionPane.showMessageDialog(null, cl.getSaldo());
								db.persist(clientdb);
								db.getTransaction().commit();
							
							// 0 - APUESTA CREADA
							return 0;
							
						} catch (Exception ex) {
							
							// 1 - ERROR DE INGRESO DE APUESTA
							return 1;
						}
						
					}
					
				}
			}
		
	}
	
	
	
	

	public Cliente getClientByUsername(String pusername) {
		System.out.println(">> DataAccess: getClientByUsername");
		
		TypedQuery<Cliente> query = db.createQuery("SELECT cli FROM Cliente cli", Cliente.class);
		List<Cliente> cliList = query.getResultList();
		
		
	       	//ArrayList<Cliente> result = new ArrayList<Cliente>();
	        for (Cliente c : cliList) {
	            if (c.getUsername().equals(pusername)) 
	    	        return c;
	        }
	        return null;
		    
	}
	
	
	
	
	public Admin getAdminByUsername(String pusername) {
		System.out.println(">> DataAccess: getAdminByUsername");
		
		TypedQuery<Admin> query = db.createQuery("SELECT adm FROM Admin adm", Admin.class);
		List<Admin> admList = query.getResultList();
		
		
	       	//ArrayList<Admin> result = new ArrayList<Admin>();
	        for (Admin a : admList) {
	            if (a.getUsername().equals(pusername)) 
	    	        return a;
	        }
	        return null;
		    
	}
	
	
	
	
	
	
	public ArrayList<Apuesta> BetsByClient(Cliente a){ //Questions by event
		TypedQuery<Apuesta> query = db.createQuery("SELECT q FROM Apuesta q", Apuesta.class);
		List<Apuesta> ApuList = query.getResultList();
		ArrayList<Apuesta> BetList;
		if (ApuList.isEmpty()) {
			System.out.println("No bets for that client");
			return null;
		} else {

			//JOptionPane.showMessageDialog(null, "ApuList1; "+ApuList.size());
			BetList= new ArrayList<Apuesta>();
			for (Apuesta q : ApuList) {
				 if (q.getCliente().getUsername().equals(a.getUsername())) {
					 BetList.add(q);
					 //JOptionPane.showMessageDialog(null, "ApuList; "+q.getCliente().getUsername());
				 }
			}

			return BetList;
		}
	}
	
	
	
	

	public boolean insertSomething(Object something) {
		try {
			db.getTransaction().begin();
			db.persist(something);
			db.getTransaction().commit();
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}

	
	
	
	
	public Vector<Event> getOpenEvents(Date date) {
		System.out.println(">> DataAccess: getOpenEvents");
		Vector<Event> res = new Vector<Event>();
		TypedQuery<Event> query = db.createQuery("SELECT ev FROM Event ev WHERE ev.eventDate=?1 AND ev.result=?2",Event.class); 
		query.setParameter(1, date);
		query.setParameter(2, false);
		List<Event> events = query.getResultList();
	 	 for (Event ev:events){
	 	   System.out.println(ev.toString());
		   res.add(ev);
		  }
	 	return res;
	}

	
	
	
	
	public CuentaGlobal getCuentaGlobal() {
		CuentaGlobal cg = db.find(CuentaGlobal.class, 1);
		return cg;
	}
	
	
	
	

	public boolean createAdmin(String pNombre, String pUsername, String pEmail, String pPassword) {
	
		System.out.println(">> DataAccess: createAdmin=> username= "+pUsername);
		
		try {
			Admin adm = new Admin(pNombre, pUsername, pEmail, pPassword);
			db.getTransaction().begin();
			db.persist(adm);
			db.getTransaction().commit();
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	

	
	
	public boolean definirResultados(Event pselectedEvent, Question pselectedQuestion, Answer pselectedAnswer, Admin pcurrentAdmin) {

		db.getTransaction().begin();
		Answer ganadora=db.find(Answer.class, pselectedAnswer);
		ganadora.setRespuestaGanadora(true); //1.
		db.getTransaction().commit();

		//TODAS LAS RESPUESTAS POSIBLES DE ESA PREGUNTA
		TypedQuery<Answer> query0 = db.createQuery("SELECT ans FROM Answer ans WHERE ans.question=?1", Answer.class);
		query0.setParameter(1, pselectedQuestion);
		db.getTransaction().begin();
		ArrayList<Answer> ArrayListRespuestas = new ArrayList<Answer>(query0.getResultList());		
		db.getTransaction().commit();
		
		CuentaGlobal cuentaGlobal = db.find(CuentaGlobal.class, 1);
				
		try {

			db.getTransaction().begin();
			for(Answer ans : ArrayListRespuestas) {
				TypedQuery<Apuesta> query1 = db.createQuery("SELECT ap FROM Apuesta ap WHERE ap.answer=?1", Apuesta.class);
				query1.setParameter(1, ans);
				ArrayList<Apuesta> ArrayListApuestas = new ArrayList<Apuesta>(query1.getResultList());			
				if (ArrayListApuestas.isEmpty()) {
					System.out.println("No bets for this answer.");
				} else {
					for(Apuesta apu : ArrayListApuestas) {

						//FILTRA QUE NO ESTÉ ANULADA
						if (apu.getEstado()!=2) {
							
							if (apu.getAnswer().getAnswerNumber()==pselectedAnswer.getAnswerNumber()) {
								apu.setEstado(3);//SET ACIERTO
								
								//ACREDITA SALDO CLIENTE
								Cliente cliente = apu.getCliente();
								float saldoCliente = cliente.getSaldo();
								float total = saldoCliente + apu.getAmount()*pselectedAnswer.getCoeficienteGanancia();

								System.out.println("\nAcredita al cliente "+cliente.getUsername()+" un total de "+apu.getAmount()*pselectedAnswer.getCoeficienteGanancia()+"€ ("+apu.getAmount()+"€ x "+pselectedAnswer.getCoeficienteGanancia()+")");
								
								cuentaGlobal.setSaldoGlobal(cuentaGlobal.getSaldoGlobal()-apu.getAmount()*pselectedAnswer.getCoeficienteGanancia());
								cliente.setSaldo(total);
								
							} else {
								
								apu.setEstado(1);//SET FALLO
							}
							
						}//filtro anulada
					} //para cada apuesta
				} //existen apuestas
			} //para cada respueta de la pregunta
		
			//CIERRO LA PREGUNTA
			Question q = db.find(Question.class, pselectedQuestion);
			
			q.setResult(true);
			System.out.println("\n// Apuestas sobre pregunta '"+q.getQuestion()+"' resueltas.\nPregunta cerrada. //");
			db.getTransaction().commit();
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "DataAccess >> Asignar Resultados y Pagos >> Catch: "+e.getMessage()); //FIX-ME! Comentar la línea
			return false;
		}
		
		//CIERRE DE EVENTO
		try {
			
			//SI NO HAY PREGUNTAS ABIERTAS, CIERRA EVENTO
			Event ev = db.find(Event.class, pselectedEvent);

			db.getTransaction().begin();
			TypedQuery<Question> query = db.createQuery("SELECT q FROM Question q WHERE q.event=?1 AND q.result=?2", Question.class);
			query.setParameter(1, ev);
			query.setParameter(2, false);
			
			ArrayList<Question> ArrayListQuestions = new ArrayList<Question>(query.getResultList());
			
			//  SOLO CIERRA EL EVENTO SI NO TIENE
			//  PREGUNTAS EN ESTADO PENDIENTE
			//JOptionPane.showMessageDialog(null, "ArrayListQuestions: "+ArrayListQuestions.isEmpty()); //FIX-ME! Comentar la línea
			if (ArrayListQuestions.isEmpty()) {
				ev.setResult(true);
				db.getTransaction().commit();
			}
			return true;
		
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "DataAccess >> Cerrar evento >> Catch");
			return false;
		}
	}

	
	
	
	public boolean acreditarSaldo(Cliente pClient, float pMonto, int pPin, String pTipo) {
		
		if (pTipo=="t") {
			//SIMULA UNA TRANSACCIÓN POR BANCO
			if(pPin ==123) {
				db.getTransaction().begin();
				Cliente cli = db.find(Cliente.class, pClient);
				cli.setSaldo(cli.getSaldo()+pMonto);
				db.getTransaction().commit();
				return true;
			} else {
				return false;
			}
		} else {
			//SIMULA UNA TRANSACCIÓN CON TARJETA DE CRÉDITO Y ENVÍA NÚMERO DE TARJETA Y PIN
			db.getTransaction().begin();
			Cliente cli = db.find(Cliente.class, pClient);
			cli.setSaldo(cli.getSaldo()+pMonto);
			db.getTransaction().commit();
			return true;
		}
	}

	
	
	
	
	public boolean confirmarEditarPerfilCliente(String pNombre, String pUsername, String pEmail, String pTarjeta, String pCC) {
		try {
			db.getTransaction().begin();
			Cliente cliente=db.find(Cliente.class, pUsername);
			cliente.setName(pNombre);
			cliente.setEmail(pEmail);
			cliente.setTarjeta(pTarjeta);
			cliente.setCC(pCC);
			db.getTransaction().commit();
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	
	
	
	
	public boolean anularApuesta(Apuesta pApuesta) {
		try {
			db.getTransaction().begin();
			Apuesta apuesta=db.find(Apuesta.class, pApuesta);
			Cliente cliente=db.find(Cliente.class, pApuesta.getCliente());
			CuentaGlobal cg=db.find(CuentaGlobal.class, 1);
			apuesta.setEstado(4);
			cliente.setSaldo(cliente.getSaldo()+pApuesta.getAmount());
			cg.setSaldoGlobal(cg.getSaldoGlobal()-pApuesta.getAmount());
			
			db.getTransaction().commit();
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	public boolean updateQuestion(Question pselectedQuestion, String r, float f) {
		try {
			db.getTransaction().begin();
			Question qselected = db.find(Question.class, pselectedQuestion);
			qselected.setQuestion(r);
			qselected.setBetMinimum(f);
			db.getTransaction().commit();
			return true;
		} catch (Exception ex) {
			return false;
		}
	}
	
	public boolean updateEvent(Event selectedEvent, String texto, Deporte selectedDeporte) {
		try {
			db.getTransaction().begin();
			 			
			Event Evselected = db.find(Event.class, selectedEvent);
			
			//primero eliminamos el evento del array del deporte
			Deporte deporteAnterior=db.find(Deporte.class, selectedEvent.getTipoDeporte());
		 	deporteAnterior.removeEvent(selectedEvent);
			
			//editamos el evento con los nuevos valores
			Evselected.setDescription(texto);
			Evselected.setTipoDeporte(selectedDeporte);
			
			//luego agregamos el evento al deporte nuevo
			Deporte deporteNuevo=db.find(Deporte.class, selectedDeporte);
		 	deporteNuevo.addEvent(Evselected);
			
			db.getTransaction().commit();
			return true;
		} catch (Exception ex) {
			return false;
		}
	}
	
	
	public boolean updateAnswer(Answer pselectedAnswer, String prespuestaModificada, Float pcoeficienteGanancia) {
		try {
			db.getTransaction().begin();
			Answer ansselected = db.find(Answer.class, pselectedAnswer);
			ansselected.setAnswerText(prespuestaModificada);
			ansselected.setCoeficienteGanancia(pcoeficienteGanancia);
			db.getTransaction().commit();
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	
	public boolean deleteAnswer(Answer pselectedAnswer) {
		try {
			db.getTransaction().begin();
			Answer ansselected = db.find(Answer.class, pselectedAnswer);
			TypedQuery<Apuesta> query1 = db.createQuery("SELECT ap FROM Apuesta ap WHERE ap.answer=?1 AND ap.estado=?2", Apuesta.class);
			query1.setParameter(1, ansselected);
			query1.setParameter(2, 0); //Apuestas en estado 0 - pendientes
			ArrayList<Apuesta> ArrayListApuestas = new ArrayList<Apuesta>(query1.getResultList());
			if (ArrayListApuestas.isEmpty()) {
				System.out.println("\nSin apuestas en esta respuesta.");
			} else {
				for(Apuesta apu : ArrayListApuestas) {
					Apuesta apuesta=db.find(Apuesta.class, apu);
					Cliente cliente=db.find(Cliente.class, apu.getCliente());
					CuentaGlobal cg=db.find(CuentaGlobal.class, 1);
					apuesta.setEstado(2);
					cliente.setSaldo(cliente.getSaldo()+apu.getAmount());
					cg.setSaldoGlobal(cg.getSaldoGlobal()-apu.getAmount());

					System.out.println("\nApuesta id "+apuesta.getIdBet()+" anulada.");
				}
			}

			ansselected.setActiva(false);
			db.getTransaction().commit();
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	public boolean deleteQuestion(Question pselectedQuestion) {
		try {
			db.getTransaction().begin();
			Question questionSelected = db.find(Question.class, pselectedQuestion);
			
			TypedQuery<Answer> queryAnswer = db.createQuery("SELECT ans FROM Answer ans WHERE ans.question=?1 AND ans.activa=?2", Answer.class);
			queryAnswer.setParameter(1, questionSelected);
			queryAnswer.setParameter(2, true);
			ArrayList<Answer> ArrayListAnswers = new ArrayList<Answer>(queryAnswer.getResultList());
			/* ACÁ TENEMOS TODAS LAS RESPUESTAS DE ESTA PREGUNTA*/
			if (ArrayListAnswers.isEmpty()) {
				System.out.println("\nSin respuestas en esta pregunta.");
			} else {
				for(Answer ans : ArrayListAnswers) {
					TypedQuery<Apuesta> queryApuesta = db.createQuery("SELECT ap FROM Apuesta ap WHERE ap.answer=?1 AND ap.estado=?2", Apuesta.class);
					queryApuesta.setParameter(1, ans);
					queryApuesta.setParameter(2, 0); //Apuestas en estado 0 - pendientes
					ArrayList<Apuesta> ArrayListApuestas = new ArrayList<Apuesta>(queryApuesta.getResultList());
					if (ArrayListApuestas.isEmpty()) {
						System.out.println("\nSin apuestas en esta respuesta.");
					} else {
						for(Apuesta apu : ArrayListApuestas) {
							Apuesta apuesta=db.find(Apuesta.class, apu);
							Cliente cliente=db.find(Cliente.class, apu.getCliente());
							CuentaGlobal cg=db.find(CuentaGlobal.class, 1);
							apuesta.setEstado(2);
							cliente.setSaldo(cliente.getSaldo()+apu.getAmount());
							cg.setSaldoGlobal(cg.getSaldoGlobal()-apu.getAmount());

							System.out.println("\nApuesta id "+apuesta.getIdBet()+" anulada.");
						}
					}
					ans.setActiva(false);
				}
			}
			
			questionSelected.setResult(true);
			db.getTransaction().commit();
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	public boolean updateQuestion(Event pselectedEvent, String ptext) {
		try {
			db.getTransaction().begin();
			Event eventselected = db.find(Event.class, pselectedEvent);
			eventselected.setDescription(ptext);
			db.getTransaction().commit();
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	public boolean deleteEvent(Event pselectedEvent) {
		try {
			
			db.getTransaction().begin();
			Event eventSelected = db.find(Event.class, pselectedEvent);
			TypedQuery<Question> queryQuestion = db.createQuery("SELECT q FROM Question q WHERE q.event=?1 AND q.result=?2", Question.class);
			queryQuestion.setParameter(1, eventSelected);
			queryQuestion.setParameter(2, false);
			ArrayList<Question> ArrayListQuestions = new ArrayList<Question>(queryQuestion.getResultList());
			if (ArrayListQuestions.isEmpty()) {
				System.out.println("\nSin preguntas en este evento.");
			} else {
				for(Question questionSelected : ArrayListQuestions) {
				
				TypedQuery<Answer> queryAnswer = db.createQuery("SELECT ans FROM Answer ans WHERE ans.question=?1 AND ans.activa=?2", Answer.class);
				queryAnswer.setParameter(1, questionSelected);
				queryAnswer.setParameter(2, true);
				ArrayList<Answer> ArrayListAnswers = new ArrayList<Answer>(queryAnswer.getResultList());
				/* ACÁ TENEMOS TODAS LAS RESPUESTAS DE ESTA PREGUNTA*/
				if (ArrayListAnswers.isEmpty()) {
					System.out.println("\nSin respuestas en esta pregunta.");
				} else {
					for(Answer ans : ArrayListAnswers) {
						TypedQuery<Apuesta> queryApuesta = db.createQuery("SELECT ap FROM Apuesta ap WHERE ap.answer=?1 AND ap.estado=?2", Apuesta.class);
						queryApuesta.setParameter(1, ans);
						queryApuesta.setParameter(2, 0); //Apuestas en estado 0 - pendientes
						ArrayList<Apuesta> ArrayListApuestas = new ArrayList<Apuesta>(queryApuesta.getResultList());
						if (ArrayListApuestas.isEmpty()) {
							System.out.println("\nSin apuestas en esta respuesta.");
						} else {
							for(Apuesta apu : ArrayListApuestas) {
								Apuesta apuesta=db.find(Apuesta.class, apu);
								Cliente cliente=db.find(Cliente.class, apu.getCliente());
								CuentaGlobal cg=db.find(CuentaGlobal.class, 1);
								apuesta.setEstado(2);
								cliente.setSaldo(cliente.getSaldo()+apu.getAmount());
								cg.setSaldoGlobal(cg.getSaldoGlobal()-apu.getAmount());
	
								System.out.println("\nApuesta id "+apuesta.getIdBet()+" anulada.");
							}
						}
						ans.setActiva(false);
					}
				}
			
				questionSelected.setResult(true);
				}
			}
			
			Deporte selectedSport = db.find(Deporte.class, pselectedEvent.getTipoDeporte());
			selectedSport.removeEvent(pselectedEvent);
			
			eventSelected.setResult(true);
			db.getTransaction().commit();
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	public Vector<Deporte> getSports() {
		System.out.println(">> DataAccess: getSports");
		Vector<Deporte> res = new Vector<Deporte>();	
		TypedQuery<Deporte> query = db.createQuery("SELECT sp FROM Deporte sp",Deporte.class);
		List<Deporte> deportes = query.getResultList();
	 	 for (Deporte sp:deportes){
	 	   System.out.println(sp.toString());		 
		   res.add(sp);
		  }
	 	return res;
	}

	public Vector<Deporte> getActiveSports() {
		System.out.println(">> DataAccess: getSports");
		Vector<Deporte> res = new Vector<Deporte>();	
		TypedQuery<Deporte> query = db.createQuery("SELECT sp FROM Deporte sp WHERE sp.activo=?1",Deporte.class);
		query.setParameter(1, true);
		List<Deporte> deportes = query.getResultList();
	 	 for (Deporte sp:deportes){
	 	   System.out.println(sp.toString());		 
		   res.add(sp);
		  }
	 	return res;
	}

	public boolean updateSports(Deporte pDeporte, String pNombre) {
		try {
			db.getTransaction().begin();
			Deporte selectedSport = db.find(Deporte.class, pDeporte);
			selectedSport.setNombre(pNombre);
			db.getTransaction().commit();
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	public boolean deleteSport(Deporte pDeporte) {
		try {
			db.getTransaction().begin();
			Deporte selectedSport = db.find(Deporte.class, pDeporte);
			selectedSport.setActivo(false);
			db.getTransaction().commit();
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	public ArrayList<Apuesta> getApuestas() {
		System.out.println(">> DataAccess: getApuestas");
		TypedQuery<Apuesta> query = db.createQuery("SELECT a FROM Apuesta a",Apuesta.class);
		ArrayList<Apuesta> ArrayListApuestas = new ArrayList<Apuesta>(query.getResultList());
		return ArrayListApuestas;
	}

	public int addSport(String pNombre) {
		db.getTransaction().begin();
		TypedQuery<Deporte> query = db.createQuery("SELECT sp FROM Deporte sp WHERE sp.nombre=?1",Deporte.class);
		query.setParameter(1, pNombre);
		List<Deporte> deportes = query.getResultList();
		
		//VALIDO QUE EL DEPORTE EXISTA
	 	if (deportes.size()!=0) {
	 		for (Deporte sp: deportes){
	 			if (sp.getActivo()==true) {
	 				return 0; //Existe y está activo
	 			} else {
		 	 	   	sp.setActivo(true);
		 	 	   	return 1; //Existe y acaba de activarse
	 			}
	 		}
	 	} else {
	 		try {
				Deporte nuevoDeporte = new Deporte(pNombre);
				db.persist(nuevoDeporte);
				db.getTransaction().commit();
				return 3; //Deporte ingresado correctamente
	 		} catch (Exception ex) {
	 			return 2; //Error de ingreso
	 		}
	 	}
		return 5;
	}

	
}