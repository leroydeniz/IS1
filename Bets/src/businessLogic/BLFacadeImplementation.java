package businessLogic;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.jws.WebMethod;
import javax.jws.WebService;

import configuration.ConfigXML;
import dataAccess.DataAccess;
import domain.Question;
import domain.Admin;
import domain.Answer;
import domain.Apuesta;
import domain.Cliente;
import domain.CuentaGlobal;
import domain.Deporte;
import domain.Event;
import exceptions.EventFinished;
import exceptions.QuestionAlreadyExist;

/**
 * It implements the business logic as a web service.
 */
@WebService(endpointInterface = "businessLogic.BLFacade")
public class BLFacadeImplementation  implements BLFacade {

	public BLFacadeImplementation()  {		
		System.out.println("Creating BLFacadeImplementation instance");
		ConfigXML c=ConfigXML.getInstance();
		
		if (c.getDataBaseOpenMode().equals("initialize")) {
			DataAccess dbManager=new DataAccess(c.getDataBaseOpenMode().equals("initialize"));
			dbManager.initializeDB();
			dbManager.close();
			}
		
	}
	

	@WebMethod
	public Question createQuestion(Event event, String question, float betMinimum) throws EventFinished, QuestionAlreadyExist{
	   
	    //The minimum bed must be greater than 0
	    DataAccess dBManager=new DataAccess();
		Question qry=null;
		
	    
		if(new Date().compareTo(event.getEventDate())>0)
			throw new EventFinished(ResourceBundle.getBundle("Etiquetas").getString("ErrorEventHasFinished"));
				
		
		 qry=dBManager.createQuestion(event,question,betMinimum);		

		dBManager.close();
		
		return qry;
	};
	
	
   	@WebMethod	
	public Vector<Event> getEvents(Date date)  {
		DataAccess dbManager=new DataAccess();
		Vector<Event>  events=dbManager.getEvents(date);
		dbManager.close();
		return events;
	}

	
    //Devuelve todos los eventos 
	@WebMethod	
	public Vector<Event> getOpenEvents(Date date)  {
		DataAccess dbManager=new DataAccess();
		Vector<Event>  events=dbManager.getOpenEvents(date);
		dbManager.close();
		return events;
	}
	

	@Override
	public Vector<Event> getNextEvents() {
		DataAccess dbManager=new DataAccess();
		Vector<Event>  events=dbManager.getNextEvents();
		dbManager.close();
		Vector<Event> e = new Vector<Event>();
		while (!events.isEmpty()) {
			Event aux = events.get(0);
			int i = 0;
			for (int j = 1; j < events.size(); j++) {
				if (aux.getEventDate().compareTo(events.get(j).getEventDate()) > 0) {
					i = j;
					aux = events.get(j);
				}
			}
			e.add(events.remove(i));
		}
    	return e;
	}


	@WebMethod public Vector<Date> getEventsMonth(Date date) {
		DataAccess dbManager=new DataAccess();
		Vector<Date>  dates=dbManager.getEventsMonth(date);
		dbManager.close();
		return dates;
	}
	
	
    @WebMethod	
	 public void initializeBD(){
		DataAccess dBManager=new DataAccess();
		dBManager.initializeDB();
		dBManager.close();
	}
    
    
    public boolean doLogin (String pusername, String ppassword) {
    	DataAccess dBManager=new DataAccess();
		boolean existUser = dBManager.doLogin(pusername, ppassword);
		dBManager.close();
    	return existUser;
    }
    

	public boolean doRegister(String pusername, String ppassword, String pemail) {
		DataAccess dBManager=new DataAccess();
		boolean existUser = dBManager.doRegister(pusername, ppassword, pemail);
		dBManager.close();
		return existUser;
	}
	
	
	public boolean isAdmin(String pusername, String ppassword) {
		DataAccess dBManager=new DataAccess();
		boolean isAdmin = dBManager.isAdmin(pusername, ppassword);
		dBManager.close();
		return isAdmin;
	}



