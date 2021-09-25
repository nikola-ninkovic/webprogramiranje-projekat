package services;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bean.Administrator;
import bean.Artikal;
import bean.Dostavljac;
import bean.ImeTip;
import bean.Komentar;
import bean.Korisnik;
import bean.Korpa;
import bean.Kupac;
import bean.Menadzer;
import bean.Porudzbina;
import bean.Porudzbina.kom;
import bean.Restoran;
import bean.StatusPorudzbine;
import bean.TipKupca;
import dao.AdministratorDAO;
import dao.ArtikalDAO;
import dao.DostavljacDAO;
import dao.KomentarDAO;
import dao.KorisnikDAO;
import dao.KupacDAO;
import dao.MenadzerDAO;
import dao.PorudzbineDAO;
import dao.RestoranDAO;

@Path("")
public class KorisniciService {

	@Context
	ServletContext ctx;
	
	@Context
	HttpServletRequest request;
	
	public KorisniciService() {
		
	}
	
	@PostConstruct
	
	public void init() throws IOException {
		//Ovaj objekat se instancira vise puta u toku rada aplikacije
		//Incijalizacija treba da se obavi samo jednom
		if(ctx.getAttribute("kupacDAO") == null) {
			String contextPath = ctx.getRealPath("");
			ctx.setAttribute("kupacDAO", new KupacDAO(contextPath));
		}
		
		if(ctx.getAttribute("administratoriDAO") == null) {
			String contextPath = ctx.getRealPath("");
			ctx.setAttribute("administratoriDAO", new AdministratorDAO(contextPath));
		}
		
		if(ctx.getAttribute("dostavljacDAO") == null) {
			String contextPath = ctx.getRealPath("");
			ctx.setAttribute("dostavljacDAO", new DostavljacDAO(contextPath));
		}
		
		if(ctx.getAttribute("menadzerDAO") == null) {
			String contextPath = ctx.getRealPath("");
			ctx.setAttribute("menadzerDAO", new MenadzerDAO(contextPath));
		}
		if(ctx.getAttribute("korisnikDAO") == null) {
			String contextPath = ctx.getRealPath("");
			ctx.setAttribute("korisnikDAO", new KorisnikDAO(contextPath));
		}
		if(ctx.getAttribute("restoranDAO") == null) {
			String contextPath = ctx.getRealPath("");
			ctx.setAttribute("restoranDAO", new RestoranDAO(contextPath));
		}
		if(ctx.getAttribute("artikalDAO") == null) {
			String contextPath = ctx.getRealPath("");
			ctx.setAttribute("artikalDAO", new ArtikalDAO(contextPath));
		}
		if(ctx.getAttribute("porudzbinaDAO") == null) {
			String contextPath = ctx.getRealPath("");
			ctx.setAttribute("porudzbinaDAO", new PorudzbineDAO(contextPath));
		}
		if(ctx.getAttribute("komentariDAO") == null) {
			String contextPath = ctx.getRealPath("");
			ctx.setAttribute("komentariDAO", new KomentarDAO(contextPath));
		}
		System.out.println(ctx.getRealPath("")); 
	}
	
	
	@POST
	@Path("/otkaziPorudzbinu")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public boolean otkaziPorudzbinu(Porudzbina podaci, @Context HttpServletRequest request) throws IOException {
		
		long id = podaci.getId();
		double cena = podaci.getCena();
		
		KupacDAO kupacDAO = (KupacDAO) ctx.getAttribute("kupacDAO");
		Kupac kupac = (Kupac) request.getSession().getAttribute("kupac");
		
		kupacDAO.otkaziPorudzbinu(kupac.getKorisnickoIme(), id);
		
		kupac.getListaDatumaOtkazivanja().add(new Date(System.currentTimeMillis()));
		
		// PROVJERA DA LI JE KORISNIK IMAO VISE OD 5 PORUDZBINA POSLEDNIH MJESEC DANA, AKO JESTE  SUMJNIV=TRUE, A AKO NIJE SUMNJIV=FALSE

		
		 LocalDate currentDate = LocalDate.now();
		 LocalDate currentDateMinus30Days = currentDate.minusDays(30);
		 
		 System.out.println("currentDate: " + currentDate);
		 System.out.println("currentDateMinus30Days : " + currentDateMinus30Days);
		 
		 
	    ArrayList<Date> listaDatumaOtkazivanja = kupac.getListaDatumaOtkazivanja();
	    int brojacOtkazivanja = 0;
		 
		for(int i=0; i < listaDatumaOtkazivanja.size(); i++) {
			
			 //Date testDate = new Date(System.currentTimeMillis());	 
			 //LocalDate date1 = testDate.toLocalDate();
			
			Date testDate = listaDatumaOtkazivanja.get(i);
			LocalDate date1 = testDate.toLocalDate();
			
			 if (date1.isBefore(currentDateMinus30Days)) {
		          System.out.println("30 months older than current date!");
		      }else {
		    	  brojacOtkazivanja++;
		      }
		}
		
		if(brojacOtkazivanja > 5) {
			kupac.setSumnjiv(true);
		}else {
			kupac.setSumnjiv(false);
		}
		System.out.println("BROJAC OTKAZIVANJA:    "  + brojacOtkazivanja); 
		
		//------------------------------------------------------------------------------------------
		
		
		double noviBrojSakupljenihBodova = kupac.getBrojSakupljenihBodova() - ((cena/1000)*133*4);
		kupac.setBrojSakupljenihBodova(noviBrojSakupljenihBodova);
		kupacDAO.write(ctx.getRealPath(""));
		
		return true;
	}
	 
	@POST
	@Path("/promeniUPripremi")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public boolean promeniUPripremi(Porudzbina podaci, @Context HttpServletRequest request) throws IOException {
		
		long id = podaci.getId();
		
		PorudzbineDAO porudzbineDAO = (PorudzbineDAO) ctx.getAttribute("porudzbinaDAO");
		Porudzbina ta = porudzbineDAO.getPorudzbinu(id);
		ta.setStatusPorudzbine(StatusPorudzbine.U_pripremi); 
		porudzbineDAO.write(ctx.getRealPath(""));
		KupacDAO kupacDAO = (KupacDAO) ctx.getAttribute("kupacDAO");
		KorisnikDAO korisnikDAO = (KorisnikDAO) ctx.getAttribute("korisnikDAO");
		ArrayList<Kupac> kupci = (ArrayList<Kupac>) kupacDAO.getKupci();
		for(int i=0; i<kupci.size();i++) {
			for(int j=0; j<kupci.get(i).getListaPorudzbina().size();j++) {
				if(kupci.get(i).getListaPorudzbina().get(j).getId() == ta.getId()) {
					kupci.get(i).getListaPorudzbina().get(j).setStatusPorudzbine(ta.getStatusPorudzbine());
					kupacDAO.updateKupca(kupci.get(i)); 
					break;
				}
			}
		}
		Kupac odredjeni = kupacDAO.pronadjiKupca(ta.getUsernameKupca());
		Korisnik istoOdredjeni = new Korisnik(odredjeni.getKorisnickoIme(), odredjeni.getLozinka(), odredjeni.getIme(), odredjeni.getPrezime(), odredjeni.getPol(), odredjeni.getDatumRodjenja(), odredjeni.getUloga(), odredjeni.getTipKupca());
		if(odredjeni.getBrojSakupljenihBodova() >= 50 && odredjeni.getBrojSakupljenihBodova() <= 100) {
			TipKupca tp = new TipKupca(ImeTip.Bronzani, 100, 200);
			odredjeni.setTipKupca(tp);
			istoOdredjeni.setTipKupca(tp);
			kupacDAO.updateKupca(odredjeni);
			korisnikDAO.updateKorisnika(istoOdredjeni);
		}
		else if(odredjeni.getBrojSakupljenihBodova() >= 101 && odredjeni.getBrojSakupljenihBodova() <= 300) {
			TipKupca tp = new TipKupca(ImeTip.Srebreni, 200, 300);
			odredjeni.setTipKupca(tp);
			istoOdredjeni.setTipKupca(tp);
			kupacDAO.updateKupca(odredjeni); 
			korisnikDAO.updateKorisnika(istoOdredjeni);
		}
		else if(odredjeni.getBrojSakupljenihBodova() >= 301) {
			TipKupca tp = new TipKupca(ImeTip.Zlatni, 300, 0);
			odredjeni.setTipKupca(tp);
			istoOdredjeni.setTipKupca(tp);
			kupacDAO.updateKupca(odredjeni); 
			korisnikDAO.updateKorisnika(istoOdredjeni);
		}
		kupacDAO.write(ctx.getRealPath(""));
		korisnikDAO.write(ctx.getRealPath(""));
		return true;
	} 
	
	@POST
	@Path("/promeniUCekaDostavljaca")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public boolean promeniUCekaDostavljaca(Porudzbina podaci, @Context HttpServletRequest request) throws IOException {
		
		long id = podaci.getId();
		
		PorudzbineDAO porudzbineDAO = (PorudzbineDAO) ctx.getAttribute("porudzbinaDAO");
		Porudzbina ta = porudzbineDAO.getPorudzbinu(id);
		ta.setStatusPorudzbine(StatusPorudzbine.Ceka_dostavljaca);
		porudzbineDAO.write(ctx.getRealPath(""));
		KupacDAO kupacDAO = (KupacDAO) ctx.getAttribute("kupacDAO");
		ArrayList<Kupac> kupci = (ArrayList<Kupac>) kupacDAO.getKupci();
		for(int i=0; i<kupci.size();i++) {
			for(int j=0; j<kupci.get(i).getListaPorudzbina().size();j++) {
				if(kupci.get(i).getListaPorudzbina().get(j).getId() == ta.getId()) {
					kupci.get(i).getListaPorudzbina().get(j).setStatusPorudzbine(ta.getStatusPorudzbine());
					kupacDAO.updateKupca(kupci.get(i)); 
					break;
				}
			}
		}
		kupacDAO.write(ctx.getRealPath(""));
		return true;
	}
	
	@POST
	@Path("/otkazi")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public boolean otkazi(Porudzbina podaci, @Context HttpServletRequest request) throws IOException {
		
		long id = podaci.getId();
		
		PorudzbineDAO porudzbineDAO = (PorudzbineDAO) ctx.getAttribute("porudzbinaDAO");
		Porudzbina ta = porudzbineDAO.getPorudzbinu(id);
		ta.setDostavljac(null);
		porudzbineDAO.write(ctx.getRealPath(""));
		return true;
	}
	