	@Override
	public List<Question> getQuestionsByEvent(Event pevent) {
		DataAccess dBManager=new DataAccess();
		List<Question> questions = dBManager.getQuestionsByEvent(pevent);
		dBManager.close();
		return questions;
	}
	
	
	@Override
	public List<Question> getOpenQuestionsByEvent(Event pevent) {
		DataAccess dBManager=new DataAccess();
		List<Question> questions = dBManager.getOpenQuestionsByEvent(pevent);
		dBManager.close();
		return questions;
	}


	@Override
	public List<Answer> getAnswersByQuestion(Question pselectedQuestion) {
		DataAccess dBManager=new DataAccess();
		List<Answer> answers = dBManager.getAnswersByQuestion(pselectedQuestion);
		dBManager.close();
		return answers;
	}
	

	@Override
	public List<Answer> getOpenAnswersByQuestion(Question pselectedQuestion) {
		DataAccess dBManager=new DataAccess();
		List<Answer> answers = dBManager.getOpenAnswersByQuestion(pselectedQuestion);
		dBManager.close();
		return answers;
	}
	

	@Override
	public int createApuesta(Answer selectedAnswer, Cliente pselectedClient, Float pselectedAmount) {
		DataAccess dBManager=new DataAccess();
		int apuestaCreada = dBManager.createApuesta(selectedAnswer, pselectedClient, pselectedAmount);
		dBManager.close();
		return apuestaCreada;
	}


	@Override
	public Cliente getClientByUsername(String pusername) {
		DataAccess dbManager= new DataAccess();
		Cliente CliDB = dbManager.getClientByUsername(pusername);
		dbManager.close();
		return CliDB;
	}

	
	@Override 
	public ArrayList <Apuesta> BetsByClient(Cliente a){
		DataAccess dBManager=new DataAccess();
		ArrayList<Apuesta> ApuList= dBManager.BetsByClient(a);
		return ApuList;
	}

	
	public Admin getAdminByUsername(String pusername) {
		DataAccess dbManager= new DataAccess();
		Admin AdmDB = dbManager.getAdminByUsername(pusername);
		dbManager.close();
		return AdmDB;
	}
	

	public List<Event> getAllEvents(){
		DataAccess dBManager = new DataAccess();
		List<Event> r = dBManager.getAllEvents();
		dBManager.close();
		return r;
	}
	
	
	public boolean insertEvent(Event ev) {
		DataAccess dBManager = new DataAccess();
		return dBManager.insertEvent(ev);
	}
	
	
	public boolean insertAnswer(String pAnswer, float pCoeficienteFloat, Question q) {
		DataAccess dBManager = new DataAccess();
		return dBManager.insertAnswer(pAnswer, pCoeficienteFloat, q);
	}

	
	@Override
	public Float getSaldoEnCuenta(Cliente pcurrentClient) {
		return pcurrentClient.getSaldo();
	}


	@Override
	public boolean definirResultados(Event pselectedEvent, Question pselectedQuestion, Answer pselectedAnswer,Admin pcurrentAdmin) {
		DataAccess dBManager = new DataAccess();
		return dBManager.definirResultados(pselectedEvent, pselectedQuestion, pselectedAnswer, pcurrentAdmin);
	}


	@Override
	public CuentaGlobal getCuentaGlobal() {
		DataAccess dBManager = new DataAccess();
		return dBManager.getCuentaGlobal();
	}