	@POST
	@Path("/potvrdi")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public boolean potvrdi(Porudzbina podaci, @Context HttpServletRequest request) throws IOException {
		long id = podaci.getId();
		
		PorudzbineDAO porudzbineDAO = (PorudzbineDAO) ctx.getAttribute("porudzbinaDAO");
		Porudzbina ta = porudzbineDAO.getPorudzbinu(id);
		ta.setStatusPorudzbine(StatusPorudzbine.U_transportu);
		porudzbineDAO.write(ctx.getRealPath(""));
		String nickDostavljaca = ta.getDostavljac();
		DostavljacDAO dostavljacDAO = (DostavljacDAO) ctx.getAttribute("dostavljacDAO");
		Dostavljac dostavljac = dostavljacDAO.pronadjiDostavljaca(nickDostavljaca);
		//dostavljacDAO.updateDostavljaca(dostavljac);
		List<Porudzbina> porudzbine = new ArrayList<Porudzbina>();
		if(dostavljac.getListaPorudzbinaZaDostaviti() != null)
			porudzbine = dostavljac.getListaPorudzbinaZaDostaviti();
		porudzbine.add(ta);
		dostavljac.setListaPorudzbinaZaDostaviti(porudzbine);
		dostavljacDAO.updateDostavljaca(dostavljac);
		dostavljacDAO.write(ctx.getRealPath(""));
		
		return true;
	}
	
	@POST
	@Path("/potvrdiKomentar")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public boolean potvrdi(Komentar podaci, @Context HttpServletRequest request) throws IOException {
		double k = 0;
		double x = 0;
		int id = podaci.getId();
		
		KomentarDAO komentarDAO = (KomentarDAO) ctx.getAttribute("komentariDAO");
		Komentar taj = komentarDAO.getKomentar(id);
		taj.getPorudzbina().setKoment(kom.potvdjen);;
		komentarDAO.write(ctx.getRealPath(""));
		
		RestoranDAO restoranDAO = (RestoranDAO) ctx.getAttribute("restoranDAO");
		ArrayList<Restoran> restorani = restoranDAO.sviRestorani();
		for(int i=0; i<restorani.size(); i++) {
			if(restorani.get(i).getNaziv().equals(taj.getRestoran().getNaziv()) ) {
				if(restorani.get(i).getOcene() == null)
					restorani.get(i).setOcene(new ArrayList<Double>());
				restorani.get(i).getOcene().add(taj.getOcena());
				if(restorani.get(i).getOcene().size() == 1) {
					restorani.get(i).setProsecnaOcena(taj.getOcena());
					restoranDAO.write(ctx.getRealPath(""));
					return true;
				}
				for(int j=0; j<restorani.get(i).getOcene().size(); j++) {
					
					k++;
					x += restorani.get(i).getOcene().get(j);
					
				}
				restorani.get(i).setProsecnaOcena(x/k);
			}
		}
		restoranDAO.write(ctx.getRealPath(""));
		
		return true;
	}
	
	@POST
	@Path("/odbijKomentar")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public boolean odbijKomentar(Komentar podaci, @Context HttpServletRequest request) throws IOException {
		int id = podaci.getId();
		
		KomentarDAO komentarDAO = (KomentarDAO) ctx.getAttribute("komentariDAO");
		Komentar taj = komentarDAO.getKomentar(id);
		taj.getPorudzbina().setKoment(kom.odbijen);;
		komentarDAO.write(ctx.getRealPath(""));
		
		return true;
	}
	
	@POST
	@Path("/posaljiZahtev")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public boolean posaljiZahtev(Porudzbina podaci, @Context HttpServletRequest request) throws IOException {
		HttpSession session = request.getSession();
		Dostavljac dostavljac = (Dostavljac) session.getAttribute("dostavljac");
		
		long id = podaci.getId();
		
		PorudzbineDAO porudzbineDAO = (PorudzbineDAO) ctx.getAttribute("porudzbinaDAO");
		Porudzbina ta = porudzbineDAO.getPorudzbinu(id);
		if(ta.getDostavljac() != null)
			return false;
		ta.setDostavljac(dostavljac.getKorisnickoIme());
		porudzbineDAO.write(ctx.getRealPath(""));
		return true;
	}
	
	@POST
	@Path("/kreirajKomentar")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public boolean kreirajKomentar(Komentar komentar, @Context HttpServletRequest request) throws IOException {
		KomentarDAO komentarDAO = (KomentarDAO) ctx.getAttribute("komentariDAO");
		komentarDAO.createKomentar(komentar, ctx.getRealPath(""));
		PorudzbineDAO porudzbinaDAO = (PorudzbineDAO) ctx.getAttribute("porudzbinaDAO");
		Porudzbina ta = porudzbinaDAO.getPorudzbinu(komentar.getPorudzbina().getId());
		ta = komentar.getPorudzbina();
		porudzbinaDAO.write(ctx.getRealPath(""));
		KupacDAO kupacDAO = (KupacDAO) ctx.getAttribute("kupacDAO");
		Kupac kupac = (Kupac) request.getSession().getAttribute("kupac");
		for(int i=0; i<kupac.getListaPorudzbina().size();i++) {
			if(kupac.getListaPorudzbina().get(i).getId() == ta.getId()) {
				kupac.getListaPorudzbina().set(i, ta);
				break;
			}
				
		}
		kupacDAO.write(ctx.getRealPath(""));
		return true;
	}
	
	@POST
	@Path("/dostavi")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public boolean dostavi(Porudzbina podaci, @Context HttpServletRequest request) throws IOException {
		HttpSession session = request.getSession();
		Dostavljac dostavljac = (Dostavljac) session.getAttribute("dostavljac");
		List<Porudzbina> njegovePorudzbine = dostavljac.getListaPorudzbinaZaDostaviti();
		
		long id = podaci.getId();
		
		PorudzbineDAO porudzbineDAO = (PorudzbineDAO) ctx.getAttribute("porudzbinaDAO");
		Porudzbina ta = porudzbineDAO.getPorudzbinu(id);
		
		for(int i=0; i<njegovePorudzbine.size();i++) {
			if(njegovePorudzbine.get(i).getId() == ta.getId()) {
				njegovePorudzbine.get(i).setStatusPorudzbine(StatusPorudzbine.Dostavljena);
				break;
			}
		}
		DostavljacDAO dostavljacDAO = (DostavljacDAO) ctx.getAttribute("dostavljacDAO");
		dostavljacDAO.updateDostavljaca(dostavljac);
		dostavljacDAO.write(ctx.getRealPath(""));
		ta.setStatusPorudzbine(StatusPorudzbine.Dostavljena);;
		porudzbineDAO.write(ctx.getRealPath(""));
		return true;
	}
	
	@POST
	@Path("/createOrder")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public boolean createOrder(Porudzbina porudzbina, @Context HttpServletRequest request) throws IOException {
		RestoranDAO restoranDAO = (RestoranDAO) ctx.getAttribute("restoranDAO");
		Restoran taj = restoranDAO.getRestoran(porudzbina.getArtikli().get(0).getImeRestorana());
		porudzbina.setRestoran(taj);
		porudzbina.setStatusPorudzbine(StatusPorudzbine.Obrada);
		PorudzbineDAO porudzbineDAO = (PorudzbineDAO) ctx.getAttribute("porudzbinaDAO");
		KupacDAO kupacDAO = (KupacDAO) ctx.getAttribute("kupacDAO");
		Kupac kupac = (Kupac) request.getSession().getAttribute("kupac");
		porudzbina.setUsernameKupca(kupac.getKorisnickoIme());
		
		//popust
		if(porudzbina.getCena() > 300) {
			if(kupac.getTipKupca() != null) 
				porudzbina.setCena(porudzbina.getCena()-kupac.getTipKupca().getPopust());
		}
		
		// isprazni korpu
		Map<String, Integer> mp = kupac.getKorpa().getArtikalIkolicina(); 
		mp = Collections.<String, Integer>emptyMap();
		kupac.getKorpa().setArtikalIkolicina(mp);
		
		//broj_bodova = ukupna_cena_porud�bine/1000 * 133
		
		double brojilac = porudzbina.getCena();
		double imenilac = brojilac/1000;
		double poeni = imenilac*133;
		double trenutnoPoena = kupac.getBrojSakupljenihBodova();
		kupac.setBrojSakupljenihBodova(trenutnoPoena + poeni);
		porudzbina.setPunoImePrezimeKupca(kupac.getIme() + " " + kupac.getPrezime());
		porudzbina.setId(kupacDAO.generisiIdPorudzbine());
		ArrayList<Porudzbina> porudzbine = (ArrayList<Porudzbina>) kupac.getListaPorudzbina();
		if(porudzbine == null) porudzbine = new ArrayList<Porudzbina>();
		porudzbine.add(porudzbina);
		kupac.setListaPorudzbina(porudzbine);
		
		kupac.getKorpa().setCena(0);
		porudzbineDAO.createPorudzbinu(porudzbina, ctx.getRealPath(""));
		kupacDAO.write(ctx.getRealPath(""));
		
		return true;
	}
	