	@Override
	public boolean createAdmin(String pNombre, String pUsername, String pEmail, String pPassword) {
		DataAccess dBManager = new DataAccess();
		boolean registrado = dBManager.createAdmin(pNombre, pUsername, pEmail, pPassword);
		dBManager.close();
		return registrado;
	}
	
	
	@Override
	public boolean acreditarSaldo(Cliente pClient, float pMonto, int pPin, String pTipo) {
		DataAccess dBManager = new DataAccess();
		boolean acreditado = dBManager.acreditarSaldo(pClient, pMonto, pPin, pTipo);
		dBManager.close();
		return acreditado;
	}

	
	@Override
	public boolean confirmarEditarPerfilCliente(String pNombre, String pUsername, String pEmail, String pTarjeta, String pCC) {
		DataAccess dBManager = new DataAccess();
		boolean modificado = dBManager.confirmarEditarPerfilCliente(pNombre, pUsername, pEmail, pTarjeta, pCC);
		dBManager.close();
		return modificado;
	}
	
	
	@Override
	public boolean anularApuesta(Apuesta pApuesta) {
		DataAccess dBManager = new DataAccess();
		boolean anulada = dBManager.anularApuesta(pApuesta);
		dBManager.close();
		return anulada;
	}
	
	
	@Override
	public boolean updateQuestion(Question q, String r, float f) {
		DataAccess dBManager = new DataAccess();
		boolean actualizada = dBManager.updateQuestion(q, r, f);
		dBManager.close();
		return actualizada;
	}
	
	
	@Override
	public boolean updateAnswer(Answer selectedAnswer, String respuestaModificada, Float coeficienteFloat) {
		DataAccess dBManager = new DataAccess();
		boolean actualizada = dBManager.updateAnswer(selectedAnswer, respuestaModificada, coeficienteFloat);
		dBManager.close();
		return actualizada;
	}

	
	@Override
	public boolean deleteAnswer(Answer pselectedAnswer) {
		DataAccess dBManager = new DataAccess();
		boolean anulada = dBManager.deleteAnswer(pselectedAnswer);
		dBManager.close();
		return anulada;
	}


	@Override
	public boolean updateQuestion(Question pselectedQuestion, String ppreguntaModificada, Float papuestaMinimaFloat) {
		DataAccess dBManager = new DataAccess();
		boolean actualizada = dBManager.updateQuestion(pselectedQuestion, ppreguntaModificada, papuestaMinimaFloat);
		dBManager.close();
		return actualizada;
	}

	
	@Override
	public boolean deleteQuestion(Question pselectedQuestion) {
		DataAccess dBManager = new DataAccess();
		boolean anulada = dBManager.deleteQuestion(pselectedQuestion);
		dBManager.close();
		return anulada;
	}


	@Override
	public boolean updateEvent(Event pselectedEvent, String ptext, Deporte selectedDeporte) {
		DataAccess dBManager = new DataAccess();
		boolean actualizado = dBManager.updateEvent(pselectedEvent, ptext, selectedDeporte);
		dBManager.close();
		return actualizado;
	}


	@Override
	public boolean deleteEvent(Event pselectedEvent) {
		DataAccess dBManager = new DataAccess();
		boolean anulado = dBManager.deleteEvent(pselectedEvent);
		dBManager.close();
		return anulado;
	}


	@Override
	public Vector<Deporte> getSports() {
		DataAccess dbManager=new DataAccess();
		Vector<Deporte>  deportes=dbManager.getSports();
		dbManager.close();
		return deportes;
	}
	

	@Override
	public boolean updateSports(Deporte pDeporte, String pNombre) {
		DataAccess dBManager = new DataAccess();
		boolean actualizado = dBManager.updateSports(pDeporte, pNombre);
		dBManager.close();
		return actualizado;
	}


	@Override
	public Vector<Deporte> getActiveSports() {
		DataAccess dbManager=new DataAccess();
		Vector<Deporte>  deportes=dbManager.getActiveSports();
		dbManager.close();
		return deportes;
	}


	@Override
	public boolean deleteSport(Deporte deporte) {
		DataAccess dBManager = new DataAccess();
		boolean anulado = dBManager.deleteSport(deporte);
		dBManager.close();
		return anulado;
	}


	@Override
	public ArrayList<Apuesta> getApuestas() {
		DataAccess dBManager = new DataAccess();
		ArrayList<Apuesta> apuestas = dBManager.getApuestas();
		dBManager.close();
		return apuestas;
	}


	@Override
	public int addSport(String pNombre) {
		DataAccess dBManager = new DataAccess();
		int ingresado = dBManager.addSport(pNombre);
		dBManager.close();
		return ingresado;
	}


}