	@GET
	@Path("/addItemToCart")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public boolean addItemToCart(@QueryParam("name") String name, @QueryParam("quantity") int quantity, @QueryParam("flag") String flag, @QueryParam("cena") double cent, @Context HttpServletRequest request) throws IOException {
		//ako je flag yes to znaci da je funkcija pozvana iz shopping carta i onda samo treba da azuriram vrednost quantity
		//ako je flag no ona je pozvana is restaurant.js i onda dodajem quantity na njegovu staru vrednost
		//FileUsers fileUsers = (FileUsers) ctx.getAttribute("fileUsers");
		KupacDAO kupacDAO = (KupacDAO) ctx.getAttribute("kupacDAO");
		//User user = (User) request.getSession().getAttribute("user");
		Kupac kupac = (Kupac) request.getSession().getAttribute("kupac");
		
		
		//double kosta = quantity * cent;
		//kosta += kupac.getKorpa().getCena();
		
		if(kupac.getKorpa() == null) kupac.setKorpa(new Korpa(new HashMap<String, Integer>(), kupac.getKorisnickoIme(), 0)); 
		
		double kosta = quantity * cent;
		kosta += kupac.getKorpa().getCena();
		
		//Map<String, Integer> mp = user.getShoppingCart().getItemAndQuantity();
		Map<String, Integer> mp =  kupac.getKorpa().getArtikalIkolicina(); 
	
		if(mp.size() == 0) mp = new HashMap<String, Integer>();
		if(mp.containsKey(name) == false) {
			mp.put(name, quantity);
		}
		else {
			if(flag.equals("yes")) mp.put(name, quantity); // Kada je flag Yes, nije pozivano nigdje
			else mp.put(name, mp.get(name) + quantity);
		}
		
		//user.getShoppingCart().setItemAndQuantity(mp);
		kupac.getKorpa().setArtikalIkolicina(mp);
		kupac.getKorpa().setCena(kosta);
		
		//fileUsers.saveUser(user);
		kupacDAO.write(ctx.getRealPath(""));
		
		
		return true;
	}
	
	
	@GET
	@Path("/changeItemInCart")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public boolean changeItemInCart(@QueryParam("name") String name, @QueryParam("quantity") int quantity,  @QueryParam("cena") double cent, @QueryParam("oldQuantity") int oldQuantity, @Context HttpServletRequest request) throws IOException {
		//FileUsers fileUsers = (FileUsers) ctx.getAttribute("fileUsers");
		KupacDAO kupacDAO = (KupacDAO) ctx.getAttribute("kupacDAO");
		//User user = (User) request.getSession().getAttribute("user");
		Kupac kupac = (Kupac) request.getSession().getAttribute("kupac");
		
		System.out.println(quantity);
		System.out.println(oldQuantity); //  !!!!!!!! NE IZBACUJE ISPRAVNO STARU VRIJEDNOST !!!!!!!!!!!
		System.out.println(cent);
		
		
		//Map<String, Integer> mp = user.getShoppingCart().getItemAndQuantity();
		Map<String, Integer> mp =  kupac.getKorpa().getArtikalIkolicina(); 
		
		if(mp.containsKey(name)) {
			mp.put(name, quantity);
		}
		
		//user.getShoppingCart().setItemAndQuantity(mp);
		kupac.getKorpa().setArtikalIkolicina(mp);
		
		double ukupno = 0;
		double veziZa = 0; // za koliko se puta poveca cena
		double manjiZa = 0; // za koliko se puta smanji cena
		
		if( quantity >  oldQuantity ) {
			// dodajemo na ukupnu cenu korpe
			veziZa = quantity - oldQuantity;
			ukupno = veziZa * cent;
			kupac.getKorpa().setCena( kupac.getKorpa().getCena() + ukupno );
			
		}else if( quantity < oldQuantity ){
			// oduzimamo na ukupnu cenu korpe
			manjiZa = oldQuantity - quantity;
			ukupno = manjiZa * cent;
			kupac.getKorpa().setCena( kupac.getKorpa().getCena() - ukupno );
		}
		
		//fileUsers.saveUser(user);
		kupacDAO.write(ctx.getRealPath(""));
		
		
		return true;
	}
	
	
	@GET
	@Path("/blokirajKorisnika")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public boolean blokirajKorisnika(@QueryParam("username") String username) throws IOException {
		
		KupacDAO kupacDAO = (KupacDAO) ctx.getAttribute("kupacDAO");
		Kupac pronadjenKupac = kupacDAO.pronadjiKupca(username);
		
		DostavljacDAO dostavljacDAO = (DostavljacDAO) ctx.getAttribute("dostavljacDAO");
		Dostavljac pronadjenDostavljac = dostavljacDAO.pronadjiDostavljaca(username);
		
		MenadzerDAO menadzerDAO = (MenadzerDAO) ctx.getAttribute("menadzerDAO");
		Menadzer pronadjenMenadzer = menadzerDAO.pronadjiMenadzera(username);
		
		if(pronadjenKupac != null) {
			pronadjenKupac.setBlokiran(true);
			pronadjenKupac.setSumnjiv(false);
			kupacDAO.write(ctx.getRealPath(""));
			return true;
			
		}else if(pronadjenDostavljac != null) {
			pronadjenDostavljac.setBlokiran(true);
			dostavljacDAO.write(ctx.getRealPath(""));
			return true;
			
		}else if(pronadjenMenadzer != null) {
			pronadjenMenadzer.setBlokiran(true);
			menadzerDAO.write(ctx.getRealPath(""));
			return true;
		}
		
		return false;
	}
	
	@GET
	@Path("/blokirajKupca")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public boolean blokirajKupca(@QueryParam("username") String username) throws IOException {
		
		KupacDAO kupacDAO = (KupacDAO) ctx.getAttribute("kupacDAO");
		Kupac pronadjenKupac = kupacDAO.pronadjiKupca(username);
		
		if(pronadjenKupac == null) {
			return false;
		}
		
		pronadjenKupac.setBlokiran(true);
		pronadjenKupac.setSumnjiv(false);
		kupacDAO.write(ctx.getRealPath(""));

		return true;
	}
	
	
	@GET
	@Path("/removeItemFromCart")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public boolean removeItemFromCart(@QueryParam("name") String name, @QueryParam("quantity") int quantity,  @QueryParam("cena") double cent, @Context HttpServletRequest request) throws IOException {
		
		//FileUsers fileUsers = (FileUsers) ctx.getAttribute("fileUsers");
		//User user = (User) request.getSession().getAttribute("user");
		
		KupacDAO kupacDAO = (KupacDAO) ctx.getAttribute("kupacDAO");
		Kupac kupac = (Kupac) request.getSession().getAttribute("kupac");
		
		double ukupnoZaUkloniti = quantity * cent;
		
		kupac.getKorpa().setCena( kupac.getKorpa().getCena() - ukupnoZaUkloniti );
		
		//Map<String, Integer> mp = user.getShoppingCart().getItemAndQuantity();
		Map<String, Integer> mp = kupac.getKorpa().getArtikalIkolicina();

		//mp.remove(name);
		mp.remove(name);

		//fileUsers.saveUser(user);
		kupacDAO.write(ctx.getRealPath(""));

		return true;
	}
	
	@GET
	@Path("/getPorudzbinu")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Porudzbina getPorudzbinu(@QueryParam("id") Long id, @Context HttpServletRequest request) throws IOException {
		
		PorudzbineDAO porudzbinaDAO = (PorudzbineDAO) ctx.getAttribute("porudzbinaDAO");
		ArrayList<Porudzbina> porudzbine = porudzbinaDAO.getSvePorudzbine();
		for(int i=0; i<porudzbine.size(); i++) {
			if(porudzbine.get(i).getId()==id) {
				porudzbine.get(i).setKoment(kom.da);
				return porudzbine.get(i);
			}
				
		}
		return null;
	}
	
	@GET
	@Path("/getRestoran")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Restoran getRestoran(@QueryParam("id") String naziv, @Context HttpServletRequest request) throws IOException {
		RestoranDAO restoranDAO = (RestoranDAO) ctx.getAttribute("restoranDAO");
		
		return restoranDAO.getRestoran(naziv);
	}
	
	@POST
	@Path("/noviArtikal")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public boolean noviArtikal(Artikal noviArtikal) throws IOException {
		ArtikalDAO artikalDAO = (ArtikalDAO) ctx.getAttribute("artikalDAO");
		ArrayList<Artikal> artikli = artikalDAO.sviArtikli();
		if(artikalDAO.artikalPostoji(noviArtikal.getNaziv())) return false;
		artikli.add(noviArtikal);
		artikalDAO.write(ctx.getRealPath(""));
		
		return true;
	}
	
	
	@POST
	@Path("/saveArtikalImage")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public boolean saveArtikalImage(String data) throws IOException {
		ArtikalDAO artikalDAO = (ArtikalDAO) ctx.getAttribute("artikalDAO");
		Artikal artikal = artikalDAO.getPoslednjiArtikal();
		artikal.setSlika(data);
		artikalDAO.write(ctx.getRealPath(""));
		
		return true;
	}
	
	
	@POST
	@Path("/setManagersRestaurant")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public boolean setManagersRestaurant(String username) throws IOException {
		//if(username.equals("no free managers")) return false; TREBA NAMESTITI KADA JE PRAZAN BOX DA NE DODA NIKAKAV RESTORAN ODNOSNO DA NEMA SLOBODNIH MENADZERA 
	
		// Restoran pronalazimo poslednji dodat
		RestoranDAO restoranDAO = (RestoranDAO) ctx.getAttribute("restoranDAO");
		Restoran restoran =  restoranDAO.getLastRestaurant();
	
		// Pronalazimo menadzer sa tim usernamemom
		MenadzerDAO menadzerDAO = (MenadzerDAO) ctx.getAttribute("menadzerDAO");
		Menadzer menadzer = menadzerDAO.getMenadzer(username);
		
		// setuje menadzeru restoran
		menadzer.setRestoran(restoran);
		
		// ispisi
		menadzerDAO.write(ctx.getRealPath(""));
		
		return true;
	}
	
	@POST
	@Path("/registration")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response registration(Kupac kupac, @Context HttpServletRequest request) throws IOException {
		KupacDAO kupacDAO = (KupacDAO) ctx.getAttribute("kupacDAO");
		kupacDAO.createKupac(kupac, ctx.getRealPath(""));
		return Response.status(200).build();
	}
	
	@POST
	@Path("/createMenadzer")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createMenadzer(Menadzer menadzer, @Context HttpServletRequest request) throws IOException {
		MenadzerDAO menadzerDAO = (MenadzerDAO) ctx.getAttribute("menadzerDAO");
		menadzerDAO.createMenadzer(menadzer, ctx.getRealPath(""));
		return Response.status(200).build();
	}
	
	@POST
	@Path("/createDostavljac")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createDostavljac(Dostavljac dostavljac, @Context HttpServletRequest request) throws IOException {
		DostavljacDAO dostavljacDAO = (DostavljacDAO) ctx.getAttribute("dostavljacDAO");
		dostavljacDAO.createDostavljac(dostavljac, ctx.getRealPath(""));
		return Response.status(200).build();
	}
	
	@POST
	@Path("/createRestoran")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createRestoran(Restoran restoran, @Context HttpServletRequest request) throws IOException {
		RestoranDAO restoranDAO = (RestoranDAO) ctx.getAttribute("restoranDAO");
		restoranDAO.createRestoran(restoran, ctx.getRealPath(""));
		return Response.status(200).build();
	}
	
	@POST
	@Path("/editKupac")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response editKupac(Kupac kupac, @Context HttpServletRequest request) throws IOException {
		KupacDAO kupacDAO = (KupacDAO) ctx.getAttribute("kupacDAO");
		kupacDAO.updateKupca(kupac);
		kupacDAO.write(ctx.getRealPath(""));
		HttpSession session = request.getSession();
		session.setAttribute("kupac", kupac);
		return Response.status(200).build();
	}
	
	@POST
	@Path("/editAdministrator")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response editAdministrator(Administrator admin, @Context HttpServletRequest request) throws IOException {
		AdministratorDAO administratorDAO = (AdministratorDAO) ctx.getAttribute("administratoriDAO");
		administratorDAO.updateAdministratora(admin);
		administratorDAO.write(ctx.getRealPath(""));
		HttpSession session = request.getSession();
		session.setAttribute("administrator", admin);
		return Response.status(200).build();
	}
	
	@POST
	@Path("/editMenadzer")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response editMenadzer(Menadzer menadzer, @Context HttpServletRequest request) throws IOException {
		MenadzerDAO menadzerDAO = (MenadzerDAO) ctx.getAttribute("menadzerDAO");
		menadzerDAO.updateMenadzer(menadzer);
		menadzerDAO.write(ctx.getRealPath(""));
		HttpSession session = request.getSession();
		session.setAttribute("menadzer", menadzer);
		return Response.status(200).build();
	}
	
	@POST
	@Path("/editDostavljac")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response editDostavljac(Dostavljac dostavljac, @Context HttpServletRequest request) throws IOException {
		DostavljacDAO dostavljacDAO = (DostavljacDAO) ctx.getAttribute("dostavljacDAO");
		dostavljacDAO.updateDostavljac(dostavljac);
		dostavljacDAO.write(ctx.getRealPath(""));
		HttpSession session = request.getSession();
		session.setAttribute("dostavljac", dostavljac);
		return Response.status(200).build();
	}
	
	@POST
	@Path("/saveImage")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public boolean saveImage(String data) throws IOException{
		RestoranDAO restoranDAO = (RestoranDAO) ctx.getAttribute("restoranDAO");
		Restoran restoran = restoranDAO.getLastRestaurant();
		restoran.setLogoRestorana(data);
		restoranDAO.write(ctx.getRealPath("")); 
		return true;
	}
	
	@GET
	@Path("/getSession")
	@Produces(MediaType.APPLICATION_JSON)
	public Object getSession() {

		HttpSession session = request.getSession();
		Kupac kupac = (Kupac) session.getAttribute("kupac");
		Administrator admin = (Administrator) session.getAttribute("administrator");
		Dostavljac dostava = (Dostavljac) session.getAttribute("dostavljac");
		Menadzer men = (Menadzer) session.getAttribute("menadzer");
		Kupac c = new Kupac();

		if (kupac != null) {
			return kupac;
		} 
		else if(admin != null){
			return admin;
		}
		else if(dostava != null){
			return dostava;
		}
		else if(men != null){
			return men;
		}
		else {
			return c;
		}
	}
	
	@GET
	@Path("/getKorisnici")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Korisnik> getKorisnici() throws IOException {
		
		KorisnikDAO korisnikDAO = (KorisnikDAO) ctx.getAttribute("korisnikDAO");
		ArrayList<Korisnik> k = new ArrayList<Korisnik>(korisnikDAO.getKorisnici(ctx.getRealPath("")));
		return k;
	}
	

	
	@GET
	@Path("/getKomentare")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Komentar> getKomentar() throws IOException {
		
		KomentarDAO komentarDAO = (KomentarDAO) ctx.getAttribute("komentariDAO");
		ArrayList<Komentar> k = new ArrayList<Komentar>(komentarDAO.getKomentare());
		return k;
	}
	
	@GET
	@Path("/getKorpa")
	@Produces(MediaType.APPLICATION_JSON)
	public Korpa getKorpa() throws IOException {
		
		HttpSession session = request.getSession();
		Kupac kupac = (Kupac) session.getAttribute("kupac");
		return kupac.getKorpa();
	}
	
	@GET
	@Path("/getPorudzbine")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Porudzbina> getPorudzbine() throws IOException {
		
		HttpSession session = request.getSession();
		Kupac kupac = (Kupac) session.getAttribute("kupac");
		KupacDAO kupacDAO = (KupacDAO) ctx.getAttribute("kupacDAO");
		kupacDAO.write(ctx.getRealPath(""));
		return kupac.getListaPorudzbina();
	}
	
	
	@GET
	@Path("/getSvePorudzbine")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Porudzbina> getSvePorudzbine() throws IOException {
		PorudzbineDAO porudzbineDAO = (PorudzbineDAO) ctx.getAttribute("porudzbinaDAO");
		
		return porudzbineDAO.getSvePorudzbine();
	}
	
	@GET
	@Path("/getSlobodniMenadzeri")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Menadzer> getSlobodniMenadzeri() throws IOException {
		
		MenadzerDAO menadzerDAO = (MenadzerDAO) ctx.getAttribute("menadzerDAO");
		ArrayList<Menadzer> slobodni = new ArrayList<Menadzer>(menadzerDAO.slobodniMenadzeri());
		return slobodni;
	}
	
	@GET
	@Path("/getRestorani")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Restoran> getRestorani() throws IOException {
		RestoranDAO restoranDAO = (RestoranDAO) ctx.getAttribute("restoranDAO");
		ArrayList<Restoran> svi = new ArrayList<Restoran>(restoranDAO.sviRestorani());
		return svi;
	}
	
	@GET
	@Path("/getKupci")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Kupac> getKupci() throws IOException {
		KupacDAO kupacDAO = (KupacDAO) ctx.getAttribute("kupacDAO");
		ArrayList<Kupac> svi = new ArrayList<Kupac>(kupacDAO.getKupci());
		return svi;
	}
	
	@GET
	@Path("/getArtikle")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Artikal> getArtikle() throws IOException {
		ArtikalDAO artikalDAO = (ArtikalDAO) ctx.getAttribute("artikalDAO");
		ArrayList<Artikal> svi = new ArrayList<Artikal>(artikalDAO.sviArtikli());
		return svi;
	}
	
	@GET
	@Path("/loginStatusKupac")
	@Produces(MediaType.APPLICATION_JSON)
	public Kupac loginStatusKupac() {

		HttpSession session = request.getSession();
		Kupac kupac = (Kupac) session.getAttribute("kupac");

		if (kupac != null) {
			return kupac;
		} else {
			return null;
		}
	}
	
	@GET
	@Path("/loginStatusMenadzer")
	@Produces(MediaType.APPLICATION_JSON)
	public Menadzer loginStatusMenadzer() {

		HttpSession session = request.getSession();
		Menadzer menadzer = (Menadzer) session.getAttribute("menadzer");

		if (menadzer != null) {
			return menadzer;
		} else {
			return null;
		}
	}
	
	@GET
	@Path("/loginStatusDostavljac")
	@Produces(MediaType.APPLICATION_JSON)
	public Dostavljac loginStatusDostavljac() {

		HttpSession session = request.getSession();
		Dostavljac dostavljac = (Dostavljac) session.getAttribute("dostavljac");

		if (dostavljac != null) {
			return dostavljac;
		} else {
			return null;
		}
	}
	
	@GET
	@Path("/loginStatusAdministrator")
	@Produces(MediaType.APPLICATION_JSON)
	public Administrator loginStatusAdministrator() {

		HttpSession session = request.getSession();
		Administrator administrator = (Administrator) session.getAttribute("administrator");

		if (administrator != null) {
			return administrator;
		} else {
			return null;
		}
	}
	
	
	@GET
	@Path("/logout")
	@Produces(MediaType.APPLICATION_JSON)
	public Response logout() {
		
		HttpSession session = request.getSession();
		Kupac kupac = (Kupac) session.getAttribute("kupac");
		Administrator administrator = (Administrator) session.getAttribute("administrator");
		Dostavljac dostavljac = (Dostavljac) session.getAttribute("dostavljac");
		Menadzer menadzer = (Menadzer) session.getAttribute("menadzer");
		
		if(kupac != null) {
			session.invalidate();
			return Response.status(200).build();
		}else if(administrator != null) {
			session.invalidate();
			return Response.status(200).build();
		}else if(dostavljac != null) {
			session.invalidate();
			return Response.status(200).build();
		}else if(menadzer != null) {
			session.invalidate();
			return Response.status(200).build();
		}else {
			return Response.status(400).entity("Korisnik je vec izlogovan!").build();
		}
	}
	
	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response login(Korisnik userToLogIn) {

		HttpSession session = request.getSession();

		if (userToLogIn.getKorisnickoIme() == null || userToLogIn.getLozinka() == null
				|| userToLogIn.getKorisnickoIme().equals("") || userToLogIn.getLozinka().equals("")) {
			return Response.status(400).entity("Prilikom logovanja unesite korisnicko ime i lozinku!").build();
		}
	
		// -------------- LOGOVANJE ZA SVE 4 ULOGE ---------------------
		KupacDAO kupacDAO = (KupacDAO) ctx.getAttribute("kupacDAO");
		AdministratorDAO administratorDAO = (AdministratorDAO) ctx.getAttribute("administratoriDAO");
		DostavljacDAO dostavljacDAO = (DostavljacDAO) ctx.getAttribute("dostavljacDAO"); 
		MenadzerDAO menadzerDAO = (MenadzerDAO) ctx.getAttribute("menadzerDAO");
		
		
		if( kupacDAO.pronadjiKupca(userToLogIn.getKorisnickoIme())  != null ) {
			
			Kupac kupac = kupacDAO.pronadjiKupca(userToLogIn.getKorisnickoIme());
			
			if(kupac.isBlokiran()) {
				return Response.status(400).entity("Nalog je blokiran!").build();
			}
			
			if( kupac.getLozinka().equals(userToLogIn.getLozinka()) == true ) {
				session.setAttribute("kupac", kupac);
				//session.setAttribute("cart", new ArrayList<StavkaPorudzbine>()); OVDE MOZDA KORPU da ubacimo da prati stavke !!!! VIDITI JOS TO !!!!
				return Response.status(200).build();
			}else {
				return Response.status(400).entity("Pogresana lozinka!").build();
			}
		}
		else if( administratorDAO.pronadjiAdministratora(userToLogIn.getKorisnickoIme())  != null ) {
			
			Administrator administrator = administratorDAO.pronadjiAdministratora(userToLogIn.getKorisnickoIme());
			
			if( administrator.getLozinka().equals(userToLogIn.getLozinka()) == true ) {
				session.setAttribute("administrator", administrator);
				//session.setAttribute("cart", new ArrayList<StavkaPorudzbine>()); OVDE MOZDA KORPU da ubacimo da prati stavke !!!! VIDITI JOS TO !!!!
				return Response.status(200).build();
			}else {
				return Response.status(400).entity("Pogresana lozinka!").build();
			}
		}else if( dostavljacDAO.pronadjiDostavljaca(userToLogIn.getKorisnickoIme())  != null ) {
			
			Dostavljac dostavljac = dostavljacDAO.pronadjiDostavljaca(userToLogIn.getKorisnickoIme());
			
			if(dostavljac.isBlokiran()) {
				return Response.status(400).entity("Nalog je blokiran!").build();
			}
			
			if( dostavljac.getLozinka().equals(userToLogIn.getLozinka()) == true ) {
				session.setAttribute("dostavljac", dostavljac);
				//session.setAttribute("cart", new ArrayList<StavkaPorudzbine>()); OVDE MOZDA KORPU da ubacimo da prati stavke !!!! VIDITI JOS TO !!!!
				return Response.status(200).build();
			}else {
				return Response.status(400).entity("Pogresana lozinka!").build();
			}
		}else if( menadzerDAO.pronadjiMenadzera(userToLogIn.getKorisnickoIme())  != null ) {
			
			Menadzer menadzer = menadzerDAO.pronadjiMenadzera(userToLogIn.getKorisnickoIme());
			
			if(menadzer.isBlokiran()) {
				return Response.status(400).entity("Nalog je blokiran!").build();
			}
			
			if( menadzer.getLozinka().equals(userToLogIn.getLozinka()) == true ) {
				session.setAttribute("menadzer", menadzer);
				//session.setAttribute("cart", new ArrayList<StavkaPorudzbine>()); OVDE MOZDA KORPU da ubacimo da prati stavke !!!! VIDITI JOS TO !!!!
				return Response.status(200).build();
			}else {
				return Response.status(400).entity("Pogresana lozinka!").build();
			}
		}

		// PROVERA DA LI JE LOGOVANJE USPESNO ILI NIJE
		if (session.getAttribute("kupac") != null) {
			return Response.status(400).entity("Vec ste ulogovani kao kupac!").build();
		}else if(session.getAttribute("administrator") != null) {
			return Response.status(400).entity("Vec ste ulogovani kao administrator!").build();
		}else if(session.getAttribute("dostavljac") != null) {
			return Response.status(400).entity("Vec ste ulogovani kao dostavljac!").build();
		}else if(session.getAttribute("menadzer") != null) {
			return Response.status(400).entity("Vec ste ulogovani kao menadzer!").build();
		}else {
			return Response.status(400).entity("Logovanje nije uspesno!").build();
		}
	}
	
	
}
